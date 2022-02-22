package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;

public class AddedDojoRule extends DomainEvent {
    private final String rule;

    public AddedDojoRule(String rule) {
        super("core.dojo.addeddojorule");
        this.rule = rule;
    }

    public String getRule() {
        return rule;
    }
}
