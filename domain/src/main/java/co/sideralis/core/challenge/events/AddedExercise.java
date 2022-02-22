package co.sideralis.core.challenge.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.challenge.values.Exercise;
import co.sideralis.core.challenge.values.KataId;

public class AddedExercise extends DomainEvent {
    private final KataId kataId;
    private final Exercise exercise;

    public AddedExercise(final KataId kataId, final Exercise exercise) {
        super("core.challenge.addedexercise");
        this.kataId = kataId;
        this.exercise = exercise;
    }

    public KataId getKataId() {
        return kataId;
    }

    public Exercise getExercise() {
        return exercise;
    }
}
