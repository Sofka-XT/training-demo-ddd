package co.sideralis.core.usecases.clan.exception;

import co.com.sofka.business.generic.BusinessException;

public class NecessaryDependency extends BusinessException {
    public NecessaryDependency(String identify, String message) {
        super(identify, message);
    }
}
