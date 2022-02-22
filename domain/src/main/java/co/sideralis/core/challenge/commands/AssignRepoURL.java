package co.sideralis.core.challenge.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.challenge.values.ChallengeId;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class AssignRepoURL implements Command {
    private ChallengeId challengeId;
    private String repoUrl;

    public AssignRepoURL(ChallengeId challengeId, String repoUrl) {
        this.challengeId = challengeId;
        this.repoUrl = Objects.requireNonNull(repoUrl, "The repo url is required");

        try {
            URL url = new URL(repoUrl);
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (IOException e) {
            throw new IllegalArgumentException("The repo url is malformed url or may be no connection");
        }
    }

    public ChallengeId getChallengeId() {
        return challengeId;
    }

    public String getRepoUrl() {
        return repoUrl;
    }
}
