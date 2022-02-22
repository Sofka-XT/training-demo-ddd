package co.sideralis.core.usecases.clan;

import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.events.AddedMember;
import co.sideralis.core.clan.events.CreatedClan;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.usecases.clan.handle.CreateClanHandle;
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

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CreateClanHandleTest {
    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;

    @Mock
    private DomainEventRepository repository;

    @Mock
    private QueryClanService queryClanService;

    @Spy
    private SubscriberMockTest subscriber;

    @ParameterizedTest
    @CsvSource({"dream clan,xxx,raul alzate,example@gmail.com,M", "asdasdasd,xxx,andres,example@gmail.com,F"})
    void createClanHandleHappyPath(String clanName, String personId, String memberName, String email, String memberGender) throws InterruptedException {
        var serviceBuilder = new ServiceBuilder();
        when(queryClanService.existClanName(clanName)).thenReturn(false);
        serviceBuilder.addService(queryClanService);

        var handle = new CreateClanHandle()
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withServiceBuilder(serviceBuilder)
                .withAggregateId("xxxx-xxxx")
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "name", clanName,
                "memberName", memberName,
                "color", "RED",
                "email", email,
                "memberGender", memberGender,
                "personId", personId
        ));
        handle.run();
        Thread.sleep(80);

        verify(subscriber, times(2)).onNext(eventCaptor.capture());
        var createdClan = (CreatedClan) eventCaptor.getAllValues().get(0);
        var addedMember = (AddedMember) eventCaptor.getAllValues().get(1);

        Assertions.assertEquals("core.clan.create", createdClan.type);
        Assertions.assertEquals("core.clan.addedmember", addedMember.type);

        Assertions.assertEquals(clanName, createdClan.getName().value());
        Assertions.assertEquals("RED", createdClan.getColor().value());
        Assertions.assertEquals(memberName, addedMember.getName().value());
        Assertions.assertEquals(memberGender, addedMember.getGender().value());
        Assertions.assertEquals(personId, addedMember.getPersonId().value());
        Assertions.assertEquals(true, addedMember.isOwner());

        verify(queryClanService).existClanName(clanName);
        //TODO: add more assertions for createdGitGroup and addedMember

    }

    @Test
    void createClanHandleSadPath() {
        var serviceBuilder = new ServiceBuilder();
        when(queryClanService.existClanName("my clan")).thenReturn(true);
        serviceBuilder.addService(queryClanService);

        var handle = new CreateClanHandle()
                .withSubscriberEvent(subscriber)
                .withAggregateId("xxxx-xxxx")
                .withServiceBuilder(serviceBuilder)
                .withUseCaseHandler(UseCaseHandler.getInstance());


        Assertions.assertThrows(NullPointerException.class, () -> {
            handle.accept(Map.of("name", "xx"));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "xx cccc",
                    "memberMame", "",
                    "color", "RED",
                    "memberGender", "",
                    "email", "example@gmail.com",
                    "personId", ""
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "xx ccc",
                    "memberName", "raul andres",
                    "email", "example@gmail.com",
                    "color", "RED",
                    "memberGender", "",
                    "personId", ""
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "xx ccc",
                    "memberName", "raul andres",
                    "color", "RED",
                    "email", "example@gmail.com",
                    "memberGender", "F",
                    "personId", ""
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "xx",
                    "memberName", "raul andres",
                    "email", "example@gmail.com",
                    "color", "RED",
                    "memberGender", "F",
                    "personId", "xxxx"
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "xx ccc",
                    "memberName", "raul andres",
                    "email", "example",
                    "memberGender", "F",
                    "color", "RED",
                    "personId", "xxxx-xxxx"
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "xx ccc",
                    "memberName", "raul andres",
                    "email", "example@gmail.com",
                    "memberGender", "F",
                    "color", "ss",
                    "personId", "xxxx-xxxx"
            ));
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", "my clan",
                    "memberName", "raul andres",
                    "email", "example@gmail.com",
                    "memberGender", "F",
                    "color", "RED",
                    "personId", "xxxx-xxxx"
            ));
        });

    }
}