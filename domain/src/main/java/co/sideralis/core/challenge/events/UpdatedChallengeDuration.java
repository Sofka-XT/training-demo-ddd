package co.sideralis.core.challenge.events;

import co.com.sofka.domain.generic.DomainEvent;

public class UpdatedChallengeDuration extends DomainEvent {
    private final Integer newDurationDays;

    public UpdatedChallengeDuration(final Integer newDurationDays) {
        super("core.challenge.updatedChallengeDuration");
        this.newDurationDays = newDurationDays;
    }

    public Integer getNewDurationDays() {
        return newDurationDays;
    }
}
