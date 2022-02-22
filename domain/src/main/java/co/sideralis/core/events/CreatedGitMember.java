package co.sideralis.core.events;

import co.com.sofka.domain.generic.DomainEvent;

public class CreatedGitMember extends DomainEvent {
    private final Integer memberGitId;
    private final String name;
    private final String username;
    private final String email;

    public CreatedGitMember(Integer memberGitId, String name, String username, String email) {
        super("core.clan.createdgitmember");
        this.memberGitId = memberGitId;
        this.name = name;
        this.username = username;
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Integer getMemberGitId() {
        return memberGitId;
    }

    public String getName() {
        return name;
    }
}
