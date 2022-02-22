package co.sideralis.core.clan.events;

import co.com.sofka.domain.generic.DomainEvent;

public class RemoveGitMember extends DomainEvent {
    private final Integer groupId;
    private final Integer memberGitId;

    public RemoveGitMember(Integer groupId, Integer memberGitId) {
        super("core.clan.removegitmember");
        this.groupId = groupId;
        this.memberGitId = memberGitId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getMemberGitId() {
        return memberGitId;
    }
}
