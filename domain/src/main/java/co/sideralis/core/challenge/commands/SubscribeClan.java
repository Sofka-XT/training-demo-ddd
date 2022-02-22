package co.sideralis.core.challenge.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.clan.values.ClanId;

public class SubscribeClan implements Command {
    private final ChallengeId challengeId;
    private final ClanId clanId;

    public SubscribeClan(ChallengeId challengeId, ClanId clanId) {
        this.challengeId = challengeId;
        this.clanId = clanId;
    }

    public ChallengeId getChallengeId() {
        return challengeId;
    }

    public ClanId getClanId() {
        return clanId;
    }
}
