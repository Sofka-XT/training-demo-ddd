package co.sideralis.core.values;

import co.com.sofka.domain.generic.ValueObject;

public class Color implements ValueObject<String> {
    private final Type type;

    public Color(Type type) {
        this.type = type;
    }

    @Override
    public String value() {
        return type.name();
    }

    public enum Type {
        WHITE, LIGHT_GRAY, GRAY, DARK_GRAY, BLACK, RED, PINK, ORANGE, YELLOW, GREEN, MAGENTA, CYAN, BLUE
    }
}
