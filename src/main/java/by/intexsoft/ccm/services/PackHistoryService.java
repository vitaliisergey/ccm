package by.intexsoft.ccm.services;

import by.intexsoft.ccm.model.db.Pack;
import by.intexsoft.ccm.model.db.PackHistory;
import by.intexsoft.ccm.model.transfer.PackHistoryItem;
import by.intexsoft.ccm.model.transfer.ProvisionRequestItem;

import java.util.List;

/**
 * Service for history of packs
 */
public interface PackHistoryService
{

	/**
	 * Create history
	 * @return {@link PackHistory}
	 */
	PackHistory create(PackHistory packHistory);

	/**
	 * Find history by subscriber
	 * @return {@link List} of {@link PackHistory}
	 */
	List<PackHistory> find(long subscriberId, int offset, int limit);

	/**
	 * Find history items for subscriber
	 * @return {@link List} of {@link PackHistoryItem}
	 */
	List<PackHistoryItem> findHistoryItems(long subscriberId, int offset, int limit);

	/**
	 * Find last {@link PackHistory} of subscriber's pack
	 * @param subscriberId
	 * 		subscriber identity
	 * @param pack
	 * 		{@link by.intexsoft.ccm.model.db.Pack} identity
	 * @return {@link PackHistory}
	 */
	PackHistory findLastHistory(long subscriberId, Pack pack);
}
