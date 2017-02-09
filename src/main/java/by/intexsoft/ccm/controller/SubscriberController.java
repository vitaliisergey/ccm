package by.intexsoft.ccm.controller;

import by.intexsoft.ccm.exception.ActionException;
import by.intexsoft.ccm.model.transfer.ActivateRequestItem;
import by.intexsoft.ccm.model.transfer.DeactivateRequestItem;
import by.intexsoft.ccm.model.transfer.PackHistoryItem;
import by.intexsoft.ccm.model.transfer.ResponseItem;
import by.intexsoft.ccm.services.PackHistoryService;
import by.intexsoft.ccm.services.SubscriberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST Controller for subscriber managing
 */
@RestController
@RequestMapping(value = "/subscriber", produces = APPLICATION_JSON_VALUE)
public class SubscriberController
{

	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberController.class);
	private SubscriberService subscriberService;
	private PackHistoryService packHistoryService;

	@Autowired
	private SubscriberController(SubscriberService subscriberService, PackHistoryService packHistoryService)
	{
		this.subscriberService = subscriberService;
		this.packHistoryService = packHistoryService;
	}

	/**
	 * Get all packs of subscriber.
	 * @param subscriberId
	 * 		id of subscriber
	 * @param limit
	 * 		max count of packs
	 * @param offset
	 * 		for pagination
	 * @return List of {@link by.intexsoft.ccm.model.transfer.PackHistoryItem}
	 */
	@RequestMapping(value = "/{id}/packs", method = RequestMethod.GET)
	@ResponseBody
	private List<PackHistoryItem> load(@PathVariable("id") long subscriberId,
									   @RequestParam(value = "limit", defaultValue = "9999") int limit,
									   @RequestParam(value = "offset", defaultValue = "0") int offset)
	{
		LOGGER.info("Load subscriber packs. Subscriber id: {}. Limit: {}. Offset: {}", subscriberId, limit, offset);
		return packHistoryService.findHistoryItems(subscriberId, offset, limit);
	}

	/**
	 * Connect pack to subscriber
	 * @param subscriberId
	 * 		id of subscriber
	 * @return history id
	 */
	@RequestMapping(value = "/{id}/packs/activate", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
	@ResponseBody
	private ResponseItem activate(@PathVariable("id") long subscriberId, @RequestBody ActivateRequestItem body)
	{
		LOGGER.info("Activate subscriber packs. Subscriber id: {}", subscriberId);
		try
		{
			return new ResponseItem(
					subscriberService.activate(subscriberId, body.packId, body.activateDate, body.deactivateDate));
		}
		catch (ActionException e)
		{
			return new ResponseItem("Подключение\tпакета\tневозможно");
		}
	}

	/**
	 * Disconnect pack to subscriber
	 * @param subscriberId
	 * 		id of subscriber
	 * @return history id
	 */
	@RequestMapping(value = "/{id}/packs/deactivate", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
	@ResponseBody
	private ResponseItem deactivate(@PathVariable("id") long subscriberId, @RequestBody DeactivateRequestItem body)
	{
		LOGGER.info("Deactivate subscriber packs. Subscriber id: {}", subscriberId);
		try
		{
			return new ResponseItem(
					subscriberService.deactivate(subscriberId, body.packId, body.traceNumber, body.deactivateDate));
		}
		catch (ActionException e)
		{
			return new ResponseItem("Отключение\tпакета\tневозможно");
		}

	}
}
