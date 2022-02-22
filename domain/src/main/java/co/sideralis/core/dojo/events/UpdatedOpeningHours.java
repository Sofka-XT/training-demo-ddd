package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.dojo.values.OpeningHours;

public class UpdatedOpeningHours extends DomainEvent implements Incremental {
    private final OpeningHours newOpeningHours;

    public UpdatedOpeningHours(OpeningHours newOpeningHours) {
        super("core.dojo.updatedopeninghours");
        this.newOpeningHours = newOpeningHours;
    }

    public OpeningHours getNewOpeningHours() {
        return newOpeningHours;
    }
}
