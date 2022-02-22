package co.sideralis.core.dojo.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class DataInfo implements ValueObject<DataInfo.Properties> {
    private final String name;
    private final String legend;

    public DataInfo(String name, String legend) {
        this.name = Objects.requireNonNull(name, "The name is required").toLowerCase();
        this.legend = Objects.requireNonNull(legend, "The legend is required");

        if (this.name.isBlank()) {
            throw new IllegalArgumentException("The name cannot be black");
        }
        if (this.legend.isBlank()) {
            throw new IllegalArgumentException("The name cannot be black");
        }
        if (this.name.length() <= 4 || this.name.length() >= 250) {
            throw new IllegalArgumentException("the name must be more than 3 characters and less than 250 characters");
        }
        if (this.legend.length() <= 4 || this.legend.length() >= 250) {
            throw new IllegalArgumentException("the legend must be more than 3 characters and less than 250 characters");
        }
    }

    @Override
    public Properties value() {
        return new Properties() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public String legend() {
                return legend;
            }
        };
    }

    public interface Properties {
        String name();

        String legend();
    }
}
