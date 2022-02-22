package co.sideralis.core.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.values.MemberId;

public class AddedGitMember extends DomainEvent {
    private final Integer memberGitId;
    private final MemberId memberId;
    private final Integer groupId;
    private final String username;
    private final Boolean isOwner;

    public AddedGitMember(MemberId memberId, Integer memberGitId, String username, Integer groupId, Boolean isOwner) {
        super("core.clan.addedgitmember");
        this.memberGitId = memberGitId;
        this.groupId = groupId;
        this.username = username;
        this.memberId = memberId;
        this.isOwner = isOwner;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public Integer getMemberGitId() {
        return memberGitId;
    }

    public String getUsername() {
        return username;
    }

    public Integer getGroupId() {
        return groupId;
    }


}
