package co.sideralis.core.dojo.queries;

import co.com.sofka.domain.generic.Query;

import java.util.Objects;

public class GetDojoById implements Query {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "The id is required");
    }
}
