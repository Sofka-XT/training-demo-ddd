package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.dojo.values.CoachId;
import co.sideralis.core.dojo.values.Specialty;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

public class AssociatedCoach extends DomainEvent implements Incremental {

    private final CoachId coachId;
    private final PersonId personId;
    private final Name couchName;
    private final Specialty specialty;
    private final Email email;

    public AssociatedCoach(CoachId coachId, Email email, PersonId personId, Name couchName, Specialty specialty) {
        super("core.dojo.associatedcoach");
        this.coachId = coachId;
        this.personId = personId;
        this.couchName = couchName;
        this.specialty = specialty;
        this.email = email;
    }

    public CoachId getCoachId() {
        return coachId;
    }

    public PersonId getPersonId() {
        return personId;
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
