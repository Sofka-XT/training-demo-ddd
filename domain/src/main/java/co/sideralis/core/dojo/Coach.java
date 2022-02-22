package co.sideralis.core.dojo;

import co.com.sofka.domain.generic.Entity;
import co.sideralis.core.dojo.values.Accomplishment;
import co.sideralis.core.dojo.values.CoachId;
import co.sideralis.core.dojo.values.Specialty;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

import java.util.ArrayList;
import java.util.List;

public class Coach extends Entity<CoachId> {
    private final PersonId personId;
    private final List<Accomplishment> accomplishments;
    private final Email email;

    private Name name;
    private Specialty specialty;

    public Coach(CoachId entityId, Email email, PersonId personId, Name name, Specialty specialty) {
        super(entityId);
        this.personId = personId;
        this.email = email;
        this.name = name;
        this.specialty = specialty;
        this.accomplishments = new ArrayList<>();
    }

    protected void updateName(Name name) {
        this.name = name;
    }

    protected void updateSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    protected void addAccomplishment(Accomplishment accomplishment) {
        this.accomplishments.add(accomplishment);
    }

    protected void removeAccomplishment(Accomplishment accomplishment) {
        this.accomplishments.remove(accomplishment);
    }

    public String personId() {
        return personId.value();
    }

    public String name() {
        return name.value();
    }

    public String specialty() {
        return specialty.value();
    }

    public List<Accomplishment> accomplishments() {
        return accomplishments;
    }

    public String email() {
        return email.value();
    }

}
