package co.sideralis.core.dojo.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.values.Name;

public class UpdateCoachName implements Command {
    private final Name name;

    public UpdateCoachName(Name name) {

        this.name = name;
    }

    public Name getName() {
        return name;
    }
}
