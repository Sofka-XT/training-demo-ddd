package co.sideralis.core.usecases.challenge;


import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.challenge.events.CreatedChallenge;
import co.sideralis.core.usecases.SubscriberMockTest;
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

import java.util.Map;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CreateChallengeHandleTest {

    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;

    @Mock
    private DomainEventRepository repository;

    @Spy
    private SubscriberMockTest subscriber;

    @ParameterizedTest
    @CsvSource({"hard challenge,30,7,https://github.com/sideralis-co/,summary,xxx", "otro challenge,360,10,otro summary,xxx"})
    void createChallengeHandleHappyPath(String challengeName, String durationDays, String degreeOfDificulty, String summary, String dojoId) throws InterruptedException {

        var handle = new CreateChallengeHandle()
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withAggregateId("xxxx-xxxx")
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "name", challengeName,
                "durationDays", durationDays,
                "degreeOfDifficulty", degreeOfDificulty,
                "summary", summary,
                "dojoId", dojoId
        ));

        handle.run();

        Thread.sleep(80);

        verify(subscriber).onNext(eventCaptor.capture());
        var createdChallenge = (CreatedChallenge) eventCaptor.getValue();

        Assertions.assertEquals("core.challenge.createdchallenge", createdChallenge.type);

        Assertions.assertEquals(challengeName, createdChallenge.getName().value());
        Assertions.assertEquals(durationDays, createdChallenge.getDurationDays().toString());
        Assertions.assertEquals(degreeOfDificulty, createdChallenge.getAssesment().value().degreeOfDificulty().toString());
        Assertions.assertEquals(summary, createdChallenge.getAssesment().value().summary());
        Assertions.assertEquals(dojoId, createdChallenge.getDojoId().value());
        Assertions.assertFalse(createdChallenge.getRevoked());
    }

    @Test
    void createChallengeHandleSadPath() {

        var handle = new CreateChallengeHandle()
                .withSubscriberEvent(subscriber)
                .withAggregateId("xxxx-xxxx")
                .withUseCaseHandler(UseCaseHandler.getInstance());


        Assertions.assertThrows(NullPointerException.class, () -> {
            handle.accept(Map.of("name", "xx"));
        });

        Assertions.assertThrows(NumberFormatException.class, () -> {
            handle.accept(Map.of(
                    "name", "challenge name",
                    "durationDays", "dd",
                    "degreeOfDifficulty", "1",
                    "repoUrl", "https://github.com/sideralis-co/",
                    "summary", "summary",
                    "dojoId", "dojoId"
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "",
                    "durationDays", "15",
                    "degreeOfDifficulty", "1",
                    "repoUrl", "https://github.com/sideralis-co/",
                    "summary", "summary",
                    "dojoId", "dojoId"
            ));
        });

        Assertions.assertThrows(NumberFormatException.class, () -> {
            handle.accept(Map.of(
                    "name", "challenge name",
                    "durationDays", "30",
                    "degreeOfDifficulty", "",
                    "repoUrl", "https://github.com/sideralis-co/",
                    "summary", "summary",
                    "dojoId", "dojoId"
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "challenge name",
                    "durationDays", "30",
                    "degreeOfDifficulty", "5",
                    "repoUrl", "https://github.com/sideralis-co/",
                    "summary", "",
                    "dojoId", "dojoId"
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "challenge name",
                    "durationDays", "30",
                    "degreeOfDifficulty", "5",
                    "repoUrl", "https://github.com/sideralis-co/",
                    "summary", "summary",
                    "dojoId", ""
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "challenge name",
                    "durationDays", "30",
                    "degreeOfDifficulty", "-1",
                    "repoUrl", "https://github.com/sideralis-co/",
                    "summary", "summary",
                    "dojoId", "dojoId"
            ));
        });


        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "challenge name",
                    "durationDays", "45",
                    "degreeOfDifficulty", "100",
                    "repoUrl", "https://github.com/sideralis-co/",
                    "summary", "summary",
                    "dojoId", "dojoId"
            ));
        });
    }
}
