package co.sideralis.core.events;

import co.com.sofka.domain.generic.DomainEvent;

public class CreatedGitGroup extends DomainEvent {
    private final Integer groupId;
    private final String path;
    private final String name;

    public CreatedGitGroup(Integer groupId, String path, String name) {
        super("core.clan.createdgitgroup");
        this.groupId = groupId;
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public Integer getGroupId() {
        return groupId;
    }

}
