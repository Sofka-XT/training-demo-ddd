package co.sideralis.core.application.service;

public class IntegrationServiceException extends RuntimeException {
    public IntegrationServiceException(Throwable throwable) {
        super(throwable);
    }
}
