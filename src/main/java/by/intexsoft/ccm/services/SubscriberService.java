package by.intexsoft.ccm.services;

import by.intexsoft.ccm.exception.ActionException;
import by.intexsoft.ccm.model.db.Pack;

import java.util.Date;

/**
 * Service for work with subscriber
 */
public interface SubscriberService
{

	/**
	 * Activation {@link Pack} for subscriber.
	 * Also it activates all dependent {@link Pack}s and
	 * deactivates all alternative {@link Pack}s
	 * @param subscriberId
	 * 		subscriber identity of activated {@link Pack}
	 * @param packId
	 * 		activated {@link Pack} identity
	 * @param activateDate
	 * 		date of activation of {@link Pack}
	 * @param deactivateDate
	 * 		date of deactivation of {@link Pack}
	 * @return identity of {@link by.intexsoft.ccm.model.db.PackHistory} about activation
	 * @throws ActionException
	 * 		when activate fails
	 */
	long activate(long subscriberId, long packId, Date activateDate, Date deactivateDate) throws ActionException;

	/**
	 * Deactivate {@link Pack} for subscriber.
	 * Also it deactivates all dependent {@link Pack}s
	 * @param subscriberId
	 * 		subscriber identity of activated {@link Pack}
	 * @param packId
	 * 		deactivated {@link Pack} identity
	 * @param traceNumber
	 * 		identity of active {@link Pack}. It is possible to activate one {@link Pack} many times
	 * @param deactivateDate
	 * 		date of deactivation of {@link Pack}
	 * @return identity of {@link by.intexsoft.ccm.model.db.PackHistory} about deactivation
	 * @throws ActionException
	 * 		when deactivate fails
	 */
	long deactivate(long subscriberId, long packId, long traceNumber, Date deactivateDate) throws ActionException;
}
