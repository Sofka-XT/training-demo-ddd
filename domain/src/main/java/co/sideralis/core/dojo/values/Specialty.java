package co.sideralis.core.dojo.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Specialty implements ValueObject<String> {
    private final String description;

    public Specialty(String description) {
        this.description = Objects.requireNonNull(description, "The description is required");
        if (this.description.isBlank()) {
            throw new IllegalArgumentException("The description cannot be black");
        }
        if (this.description.length() <= 5 || this.description.length() >= 250) {
            throw new IllegalArgumentException("The description must be more than 5 characters and less than 250 characters");
        }
    }

    @Override
    public String value() {
        return description;
    }
}
