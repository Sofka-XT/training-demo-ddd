package co.sideralis.core.challenge.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.challenge.values.ChallengeId;

import java.util.Objects;

public class UpdateChallengeDuration implements Command {
    private final ChallengeId challengeId;
    private final Integer newDurationDays;

    public UpdateChallengeDuration(ChallengeId challengeId, Integer newDurationDays) {
        this.challengeId = challengeId;
        this.newDurationDays = Objects.requireNonNull(newDurationDays);

        if(newDurationDays <= 0){
            throw new IllegalArgumentException("The new duration days canot be less or equal to zero");
        }
    }

    public ChallengeId getChallengeId() {
        return challengeId;
    }

    public Integer getNewDurationDays() {
        return newDurationDays;
    }
}
