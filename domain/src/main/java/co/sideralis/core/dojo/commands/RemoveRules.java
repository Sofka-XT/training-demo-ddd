package co.sideralis.core.dojo.commands;

import co.com.sofka.domain.generic.Command;

import java.util.Objects;

public class RemoveRules implements Command {
    private final String rule;

    public RemoveRules(String rule) {
        this.rule = Objects.requireNonNull(rule);
        if (this.rule.length() < 3) {
            throw new IllegalArgumentException("The rule cannot be less than 3");
        }
    }

    public String getRule() {
        return rule;
    }
}
