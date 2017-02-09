package by.intexsoft.ccm.services.impl;

import by.intexsoft.ccm.exception.ActionException;
import by.intexsoft.ccm.model.db.*;
import by.intexsoft.ccm.repository.MapperRepository;
import by.intexsoft.ccm.repository.PackHistoryRepository;
import by.intexsoft.ccm.repository.ProductRepository;
import by.intexsoft.ccm.services.PackHistoryService;
import by.intexsoft.ccm.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation {@link SubscriberService}
 */
@Service
@Transactional
public class SubscriberServiceImpl implements SubscriberService
{

	private static final long FIRST_TRACE_NUMBER = 100;

	private MapperRepository mapperRepository;
	private ProductRepository productRepository;
	private PackHistoryRepository packHistoryRepository;
	private PackHistoryService packHistoryService;

	private Date deactivateDate;

	@Autowired
	public SubscriberServiceImpl(MapperRepository mapperRepository, ProductRepository productRepository,
								 PackHistoryRepository packHistoryRepository, PackHistoryService packHistoryService)
	{
		this.mapperRepository = mapperRepository;
		this.productRepository = productRepository;
		this.packHistoryRepository = packHistoryRepository;
		this.packHistoryService = packHistoryService;
	}

	@PostConstruct
	private void post()
	{
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(2999, Calendar.DECEMBER, 31, 23, 59, 59);
		deactivateDate = calendar.getTime();
	}

	@Override
	public long activate(long subscriberId, long packId, Date activateDate, Date deactivateDate) throws ActionException
	{
		activateDate = activateDate == null ? new Date() : activateDate;
		deactivateDate = deactivateDate == null ? this.deactivateDate : deactivateDate;
		Mapper mapper = mapperRepository.find(packId);
		Product product = mapper.product;
		Pack pack = mapper.pack;
		if (product.start.after(activateDate) || product.end.before(deactivateDate) || product.end.equals(deactivateDate))
		{
			throw new ActionException();
		}
		activateDependsPacks(subscriberId, activateDate, deactivateDate, product.identity);
		deactivateAlternativePacks(subscriberId, deactivateDate, product.identity);
		PackHistory provision = createProvision(subscriberId, activateDate, deactivateDate, pack);
		return packHistoryRepository.save(provision).identity;
	}

	private PackHistory createProvision(long subscriberId, Date activateDate, Date deactivateDate, Pack pack)
	{
		PackHistory result = new PackHistory();
		result.startDate = activateDate;
		result.endDate = deactivateDate;
		result.subsId = subscriberId;
		result.action = ActionType.ACTIVATE;
		result.pack = pack;
		PackHistory history = packHistoryService.findLastHistory(subscriberId, pack);
		result.traceNumber = history == null ? FIRST_TRACE_NUMBER : history.traceNumber + 1;
		return result;
	}


	@Override
	public long deactivate(final long subscriberId, final long packId, final long traceNumber, final Date endDate)
			throws ActionException
	{
		final Date deactivateDate = endDate == null ? new Date() : endDate;
		Mapper mapper = mapperRepository.find(packId);
		Product product = mapper.product;
		if (product.end.before(deactivateDate) || product.end.equals(deactivateDate))
		{
			throw new ActionException();
		}
		Pack pack = mapper.pack;
		PackHistory history = packHistoryRepository.findBySubsIdAndPackAndTraceNumber(subscriberId, pack, traceNumber);
		if (history == null)
		{
			throw new ActionException();
		}
		deactivateDependsPacks(subscriberId, deactivateDate, product.identity);
		history.endDate = deactivateDate;
		history.action = ActionType.DEACTIVATE;
		return packHistoryRepository.save(history).identity;
	}


	private void activateDependsPacks(long subscriberId, Date activateDate, Date deactivateDate, long productId)
			throws ActionException
	{
		List<Number> dependsProductsIds = productRepository.findRelativeProductByType(productId, RelsType.DEPENDS.ordinal());
		if (dependsProductsIds.isEmpty())
		{
			return;
		}
		List<Long> dependsIds = dependsProductsIds.stream().map(Number::longValue).collect(Collectors.toList());
		List<Pack> dependsPacks = mapperRepository.findPacksByProduct(dependsIds);
		for (Pack pack : dependsPacks)
		{
			PackHistory dependsHistory = packHistoryService.findLastHistory(subscriberId, pack);
			if (dependsHistory == null || dependsHistory.action == ActionType.DEACTIVATE)
			{
				activate(subscriberId, pack.identity, activateDate, deactivateDate);
			}
		}

	}

	private void deactivateDependsPacks(long subscriberId, Date deactivateDate, long productId) throws ActionException
	{
		List<Number> dependsProductsIds = productRepository.findRelativeProductByType(productId, RelsType.DEPENDS.ordinal());
		if (dependsProductsIds.isEmpty())
		{
			return;
		}
		List<Long> depends = dependsProductsIds.stream().map(Number::longValue).collect(Collectors.toList());
		List<Pack> dependsPacks = mapperRepository.findPacksByProduct(depends);
		for (Pack dependPack : dependsPacks)
		{
			PackHistory dependsHistory = packHistoryService.findLastHistory(subscriberId, dependPack);
			if (dependsHistory != null && dependsHistory.action != ActionType.DEACTIVATE)
			{
				deactivate(subscriberId, dependPack.identity, dependsHistory.traceNumber, deactivateDate);
			}
		}

	}

	private void deactivateAlternativePacks(long subscriberId, Date deactivateDate, long productId) throws ActionException
	{
		List<Number> alternativeProductsIds =
				productRepository.findRelativeProductByType(productId, RelsType.MUTUAL_EXCLUSION.ordinal());
		if (alternativeProductsIds.isEmpty())
		{
			return;
		}
		List<Long> alternativeIds = alternativeProductsIds.stream().map(Number::longValue).collect(Collectors.toList());
		List<Pack> alternativePacks = mapperRepository.findPacksByProduct(alternativeIds);
		for (Pack alternativePack : alternativePacks)
		{
			PackHistory alternativeHistory = packHistoryService.findLastHistory(subscriberId, alternativePack);
			if (alternativeHistory != null && alternativeHistory.action == ActionType.ACTIVATE)
			{
				deactivate(subscriberId, alternativePack.identity, alternativeHistory.traceNumber, deactivateDate);
			}
		}
	}
}
