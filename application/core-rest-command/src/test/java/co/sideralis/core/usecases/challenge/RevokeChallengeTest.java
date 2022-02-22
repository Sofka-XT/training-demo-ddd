package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.challenge.events.AddedKata;
import co.sideralis.core.challenge.events.ChallengeRevoked;
import co.sideralis.core.challenge.events.CreatedChallenge;
import co.sideralis.core.challenge.values.Assesment;
import co.sideralis.core.challenge.values.KataId;
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
public class RevokeChallengeTest {

    @Mock
    private DomainEventRepository repository;
    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;
    @Spy
    private SubscriberMockTest subscriber;

    @Test
    void revokeChallengeHandleHappyPath() throws InterruptedException {
        String aggregateRootId = "95261ecc-ec21-4c4e-be63-eb12fb75387d";

        when(repository.getEventsBy(any())).thenReturn(domainEvents());

        var handle = new RevokeChallengeHandle()
                .withAggregateId(aggregateRootId)
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "aggregateId", aggregateRootId
        ));

        handle.run();

        Thread.sleep(100);

        verify(subscriber).onNext(eventCaptor.capture());

        var event = eventCaptor.getValue();
        var challengeRevoked = (ChallengeRevoked) event;

        Assertions.assertEquals("core.challenge.challengeRevoked", challengeRevoked.type);
        Assertions.assertTrue(challengeRevoked.getRevoked());
    }

    private List<DomainEvent> domainEvents() {
        return List.of(
                new CreatedChallenge(
                        new Name("My Challenge"),
                        DojoId.of("dojoId"),
                        new Assesment(7, "summary"),
                        30),
                new AddedKata(KataId.of("KataId"),
                        "Improve your skills",
                       3)
        );
    }
}
