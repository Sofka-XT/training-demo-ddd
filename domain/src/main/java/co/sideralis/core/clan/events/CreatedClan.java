package co.sideralis.core.clan.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.values.Color;
import co.sideralis.core.values.Name;

public class CreatedClan extends DomainEvent {
    private final Name name;
    private final Color color;

    public CreatedClan(Name name, Color color) {
        super("core.clan.create");
        this.name = name;
        this.color = color;
    }

    public Name getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

}
