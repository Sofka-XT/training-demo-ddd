package co.sideralis.core.dojo.queries;

import co.com.sofka.domain.generic.Query;

public class GetAllDojoOpen implements Query {
    private final String status;

    public GetAllDojoOpen() {
        this.status = "OPEN";
    }

    public String getStatus() {
        return status;
    }
}
