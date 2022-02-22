package co.sideralis.core.challenge;

import co.com.sofka.domain.generic.Entity;
import co.sideralis.core.challenge.values.Exercise;
import co.sideralis.core.challenge.values.KataId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Kata extends Entity<KataId> {
    private String purpose;
    private List<Exercise> exercises;
    private Integer limitOfTime;

    public Kata(KataId entityId, String purpose, Integer limitOfTime) {
        super(entityId);
        this.purpose = Objects.requireNonNull(purpose, "The purpose is required");
        this.limitOfTime = Objects.requireNonNull(limitOfTime, "The limitOfTime is required");
        this.exercises = new ArrayList<>();

        if(limitOfTime <= 0){
            throw new IllegalArgumentException("The limitOfTime cannot be less than the current one");
        }

        if(purpose.isBlank()){
            throw new IllegalArgumentException("The purpose cannot be blank");
        }
    }

    public String purpose(){
        return purpose;
    }

    public Integer limitOfTime(){
        return limitOfTime;
    }

    public List<Exercise> exercises(){
        return exercises;
    }

    protected void changeLimitOfTime(Integer limitOfTime){
        this.limitOfTime = limitOfTime;
    }

    protected void removeExercise(Exercise exercise){
        this.exercises.remove(exercise);
    }

    protected void addNewExercise(Exercise exercise){
        this.exercises.add(exercise);
    }
}
