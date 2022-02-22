package co.sideralis.core.clan.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Calendar;
import java.util.Date;

public class Score implements ValueObject<Score.Values> {

    private final int points;
    private final Date lastDate;

    public Score(int points) {
        this.points = points;
        if (points < 0) {
            throw new IllegalArgumentException("No allow the negative value");
        }
        this.lastDate = Calendar.getInstance().getTime();
    }

    @Override
    public Values value() {
        return new Values() {
            @Override
            public int points() {
                return points;
            }

            @Override
            public long time() {
                return lastDate.getTime();
            }
        };
    }

    public interface Values {
        int points();

        long time();
    }

}
