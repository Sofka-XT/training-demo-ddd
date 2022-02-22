package co.sideralis.core.clan.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.values.Name;

public class UpdatedMember extends DomainEvent implements Incremental {

    private final MemberId memberIdentity;
    private final Name name;

    public UpdatedMember(MemberId memberIdentity, Name name) {
        super("core.clan.updatedmember");

        this.memberIdentity = memberIdentity;
        this.name = name;
    }

    public MemberId getMemberId() {
        return memberIdentity;
    }

    public Name getName() {
        return name;
    }
}
