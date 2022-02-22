package co.sideralis.core.clan.queries;

import co.com.sofka.domain.generic.Query;

import java.util.Objects;

public class TopMember implements Query {
    private String aggregateRootId;

    public String getAggregateRootId() {
        return aggregateRootId;
    }

    public void setAggregateRootId(String aggregateRootId) {
        this.aggregateRootId = Objects.requireNonNull(aggregateRootId, "aggregateRootId is requited");
    }
}
