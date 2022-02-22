package co.sideralis.core.application.controller;

import co.sideralis.core.events.CreatedGitGroup;
import co.sideralis.core.values.Color;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;
import co.sideralis.core.events.AddedGitMember;
import co.sideralis.core.clan.events.AddedMember;
import co.sideralis.core.clan.events.CreatedClan;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClanQueryControllerIntegrationTest extends QueryBaseIntegrationTest {
    private static final ClanId clanId = new ClanId();

    @Test
    @Order(1)
    public void getClanAll() {
        var createdClan = new CreatedClan(
                new Name("Dream Clan"),
                new Color(Color.Type.BLUE)
        );
        var createdGitGroup = new CreatedGitGroup(
                10, "dream_clean", "Dream Clan"
        );
        FieldDescriptor[] list = new FieldDescriptor[]{
                fieldWithPath("name").description("The name of clan"),
                fieldWithPath("id").description("The id of clan"),
                fieldWithPath("gitlabGroupId").description("The group id of gitlab"),
                fieldWithPath("gitlabName").description("The group name of gitlab"),
                fieldWithPath("gitlabPath").description("The path of gitlab"),
                fieldWithPath("members").description("The members of clan")
        };

        fireEvent(createdClan, "clan", clanId.value());
        fireEvent(createdGitGroup, "clan", clanId.value());

        executor("api/find/clan-all",
                responseFields(fieldWithPath("[]").description("An array of clans"))
                        .andWithPrefix("[].", list)
        );
    }

    @Test
    @Order(2)
    public void getClanById() {
        executor("api/get/clan?=clanId=" + clanId.value(),
                responseFields(fieldWithPath("name").description("The name of clan"),
                        fieldWithPath("id").description("The id of clan"),
                        fieldWithPath("members").description("The members of clan"),
                        fieldWithPath("gitlabGroupId").description("The group id of gitlab"),
                        fieldWithPath("gitlabName").description("The group name of gitlab"),
                        fieldWithPath("gitlabPath").description("The path of gitlab")
                )
        );
    }


    @Test
    @Order(2)
    public void getClanByName() {
        executor("api/get/clan-by-name?=name=Dream Clan",
                responseFields(fieldWithPath("name").description("The name of clan"),
                        fieldWithPath("id").description("The id of clan"),
                        fieldWithPath("members").description("The members of clan"),
                        fieldWithPath("gitlabGroupId").description("The group id of gitlab"),
                        fieldWithPath("gitlabName").description("The group name of gitlab"),
                        fieldWithPath("gitlabPath").description("The path of gitlab")
                )
        );
    }

    @Test
    @Order(3)
    public void getMembersBy() {
        var memberId = new MemberId();

        var addedMember1 = new AddedMember(
                memberId,
                new PersonId(),
                new Email("example@gmail.com"),
                new Name("Andres Camilo Sanchez"),
                new Gender(Gender.Type.M)
        );

        var addedGitMember = new AddedGitMember(
                memberId,
                10,
                "andres_camilo",
                40,
                false
        );

        fireEvent(addedMember1, "clan", clanId.value());
        fireEvent(addedGitMember, "clan", clanId.value());

        executor("api/get/members-by?clanId=" + clanId.value(),
                responseFields(
                        fieldWithPath("id").description("The name of clan"),
                        fieldWithPath(
                                "members." + addedMember1.getMemberId().value() + ".id"
                        ).description("The id of member"),
                        fieldWithPath(
                                "members." + addedMember1.getMemberId().value() + ".name"
                        ).description("The name of member"),
                        fieldWithPath(
                                "members." + addedMember1.getMemberId().value() + ".personId"
                        ).description("The id of person"),
                        fieldWithPath(
                                "members." + addedMember1.getMemberId().value() + ".gender"
                        ).description("The gender of member"),
                        fieldWithPath(
                                "members." + addedMember1.getMemberId().value() + ".isOwner"
                        ).description("If is a owner of the clan"),
                        fieldWithPath(
                                "members." + addedMember1.getMemberId().value() + ".gitlabMemberId"
                        ).description("The user id of gitlab"),
                        fieldWithPath(
                                "members." + addedMember1.getMemberId().value() + ".gitlabUsername"
                        ).description("The user name for gitlab")
                )
        );
    }
}