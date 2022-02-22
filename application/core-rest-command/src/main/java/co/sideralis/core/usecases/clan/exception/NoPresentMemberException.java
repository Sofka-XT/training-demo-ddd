package co.sideralis.core.usecases.clan.exception;

import co.com.sofka.business.generic.BusinessException;

public class NoPresentMemberException extends BusinessException {
    public NoPresentMemberException(String identify, String message) {
        super(identify, message);
    }
}
