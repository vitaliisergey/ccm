package by.intexsoft.ccm.repository;

import by.intexsoft.ccm.model.db.PackHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * {@link org.springframework.data.repository.Repository} for {@link PackHistory}
 */
public interface PackHistoryRepository extends PagingAndSortingRepository<PackHistory, Long> {

    /**
     * Find history for subscriber
     *
     * @param subsId
     * @param pageable
     * @return {@link Page} of {@link PackHistory}
     */
    Page<PackHistory> findBySubsId(long subsId, Pageable pageable);

    /**
     * Find identities of subscriber's history
     *
     * @param subscriberId
     * @param offset
     * @param limit
     * @return {@link List} of identities
     */
    @Query(value = "select ph.ID from Pack_history ph " +
            "left join  packs pk " +
            "ON ph.pack_id = pk.ID " +
            "where ph.subs_id = :subsId " +
            "order by pk.name ASC " +
            "limit :limit offset :offset ", nativeQuery = true)
    List<Number> find(@Param("subsId") long subscriberId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * Find history by identities with sort
     *
     * @param identities
     * @return {@link List} of {@link PackHistory}
     */
    @Query("select h from PackHistory h " +
            "left join fetch h.pack p " +
            "where h.identity in :identities " +
            "order by p.name asc")
    List<PackHistory> find(@Param("identities") List<Long> identities);
}
