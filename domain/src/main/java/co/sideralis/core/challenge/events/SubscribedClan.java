package co.sideralis.core.challenge.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.values.ClanId;

public class SubscribedClan extends DomainEvent {
    private final ClanId clanId;

    public SubscribedClan(final ClanId clanId) {
        super("core.challenge.subscribedClan");
        this.clanId = clanId;
    }

    public ClanId getClanId() {
        return clanId;
    }
}
