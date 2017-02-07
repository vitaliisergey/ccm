package by.intexsoft.ccm.model.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ZERO;

/**
 * Base entity that should be extended by all the entities in the application.
 */
@MappedSuperclass
public abstract class AbstractEntity {
    private static final int HASH_CODE_START = 17;
    private static final int HASH_CODE_MULTIPLIER = 31;
    /**
     * Identifier of the entity.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    public long identity;

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) obj;
        return LONG_ZERO == this.identity ? false : this.identity == that.identity;
    }

    @Override
    public int hashCode() {
        int hashCode = HASH_CODE_START;
        hashCode += LONG_ZERO == identity ? LONG_ZERO : Objects.hashCode(identity) * HASH_CODE_MULTIPLIER;
        return hashCode;
    }
}
