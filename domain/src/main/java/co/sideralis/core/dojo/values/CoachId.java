package co.sideralis.core.dojo.values;

import co.com.sofka.domain.generic.Identity;

import java.util.Objects;

public class CoachId extends Identity {
    public CoachId() {
        super();
    }

    private CoachId(String uuid) {
        super(uuid);
    }

    public static CoachId of(String uuid) {
        Objects.requireNonNull(uuid, "The coach id is required");
        return new CoachId(uuid);
    }
}
