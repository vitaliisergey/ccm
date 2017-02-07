package by.intexsoft.ccm.model.transfer;

import java.util.Date;

/**
 * Deactivate action model
 */
public class DeactivateRequestItem {

    /**
     * Identity of {@link by.intexsoft.ccm.model.db.Pack}
     */
    public long packId;

    /**
     * TraceNumber for {@link by.intexsoft.ccm.model.db.PackHistory}
     */
    public long traceNumber;

    /**
     * Deactivation date
     */
    public Date deactivateDate;
}
