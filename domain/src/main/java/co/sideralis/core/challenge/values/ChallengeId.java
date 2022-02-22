package co.sideralis.core.challenge.values;

import co.com.sofka.domain.generic.Identity;

import java.util.Objects;

public class ChallengeId extends Identity {

    public ChallengeId() {
        super();
    }

    public ChallengeId(String id){
        super(id);
    }

    public static ChallengeId of(String uuid){
        Objects.requireNonNull(uuid, "The challenge id is required");
        return new ChallengeId(uuid);
    }
}
