package co.sideralis.core.dojo.values;

import co.com.sofka.domain.generic.Identity;

import java.util.Objects;

public class DojoId extends Identity {
    public DojoId() {
        super();
    }

    private DojoId(String uuid) {
        super(uuid);
    }

    public static DojoId of(String uuid) {
        Objects.requireNonNull(uuid, "The dojo id is required");
        return new DojoId(uuid);
    }
}
