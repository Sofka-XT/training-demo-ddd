package co.sideralis.core.dojo.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.dojo.values.Accomplishment;

public class RemoveAccomplishmentToCoach implements Command {
    private final Accomplishment accomplishment;

    public RemoveAccomplishmentToCoach(Accomplishment accomplishment) {

        this.accomplishment = accomplishment;
    }

    public Accomplishment getAccomplishment() {
        return accomplishment;
    }
}
