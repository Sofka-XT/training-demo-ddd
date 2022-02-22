package co.sideralis.core.dojo.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.dojo.values.OpeningHours;

public class UpdateOpeningHours implements Command {
    private final OpeningHours newOpeningHours;

    public UpdateOpeningHours(OpeningHours newOpeningHours) {

        this.newOpeningHours = newOpeningHours;
    }

    public OpeningHours getNewOpeningHours() {
        return newOpeningHours;
    }
}
