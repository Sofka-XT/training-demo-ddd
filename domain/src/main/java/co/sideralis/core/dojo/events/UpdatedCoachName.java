package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.values.Name;

public class UpdatedCoachName extends DomainEvent implements Incremental {
    private final Name name;

    public UpdatedCoachName(Name name) {
        super("core.dojo.updatedcoachname");
        this.name = name;
    }

    public Name getName() {
        return name;
    }
}
