package co.sideralis.core.clan.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

public class AddNewMember implements Command {
    private final Name name;
    private final Email email;
    private final Gender gender;
    private final Boolean isOwner;
    private final ClanId clanId;
    private final MemberId memberId;
    private final PersonId personId;

    public AddNewMember(ClanId clanId, MemberId memberId, PersonId personId, Email email, Name name, Gender gender, Boolean isOwner) {
        this.clanId = clanId;
        this.memberId = memberId;
        this.personId = personId;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.isOwner = isOwner;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }


    public Gender getGender() {
        return gender;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public ClanId getClanId() {
        return clanId;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public PersonId getPersonId() {
        return personId;
    }
}
