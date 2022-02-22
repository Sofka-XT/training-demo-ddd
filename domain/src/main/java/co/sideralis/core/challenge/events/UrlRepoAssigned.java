package co.sideralis.core.challenge.events;

import co.com.sofka.domain.generic.DomainEvent;

public class UrlRepoAssigned extends DomainEvent {
    private final String repoUrl;

    public UrlRepoAssigned(String repoUrl) {
        super("core.challenge.urlRepoAssigned");
        this.repoUrl = repoUrl;
    }

    public String getRepoUrl() {
        return repoUrl;
    }
}
