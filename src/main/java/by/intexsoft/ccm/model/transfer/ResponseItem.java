package by.intexsoft.ccm.model.transfer;

/**
 * Action response
 */
public class ResponseItem {

    /**
     * Identity of {@link by.intexsoft.ccm.model.db.PackHistory}
     */
    public long id;

    /**
     * Conflict message
     */
    public String conflict;

    public ResponseItem(long id) {
        this.id = id;
    }

    public ResponseItem(String conflict) {
        this.conflict = conflict;
    }
}
