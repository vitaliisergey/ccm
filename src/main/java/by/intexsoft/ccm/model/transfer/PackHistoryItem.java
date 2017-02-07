package by.intexsoft.ccm.model.transfer;

import by.intexsoft.ccm.model.db.ActionType;

import java.util.Date;

/**
 * Packages of subscriber response
 */
public class PackHistoryItem {

    /**
     * Identity of {@link by.intexsoft.ccm.model.db.Pack}
     */
    public long packId;

    /**
     * Name of {@link by.intexsoft.ccm.model.db.Pack}
     */
    public String name;

    /**
     * Activation Date
     */
    public Date start;

    /**
     * Deactivation Date
     */
    public Date end;

    /**
     * Action from {@link by.intexsoft.ccm.model.db.ActionType}
     */
    public ActionType type;
}
