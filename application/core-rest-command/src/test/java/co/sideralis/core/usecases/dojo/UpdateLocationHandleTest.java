package co.sideralis.core.usecases.dojo;

import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.dojo.events.UpdatedLocation;
import co.sideralis.core.dojo.values.OpeningHours;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.usecases.dojo.handle.UpdateLocationHandle;
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

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UpdateLocationHandleTest {
    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;
    @Mock
    private DomainEventRepository repository;

    @Spy
    private SubscriberMockTest subscriber;

    @Test
    void updateLocationHappyPath() throws InterruptedException {
        var handle = initializeHandle();

        var args = new HashMap<String, String>();
        args.put("urlMeet", "https://google.com");
        args.put("location", "Star Building [Medellin/CO]");
        args.put("locationDescription", "Near the park");
        args.put("openHour", "16");
        args.put("closeHour", "18");
        args.put("openingFrequency", OpeningHours.Frequency.WEEKLY.name());

        handle.accept(args);
        handle.run();
        Thread.sleep(150);

        verify(subscriber).onNext(eventCaptor.capture());
        var updatedLocation = (UpdatedLocation) eventCaptor.getAllValues().get(0);

        Assertions.assertEquals("https://google.com", updatedLocation.getLocation().value().urlMeet());
        Assertions.assertEquals(OpeningHours.Frequency.WEEKLY.name(), updatedLocation.getLocation().value().openingHours().frequency());
        Assertions.assertEquals(18, updatedLocation.getLocation().value().openingHours().hourEnd());
        Assertions.assertEquals(16, updatedLocation.getLocation().value().openingHours().hourStart());
    }

    @Test
    void updateLocationSadPath() {
        var handle = initializeHandle();

        var args = new HashMap<String, String>();
        args.put("urlMeet", "https://google.com");
        args.put("location", "Star Building [Medellin/CO]");
        args.put("locationDescription", "Near the park");
        args.put("openHour", "16");
        args.put("closeHour", "18");
        args.put("openingFrequency", OpeningHours.Frequency.WEEKLY.name());

        handle.accept(args);

        //error of url
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            args.put("urlMeet", "httpaaa");
            handle.accept(args);
        });

        //error of opening
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            args.put("openHour", "55");
            args.put("closeHour", "3");
            handle.accept(args);
        });

        //error of opening
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            args.put("openHour", "12");
            args.put("closeHour", "11");
            handle.accept(args);
        });

        //error of opening
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            args.put("openHour", "0");
            args.put("closeHour", "0");
            handle.accept(args);
        });

        //error of opening
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            args.put("openHour", "1");
            args.put("closeHour", "1");
            handle.accept(args);
        });
    }

    private UseCaseExecutor initializeHandle() {
        return new UpdateLocationHandle()
                .withAggregateId("xxx-xxx-xxx")
                .withUseCaseHandler(UseCaseHandler.getInstance())
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber);
    }


}