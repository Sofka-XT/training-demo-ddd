package co.sideralis.core.challenge.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.values.Name;

public class UpdateChallengeName implements Command {
    private final ChallengeId challengeId;
    private final Name newName;

    public UpdateChallengeName(ChallengeId challengeId, Name newName) {
        this.challengeId = challengeId;
        this.newName = newName;
    }

    public ChallengeId getChallengeId() {
        return challengeId;
    }

    public Name getNewName() {
        return newName;
    }
}
