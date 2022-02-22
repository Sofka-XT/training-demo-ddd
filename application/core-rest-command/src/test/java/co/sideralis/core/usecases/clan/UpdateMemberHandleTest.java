package co.sideralis.core.usecases.clan;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.events.AddedMember;
import co.sideralis.core.clan.events.CreatedClan;
import co.sideralis.core.clan.events.UpdatedMember;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.usecases.clan.exception.NoPresentMemberException;
import co.sideralis.core.usecases.clan.handle.UpdateMemberHandle;
import co.sideralis.core.values.Color;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UpdateMemberHandleTest {

    @Captor
    private ArgumentCaptor<UpdatedMember> eventCaptor;

    @Captor
    private ArgumentCaptor<NoPresentMemberException> eventErrorCaptor;

    @Spy
    private SubscriberMockTest subscriber;

    @Mock
    private DomainEventRepository repository;

    @Test
    void updateStudentHappyPath() throws InterruptedException {
        when(repository.getEventsBy("xxx-xxx-xx")).thenReturn(domainEvents());
        var handle = new UpdateMemberHandle()
                .withAggregateId("xxx-xxx-xx")
                .withSubscriberEvent(subscriber)
                .withDomainEventRepo(repository)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "memberId", "zzzzz-zzzz",
                "name", "Raul Alzate Gomez"
        ));
        handle.run();
        Thread.sleep(120);

        verify(subscriber).onNext(eventCaptor.capture());
        var event = eventCaptor.getValue();

        Assertions.assertEquals("core.clan.updatedmember", event.type);
        Assertions.assertEquals("zzzzz-zzzz", event.getMemberId().value());
        Assertions.assertEquals("raul alzate gomez", event.getName().value());

    }


    @ParameterizedTest
    @CsvSource({"xxxxx,a", "a,a"})
    void updateStudentSadPath(String memberId, String name) {
        var handle = new UpdateMemberHandle()
                .withAggregateId("xxx-xxx-xx")
                .withUseCaseHandler(UseCaseHandler.getInstance());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "memberId", memberId,
                    "name", name
            ));
        });
    }

    @Test
    void updateMemberSadPathNoMemberPresent() throws InterruptedException {
        when(repository.getEventsBy("xxx-xxx-xx")).thenReturn(domainEvents());
        var handle = new UpdateMemberHandle()
                .withAggregateId("xxx-xxx-xx")
                .withSubscriberEvent(subscriber)
                .withDomainEventRepo(repository)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "memberId", "ffff-fffff",//no existe
                "name", "Raul Alzate Gomez"
        ));
        handle.run();
        Thread.sleep(120);

        verify(subscriber).onError(eventErrorCaptor.capture());
        var error = eventErrorCaptor.getValue();

        Assertions.assertEquals("No member present", error.getMessage());
    }

    private List<DomainEvent> domainEvents() {
        return List.of(new CreatedClan(
                new Name("My Dream Clan"),
                new Color(Color.Type.BLUE)
        ), new AddedMember(
                MemberId.of("zzzzz-zzzz"),
                PersonId.of("zzzzz-zzzz"),
                new Email("alzate@gmail.com"),
                new Name("Raul A. Alzate"),
                new Gender(Gender.Type.M)
        ));
    }
}