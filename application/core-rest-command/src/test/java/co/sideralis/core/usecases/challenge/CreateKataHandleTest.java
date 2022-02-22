package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.challenge.events.AddedKata;
import co.sideralis.core.challenge.events.CreatedChallenge;
import co.sideralis.core.challenge.values.Assesment;
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
public class CreateKataHandleTest {

    @Mock
    private DomainEventRepository repository;
    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;
    @Spy
    private SubscriberMockTest subscriber;


    @ParameterizedTest
    @CsvSource(
            {
                    "xxx-xxx-xx,zzz-zzz-zzz,3,learn a lot of new stuff",
                    "xxx-xxx-xx,zzz-zzz-zzz,4,Practice your knowledge",
                    "xxx-xxx-xx,zzz-zzz-zzz,2,improve yours skills"
            })
    void addNewKataHandleHappyPath(String id, String kataId, String limitOfTime, String purpose) throws InterruptedException {

        when(repository.getEventsBy(any())).thenReturn(domainEvents());

        var handle = new CreateKataHandle()
                .withAggregateId(id)
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "aggregateId", id,
                "kataId", kataId,
                "limitOfTime", limitOfTime,
                "purpose", purpose
        ));

        handle.run();

        Thread.sleep(100);

        verify(subscriber).onNext(eventCaptor.capture());

        var event = eventCaptor.getValue();
        var addedKata = (AddedKata) event;

        Assertions.assertEquals("core.challenge.addedKata", addedKata.type);
        Assertions.assertEquals(kataId, addedKata.getKataId().value());
        Assertions.assertEquals(Integer.valueOf(limitOfTime), addedKata.getLimitOfTime());
        Assertions.assertEquals(purpose, addedKata.getPurpose());
    }

    @Test
    public void createKataHandleSadPath() {
        String agregadoId = "xxx-zzz-xxx";

        var handle = new CreateKataHandle()
                .withAggregateId(agregadoId)
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "kataId", "",
                    "limitOfTime", "1605416400000",
                    "purpose", "puspose"
            ));
        });

        Assertions.assertThrows(NumberFormatException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "kataId", "kataId",
                    "limitOfTime", "",
                    "purpose", "puspose"
            ));
        });

        Assertions.assertThrows(NumberFormatException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "kataId", "kataId",
                    "limitOfTime", "ddd",
                    "purpose", "puspose"
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "aggregateId", agregadoId,
                    "kataId", "kataId",
                    "limitOfTime", "1605416400000",
                    "purpose", ""
            ));
        });

    }

    private List<DomainEvent> domainEvents() {
        return List.of(new CreatedChallenge(
                new Name("My Challenge"),
                DojoId.of("dojoId"),
                new Assesment(7, "summary"),
                30 )
        );
    }
}
