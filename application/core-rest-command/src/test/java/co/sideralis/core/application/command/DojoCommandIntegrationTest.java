package co.sideralis.core.application.command;

import co.sideralis.core.dojo.values.OpeningHours;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DojoCommandIntegrationTest extends CommandBaseIntegrationTest {
    private final String aggregateRootId = "95261ecc-ec21-4c4e-be63-eb12fb75387d";

    @Test
    @Order(1)
    public void createDojo() {

        var args = new HashMap<String, String>();
        args.put("coachId", "ffff-fff");
        args.put("aggregateId", aggregateRootId);
        args.put("commandType", "core.dojo.create");
        args.put("personId", "xxx-zzzz");
        args.put("coachName", "Raul A. Alzate");
        args.put("name", "Super Dojo");
        args.put("email", "alzategomez.raul@gmail.com");
        args.put("legend", "The best dojo site");
        args.put("urlMeet", "https://google.com");
        args.put("location", "Star Building [Medellin/CO]");
        args.put("locationDescription", "Near the park");
        args.put("openHour", "10");
        args.put("closeHour", "12");
        args.put("openingFrequency", OpeningHours.Frequency.WEEKLY.name());
        args.put("coachSpecialty", "Java Developer");

        executor(args, requestFields(
                fieldWithPath("commandType").description("core.dojo.create"),
                fieldWithPath("aggregateId").description("[OPCIONAL] The id of the aggregate").type("String").optional(),
                fieldWithPath("coachId").description("[OPCIONAL] The id of the coach").type("String").optional(),
                fieldWithPath("personId").description("The id of person"),
                fieldWithPath("email").description("The email of coach"),
                fieldWithPath("name").description("The name of dojo"),
                fieldWithPath("legend").description("The legend of the dojo"),
                fieldWithPath("coachName").description("The name coach of dojo"),
                fieldWithPath("coachSpecialty").description("The specialty  of coach"),
                fieldWithPath("closeHour").type("Integer").description("[OPTIONAL] The hour close of dojo [hour 24]").optional(),
                fieldWithPath("openHour").type("Integer").description("[OPTIONAL] The hour open of dojo [hour 24]").optional(),
                fieldWithPath("openingFrequency").type("String").description("[OPTIONAL] The frequency open of dojo").optional(),
                fieldWithPath("urlMeet").type("String").description("[OPCIONAL] The url of the meet").optional(),
                fieldWithPath("location").type("String").description("[OPTIONAL] The location of dojo").optional(),
                fieldWithPath("locationDescription").description("[OPTIONAL] The description location of dojo")
        ), 2);
    }

    @Test
    @Order(2)
    public void updateLocation() {

        var args = new HashMap<String, String>();

        args.put("commandType", "core.dojo.updatelocation");
        args.put("aggregateId", aggregateRootId);
        args.put("urlMeet", "https://google.com");
        args.put("location", "Star Building [Medellin/CO]");
        args.put("locationDescription", "Near the park");
        args.put("openHour", "10");
        args.put("closeHour", "12");
        args.put("openingFrequency", OpeningHours.Frequency.WEEKLY.name());

        executor(args, requestFields(
                fieldWithPath("commandType").description("core.dojo.updatelocation"),
                fieldWithPath("aggregateId").description("The id of the aggregate"),
                fieldWithPath("closeHour").type("Integer").description("The hour close of dojo [hour 24]"),
                fieldWithPath("openHour").type("Integer").description("The hour open of dojo [hour 24]"),
                fieldWithPath("openingFrequency").description("The frequency open of dojo"),
                fieldWithPath("urlMeet").description("The url of the meet"),
                fieldWithPath("location").description("The location of dojo"),
                fieldWithPath("locationDescription").description("The description location of dojo")
        ), 1);
    }

    @Test
    @Order(3)
    public void updateOpeningHour() {
        var args = new HashMap<String, String>();
        args.put("commandType", "core.dojo.updateopeninghour");
        args.put("aggregateId", aggregateRootId);
        args.put("openHour", "16");
        args.put("closeHour", "18");
        args.put("openingFrequency", OpeningHours.Frequency.WEEKLY.name());

        executor(args, requestFields(
                fieldWithPath("commandType").description("core.dojo.updateopeninghour"),
                fieldWithPath("aggregateId").description("The id of the aggregate"),
                fieldWithPath("closeHour").type("Integer").description("The hour close of dojo [hour 24]"),
                fieldWithPath("openHour").type("Integer").description("The hour open of dojo [hour 24]"),
                fieldWithPath("openingFrequency").description("The frequency open of dojo")
        ), 1);
    }

    @Test
    @Order(3)
    public void addRule() {
        var args = new HashMap<String, String>();
        args.put("commandType", "core.dojo.addrule");
        args.put("aggregateId", aggregateRootId);
        args.put("rule", "#1 the rule for dojo");

        executor(args, requestFields(
                fieldWithPath("commandType").description("core.dojo.addrule"),
                fieldWithPath("aggregateId").description("The id of the aggregate"),
                fieldWithPath("rule").description("The new rule for dojo")
        ), 1);
    }

    @Test
    @Order(4)
    public void removeRule() {
        var args = new HashMap<String, String>();
        args.put("commandType", "core.dojo.removerule");
        args.put("aggregateId", aggregateRootId);
        args.put("rule", "#1 the rule for dojo");

        executor(args, requestFields(
                fieldWithPath("commandType").description("core.dojo.removerule"),
                fieldWithPath("aggregateId").description("The id of the aggregate"),
                fieldWithPath("rule").description("The remove rule for dojo")
        ), 1);
    }

    @Test
    @Order(5)
    public void updateDataInfo() {
        var args = new HashMap<String, String>();
        args.put("commandType", "core.dojo.updatedatainfo");
        args.put("aggregateId", aggregateRootId);
        args.put("name", "Super Dojo 2");
        args.put("legend", "The best dojo site");

        executor(args, requestFields(
                fieldWithPath("commandType").description("core.dojo.updatedatainfo"),
                fieldWithPath("aggregateId").description("The id of the aggregate"),
                fieldWithPath("name").description("The name of dojo"),
                fieldWithPath("legend").description("The legend of dojo")
        ), 1);
    }
}
