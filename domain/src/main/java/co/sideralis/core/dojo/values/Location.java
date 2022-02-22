package co.sideralis.core.dojo.values;

import co.com.sofka.domain.generic.ValueObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;


public class Location implements ValueObject<Location.Properties> {
    private final String urlMeet;
    private final String location;
    private final String description;
    private final OpeningHours openingHours;

    public Location(String urlMeet, String location, String description, OpeningHours openingHours) {
        this.location = Objects.requireNonNull(location, "The location is required");
        this.description = Objects.requireNonNull(description, "The description is required");
        this.openingHours = Objects.requireNonNull(openingHours, "The opening hours is required");
        this.urlMeet = urlMeet;

        if (this.location.isBlank()) {
            throw new IllegalArgumentException("The location cannot be blank");
        }
        if (this.description.isBlank()) {
            throw new IllegalArgumentException("The description cannot be blank");
        }

        if (this.location.length() <= 4 || this.location.length() >= 150) {
            throw new IllegalArgumentException("the location must be more than 3 characters and less than 150 characters");
        }
        if (this.description.length() <= 4 || this.description.length() >= 250) {
            throw new IllegalArgumentException("the description must be more than 3 characters and less than 250 characters");
        }

        if (Objects.nonNull(urlMeet)) {
            try {
                URL url = new URL(urlMeet);
                URLConnection conn = url.openConnection();
                conn.connect();
            } catch (IOException e) {
                throw new IllegalArgumentException("The url meet is malformed url or may be no connection");
            }
        }
    }

    public Location updateOpeningHours(OpeningHours newOpeningHours) {
        return new Location(
                urlMeet, location, description, newOpeningHours
        );
    }

    public Location updateUrlMeet(String newUrlMeet) {
        return new Location(
                newUrlMeet, location, description, openingHours
        );
    }

    @Override
    public Properties value() {
        return new Properties() {
            @Override
            public String urlMeet() {
                return urlMeet;
            }

            @Override
            public String location() {
                return location;
            }

            @Override
            public String description() {
                return description;
            }

            @Override
            public OpeningHours.Properties openingHours() {
                return openingHours.value();
            }
        };
    }

    public interface Properties {
        String urlMeet();

        String location();

        String description();

        OpeningHours.Properties openingHours();
    }
}
