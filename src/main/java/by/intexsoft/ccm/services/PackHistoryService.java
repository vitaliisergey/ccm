package by.intexsoft.ccm.services;

import by.intexsoft.ccm.model.db.PackHistory;
import by.intexsoft.ccm.model.transfer.PackHistoryItem;

import java.util.List;

/**
 * Service for history of packs
 */
public interface PackHistoryService {

    /**
     * Create history
     *
     * @param packHistory
     * @return {@link PackHistory}
     */
    PackHistory create(PackHistory packHistory);

    /**
     * Find history by subscriber
     *
     * @param subscriberId
     * @param offset
     * @param limit
     * @return {@link List} of {@link PackHistory}
     */
    List<PackHistory> find(long subscriberId, int offset, int limit);

    /**
     * Find history items for subscriber
     *
     * @param subscriberId
     * @param offset
     * @param limit
     * @return {@link List} of {@link PackHistoryItem}
     */
    List<PackHistoryItem> findHistoryItems(long subscriberId, int offset, int limit);
}
