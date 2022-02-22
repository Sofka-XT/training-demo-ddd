package co.sideralis.core.application.materialize;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.dojo.events.*;
import co.sideralis.core.dojo.values.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class DojoMaterialize {

    private static final String COLLECTION_NAME = "dojos";

    @Autowired
    private MongoTemplate mongoTemplate;

    @EventListener
    public void handleEventCreatedDojo(CreatedDojo createdDojo) {
        var dataInfo = createdDojo.getDataInfo().value();

        Map<String, Object> data = new HashMap<>();
        data.put("_id", createdDojo.aggregateRootId());
        data.put("name", dataInfo.name());
        data.put("status", Status.Type.CLOSED.name());
        data.put("legend", dataInfo.legend());

        Optional.ofNullable(createdDojo.getLocation()).ifPresent(value -> {
            var location = value.value();
            data.put("location", Map.of(
                    "description", location.description(),
                    "urlMeet", location.urlMeet(),
                    "location", location.location(),
                    "openingHours", Map.of(
                            "hourEnd", location.openingHours().hourEnd(),
                            "hourStart", location.openingHours().hourStart(),
                            "frequency", location.openingHours().frequency()
                    )
            ));
        });

        data.put("coachId", createdDojo.getCoachId());
        mongoTemplate.save(data, COLLECTION_NAME);
    }

    @EventListener
    public void handleEventAssociateCoach(AssociatedCoach associatedCoach) {
        Update update = new Update();
        var id = associatedCoach.getCoachId().value();
        update.set("coach." + id + ".id", associatedCoach.getCoachId().value());
        update.set("coach." + id + ".name", associatedCoach.getCouchName().value());
        update.set("coach." + id + ".email", associatedCoach.getEmail().value());
        update.set("coach." + id + ".personId", associatedCoach.getPersonId().value());
        update.set("coach." + id + ".specialty", associatedCoach.getSpecialty().value());
        mongoTemplate.updateFirst(getFilterByAggregateId(associatedCoach), update, COLLECTION_NAME);
    }


    @EventListener
    public void handleEventUpdatedOpeningHours(UpdatedOpeningHours openingHours) {
        var opening = openingHours.getNewOpeningHours().value();
        Update update = new Update();
        update.set("location.openingHours", Map.of(
                "hourEnd", opening.hourEnd(),
                "hourStart", opening.hourStart(),
                "frequency", opening.frequency()
        ));
        mongoTemplate.updateFirst(getFilterByAggregateId(openingHours), update, COLLECTION_NAME);
    }

    @EventListener
    public void handleEventChangeStatus(ChangedStatus changedStatus) {
        Update update = new Update();
        update.set("status", changedStatus.getStatus().value());
        mongoTemplate.updateFirst(getFilterByAggregateId(changedStatus), update, COLLECTION_NAME);
    }

    @EventListener
    public void handleEventAddedDojoRule(AddedDojoRule addedDojoRule) {
        Update update = new Update();
        var idRule = Math.abs(addedDojoRule.getRule().hashCode());
        update.set("rules." + idRule, addedDojoRule.getRule());
        mongoTemplate.updateFirst(getFilterByAggregateId(addedDojoRule), update, COLLECTION_NAME);
    }

    @EventListener
    public void handleEventRemovedDojoRule(RemovedDojoRule removedDojoRule) {
        Update update = new Update();
        var idRule = Math.abs(removedDojoRule.getRule().hashCode());
        update.unset("rules." + idRule);
        mongoTemplate.updateFirst(getFilterByAggregateId(removedDojoRule), update, COLLECTION_NAME);
    }

    @EventListener
    public void handleEventUpdatedDateInfo(UpdatedDateInfo updatedDateInfo) {
        Update update = new Update();
        var dataInfo = updatedDateInfo.getDataInfo().value();
        update.set("name", dataInfo.name());
        update.set("legend", dataInfo.legend());
        mongoTemplate.updateFirst(getFilterByAggregateId(updatedDateInfo), update, COLLECTION_NAME);
    }

    private Query getFilterByAggregateId(DomainEvent event) {
        return new Query(
                Criteria.where("_id").is(event.aggregateRootId())
        );
    }

}
