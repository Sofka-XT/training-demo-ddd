package co.sideralis.core.clan.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.MemberId;

public class RevokeMember implements Command {
    private final MemberId memberId;
    private final ClanId clanId;


    public RevokeMember(ClanId clanId, MemberId memberId) {
        this.memberId = memberId;
        this.clanId = clanId;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public ClanId getClanId() {
        return clanId;
    }

}
