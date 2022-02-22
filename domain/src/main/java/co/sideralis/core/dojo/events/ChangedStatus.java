package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.dojo.values.Status;

public class ChangedStatus extends DomainEvent implements Incremental {
    private final Status status;

    public ChangedStatus(Status status) {
        super("core.dojo.changedstatus");

        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
