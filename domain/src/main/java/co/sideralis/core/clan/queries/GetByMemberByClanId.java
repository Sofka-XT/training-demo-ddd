package co.sideralis.core.clan.queries;

import co.com.sofka.domain.generic.Query;

import java.util.Objects;

public class GetByMemberByClanId implements Query {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "Identity is required");
    }
}
