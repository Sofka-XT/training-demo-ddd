package co.sideralis.core.challenge.values;

import co.com.sofka.domain.generic.Identity;

import java.util.Objects;

public class KataId extends Identity {

    public KataId() {
        super();
    }

    public KataId(String id){
        super(id);
    }

    public static KataId of(String uuid){
        Objects.requireNonNull(uuid, "The kata id is required");
        return new KataId(uuid);
    }
}
