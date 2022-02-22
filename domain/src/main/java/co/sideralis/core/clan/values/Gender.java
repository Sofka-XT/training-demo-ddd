package co.sideralis.core.clan.values;

import co.com.sofka.domain.generic.ValueObject;

public class Gender implements ValueObject<String> {
    private final Type type;

    public Gender(Type gender) {
        this.type = gender;
    }

    public String value() {
        return type.name();
    }


    public enum Type {
        M, F
    }

}
