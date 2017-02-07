package by.intexsoft.ccm.model.transfer;

import by.intexsoft.ccm.model.db.ActionType;

import java.util.Date;

/**
 * Model for provision saving
 */
public class ProvisionRequestItem {

    /**
     * Identity of subscriber
     */
    public Long subscriberId;

    /**
     * Identity of {@link by.intexsoft.ccm.model.db.Pack}
     */
    public Long packId;

    /**
     * Action from {@link by.intexsoft.ccm.model.db.ActionType}
     */
    public ActionType actionType;

    /**
     * Activation date
     */
    public Date activateDate;

    /**
     * Deactivation date
     */
    public Date deactivateDate;

    /**
     * TraceNumber for {@link by.intexsoft.ccm.model.db.PackHistory}
     */
    public Long traceNumber;
}
