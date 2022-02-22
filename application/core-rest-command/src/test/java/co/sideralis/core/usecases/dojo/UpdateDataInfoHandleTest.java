package co.sideralis.core.usecases.dojo;

import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.dojo.events.CreatedDojo;
import co.sideralis.core.dojo.events.UpdatedDateInfo;
import co.sideralis.core.dojo.values.CoachId;
import co.sideralis.core.dojo.values.DataInfo;
import co.sideralis.core.dojo.values.Location;
import co.sideralis.core.dojo.values.OpeningHours;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.usecases.dojo.handle.UpdateDataInfoHandle;
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
class UpdateDataInfoHandleTest {
    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;

    @Mock
    private DomainEventRepository repository;
    @Mock
    private QueryDojoService queryDojoService;
    @Spy
    private SubscriberMockTest subscriber;

    @Test
    void updateDataInfoHappyPath() throws InterruptedException {
        var serviceBuilder = new ServiceBuilder();
        when(repository.getEventsBy(any())).thenReturn(domainEvents());
        when(queryDojoService.existDojoName("Super Dojo")).thenReturn(false);
        serviceBuilder.addService(queryDojoService);

        var handle = initializeHandle().withServiceBuilder(serviceBuilder);

        handle.accept(Map.of(
                "name", "Super Dojo",
                "legend", "The best dojo place"
        ));
        handle.run();
        Thread.sleep(150);

        verify(subscriber).onNext(eventCaptor.capture());
        verify(queryDojoService).existDojoName("Super Dojo");
        var updatedDateInfo = (UpdatedDateInfo) eventCaptor.getAllValues().get(0);

        Assertions.assertEquals("super dojo", updatedDateInfo.getDataInfo().value().name());
        Assertions.assertEquals("The best dojo place", updatedDateInfo.getDataInfo().value().legend());
    }

    @Test
    void updateDataInfoSadPath_existName() {
        var serviceBuilder = new ServiceBuilder();
        when(queryDojoService.existDojoName("Super Dojo")).thenReturn(true);
        serviceBuilder.addService(queryDojoService);

        var handle = initializeHandle().withServiceBuilder(serviceBuilder);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "Super Dojo",
                    "legend", "The best dojo place"
            ));
        });
    }


    private UseCaseExecutor initializeHandle() {

        return new UpdateDataInfoHandle()
                .withAggregateId("xxx-xxx-xxx")
                .withUseCaseHandler(UseCaseHandler.getInstance())
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber);
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

}