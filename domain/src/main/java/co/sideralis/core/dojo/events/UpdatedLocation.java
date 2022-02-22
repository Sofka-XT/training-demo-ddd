package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.dojo.values.Location;

public class UpdatedLocation extends DomainEvent implements Incremental {

    private final Location location;

    public UpdatedLocation(Location location) {
        super("core.dojo.updatedLocation");
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
