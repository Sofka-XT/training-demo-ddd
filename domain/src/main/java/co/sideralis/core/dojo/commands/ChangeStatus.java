package co.sideralis.core.dojo.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.dojo.values.Status;

public class ChangeStatus implements Command {
    private final Status status;

    public ChangeStatus(Status status) {

        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
