package co.sideralis.core.dojo.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.dojo.values.*;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

public class CreateDojo implements Command {
    private final DojoId entityId;
    private final CoachId coachId;
    private final PersonId personId;
    private final DataInfo dataInfo;
    private final Location location;
    private final Name couchName;
    private final Email email;
    private final Specialty specialty;

    public CreateDojo(DojoId entityId, CoachId coachId, Email email, PersonId personId, DataInfo dataInfo, Location location, Name couchName, Specialty specialty) {
        this.entityId = entityId;
        this.coachId = coachId;
        this.personId = personId;
        this.email = email;
        this.dataInfo = dataInfo;
        this.location = location;
        this.couchName = couchName;
        this.specialty = specialty;
    }

    public DojoId getEntityId() {
        return entityId;
    }

    public CoachId getCoachId() {
        return coachId;
    }

    public PersonId getPersonId() {
        return personId;
    }

    public DataInfo getDataInfo() {
        return dataInfo;
    }

    public Location getLocation() {
        return location;
    }

    public Name getCouchName() {
        return couchName;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public Email getEmail() {
        return email;
    }

}
