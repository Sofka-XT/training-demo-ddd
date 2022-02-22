package co.sideralis.core.challenge.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.challenge.values.KataId;

public class AddedKata extends DomainEvent {
    private final KataId kataId;
    private final String purpose;
    private final Integer limitOfTime;

    public AddedKata(final KataId kataId, final String purpose, final Integer limitOfTime) {
        super("core.challenge.addedKata");
        this.kataId = kataId;
        this.purpose = purpose;
        this.limitOfTime = limitOfTime;
    }

    public KataId getKataId() {
        return kataId;
    }

    public String getPurpose() {
        return purpose;
    }

    public Integer getLimitOfTime() {
        return limitOfTime;
    }
}
