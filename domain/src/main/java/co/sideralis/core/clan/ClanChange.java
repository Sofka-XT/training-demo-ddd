package co.sideralis.core.clan;

import co.com.sofka.domain.generic.EventChange;
import co.sideralis.core.clan.events.*;
import co.sideralis.core.clan.values.MemberGit;
import co.sideralis.core.events.AddedGitMember;
import co.sideralis.core.events.CreatedGitGroup;
import co.sideralis.core.values.GroupGit;

import java.util.HashSet;
import java.util.function.Consumer;

public class ClanChange extends EventChange {
    protected ClanChange(Clan entity) {
        apply(getCreatedClanConsumer(entity));
        apply(getAddedMemberConsumer(entity));
        apply(getRevokedMemberConsumer(entity));
        apply(getUpdatedNameConsumer(entity));
        apply(getUpdatedMemberConsumer(entity));
        apply(getAddedScoreOfMemberConsumer(entity));
        apply(getCreatedGitGroupConsumer(entity));
        apply(getAddedGitMemberConsumer(entity));
    }

    private Consumer<AddedGitMember> getAddedGitMemberConsumer(Clan entity) {
        return (AddedGitMember event) -> {
            var memberUpdate = entity.getMemberById(event.getMemberId());
            memberUpdate.updateMemberGit(new MemberGit(
                    event.getMemberGitId(),
                    event.getUsername()
            ));
        };
    }

    private Consumer<CreatedGitGroup> getCreatedGitGroupConsumer(Clan entity) {
        return (CreatedGitGroup event) -> {
            entity.groupGit = new GroupGit(
                    event.getGroupId(),
                    event.getPath(),
                    event.getName()
            );
        };
    }

    private Consumer<AddedScoreOfMember> getAddedScoreOfMemberConsumer(Clan entity) {
        return (AddedScoreOfMember event) -> {
            var memberUpdate = entity.getMemberById(event.getMemberId());
            memberUpdate.addScore(event.getScore());
        };
    }

    private Consumer<UpdatedMember> getUpdatedMemberConsumer(Clan entity) {
        return (UpdatedMember event) -> {
            var memberUpdate = entity.getMemberById(event.getMemberId());
            memberUpdate.updateName(event.getName());
        };
    }

    private Consumer<UpdatedName> getUpdatedNameConsumer(Clan entity) {
        return (UpdatedName event) -> entity.name = event.getNewName();
    }

    private Consumer<RevokedMember> getRevokedMemberConsumer(Clan entity) {
        return (RevokedMember event) -> entity.members
                .removeIf(e -> e.identity().equals(event.getMemberId()));
    }

    private Consumer<AddedMember> getAddedMemberConsumer(Clan entity) {
        return (AddedMember event) -> {
            var member = new Member(
                    event.getMemberId(),
                    event.getPersonId(),
                    event.getEmail(),
                    event.getName(),
                    event.getGender(),
                    event.isOwner()
            );
            entity.members.add(member);
        };
    }

    private Consumer<CreatedClan> getCreatedClanConsumer(Clan entity) {
        return (CreatedClan event) -> {
            entity.members = new HashSet<>();
            entity.name = event.getName();
            entity.color = event.getColor();
        };
    }

}
