package co.sideralis.core.clan.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.clan.values.Score;

public class AddedScoreOfMember extends DomainEvent implements Incremental {
    private final MemberId memberId;
    private final Score score;

    public AddedScoreOfMember(MemberId memberIdentity, Score score) {
        super("core.clan.addedscoreofmember");
        this.memberId = memberIdentity;
        this.score = score;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public Score getScore() {
        return score;
    }
}
