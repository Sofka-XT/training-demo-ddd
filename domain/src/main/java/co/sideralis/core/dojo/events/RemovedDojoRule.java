package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;

public class RemovedDojoRule extends DomainEvent {
    private final String rule;

    public RemovedDojoRule(String rule) {
        super("core.dojo.removeddojorule");
        this.rule = rule;
    }

    public String getRule() {
        return rule;
    }
}
