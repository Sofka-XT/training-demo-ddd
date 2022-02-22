package co.sideralis.core.clan.values;

import co.com.sofka.domain.generic.Identity;

import java.util.Objects;


public class MemberId extends Identity {
    public MemberId() {
        super();
    }

    private MemberId(String uuid) {
        super(uuid);
    }

    public static MemberId of(String uuid) {
        Objects.requireNonNull(uuid, "The member id is required");
        return new MemberId(uuid);
    }
}
