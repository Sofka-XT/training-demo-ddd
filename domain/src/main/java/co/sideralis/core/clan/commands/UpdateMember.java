package co.sideralis.core.clan.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.values.Name;

public class UpdateMember implements Command {

    private final ClanId clanId;
    private final MemberId memberId;
    private final Name name;
    private final Gender gender;
    private final Boolean isOwner;

    public UpdateMember(ClanId clanId, MemberId memberId, Name name, Gender gender, boolean isOwner) {
        this.clanId = clanId;
        this.memberId = memberId;
        this.name = name;
        this.gender = gender;
        this.isOwner = isOwner;
    }

    public UpdateMember(ClanId clanId, MemberId memberId, Name name) {
        this.clanId = clanId;
        this.memberId = memberId;
        this.name = name;
        this.gender = null;
        this.isOwner = null;
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

    public MemberId getMemberId() {
        return memberId;
    }


    public ClanId getClanId() {
        return clanId;
    }

}
