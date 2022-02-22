package co.sideralis.core.challenge.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.values.Name;

public class UpdatedChallengeName extends DomainEvent {
    private final Name newName;

    public UpdatedChallengeName(final Name newName) {
        super("core.challenge.updatedChallengeName");
        this.newName = newName;
    }

    public Name getNewName() {
        return newName;
    }
}
