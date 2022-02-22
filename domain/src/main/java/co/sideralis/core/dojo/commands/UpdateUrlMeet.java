package co.sideralis.core.dojo.commands;

import co.com.sofka.domain.generic.Command;

public class UpdateUrlMeet implements Command {
    private final String newMeetUrl;

    public UpdateUrlMeet(String newMeetUrl) {

        this.newMeetUrl = newMeetUrl;
    }

    public String getNewMeetUrl() {
        return newMeetUrl;
    }
}
