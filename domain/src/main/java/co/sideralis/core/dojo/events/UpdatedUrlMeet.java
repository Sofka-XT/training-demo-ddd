package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class UpdatedUrlMeet extends DomainEvent implements Incremental {
    private final String newMeetUrl;

    public UpdatedUrlMeet(String newMeetUrl) {
        super("core.dojo.updatedurlmeet");
        this.newMeetUrl = newMeetUrl;
    }

    public String getNewMeetUrl() {
        return newMeetUrl;
    }
}
