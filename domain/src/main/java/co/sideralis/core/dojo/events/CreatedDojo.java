package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.dojo.values.CoachId;
import co.sideralis.core.dojo.values.DataInfo;
import co.sideralis.core.dojo.values.Location;

public class CreatedDojo extends DomainEvent {
    private final CoachId identity;
    private final DataInfo dataInfo;
    private final Location location;

    public CreatedDojo(CoachId identity, DataInfo dataInfo, Location location) {
        super("core.dojo.createddojo");
        this.identity = identity;
        this.dataInfo = dataInfo;
        this.location = location;
    }

    public CoachId getCoachId() {
        return identity;
    }

    public DataInfo getDataInfo() {
        return dataInfo;
    }

    public Location getLocation() {
        return location;
    }
}
