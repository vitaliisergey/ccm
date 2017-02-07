package by.intexsoft.ccm.repository;

import by.intexsoft.ccm.model.db.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link org.springframework.data.repository.Repository} for {@link Product}
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
}
