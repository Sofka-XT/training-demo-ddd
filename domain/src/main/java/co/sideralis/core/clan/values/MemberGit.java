package co.sideralis.core.clan.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class MemberGit implements ValueObject<MemberGit.Properties> {
    private final Integer memberGitId;
    private final String username;

    public MemberGit(Integer memberGitId, String username) {
        this.memberGitId = Objects.requireNonNull(memberGitId, "The member id is required");
        this.username = Objects.requireNonNull(username, "The username is required");
    }

    @Override
    public Properties value() {
        return new Properties() {
            @Override
            public Integer memberGitId() {
                return memberGitId;
            }

            @Override
            public String username() {
                return username;
            }
        };
    }

    public interface Properties {
        Integer memberGitId();

        String username();
    }
}
