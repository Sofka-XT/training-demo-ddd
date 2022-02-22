package co.sideralis.core.application.command;


import co.com.sofka.domain.generic.Identity;
import co.sideralis.core.values.PersonId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Map;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClanCommandIntegrationTest extends CommandBaseIntegrationTest {
    private final String aggregateRootId = "95261ecc-ec21-4c4e-be63-eb12fb75387d";
    private final String memberId = "95261ecc-ec21-4c4e-be63-df0g9d8sd8";

    @Test
    @Order(1)
    public void createClan() {
        executor(Map.of(
                "commandType", "core.clan.create",
                "aggregateId", aggregateRootId,
                "name", "Dream Clan",
                "color", "RED",
                "email", "raul.alzate@gmail.com",
                "memberName", "raul andres alzate",
                "memberGender", "M",
                "personId", new PersonId().value()
        ), requestFields(
                fieldWithPath("commandType").description("core.clan.create"),
                fieldWithPath("aggregateId").description("[OPCIONAL] The id of the aggregate")
                        .type("String")
                        .optional(),
                fieldWithPath("name").description("The name of clan"),
                fieldWithPath("color").description("The color of clan [WHITE,LIGHT_GRAY, GRAY,DARK_GRAY,BLACK,RED,PINK,ORANGE,YELLOW,GREEN,MAGENTA,CYAN,BLUE]"),
                fieldWithPath("email").description("The email of member owner"),
                fieldWithPath("memberName").description("The name of the member owner"),
                fieldWithPath("memberGender").description("The gender of member [F,M]"),
                fieldWithPath("personId").description("The id of person")
        ), 2);
    }

    @Test
    @Order(2)
    public void createMember() {

        executor(Map.of(
                "commandType", "core.clan.createmember",
                "aggregateId", aggregateRootId,
                "personId", new Identity().value(),
                "memberId", memberId,
                "email", "maria.camila@gmail.com",
                "name", "Maria Camila",
                "gender", "F"
        ), requestFields(
                fieldWithPath("commandType").description("core.clan.createmember"),
                fieldWithPath("aggregateId").description("The id of the aggregate"),
                fieldWithPath("personId").description("The id of the person"),
                fieldWithPath("email").description("The email of member owner"),
                fieldWithPath("memberId").description("[OPCIONAL] The id of the member")
                        .type("String")
                        .optional(),
                fieldWithPath("isOwner").description("[OPCIONAL] Is a owner of the clan, by default false")
                        .type("Boolean")
                        .optional(),
                fieldWithPath("name").description("The name of member"),
                fieldWithPath("gender").description("The gender of member [F or M]")
        ), 1);
    }

    @Test
    @Order(3)
    public void updateMember() {
        executor(Map.of(
                "commandType", "core.clan.updatemember",
                "aggregateId", aggregateRootId,
                "memberId", memberId,
                "name", "Ana Maria Valencia"
        ), requestFields(
                fieldWithPath("commandType").description("The command type"),
                fieldWithPath("aggregateId").description("The id of the aggregate"),
                fieldWithPath("memberId").description("The id of member"),
                fieldWithPath("name").description("The name of member")
        ), 1);
    }

    @Test
    @Order(4)
    public void revokeMember() {
        executor(Map.of(
                "commandType", "core.clan.revokemember",
                "aggregateId", aggregateRootId,
                "memberId", memberId
        ), requestFields(
                fieldWithPath("commandType").description("core.clan.revokemember"),
                fieldWithPath("aggregateId").description("The id of the aggregate"),
                fieldWithPath("memberId").description("The id of member")
        ), 1);
    }
}