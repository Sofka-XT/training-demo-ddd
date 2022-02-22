package co.sideralis.core.usecases.dojo;

import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.dojo.events.CreatedDojo;
import co.sideralis.core.dojo.events.RemovedDojoRule;
import co.sideralis.core.dojo.values.CoachId;
import co.sideralis.core.dojo.values.DataInfo;
import co.sideralis.core.dojo.values.Location;
import co.sideralis.core.dojo.values.OpeningHours;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.usecases.dojo.handle.RemoveRuleHandle;
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
class RemoveRuleHandleTest {
    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;
    @Mock
    private DomainEventRepository repository;
    @Spy
    private SubscriberMockTest subscriber;

    @Test
    void removeRuleHappyPath() throws InterruptedException {
        when(repository.getEventsBy(any())).thenReturn(domainEvents());

        var handle = initializeHandle();

        handle.accept(Map.of(
                "rule", "#1 the rule for dojo"
        ));
        handle.run();
        Thread.sleep(120);
        verify(subscriber).onNext(eventCaptor.capture());
        var removedDojoRule = (RemovedDojoRule) eventCaptor.getAllValues().get(0);

        Assertions.assertEquals("#1 the rule for dojo", removedDojoRule.getRule());

    }

    @Test
    void removeRuleSadPath() {
        var handle = initializeHandle();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "rule", ""
            ));
        });
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
        return new RemoveRuleHandle()
                .withAggregateId("xxx-xxx-xxx")
                .withUseCaseHandler(UseCaseHandler.getInstance())
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber);
    }
}