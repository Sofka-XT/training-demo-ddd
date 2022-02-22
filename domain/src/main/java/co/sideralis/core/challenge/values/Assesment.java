package co.sideralis.core.challenge.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Assesment implements ValueObject<Assesment.Properties> {
    private Integer degreeOfDifficulty; //TODO: Validate allowed range
    private String repoUrl;
    private String summary;

    public Assesment(Integer degreeOfDifficulty, String summary) {
        this.degreeOfDifficulty = Objects.requireNonNull(degreeOfDifficulty, "The degreeOfDifficulty is required");
        this.repoUrl = "Pending";
        this.summary = Objects.requireNonNull(summary, "The summary hours is required");

        if(this.degreeOfDifficulty < 1 || this.degreeOfDifficulty > 10){
            throw new IllegalArgumentException("The degreeOfDificulty cannot be less than one nor greater than ten");
        }

        if (this.summary.isBlank()) {
            throw new IllegalArgumentException("The summary cannot be blank");
        }
    }

    public Assesment updateUrlRepo(String newRepoUrl) {
        Assesment assesment = new Assesment(degreeOfDifficulty, summary);
        assesment.repoUrl = newRepoUrl;
        return assesment;
    }

    @Override
    public Properties value() {
        return new Properties() {
            @Override
            public Integer degreeOfDificulty() {
                return degreeOfDifficulty;
            }

            @Override
            public String repoUrl() {
                return repoUrl;
            }

            @Override
            public String summary() {
                return summary;
            }
        };
    }


    public interface Properties {
        Integer degreeOfDificulty();

        String repoUrl();

        String summary();
    }
}
