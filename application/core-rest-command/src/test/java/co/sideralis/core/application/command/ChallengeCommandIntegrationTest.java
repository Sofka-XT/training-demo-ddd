package co.sideralis.core.application.command;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

public class ChallengeCommandIntegrationTest extends CommandBaseIntegrationTest {
    private final String aggregateRootId = "95261ecc-ec21-4c4e-be63-eb12fb75387dd";
    private final String dojoId = "95261ecc-ec21-4c4e-be63-df0g9d8sd8";
    private final String kataId = "95261ecc-ec21-4c4e-be63-df0g9d8sd8";
    private final String clanId = "85261ecc-ec40-4c4e-be63-df0g9d8sf5";

    @Test
    @Order(1)
    public void createChallenge() {
        executor(Map.of(
                "commandType", "core.challenge.create",
                "aggregateId", aggregateRootId,
                "name", "Challenge Java",
                "durationDays", "30",
                "degreeOfDifficulty", "10",
                "summary", "This is to improve your java skills",
                "dojoId", dojoId
        ), requestFields(
                fieldWithPath("commandType").description("core.challenge.create"),
                fieldWithPath("aggregateId").description("[OPCIONAL] The id of the aggregate")
                        .type("String")
                        .optional(),
                fieldWithPath("name").description("The name of the challenge"),
                fieldWithPath("durationDays").description("The duration of the challenge in days"),
                fieldWithPath("degreeOfDifficulty").description("The degree of difficulty of the challenge"),
                fieldWithPath("summary").description("The description of the assesment"),
                fieldWithPath("dojoId").description("Id of dojo")
        ), 1);
    }

    @Test
    @Order(2)
    public void addNewKata() {
        executor(Map.of(
                "commandType", "core.challenge.createkata",
                "aggregateId", aggregateRootId,
                "kataId", kataId,
                "limitOfTime", "3",
                "purpose", "Improve your java knowledge"
        ), requestFields(
                fieldWithPath("commandType").description("core.challenge.createkata"),
                fieldWithPath("aggregateId").description("The id of the challenge aggregate"),
                fieldWithPath("kataId").description("The id of the kata")
                    .type("String")
                    .optional(),
                fieldWithPath("limitOfTime").description("Limit o time in days"),
                fieldWithPath("purpose").description("The purpose of the kata")
        ), 1);
    }

    @Test
    @Order(3)
    public void revokeChallenge(){
        executor(Map.of(
                "commandType", "core.challenge.revoke",
                "aggregateId", aggregateRootId
        ), requestFields(
                fieldWithPath("commandType").description("core.challenge.revoke"),
                fieldWithPath("aggregateId").description("The id of the challenge aggregate")
        ), 1);
    }

    @Test
    @Order(4)
    public void assignRepoUrl(){
        executor(Map.of(
                "commandType", "core.challenge.assignrepourl",
                "aggregateId", aggregateRootId,
                "repoUrl", "https://github.com/sideralis-co/api-core/"
        ), requestFields(
                fieldWithPath("commandType").description("core.challenge.assignrepourl"),
                fieldWithPath("aggregateId").description("The id of the challenge aggregate"),
                fieldWithPath("repoUrl").description("The url of the gitlab repository")
        ), 1);
    }

    @Test
    @Order(5)
    public void addExercise(){
        executor(Map.of(
                "commandType", "core.challenge.addexercise",
                "aggregateId", aggregateRootId,
                "kataId", kataId,
                "level", "7",
                "goal", "practice"
        ), requestFields(
                fieldWithPath("commandType").description("core.challenge.addexercise"),
                fieldWithPath("aggregateId").description("The id of the challenge aggregate"),
                fieldWithPath("kataId").description("the id of the kata where the exercises are added"),
                fieldWithPath("level").description("the level of the exercise"),
                fieldWithPath("goal").description("the goal of the exercise")
        ), 1);
    }

    @Test
    @Order(6)
    public void subscribeClan(){
        executor(Map.of(
                "commandType", "core.challenge.subscribeclan",
                "aggregateId", aggregateRootId,
                "clanId", clanId

        ), requestFields(
                fieldWithPath("commandType").description("core.challenge.subscribeclan"),
                fieldWithPath("aggregateId").description("The id of the challenge aggregate"),
                fieldWithPath("clanId").description("the id of the clan")
        ), 1);
    }

    @Test
    @Order(7)
    public void unsubscribeClan(){
        executor(Map.of(
                "commandType", "core.challenge.unsubscribeclan",
                "aggregateId", aggregateRootId,
                "clanId", clanId

        ), requestFields(
                fieldWithPath("commandType").description("core.challenge.unsubscribeclan"),
                fieldWithPath("aggregateId").description("The id of the challenge aggregate"),
                fieldWithPath("clanId").description("the id of the clan")
        ), 1);
    }

    @Test
    @Order(8)
    public void updateChallengeName(){
        executor(Map.of(
                "commandType", "core.challenge.updatechallengename",
                "aggregateId", aggregateRootId,
                "newName", "Cool Challenge"

        ), requestFields(
                fieldWithPath("commandType").description("core.challenge.updatechallengename"),
                fieldWithPath("aggregateId").description("The id of the challenge aggregate"),
                fieldWithPath("newName").description("the new name of the challenge")
        ), 1);
    }

    @Test
    @Order(9)
    public void updateChallengeDuration(){
        executor(Map.of(
                "commandType", "core.challenge.updatechallengeduration",
                "aggregateId", aggregateRootId,
                "newDurationDays", "60"

        ), requestFields(
                fieldWithPath("commandType").description("core.challenge.updatechallengeduration"),
                fieldWithPath("aggregateId").description("The id of the challenge aggregate"),
                fieldWithPath("newDurationDays").description("the new duration days of the challenge")
        ), 1);
    }
}
