package co.sideralis.core.clan.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

public class AddedMember extends DomainEvent implements Incremental {

    private final MemberId memberId;
    private final PersonId personId;
    private final Email email;
    private final Name name;
    private final Gender gender;
    private final Boolean isOwner;

    public AddedMember(MemberId memberId, PersonId personId, Email email, Name name, Gender gender, Boolean isOwner) {
        super("core.clan.addedmember");
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.personId = personId;
        this.gender = gender;
        this.isOwner = isOwner;
    }

    public AddedMember(MemberId memberId, PersonId personId, Email email, Name name, Gender gender) {
        super("core.clan.addedmember");
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.personId = personId;
        this.gender = gender;
        this.isOwner = false;
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Boolean isOwner() {
        return isOwner;
    }

    public PersonId getPersonId() {
        return personId;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public Email getEmail() {
        return email;
    }
}
