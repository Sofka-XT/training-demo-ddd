package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.dojo.values.Accomplishment;

public class AddedAccomplishmentToCoach extends DomainEvent {
    private final Accomplishment accomplishment;

    public AddedAccomplishmentToCoach(Accomplishment accomplishment) {
        super("core.dojo.addedaccomplishmenttoCoach");
        this.accomplishment = accomplishment;
    }

    public Accomplishment getAccomplishment() {
        return accomplishment;
    }
}
