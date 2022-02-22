package co.sideralis.core.clan.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.values.Name;

public class UpdateClan implements Command {
    private final ClanId clanId;

    private final Name name;

    public UpdateClan(ClanId clanId, Name name) {
        this.clanId = clanId;
        this.name = name;
    }


    public ClanId getClanId() {
        return clanId;
    }

    public Name getName() {
        return name;
    }


}
