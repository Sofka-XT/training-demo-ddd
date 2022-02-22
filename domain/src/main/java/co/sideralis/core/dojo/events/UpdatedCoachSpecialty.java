package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.dojo.values.Specialty;

public class UpdatedCoachSpecialty extends DomainEvent implements Incremental {
    private final Specialty specialty;

    public UpdatedCoachSpecialty(Specialty specialty) {
        super("core.dojo.updatedcoachspecialty");
        this.specialty = specialty;
    }

    public Specialty getSpecialty() {
        return specialty;
    }
}
