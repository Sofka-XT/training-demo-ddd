package co.sideralis.core.challenge.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.challenge.values.Assesment;
import co.sideralis.core.dojo.values.DojoId;
import co.sideralis.core.values.Name;

public class CreatedChallenge extends DomainEvent {
    private Name name;
    private DojoId dojoId;
    private Assesment assesment;
    private Integer durationDays;
    private Boolean isRevoked;


    public CreatedChallenge(Name name, DojoId dojoId, Assesment assesment, Integer durationDays) {
        super("core.challenge.createdchallenge");
        this.name = name;
        this.dojoId = dojoId;
        this.assesment = assesment;
        this.durationDays = durationDays;
        this.isRevoked = false;
    }

    public Boolean getRevoked() {
        return isRevoked;
    }

    public Name getName() {
        return name;
    }

    public DojoId getDojoId() {
        return dojoId;
    }

    public Assesment getAssesment() {
        return assesment;
    }

    public Integer getDurationDays() {
        return durationDays;
    }
}
