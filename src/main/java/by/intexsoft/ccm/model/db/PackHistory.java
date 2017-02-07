package by.intexsoft.ccm.model.db;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.FetchType.LAZY;

/**
 * Entity of history of {@link Pack}
 */
@Entity
@Table(name = "PACK_HISTORY")
public class PackHistory extends AbstractEntity {

    /**
     * Relation to {@link Pack}
     */
    @JoinColumn(name = "PACK_ID")
    @ManyToOne(fetch = LAZY)
    public Pack pack;

    /**
     * Identity of subscriber
     */
    @Column(name = "SUBS_ID",nullable = false, unique = true)
    public long subsId;

    /**
     * Activation date
     */
    @Column(name = "START_DATE")
    public Date startDate;

    /**
     * Deactivation date
     */
    @Column(name = "END_DATE")
    public Date endDate;

    /**
     * Type of history action
     */
    @Column(nullable = false)
    @Enumerated
    @NotEmpty(message = "Type may not be empty", groups = ActionType.class)
    public ActionType action;

    /**
     * Package identity of subscriber
     */
    @Column(name = "TRACE_NUMBER",nullable = false)
    public long traceNumber;
}
