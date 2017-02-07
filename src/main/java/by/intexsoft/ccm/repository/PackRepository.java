package by.intexsoft.ccm.repository;

import by.intexsoft.ccm.model.db.Pack;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * {@link org.springframework.data.repository.Repository} for {@link Pack}
 */
public interface PackRepository extends PagingAndSortingRepository<Pack, Long> {
}
