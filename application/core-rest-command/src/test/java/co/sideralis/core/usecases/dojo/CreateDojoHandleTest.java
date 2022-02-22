package co.sideralis.core.usecases.dojo;

import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.dojo.events.AssociatedCoach;
import co.sideralis.core.dojo.events.CreatedDojo;
import co.sideralis.core.dojo.values.OpeningHours;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.usecases.dojo.handle.CreateDojoHandle;
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
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CreateDojoHandleTest {
    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;

    @Mock
    private DomainEventRepository repository;
    @Mock
    private QueryDojoService queryDojoService;
    @Spy
    private SubscriberMockTest subscriber;

    @Test
    public void createDojoHappyPath() throws InterruptedException {
        var serviceBuilder = new ServiceBuilder();
        when(queryDojoService.existDojoName("Super Dojo")).thenReturn(false);
        serviceBuilder.addService(queryDojoService);

        var handle = initializeHandle().withServiceBuilder(serviceBuilder);
        var args = new HashMap<String, String>();
        args.put("coachId", "ffff-fff");
        args.put("personId", "xxx-zzzz");
        args.put("coachName", "Raul A. Alzate");
        args.put("name", "Super Dojo");
        args.put("legend", "The best dojo site");
        args.put("urlMeet", "https://google.com");
        args.put("location", "Star Building [Medellin/CO]");
        args.put("locationDescription", "Near the park");
        args.put("openHour", "16");
        args.put("closeHour", "18");
        args.put("email", "alzategomez.raul@gmail.com");
        args.put("openingFrequency", OpeningHours.Frequency.WEEKLY.name());
        args.put("coachSpecialty", "Java Developer");

        handle.accept(args);
        handle.run();
        Thread.sleep(150);

        verify(subscriber, times(2)).onNext(eventCaptor.capture());
        var createdDojo = (CreatedDojo) eventCaptor.getAllValues().get(0);
        var associatedCoach = (AssociatedCoach) eventCaptor.getAllValues().get(1);

        Assertions.assertEquals("ffff-fff", createdDojo.getCoachId().value());
        Assertions.assertEquals("The best dojo site", createdDojo.getDataInfo().value().legend());
        Assertions.assertEquals("super dojo", createdDojo.getDataInfo().value().name());
        Assertions.assertEquals("https://google.com", createdDojo.getLocation().value().urlMeet());
        Assertions.assertEquals(OpeningHours.Frequency.WEEKLY.name(), createdDojo.getLocation().value().openingHours().frequency());
        Assertions.assertEquals(18, createdDojo.getLocation().value().openingHours().hourEnd());
        Assertions.assertEquals(16, createdDojo.getLocation().value().openingHours().hourStart());

        Assertions.assertEquals("raul a. alzate", associatedCoach.getCouchName().value());
        Assertions.assertEquals("alzategomez.raul@gmail.com", associatedCoach.getEmail().value());
        Assertions.assertEquals("xxx-zzzz", associatedCoach.getPersonId().value());
        Assertions.assertEquals("Java Developer", associatedCoach.getSpecialty().value());
        Assertions.assertEquals("ffff-fff", associatedCoach.getCoachId().value());
        verify(queryDojoService).existDojoName("Super Dojo");
    }

    @Test
    public void createDojoHappyPath_withoutLocation() throws InterruptedException {
        var serviceBuilder = new ServiceBuilder();
        when(queryDojoService.existDojoName("Super Dojo")).thenReturn(false);
        serviceBuilder.addService(queryDojoService);

        var handle = initializeHandle().withServiceBuilder(serviceBuilder);
        var args = new HashMap<String, String>();
        args.put("coachId", "ffff-fff");
        args.put("personId", "xxx-zzzz");
        args.put("coachName", "Raul A. Alzate");
        args.put("name", "Super Dojo");
        args.put("legend", "The best dojo site");
        args.put("email", "alzategomez.raul@gmail.com");
        args.put("coachSpecialty", "Java Developer");

        handle.accept(args);
        handle.run();
        Thread.sleep(150);

        verify(subscriber, times(2)).onNext(eventCaptor.capture());
        var createdDojo = (CreatedDojo) eventCaptor.getAllValues().get(0);
        var associatedCoach = (AssociatedCoach) eventCaptor.getAllValues().get(1);

        Assertions.assertEquals("ffff-fff", createdDojo.getCoachId().value());
        Assertions.assertEquals("The best dojo site", createdDojo.getDataInfo().value().legend());
        Assertions.assertEquals("super dojo", createdDojo.getDataInfo().value().name());

        Assertions.assertEquals("raul a. alzate", associatedCoach.getCouchName().value());
        Assertions.assertEquals("alzategomez.raul@gmail.com", associatedCoach.getEmail().value());
        Assertions.assertEquals("xxx-zzzz", associatedCoach.getPersonId().value());
        Assertions.assertEquals("Java Developer", associatedCoach.getSpecialty().value());
        Assertions.assertEquals("ffff-fff", associatedCoach.getCoachId().value());
        verify(queryDojoService).existDojoName("Super Dojo");
    }

    @Test
    public void createDojoSanPath() {
        var serviceBuilder = new ServiceBuilder();
        serviceBuilder.addService(queryDojoService);

        var handle = initializeHandle().withServiceBuilder(serviceBuilder);
        var args = new HashMap<String, String>();
        args.put("coachId", "ffff-fff");
        args.put("personId", "xxx-zzzz");
        args.put("coachName", "Raul A. Alzate");
        args.put("name", "Super Dojo");
        args.put("legend", "The best dojo site");
        args.put("urlMeet", "https://google.com");
        args.put("location", "Star Building [Medellin/CO]");
        args.put("locationDescription", "Near the park");
        args.put("openHour", "16");
        args.put("closeHour", "18");
        args.put("email", "alzategomez.raul@gmail.com");
        args.put("openingFrequency", OpeningHours.Frequency.WEEKLY.name());
        args.put("coachSpecialty", "Java Developer");

        //validate fields empty no allow
        Stream.of(
                "personId", "coachId", "coachName", "name", "legend", "location", "locationDescription", "email", "coachSpecialty"
        ).forEach(field -> validationEmptyField(args, field, handle));
        //validate fields data no allow
        Stream.of(
                "personId", "coachId", "coachName", "name", "legend", "location", "locationDescription", "email", "coachSpecialty"
        ).forEach(field -> validationErrorDataField(args, field, handle));

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

    private void validationEmptyField(Map<String, String> args, String field, UseCaseExecutor handle) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            args.put(field, "");
            handle.accept(args);
        });
    }

    private void validationErrorDataField(Map<String, String> args, String field, UseCaseExecutor handle) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            args.put(field, "a");
            handle.accept(args);
        });
    }

    private UseCaseExecutor initializeHandle() {

        return new CreateDojoHandle()
                .withAggregateId("xxx-xxx-xxx")
                .withUseCaseHandler(UseCaseHandler.getInstance())
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber);
    }
}