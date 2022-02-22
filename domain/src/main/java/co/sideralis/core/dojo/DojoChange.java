package co.sideralis.core.dojo;

import co.com.sofka.domain.generic.EventChange;
import co.sideralis.core.dojo.events.*;
import co.sideralis.core.dojo.values.Status;
import co.sideralis.core.events.CreatedGitGroup;
import co.sideralis.core.values.GroupGit;

import java.util.ArrayList;
import java.util.function.Consumer;


public class DojoChange extends EventChange {

    public DojoChange(Dojo dojo) {
        apply(getAddedAccomplishmentToCoachConsumer(dojo));
        apply(getAddedDojoRuleConsumer(dojo));
        apply(getAssociatedCoachConsumer(dojo));
        apply(getChangedStatusConsumer(dojo));
        apply(getCreatedDojoConsumer(dojo));
        apply(getRemovedAccomplishmentToCoachConsumer(dojo));
        apply(getRemovedDojoRuleConsumer(dojo));
        apply(getUpdatedCoachNameConsumer(dojo));
        apply(getUpdatedCoachSpecialtyConsumer(dojo));
        apply(getUpdatedDateInfoConsumer(dojo));
        apply(getUpdatedLocationConsumer(dojo));
        apply(getUpdatedOpeningHoursConsumer(dojo));
        apply(getUpdatedUrlMeetConsumer(dojo));
        apply(getCreatedGitGroupConsumer(dojo));
    }

    private Consumer<CreatedGitGroup> getCreatedGitGroupConsumer(Dojo entity) {
        return (CreatedGitGroup event) -> {
            entity.groupGit = new GroupGit(
                    event.getGroupId(),
                    event.getPath(),
                    event.getName()
            );
        };
    }

    private Consumer<UpdatedUrlMeet> getUpdatedUrlMeetConsumer(Dojo dojo) {
        return (UpdatedUrlMeet event) -> {
            dojo.location = dojo.location.updateUrlMeet(event.getNewMeetUrl());
        };
    }

    private Consumer<UpdatedOpeningHours> getUpdatedOpeningHoursConsumer(Dojo dojo) {
        return (UpdatedOpeningHours event) -> {
            dojo.location = dojo.location.updateOpeningHours(event.getNewOpeningHours());
        };
    }

    private Consumer<UpdatedLocation> getUpdatedLocationConsumer(Dojo dojo) {
        return (UpdatedLocation event) -> {
            dojo.location = event.getLocation();
        };
    }

    private Consumer<UpdatedDateInfo> getUpdatedDateInfoConsumer(Dojo dojo) {
        return (UpdatedDateInfo event) -> {
            dojo.dataInfo = event.getDataInfo();
        };
    }

    private Consumer<UpdatedCoachSpecialty> getUpdatedCoachSpecialtyConsumer(Dojo dojo) {
        return (UpdatedCoachSpecialty event) -> {
            dojo.coach().updateSpecialty(event.getSpecialty());
        };
    }

    private Consumer<UpdatedCoachName> getUpdatedCoachNameConsumer(Dojo dojo) {
        return (UpdatedCoachName event) -> {
            dojo.coach().updateName(event.getName());
        };
    }

    private Consumer<RemovedDojoRule> getRemovedDojoRuleConsumer(Dojo dojo) {
        return (RemovedDojoRule event) -> {
            dojo.rules().remove(event.getRule());
        };
    }

    private Consumer<RemovedAccomplishmentToCoach> getRemovedAccomplishmentToCoachConsumer(Dojo dojo) {
        return (RemovedAccomplishmentToCoach event) -> {
            dojo.coach().removeAccomplishment(event.getAccomplishment());
        };
    }

    private Consumer<CreatedDojo> getCreatedDojoConsumer(Dojo dojo) {
        return (CreatedDojo event) -> {
            dojo.status = new Status(Status.Type.CLOSED);
            dojo.dataInfo = event.getDataInfo();
            dojo.location = event.getLocation();
            dojo.rules = new ArrayList<>();
        };
    }

    private Consumer<ChangedStatus> getChangedStatusConsumer(Dojo dojo) {
        return (ChangedStatus event) -> {
            dojo.status = event.getStatus();
        };
    }

    private Consumer<AssociatedCoach> getAssociatedCoachConsumer(Dojo dojo) {
        return (AssociatedCoach event) -> {
            dojo.coach = new Coach(
                    event.getCoachId(),
                    event.getEmail(),
                    event.getPersonId(),
                    event.getCouchName(),
                    event.getSpecialty()
            );
        };
    }

    private Consumer<AddedDojoRule> getAddedDojoRuleConsumer(Dojo dojo) {
        return (AddedDojoRule event) -> {
            dojo.rules.add(event.getRule());
        };
    }

    private Consumer<AddedAccomplishmentToCoach> getAddedAccomplishmentToCoachConsumer(Dojo dojo) {
        return (AddedAccomplishmentToCoach event) -> {
            dojo.coach().addAccomplishment(event.getAccomplishment());
        };
    }


}
