package co.sideralis.core.challenge.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.values.ClanId;

public class UnsubscribedClan extends DomainEvent {
    private final ClanId clanId;

    public UnsubscribedClan(final ClanId clanId) {
        super("core.challenge.unsubscribedClan");
        this.clanId = clanId;
    }

    public ClanId getClanId() {
        return clanId;
    }
}
