package co.sideralis.core.clan;

import co.com.sofka.domain.generic.Entity;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberGit;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.clan.values.Score;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

import java.util.ArrayList;
import java.util.List;

public class Member extends Entity<MemberId> {
    private final PersonId personId;

    private Name name;
    private Email email;
    private Gender gender;
    private List<Score> scores;
    private Boolean isOwner;
    private MemberGit memberGit;

    protected Member(MemberId memberIdentity, PersonId personId, Email email, Name name, Gender gender, Boolean isOwner) {
        super(memberIdentity);
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.personId = personId;
        this.isOwner = isOwner;
        this.scores = new ArrayList<>();
    }

    private Member(MemberId memberIdentity, PersonId personId) {
        super(memberIdentity);
        this.personId = personId;
    }

    public static Member form(MemberId memberId, Email email, PersonId personId, Name name, Gender gender, boolean isOwner) {
        var member = new Member(memberId, personId);
        member.name = name;
        member.email = email;
        member.gender = gender;
        member.isOwner = isOwner;
        return member;
    }

    public List<Score> scores() {
        return scores;
    }

    public String name() {
        return name.value();
    }

    public String email() {
        return email.value();
    }

    public String gender() {
        return gender.value();
    }

    public String personId() {
        return personId.value();
    }

    public Boolean isOwner() {
        return isOwner;
    }

    protected void addScore(Score score) {
        this.scores.add(score);
    }

    protected void updateName(Name name) {
        this.name = name;
    }

    protected void updateEmail(Email email) {
        this.email = email;
    }

    protected void updateGender(Gender gender) {
        this.gender = gender;
    }

    public MemberGit.Properties memberGit() {
        return memberGit.value();
    }

    public void updateMemberGit(MemberGit memberGit) {
        this.memberGit = memberGit;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
