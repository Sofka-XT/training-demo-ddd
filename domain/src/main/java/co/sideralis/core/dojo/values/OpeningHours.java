package co.sideralis.core.dojo.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class OpeningHours implements ValueObject<OpeningHours.Properties> {
    private final Integer hourStart;
    private final Integer hourEnd;
    private final Frequency frequency;

    public OpeningHours(Integer hourStart, Integer hourEnd, Frequency frequency) {
        this.hourStart = Objects.requireNonNull(hourStart, "The hour start is required");
        this.hourEnd = Objects.requireNonNull(hourEnd, "The hour end is required");
        this.frequency = Objects.requireNonNull(frequency, "The frequency is required");
        if (hourStart > hourEnd) {
            throw new IllegalArgumentException("The hour start must be before the hour end");
        }
        if (hourStart > 24 || hourStart < 0) {
            throw new IllegalArgumentException("the hour start is not valid, it must be 24 hours");
        }

        if (hourEnd < 0) {
            throw new IllegalArgumentException("the hour end is not valid, it must be 24 hours");
        }
    }

    @Override
    public Properties value() {
        return new Properties() {
            @Override
            public Integer hourStart() {
                return hourStart;
            }

            @Override
            public Integer hourEnd() {
                return hourEnd;
            }

            @Override
            public String frequency() {
                return frequency.name();
            }
        };
    }

    public enum Frequency {
        MONTHLY, BIWEEKLY, WEEKLY, EACH_THEE_DAY, WEEKENDS, EVERY_TWO_DAY
    }

    public interface Properties {
        Integer hourStart();

        Integer hourEnd();

        String frequency();
    }
}
