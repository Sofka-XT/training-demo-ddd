package co.sideralis.core.challenge.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.challenge.values.Exercise;
import co.sideralis.core.challenge.values.KataId;

public class AddExercise implements Command {
    private final ChallengeId challengeId;
    private final KataId kataId;
    private final Exercise exercise;

    public AddExercise(ChallengeId challengeId, KataId kataId, Exercise exercise) {
        this.challengeId = challengeId;
        this.kataId = kataId;
        this.exercise = exercise;
    }

    public ChallengeId getChallengeId() {
        return challengeId;
    }

    public KataId getKataId() {
        return kataId;
    }

    public Exercise getExercise() {
        return exercise;
    }
}
