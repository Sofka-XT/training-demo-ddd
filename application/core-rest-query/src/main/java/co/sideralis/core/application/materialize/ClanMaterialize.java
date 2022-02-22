package co.sideralis.core.application.materialize;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.events.*;
import co.sideralis.core.events.AddedGitMember;
import co.sideralis.core.events.CreatedGitGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ClanMaterialize {

    private static final String COLLECTION_NAME = "clans";

    @Autowired
    private MongoTemplate mongoTemplate;

    @EventListener
    public void handleEventCreatedClan(CreatedClan createdClan) {
        Map<String, Object> data = new HashMap<>();
        data.put("_id", createdClan.aggregateRootId());
        data.put("name", createdClan.getName().value());
        data.put("members", new HashMap<>());
        mongoTemplate.save(data, COLLECTION_NAME);
    }

    @EventListener
    public void handleEventCreatedGitGroup(CreatedGitGroup createdGitGroup) {
        Update update = new Update();
        update.set("gitlabGroupId", createdGitGroup.getGroupId());
        update.set("gitlabPath", createdGitGroup.getPath());
        update.set("gitlabName", createdGitGroup.getName());
        mongoTemplate.updateFirst(getFilterByAggregateId(createdGitGroup), update, COLLECTION_NAME);
    }

    @EventListener
    public void handleEventAddedMember(AddedMember addedMember) {
        var id = addedMember.getMemberId().value();
        Update update = new Update();
        Map<String, Object> data = new HashMap<>();
        data.put("name", addedMember.getName().value());
        data.put("isOwner", addedMember.isOwner());
        data.put("gender", addedMember.getGender().value());
        data.put("personId", addedMember.getPersonId().value());
        data.put("_id", id);
        update.set("members." + id, data);
        mongoTemplate.updateFirst(getFilterByAggregateId(addedMember), update, COLLECTION_NAME);
    }

    @EventListener
    public void handleEventRevokeMember(RevokedMember revokedMember) {
        Update update = new Update();
        var id = revokedMember.getMemberId().value();
        update.unset("members." + id);
        mongoTemplate.updateFirst(getFilterByAggregateId(revokedMember), update, COLLECTION_NAME);
    }

    @EventListener
    public void handleEventUpdateClan(UpdatedName updatedName) {
        Update update = new Update();
        update.set("name", updatedName.getNewName().value());
        mongoTemplate.updateFirst(getFilterByAggregateId(updatedName), update, COLLECTION_NAME);
    }

    @EventListener
    public void handleUpdatedMember(UpdatedMember updatedName) {
        Update update = new Update();
        var id = updatedName.getMemberId().value();
        update.set("members." + id + ".name", updatedName.getName().value());
        mongoTemplate.updateFirst(getFilterByAggregateId(updatedName), update, COLLECTION_NAME);
    }

    @EventListener
    public void handleAddedGitMember(AddedGitMember addedGitMember) {
        Update update = new Update();
        var id = addedGitMember.getMemberId().value();
        update.set("members." + id + ".gitlabMemberId", addedGitMember.getMemberGitId());
        update.set("members." + id + ".gitlabUsername", addedGitMember.getUsername());
        mongoTemplate.updateFirst(getFilterByAggregateId(addedGitMember), update, COLLECTION_NAME);
    }

    private Query getFilterByAggregateId(DomainEvent event) {
        return new Query(
                Criteria.where("_id").is(event.aggregateRootId())
        );
    }

}
