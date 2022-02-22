package co.sideralis.core.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class GroupGit implements ValueObject<GroupGit.Properties> {
    private final Integer groupId;
    private final String path;
    private final String name;

    public GroupGit(Integer groupId, String path, String name) {
        this.groupId = Objects.requireNonNull(groupId, "The group id is required");
        this.path = Objects.requireNonNull(path, "The path id is required");
        this.name = Objects.requireNonNull(name, "The name id is required");
    }

    @Override
    public Properties value() {
        return new Properties() {
            @Override
            public Integer getGroupId() {
                return groupId;
            }

            @Override
            public String getPath() {
                return path;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }


    public interface Properties {
        Integer getGroupId();

        String getPath();

        String getName();
    }
}
