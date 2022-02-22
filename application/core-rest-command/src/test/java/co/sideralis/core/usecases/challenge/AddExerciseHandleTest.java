package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.challenge.events.*;
import co.sideralis.core.challenge.values.Assesment;
import co.sideralis.core.challenge.values.KataId;
import co.sideralis.core.dojo.values.DojoId;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.values.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
public class AddExerciseHandleTest {

    @Mock
    private DomainEventRepository repository;
    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;
    @Spy
    private SubscriberMockTest subscriber;

    @ParameterizedTest
    @CsvSource(
            {
                    "xxx-xxx-xx,KataId,3,learn a lot of new stuff",
                    "xxx-xxx-xx,KataId,4,Practice your knowledge",
                    "xxx-xxx-xx,KataId,2,improve yours skills"
            })
    void addExerciceHandleHappyPath(String id, String kataId, String level, String goal) throws InterruptedException {

        when(repository.getEventsBy(any())).thenReturn(domainEvents());

        var handle = new AddExerciseHandle()
                .withAggregateId(id)
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "aggregateId", id,
                "kataId", kataId,
                "level", level,
                "goal", goal
        ));

        handle.run();
        Thread.sleep(100);
        verify(subscriber).onNext(eventCaptor.capture());

        var event = eventCaptor.getValue();
        var addedExercise = (AddedExercise) event;

        Assertions.assertEquals("core.challenge.addedexercise", addedExercise.type);
        Assertions.assertEquals(kataId, addedExercise.getKataId().value());
        Assertions.assertEquals(Integer.valueOf(level), addedExercise.getExercise().value().level());
        Assertions.assertEquals(goal, addedExercise.getExercise().value().goal());
    }

    @Test
    public void addExerciseHandleSadPath() {
        String agregadoId = "xxx-zzz-xxx";

        var handle = new AddExerciseHandle()
                .withAggregateId(agregadoId)
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "kataId", "",
                    "level", "10",
                    "goal", "be the best"
            ));
        });

        Assertions.assertThrows(NumberFormatException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "kataId", "kataId",
                    "level", "",
                    "goal", "goal"
            ));
        });

        Assertions.assertThrows(NumberFormatException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "kataId", "kataId",
                    "level", "ddd",
                    "goal", "learn"
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "kataId", "kataId",
                    "learn", "1",
                    "goal", ""
            ));
        });
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
                        3),
                new ChallengeRevoked(),
                new UrlRepoAssigned("https://github.com/sideralis-co/web")
        );
    }
}
