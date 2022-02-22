package co.sideralis.core.usecases.clan;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.events.CreatedClan;
import co.sideralis.core.clan.events.UpdatedName;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.usecases.clan.handle.UpdateClanHandle;
import co.sideralis.core.values.Color;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UpdateClanHandleTest {

    @Captor
    private ArgumentCaptor<UpdatedName> eventCaptor;

    @Spy
    private SubscriberMockTest subscriber;

    @Mock
    private DomainEventRepository repository;

    @Test
    void updateTeamHappyPath() throws InterruptedException {
        when(repository.getEventsBy("xxx-xxx")).thenReturn(domainEvents());
        var handle = new UpdateClanHandle()
                .withAggregateId("xxx-xxx")
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "name", "Nuevo Equipo"
        ));

        handle.run();

        Thread.sleep(300);

        verify(subscriber).onNext(eventCaptor.capture());
        var event = eventCaptor.getValue();

        Assertions.assertEquals("core.clan.updatedname", event.type);
        Assertions.assertEquals("nuevo equipo", event.getNewName().value());

    }


    @Test
    void updateTeamSadPath() {
        var handle = new UpdateClanHandle()
                .withAggregateId("xxx-xxx")
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        var exception = Assertions.assertThrows(NullPointerException.class, () -> {
            handle.accept(Map.of());
        });

        Assertions.assertEquals("the value name is not valid", exception.getMessage());
    }

    private List<DomainEvent> domainEvents() {
        return List.of(new CreatedClan(
                new Name("My Dream Clan"),
                new Color(Color.Type.BLUE)

        ));
    }
}