package co.sideralis.core.dojo;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.dojo.events.*;
import co.sideralis.core.dojo.values.*;
import co.sideralis.core.events.CreatedGitGroup;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.GroupGit;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

import java.util.List;

public class Dojo extends AggregateEvent<DojoId> {
    protected Coach coach;
    protected Status status;
    protected DataInfo dataInfo;
    protected List<String> rules;
    protected Location location;
    protected GroupGit groupGit;

    public Dojo(DojoId entityId, CoachId coachId, Email email, PersonId personId, DataInfo dataInfo, Location location, Name couchName, Specialty specialty) {
        this(entityId);
        appendChange(new CreatedDojo(coachId, dataInfo, location)).apply();
        appendChange(new AssociatedCoach(coachId, email, personId, couchName, specialty)).apply();
    }

    public Dojo(DojoId entityId, CoachId coachId, Email email, PersonId personId, DataInfo dataInfo, Name couchName, Specialty specialty) {
        this(entityId);
        appendChange(new CreatedDojo(coachId, dataInfo, null)).apply();
        appendChange(new AssociatedCoach(coachId, email, personId, couchName, specialty)).apply();
    }

    private Dojo(DojoId entityId) {
        super(entityId);
        subscribe(new DojoChange(this));
    }

    public static Dojo form(DojoId dojoId, List<DomainEvent> list) {
        var dojo = new Dojo(dojoId);
        list.forEach(dojo::applyEvent);
        return dojo;
    }

    public void createdGitGroup(Integer groupId, String path) {
        appendChange(new CreatedGitGroup(groupId, path, dataInfo.value().name())).apply();
    }

    public void changeStatus(Status status) {
        appendChange(new ChangedStatus(status));
    }

    public void updateDataInfo(DataInfo dataInfo) {
        appendChange(new UpdatedDateInfo(dataInfo));
    }

    public void addRules(String rule) {
        appendChange(new AddedDojoRule(rule)).apply();
    }

    public void removeRules(String rule) {
        appendChange(new RemovedDojoRule(rule)).apply();
    }

    public void updateLocation(Location location) {
        appendChange(new UpdatedLocation(location)).apply();
    }

    public void updateOpeningHours(OpeningHours newOpeningHours) {
        appendChange(new UpdatedOpeningHours(newOpeningHours)).apply();
    }

    public void updateUrlMeet(String newMeetUrl) {
        appendChange(new UpdatedUrlMeet(newMeetUrl)).apply();
    }

    public void updateCoachName(Name name) {
        appendChange(new UpdatedCoachName(name)).apply();
    }

    public void updateCoachSpecialty(Specialty specialty) {
        appendChange(new UpdatedCoachSpecialty(specialty)).apply();
    }

    public void addAccomplishmentToCoach(Accomplishment accomplishment) {
        appendChange(new AddedAccomplishmentToCoach(accomplishment)).apply();
    }

    public void removeAccomplishmentToCoach(Accomplishment accomplishment) {
        appendChange(new RemovedAccomplishmentToCoach(accomplishment)).apply();
    }

    public Coach coach() {
        return coach;
    }

    public String status() {
        return status.value();
    }

    public DataInfo.Properties dataInfo() {
        return dataInfo.value();
    }

    public List<String> rules() {
        return rules;
    }

    public Location.Properties location() {
        return location.value();
    }

}
