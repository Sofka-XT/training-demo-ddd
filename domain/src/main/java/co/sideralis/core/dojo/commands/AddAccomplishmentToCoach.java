package co.sideralis.core.dojo.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.dojo.values.Accomplishment;

public class AddAccomplishmentToCoach implements Command {

    private final Accomplishment accomplishment;

    public AddAccomplishmentToCoach(Accomplishment accomplishment) {

        this.accomplishment = accomplishment;
    }

    public Accomplishment getAccomplishment() {
        return accomplishment;
    }
}
