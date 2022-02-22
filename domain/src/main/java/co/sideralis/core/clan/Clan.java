package co.sideralis.core.clan;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.events.*;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.clan.values.Score;
import co.sideralis.core.events.AddedGitMember;
import co.sideralis.core.events.CreatedGitGroup;
import co.sideralis.core.events.CreatedGitMember;
import co.sideralis.core.values.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Clan extends AggregateEvent<ClanId> {
    protected Name name;
    protected Set<Member> members;
    protected GroupGit groupGit;
    protected Color color;

    public Clan(ClanId identity, Name name, Color color) {
        this(identity);
        appendChange(new CreatedClan(name, color)).apply();
    }

    private Clan(ClanId identity) {
        super(identity);
        subscribe(new ClanChange(this));
    }

    public static Clan from(ClanId aggregateId, List<DomainEvent> list) {
        Clan clan = new Clan(aggregateId);
        list.forEach(clan::applyEvent);
        return clan;
    }

    public void createGitGroup(Integer groupId, String path) {
        appendChange(new CreatedGitGroup(groupId, path, name.value())).apply();
    }

    public void addNewMember(MemberId memberId, PersonId personId, Email email, Name name, Gender gender, boolean isOwner) {
        appendChange(new AddedMember(memberId, personId, email, name, gender, isOwner)).apply();
    }

    public void addGitMember(MemberId memberId, Integer memberGitId, String username) {
        var isOwner = getMemberById(memberId).isOwner();
        appendChange(
                new AddedGitMember(memberId, memberGitId, username, groupGit().getGroupId(), isOwner)
        ).apply();
    }

    public void createGitMember(MemberId memberId, Integer memberGitId, String username, String email) {
        var member = getMemberById(memberId);
        appendChange(new CreatedGitMember(memberGitId, member.name(), username, email)).apply();
    }

    public void removeGitMember(MemberId memberId) {
        Objects.requireNonNull(memberId, "the memberIdentity is non null");
        var member = Objects.requireNonNull(getMemberById(memberId));
        appendChange(new RemoveGitMember(
                groupGit().getGroupId(), member.memberGit().memberGitId())
        ).apply();
    }

    public void revokeMember(MemberId memberId) {
        Objects.requireNonNull(memberId, "the memberIdentity is non null");
        Objects.requireNonNull(getMemberById(memberId));
        appendChange(new RevokedMember(memberId)).apply();
    }

    public Member getMemberById(MemberId memberId) {
        return members().stream()
                .filter(e -> e.identity().equals(memberId))
                .findFirst().orElseThrow();
    }

    public void updateName(Name newName) {
        appendChange(new UpdatedName(newName)).apply();
    }

    public void updateMemberName(MemberId memberIdentity, Name name) {
        appendChange(new UpdatedMember(memberIdentity, name)).apply();
    }

    public void addScoreToMember(MemberId memberIdentity, Score score) {
        appendChange(new AddedScoreOfMember(memberIdentity, score)).apply();
    }

    public GroupGit.Properties groupGit() {
        return groupGit.value();
    }

    public Set<Member> members() {
        return members;
    }

    public String name() {
        return name.value();
    }

}
