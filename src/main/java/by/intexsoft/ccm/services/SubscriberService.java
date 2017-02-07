package by.intexsoft.ccm.services;

import by.intexsoft.ccm.exception.ActionException;
import by.intexsoft.ccm.model.db.Pack;


import java.util.Date;

/**
 * Service for work with subscriber
 */
public interface SubscriberService {

    /**
     * Activation {@link Pack} for subscriber
     *
     * @param subscriberId
     * @param packId
     * @param activateDate
     * @param deactivateDate
     * @return Activation result
     * @throws ActionException when activate fails
     */
    long activate(long subscriberId, long packId, Date activateDate, Date deactivateDate) throws ActionException;

    /**
     * Deactivate {@link Pack} for subscriber
     *
     * @param subscriberId
     * @param packId
     * @param traceNumber
     * @param deactivateDate
     * @return Deactivation result
     * @throws ActionException when deactivate fails
     */
    long deactivate(long subscriberId, long packId, long traceNumber, Date deactivateDate) throws ActionException;
}
