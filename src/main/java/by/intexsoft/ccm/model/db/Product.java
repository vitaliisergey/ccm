package by.intexsoft.ccm.model.db;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entity of product offers
 */
@Entity
@Table
public class Product extends AbstractEntity {

    /**
     * Activation date
     */
    @Column(nullable = false, unique = true)
    public Date start;

    /**
     * Deactivation date
     */
    @Column(nullable = false, unique = true)
    public Date end;

    /**
     * Relation to another {@link Product}s
     */
    @Transient
    public List<Product> relations;
}
