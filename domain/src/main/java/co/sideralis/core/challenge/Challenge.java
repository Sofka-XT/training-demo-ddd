package co.sideralis.core.challenge;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.challenge.events.*;
import co.sideralis.core.challenge.values.Assesment;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.challenge.values.Exercise;
import co.sideralis.core.challenge.values.KataId;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.dojo.values.DojoId;
import co.sideralis.core.values.Name;

import java.util.List;
import java.util.Set;

public class Challenge extends AggregateEvent<ChallengeId> {
    protected Name name;
    protected Set<ClanId> clanIds;
    protected DojoId dojoId;
    protected List<Kata> katas;
    protected Assesment assesment;
    protected Boolean isRevoked;
    protected Integer durationDays;

    public Challenge(ChallengeId challengeId, Name name, DojoId dojoId, Assesment assesment, Integer durationDays){
        this(challengeId);
        appendChange(new CreatedChallenge(name, dojoId, assesment, durationDays)).apply();
    }

    public Challenge(ChallengeId entityId) {
        super(entityId);
        subscribe(new ChallengeChange(this));
    }

    public static Challenge from(ChallengeId challengeId, List<DomainEvent> events){
        var challenge = new Challenge(challengeId);
        events.forEach(challenge::applyEvent);
        return challenge;
    }

    public void addNewKata(KataId kataId, String purpose, Integer limitOfTime) {
        appendChange(new AddedKata(kataId, purpose, limitOfTime)).apply();
    }

    public void revoke() {
        appendChange(new ChallengeRevoked()).apply();
    }

    public void assignRepoUrl(String repoUrl){
        appendChange(new UrlRepoAssigned(repoUrl)).apply();
    }

    public void addExercise(KataId kataId, Exercise exercise){
        appendChange(new AddedExercise(kataId, exercise)).apply();
    }

    public void subscribeClan(ClanId clanId){
        appendChange(new SubscribedClan(clanId)).apply();
    }

    public void unsubscribeClan(ClanId clanId){
        appendChange(new UnsubscribedClan(clanId)).apply();
    }

    public void updateChallengeName(Name newName){
        appendChange(new UpdatedChallengeName(newName)).apply();
    }

    public void updateChallengeDuration(Integer newDurationDays){
        appendChange(new UpdatedChallengeDuration(newDurationDays)).apply();
    }

    public Kata getKataById(KataId kataId) {
        return katas().stream()
                .filter(kata -> kata.identity().equals(kataId))
                .findFirst()
                .orElseThrow();
    }

    private List<Kata> katas() {
        return this.katas;
    }
}
