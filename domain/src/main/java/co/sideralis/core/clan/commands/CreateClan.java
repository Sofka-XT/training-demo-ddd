package co.sideralis.core.clan.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.values.Color;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

public class CreateClan implements Command {
    private final Name name;


    private final Color color;
    private final Email email;
    private final ClanId id;
    private final Name memberName;
    private final Gender memberGender;
    private final PersonId personId;

    public CreateClan(ClanId id, Name name, Color color, Name memberName, Email email, Gender memberGender, PersonId personId) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.memberName = memberName;
        this.color = color;
        this.memberGender = memberGender;
        this.personId = personId;
    }

    public Email getEmail() {
        return email;
    }

    public Name getMemberName() {
        return memberName;
    }

    public Gender getMemberGender() {
        return memberGender;
    }

    public PersonId getPersonId() {
        return personId;
    }

    public Name getName() {
        return name;
    }

    public ClanId getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

}
