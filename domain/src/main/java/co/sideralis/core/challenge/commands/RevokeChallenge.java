package co.sideralis.core.challenge.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.challenge.values.ChallengeId;

public class RevokeChallenge implements Command {
    private final ChallengeId challengeId;

    public RevokeChallenge(ChallengeId challengeId) {
        this.challengeId = challengeId;
    }

    public ChallengeId getChallengeId() {
        return challengeId;
    }
}
