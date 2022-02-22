package co.sideralis.core.challenge.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Map;
import java.util.Objects;

public class Exercise implements ValueObject<Exercise.Properties> {
    private Integer level;  //TODO: Validate what is the allowed range
    private Map<String, String> metadata; //TODO: Validate required keys
    private String goal;

    public Exercise(Integer level, Map<String, String> metadata, String goal) {
        this.level = Objects.requireNonNull(level, "The level is required");
        this.metadata = Objects.requireNonNull(metadata, "The metadata is required");
        this.goal = Objects.requireNonNull(goal, "The goal is required");

        if (this.goal.isBlank()) {
            throw new IllegalArgumentException("The goal cannot be blank");
        }
        if(this.level <= 0){
            throw new IllegalArgumentException("The level cannot be less or equal zero");
        }
    }

    @Override
    public Properties value() {
        return new Properties() {
            @Override
            public Integer level() {
                return level;
            }

            @Override
            public Map<String, String> metadata() {
                return metadata;
            }

            @Override
            public String goal() {
                return goal;
            }
        };
    }

    public interface Properties {
        Integer level();

        Map<String, String> metadata();

        String goal();
    }
}
