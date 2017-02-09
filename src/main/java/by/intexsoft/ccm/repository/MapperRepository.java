package by.intexsoft.ccm.repository;

import by.intexsoft.ccm.model.db.Mapper;
import by.intexsoft.ccm.model.db.Pack;
import by.intexsoft.ccm.model.db.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * {@link org.springframework.data.repository.Repository} for {@link Mapper}
 */
public interface MapperRepository extends PagingAndSortingRepository<Mapper, Long>
{
	/**
	 * Find mapped {@link Product}
	 */
	@Query("select m from Mapper m " + "left join fetch m.pack pc  " + "left join fetch m.product p  " +
			"where pc.identity = :pack")
	Mapper find(@Param("pack") long identity);

	/**
	 * Find {@link Pack}s by {@link List} of identities
	 * @return {@link List} of {@link Pack}
	 */
	@Query("select pc from Mapper m " + "left join m.pack pc " + "left join m.product p " + "where p.identity in :products")
	List<Pack> findPacksByProduct(@Param("products") List<Long> identities);
}
