package co.sideralis.core.usecases.clan.exception;

import co.com.sofka.business.generic.BusinessException;

public class NoMoreMemberAllowed extends BusinessException {
    public NoMoreMemberAllowed(String identify) {
        super(identify, "No more member allowed");
    }
}
