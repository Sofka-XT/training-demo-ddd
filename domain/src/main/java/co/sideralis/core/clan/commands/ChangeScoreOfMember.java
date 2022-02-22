package co.sideralis.core.clan.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.clan.values.Score;

public class ChangeScoreOfMember implements Command {

    private final MemberId memberIdentity;
    private final Score score;
    private final ClanId clanId;

    public ChangeScoreOfMember(ClanId clanId, MemberId memberIdentity, Score score) {
        this.clanId = clanId;
        this.memberIdentity = memberIdentity;
        this.score = score;
    }

    public MemberId getStudentIdentity() {
        return memberIdentity;
    }

    public Score getScore() {
        return score;
    }

}
