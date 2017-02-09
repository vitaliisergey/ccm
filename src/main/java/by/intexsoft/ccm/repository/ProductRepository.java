package by.intexsoft.ccm.repository;

import by.intexsoft.ccm.model.db.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * {@link org.springframework.data.repository.Repository} for {@link Product}
 */
public interface ProductRepository extends CrudRepository<Product, Long>
{

	/**
	 * Find identities of relative products by relation type
	 * @return {@link List} of identities
	 */
	@Query(value = "select r.rels_to " + "from product p " + "inner join product_rels r " + "on p.id = r.parent_id " +
			"where p.id = :productId AND r.rels_type = :rels_type", nativeQuery = true)
	List<Number> findRelativeProductByType(@Param("productId") long productId, @Param("rels_type") int type);
}
