package co.sideralis.core.challenge;

import co.com.sofka.domain.generic.EventChange;
import co.sideralis.core.challenge.events.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;

public class ChallengeChange extends EventChange {

    protected ChallengeChange(Challenge entity){
        apply(getCreatedChallengeConsumer(entity));
        apply(getAddedNewKataConsumer(entity));
        apply(getChallengeRevokedConsumer(entity));
        apply(getUrlRepoAssigned(entity));
        apply(getAddedExercise(entity));
        apply(getSubscribedClanConsumer(entity));
        apply(getUnsubscribedClanConsumer(entity));
        apply(getUpdatedChallengeName(entity));
        apply(getUpdatedChallengeDuration(entity));
    }

    private Consumer<CreatedChallenge> getCreatedChallengeConsumer(Challenge challenge){
        return (CreatedChallenge event) -> {
            challenge.name = event.getName();
            challenge.assesment = event.getAssesment();
            challenge.dojoId = event.getDojoId();
            challenge.durationDays = event.getDurationDays();
            challenge.clanIds = new HashSet<>();
            challenge.katas = new ArrayList<>();
        };
    }

    private Consumer<AddedKata> getAddedNewKataConsumer(Challenge entity) {
        return (AddedKata event) -> {
            Kata kata = new Kata(event.getKataId(), event.getPurpose(), event.getLimitOfTime());
            entity.katas.add(kata);
        };
    }

    private Consumer<ChallengeRevoked> getChallengeRevokedConsumer(Challenge entity) {
        return (ChallengeRevoked event) -> {
            entity.isRevoked = true;
        };
    }

    private Consumer<UrlRepoAssigned> getUrlRepoAssigned(Challenge entity) {
        return (UrlRepoAssigned event) -> {
            entity.assesment = entity.assesment.updateUrlRepo(event.getRepoUrl());
        };
    }

    private Consumer<AddedExercise> getAddedExercise(Challenge entity){
        return (AddedExercise event) ->{
            Kata kata = entity.getKataById(event.getKataId());
            kata.addNewExercise(event.getExercise());
        };
    }

    private Consumer<SubscribedClan> getSubscribedClanConsumer(Challenge entity){
        return (SubscribedClan event) ->{
            entity.clanIds.add(event.getClanId());
        };
    }

    private Consumer<UnsubscribedClan> getUnsubscribedClanConsumer(Challenge challenge) {
        return (UnsubscribedClan event) -> {
            challenge.clanIds.removeIf(clanId -> clanId.equals(event.getClanId()));
        };
    }

    private Consumer<UpdatedChallengeName> getUpdatedChallengeName(Challenge challenge){
        return (UpdatedChallengeName event) -> {
            challenge.name = event.getNewName();
        };
    }

    private Consumer<UpdatedChallengeDuration> getUpdatedChallengeDuration(Challenge challenge){
        return (UpdatedChallengeDuration event) -> {
            challenge.durationDays = event.getNewDurationDays();
        };
    }
}
