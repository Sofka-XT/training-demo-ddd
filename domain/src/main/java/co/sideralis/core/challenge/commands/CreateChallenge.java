package co.sideralis.core.challenge.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.challenge.values.Assesment;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.dojo.values.DojoId;
import co.sideralis.core.values.Name;

public class CreateChallenge implements Command {

    private final ChallengeId id;
    private final Name name;
    private final Integer durationDays;
    private final Assesment assesment;
    private final DojoId dojoId;

    public CreateChallenge(ChallengeId id, Name name, Integer durationDays, Assesment assesment, DojoId dojoId) {
        this.id = id;
        this.durationDays = durationDays;
        this.assesment = assesment;
        this.dojoId = dojoId;
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public ChallengeId getId() {
        return id;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public Assesment getAssesment() {
        return assesment;
    }

    public DojoId getDojoId() {
        return dojoId;
    }
}
