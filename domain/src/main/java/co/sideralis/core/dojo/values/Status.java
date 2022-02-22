package co.sideralis.core.dojo.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Status implements ValueObject<String> {
    private final Status.Type type;

    public Status(Status.Type type) {
        this.type = Objects.requireNonNull(type, "The status is required");
    }

    public String value() {
        return type.name();
    }

    public enum Type {
        CLOSED, CANCELLED, OPEN
    }

}
