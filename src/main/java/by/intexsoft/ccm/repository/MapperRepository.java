package by.intexsoft.ccm.repository;

import by.intexsoft.ccm.model.db.Mapper;
import by.intexsoft.ccm.model.db.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * {@link org.springframework.data.repository.Repository} for {@link Mapper}
 */
public interface MapperRepository extends PagingAndSortingRepository<Mapper, Long> {
    /**
     * Find mapped {@link Product}
     */
    @Query("select m from Mapper m " +
            "left join m.pack pc  " +
            "left join fetch m.product p  " +
            "where pc.identity = :pack")
    Mapper find(@Param("pack") long identity);
}
