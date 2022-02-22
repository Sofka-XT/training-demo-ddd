package co.sideralis.core.usecases.dojo;

import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.dojo.events.CreatedDojo;
import co.sideralis.core.dojo.events.UpdatedOpeningHours;
import co.sideralis.core.dojo.values.CoachId;
import co.sideralis.core.dojo.values.DataInfo;
import co.sideralis.core.dojo.values.Location;
import co.sideralis.core.dojo.values.OpeningHours;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.usecases.dojo.handle.UpdateOpeningHourHandle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UpdateOpeningHourHandleTest {

    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;
    @Mock
    private DomainEventRepository repository;
    @Spy
    private SubscriberMockTest subscriber;

    @Test
    void updateOpeningHappyPath() throws InterruptedException {
        when(repository.getEventsBy(any())).thenReturn(domainEvents());

        var handle = initializeHandle();
        var args = new HashMap<String, String>();
        args.put("openHour", "16");
        args.put("closeHour", "18");
        args.put("openingFrequency", OpeningHours.Frequency.WEEKLY.name());
        handle.accept(args);
        handle.run();
        Thread.sleep(120);

        verify(subscriber).onNext(eventCaptor.capture());
        var updatedOpeningHours = (UpdatedOpeningHours) eventCaptor.getAllValues().get(0);

        Assertions.assertEquals(16, updatedOpeningHours.getNewOpeningHours().value().hourStart());
        Assertions.assertEquals(18, updatedOpeningHours.getNewOpeningHours().value().hourEnd());
        Assertions.assertEquals(OpeningHours.Frequency.WEEKLY.name(), updatedOpeningHours.getNewOpeningHours().value().frequency());
    }

    private List<DomainEvent> domainEvents() {
        return List.of(new CreatedDojo(
                CoachId.of("xxx-xxx"),
                new DataInfo("My Dojo", "The best dojo"),
                new Location(null, "Medellin", "Description",
                        new OpeningHours(
                                11, 13, OpeningHours.Frequency.EACH_THEE_DAY
                        )
                )
        ));
    }

    private UseCaseExecutor initializeHandle() {

        return new UpdateOpeningHourHandle()
                .withAggregateId("xxx-xxx-xxx")
                .withUseCaseHandler(UseCaseHandler.getInstance())
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber);
    }
}