package co.sideralis.core.values;

import co.com.sofka.domain.generic.Identity;

import java.util.Objects;

public class PersonId extends Identity {
    public PersonId() {
        super();
    }

    private PersonId(String uuid) {
        super(uuid);
    }

    public static PersonId of(String uuid) {
        Objects.requireNonNull(uuid, "The person id is required");
        return new PersonId(uuid);
    }
}
