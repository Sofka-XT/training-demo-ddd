package co.sideralis.core.clan.queries;

import co.com.sofka.domain.generic.Query;

import java.util.Objects;

public class GetClanByName implements Query {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "the name is required");
        this.name = this.name.toLowerCase();
    }
}
