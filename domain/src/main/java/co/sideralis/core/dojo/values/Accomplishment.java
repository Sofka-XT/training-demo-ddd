package co.sideralis.core.dojo.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Date;
import java.util.Objects;

public class Accomplishment implements ValueObject<Accomplishment.Properties> {
    private final String label;
    private final Integer point;
    private final Date date;

    public Accomplishment(String label, Integer point, Date date) {
        this.label = Objects.requireNonNull(label, "The label is required");
        this.point = Objects.requireNonNull(point, "The point cannot be null");
        this.date = Objects.requireNonNull(date, "The date cannot be null");

        if (this.label.isBlank()) {
            throw new IllegalArgumentException("The label cannot be black");
        }
        if (this.label.length() <= 3 || this.label.length() >= 150) {
            throw new IllegalArgumentException("the label must be more than 3 characters and less than 150 characters");
        }
        if (this.point <= 0) {
            throw new IllegalArgumentException("The point cannot be negative or zero");
        }
        if (this.date.before(new Date())) {
            throw new IllegalArgumentException("The date is required a date before now");
        }
    }

    @Override
    public Properties value() {
        return new Properties() {
            @Override
            public String label() {
                return label;
            }

            @Override
            public Integer point() {
                return point;
            }

            @Override
            public Date date() {
                return date;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accomplishment that = (Accomplishment) o;
        return Objects.equals(label, that.label) &&
                Objects.equals(point, that.point) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, point, date);
    }

    public interface Properties {
        String label();

        Integer point();

        Date date();
    }
}
