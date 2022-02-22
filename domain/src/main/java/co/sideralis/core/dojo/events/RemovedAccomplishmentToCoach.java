package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.dojo.values.Accomplishment;

public class RemovedAccomplishmentToCoach extends DomainEvent {
    private final Accomplishment accomplishment;

    public RemovedAccomplishmentToCoach(Accomplishment accomplishment) {
        super("core.dojo.removedaccomplishmenttocoach");
        this.accomplishment = accomplishment;
    }

    public Accomplishment getAccomplishment() {
        return accomplishment;
    }
}
