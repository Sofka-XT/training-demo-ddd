package co.sideralis.core.challenge.events;

import co.com.sofka.domain.generic.DomainEvent;

public class ChallengeRevoked extends DomainEvent {
    private final Boolean isRevoked;

    public ChallengeRevoked() {
        super("core.challenge.challengeRevoked");
        this.isRevoked = true;
    }

    public Boolean getRevoked() {
        return isRevoked;
    }
}
