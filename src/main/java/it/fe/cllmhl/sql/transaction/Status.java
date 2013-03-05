package it.fe.cllmhl.sql.transaction;

/**
 * @author cllmhl See javax.transaction.Status
 */
public enum Status {
    STATUS_ACTIVE(0),
    STATUS_COMMITTED(3),
    STATUS_ROLLEDBACK(4),
    STATUS_NO_TRANSACTION(6),
    STATUS_COMMITTING(8),
    STATUS_ROLLING_BACK(9);

    private int status;

    Status(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
