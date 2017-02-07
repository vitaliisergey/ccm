package by.intexsoft.ccm.model.transfer;

import java.util.Date;

/**
 * Activate action model
 */
public class ActivateRequestItem {

    /**
     * Identity of {@link by.intexsoft.ccm.model.db.Pack}
     */
    public long packId;

    /**
     * Activation date
     */
    public Date activateDate;

    /**
     * Deactivation date
     */
    public Date deactivateDate;

}
