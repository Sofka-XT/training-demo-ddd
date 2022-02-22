package co.sideralis.core.application.controller;


import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.dojo.events.*;
import co.sideralis.core.dojo.values.*;
import co.sideralis.core.events.CreatedGitGroup;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.time.LocalDate;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DojoQueryControllerIntegrationTest extends QueryBaseIntegrationTest {
    private static final DojoId dojoId = new DojoId();

    @Test
    @Order(1)
    public void getDojoAllOpen() {
        var createdDojo = new CreatedDojo(
                CoachId.of("xxxxxx"),
                new DataInfo("my dojo", "the best dojo place"),
                new Location("https://google.com", "Medellin", "description",
                        new OpeningHours(10, 12, OpeningHours.Frequency.BIWEEKLY)
                )
        );

        var associatedCoach =  new AssociatedCoach(
                CoachId.of("xxxxxx"),
                new Email("alzategomez.raul@gmail.com"),
                PersonId.of("xxx-xxx-xxx"),
                new Name("Andres Camilo"),
                new Specialty("Developer about java 11")
        );

        var changedStatus = new ChangedStatus(
                new Status(Status.Type.OPEN)
        );


        fireEvent(createdDojo, "dojo", dojoId.value());
        fireEvent(associatedCoach, "dojo", dojoId.value());
        fireEvent(changedStatus, "dojo", dojoId.value());

        FieldDescriptor[] list = new FieldDescriptor[]{
                fieldWithPath("id").description("The id of dojo"),
                fieldWithPath("name").description("The name of dojo"),
                fieldWithPath("status").description("The status of dojo [CLOSED, CANCELLED, OPEN]"),
                fieldWithPath("legend").description("The legend of dojo"),
                fieldWithPath("coach.xxxxxx.name").description("The name of the coach"),
                fieldWithPath("coach.xxxxxx.email").description("The email of the coach"),
                fieldWithPath("coach.xxxxxx.specialty").description("The specialty of the coach"),
                fieldWithPath("coach.xxxxxx.personId").description("The person id of  coach"),
                fieldWithPath("location.urlMeet").description("The url for meet"),
                fieldWithPath("location.location").description("The place of the dojo"),
                fieldWithPath("location.description").description("The description of location"),
                fieldWithPath("location.openingHours.hourStart").description("The hour start meeting [hour 24]"),
                fieldWithPath("location.openingHours.hourEnd").description("The hour end meeting [hour 24]"),
                fieldWithPath("location.openingHours.frequency").description("The frequency for meeting [MONTHLY, BIWEEKLY, WEEKLY, EACH_THEE_DAY, WEEKENDS, EVERY_TWO_DAY]"),
        };


        executor("api/find/dojo-all",
                responseFields(fieldWithPath("[]").description("An array of clans"))
                        .andWithPrefix("[].", list)
        );
    }


    @Test
    @Order(2)
    public void getDojoByNameAndUpdateOpening() {
        UpdatedOpeningHours openingHours = new UpdatedOpeningHours(
                new OpeningHours(
                        20, 22, OpeningHours.Frequency.WEEKLY
                )
        );
        fireEvent(openingHours, "dojo", dojoId.value());

        executor("api/get/dojo-by-name?name=my dojo",
                responseFields(
                        fieldWithPath("id").description("The id of dojo"),
                        fieldWithPath("name").description("The name of dojo"),
                        fieldWithPath("status").description("The status of dojo [CLOSED, CANCELLED, OPEN]"),
                        fieldWithPath("legend").description("The legend of dojo"),
                        fieldWithPath("coach.xxxxxx.name").description("The name of the coach"),
                        fieldWithPath("coach.xxxxxx.email").description("The email of the coach"),
                        fieldWithPath("coach.xxxxxx.specialty").description("The specialty of the coach"),
                        fieldWithPath("coach.xxxxxx.personId").description("The person id of  coach"),
                        fieldWithPath("location.urlMeet").description("The url for meet"),
                        fieldWithPath("location.location").description("The place of the dojo"),
                        fieldWithPath("location.description").description("The description of location"),
                        fieldWithPath("location.openingHours.hourStart").description("The hour start meeting [hour 24]"),
                        fieldWithPath("location.openingHours.hourEnd").description("The hour end meeting [hour 24]"),
                        fieldWithPath("location.openingHours.frequency").description("The frequency for meeting [MONTHLY, BIWEEKLY, WEEKLY, EACH_THEE_DAY, WEEKENDS, EVERY_TWO_DAY]")
                )
        );
    }


    @Test
    @Order(3)
    public void getDojoById() {
        var addRule = new AddedDojoRule("#1 the role for my dojo");
        var idRule = Math.abs(addRule.getRule().hashCode());
        fireEvent(addRule, "dojo", dojoId.value());

        executor("api/get/dojo?id="+dojoId.value(),
                responseFields(
                        fieldWithPath("id").description("The id of dojo"),
                        fieldWithPath("name").description("The name of dojo"),
                        fieldWithPath("status").description("The status of dojo [CLOSED, CANCELLED, OPEN]"),
                        fieldWithPath("legend").description("The legend of dojo"),
                        fieldWithPath("coach.xxxxxx.name").description("The name of the coach"),
                        fieldWithPath("coach.xxxxxx.email").description("The email of the coach"),
                        fieldWithPath("coach.xxxxxx.specialty").description("The specialty of the coach"),
                        fieldWithPath("coach.xxxxxx.personId").description("The person id of  coach"),
                        fieldWithPath("rules."+idRule).description("The name rule of the dojo"),
                        fieldWithPath("location.urlMeet").description("The url for meet"),
                        fieldWithPath("location.location").description("The place of the dojo"),
                        fieldWithPath("location.description").description("The description of location"),
                        fieldWithPath("location.openingHours.hourStart").description("The hour start meeting [hour 24]"),
                        fieldWithPath("location.openingHours.hourEnd").description("The hour end meeting [hour 24]"),
                        fieldWithPath("location.openingHours.frequency").description("The frequency for meeting [MONTHLY, BIWEEKLY, WEEKLY, EACH_THEE_DAY, WEEKENDS, EVERY_TWO_DAY]")
                )
        );
    }
}