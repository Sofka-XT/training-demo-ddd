package co.sideralis.core.challenge.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.challenge.values.KataId;

import java.util.Objects;

public class AddNewKata implements Command {
    private final ChallengeId challengeId;
    private final KataId kataId;
    private final String purpose;
    private final Integer limitOfTime;

    public AddNewKata(ChallengeId challengeId, final KataId kataId, String purpose, Integer limitOfTime) {
        this.challengeId = challengeId;
        this.kataId = kataId;
        this.purpose = Objects.requireNonNull(purpose);
        this.limitOfTime = Objects.requireNonNull(limitOfTime);

        if(purpose.isBlank()){
            throw new IllegalArgumentException("The purpose cannot be blank");
        }

        if(limitOfTime <= 0){
            throw new IllegalArgumentException("The limitOfTime cannot be less than the current one");
        }
    }

    public ChallengeId getChallengeId() {
        return challengeId;
    }

    public KataId getKataId() {
        return kataId;
    }

    public String getPurpose() {
        return purpose;
    }

    public Integer getLimitOfTime() {
        return limitOfTime;
    }
}
