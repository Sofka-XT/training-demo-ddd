package co.sideralis.core.dojo.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.dojo.values.Specialty;

public class UpdateCoachSpecialty implements Command {
    private final Specialty specialty;

    public UpdateCoachSpecialty(Specialty specialty) {

        this.specialty = specialty;
    }

    public Specialty getSpecialty() {
        return specialty;
    }
}
