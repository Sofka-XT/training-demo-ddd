package co.sideralis.core.clan.values;

import co.com.sofka.domain.generic.Identity;

import java.util.Objects;

public class ClanId extends Identity {
    public ClanId() {
        super();
    }

    private ClanId(String id) {
        super(id);
    }

    public static ClanId of(String uuid) {
        Objects.requireNonNull(uuid, "The clan id is required");
        return new ClanId(uuid);
    }
}
