package co.sideralis.core.clan.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.values.MemberId;

public class RevokedMember extends DomainEvent {

    private final MemberId identity;

    public RevokedMember(MemberId identity) {
        super("core.clan.revokedmember");
        this.identity = identity;
    }

    public MemberId getMemberId() {
        return identity;
    }
}
