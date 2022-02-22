package co.sideralis.core.usecases.clan;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.events.AddedMember;
import co.sideralis.core.clan.events.CreatedClan;
import co.sideralis.core.clan.events.RevokedMember;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.usecases.clan.handle.RevokeMemberHandle;
import co.sideralis.core.values.Color;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;
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
class RemoveMemberHandleTest {

    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;
    @Captor
    private ArgumentCaptor<BusinessException> exceptionArgumentCaptor;
    @Spy
    private SubscriberMockTest subscriber;
    @Mock
    private DomainEventRepository repository;


    @Test
    void removeMemberHappyPath() throws InterruptedException {

        when(repository.getEventsBy(any())).thenReturn(domainEvents());

        var handle = new RevokeMemberHandle()
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withAggregateId("xxx-xxx-xx")
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "memberId", "yyyy-yyyy"
        ));

        handle.run();
        Thread.sleep(100);

        verify(subscriber).onNext(eventCaptor.capture());
        var revokedMember = (RevokedMember) eventCaptor.getAllValues().get(0);

        Assertions.assertEquals("core.clan.revokedmember", revokedMember.type);

        Assertions.assertEquals("yyyy-yyyy", revokedMember.getMemberId().value());

    }

    @Test
    void deleteMemberSadPath() {
        var handle = new RevokeMemberHandle()
                .withAggregateId("xxx-xxx-xx")
                .withUseCaseHandler(UseCaseHandler.getInstance());

        Assertions.assertEquals("The member id is required",
                Assertions.assertThrows(NullPointerException.class, () -> {
                    handle.accept(Map.of());
                }).getMessage());

    }

    private List<DomainEvent> domainEvents() {
        return List.of(new CreatedClan(
                        new Name("My Dream Clan"),
                        new Color(Color.Type.BLUE)
                ),
                new AddedMember(
                        MemberId.of("yyyy-yyyy"),
                        PersonId.of("yyyy-xxx-1"),
                        new Email("alzate@gmail.com"),
                        new Name("andres felipe"),
                        new Gender(Gender.Type.M)
                )
        );
    }
}