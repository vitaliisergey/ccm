package by.intexsoft.ccm.services.impl;

import by.intexsoft.ccm.exception.ActionException;
import by.intexsoft.ccm.model.db.Product;
import by.intexsoft.ccm.repository.MapperRepository;
import by.intexsoft.ccm.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Implementation {@link SubscriberService}
 */
@Service
public class SubscriberServiceImpl implements SubscriberService {

    @Autowired
    private MapperRepository mapperRepository;

    @Override
    public long activate(long subscriberId, long packId, Date activateDate, Date deactivateDate) throws ActionException {
        Product product = mapperRepository.find(packId).product;
        if (product.start.after(activateDate) || product.end.before(deactivateDate) || product.end.equals(deactivateDate)) {
            throw new ActionException();
        }
        return -1;
    }

    @Override
    public long deactivate(long subscriberId, long packId, long traceNumber, Date deactivateDate) throws ActionException {
        Product product = mapperRepository.find(packId).product;
        if (product.end.before(deactivateDate) || product.end.equals(deactivateDate)) {
            throw new ActionException();
        }
        return -1;
    }
}
