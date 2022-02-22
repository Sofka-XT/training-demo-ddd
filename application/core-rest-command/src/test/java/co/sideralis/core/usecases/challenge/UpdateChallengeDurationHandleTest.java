package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.challenge.events.CreatedChallenge;
import co.sideralis.core.challenge.events.UpdatedChallengeDuration;
import co.sideralis.core.challenge.values.Assesment;
import co.sideralis.core.dojo.values.DojoId;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.values.Name;
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

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UpdateChallengeDurationHandleTest {

    @Mock
    private DomainEventRepository repository;
    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;
    @Spy
    private SubscriberMockTest subscriber;

    @Test
    void updateChallengeDurationHandleHappyPath() throws InterruptedException {

        when(repository.getEventsBy(any())).thenReturn(domainEvents());

        var handle = new UpdateChallengeDurationHandle()
                .withAggregateId("xxxx-zzzzz-yyyy")
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "aggregateId", "xxxx-zzzzz-yyyy",
                "newDurationDays", "60"
        ));

        handle.run();
        Thread.sleep(100);
        verify(subscriber).onNext(eventCaptor.capture());

        var event = eventCaptor.getValue();
        var updatedChallengeDuration = (UpdatedChallengeDuration) event;

        Assertions.assertEquals("core.challenge.updatedChallengeDuration", updatedChallengeDuration.type);
        Assertions.assertEquals(60, updatedChallengeDuration.getNewDurationDays());
    }

    @Test
    public void updateChallengeDurationHandleSadPath() {
        String agregadoId = "xxx-zzz-xxx";

        var handle = new UpdateChallengeDurationHandle()
                .withAggregateId(agregadoId)
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "newDurationDays", ""
            ));
        });

        Assertions.assertThrows(NullPointerException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "newDurationDays", null
            ));
        });

        Assertions.assertThrows(NumberFormatException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "newDurationDays", "d"
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "newDurationDays", "0"
            ));
        });

    }

    private List<DomainEvent> domainEvents() {
        return List.of(
                new CreatedChallenge(
                        new Name("My Challenge"),
                        DojoId.of("dojoId"),
                        new Assesment(7, "summary"),
                        30)
        );
    }
}
