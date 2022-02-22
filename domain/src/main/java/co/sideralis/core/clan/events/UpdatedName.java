package co.sideralis.core.clan.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.values.Name;

public class UpdatedName extends DomainEvent implements Incremental {
    private final Name newName;

    public UpdatedName(Name newName) {
        super("core.clan.updatedname");
        this.newName = newName;
    }

    public Name getNewName() {
        return newName;
    }
}
