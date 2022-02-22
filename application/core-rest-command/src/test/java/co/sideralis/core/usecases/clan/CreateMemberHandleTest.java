package co.sideralis.core.usecases.clan;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.clan.events.AddedMember;
import co.sideralis.core.clan.events.CreatedClan;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.events.CreatedGitGroup;
import co.sideralis.core.usecases.SubscriberMockTest;
import co.sideralis.core.usecases.clan.exception.NoMoreMemberAllowed;
import co.sideralis.core.usecases.clan.handle.CreateMemberHandle;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CreateMemberHandleTest {

    @Mock
    private DomainEventRepository repository;
    @Captor
    private ArgumentCaptor<DomainEvent> eventCaptor;
    @Captor
    private ArgumentCaptor<NoMoreMemberAllowed> allowedArgumentCaptor;
    @Spy
    private SubscriberMockTest subscriber;


    @ParameterizedTest
    @CsvSource(
            {
                    "xxx-xxx-xx,zzz-zzz-zzz,example@gmail.com,raul andres alzate,M,true",
                    "xxx-xxx-xx,zzz-zzz-zzz,example@gmail.com,ana maria valencia,F,false",
                    "xxx-xxx-xx,zzz-zzz-zzz,example@gmail.com,andres felipe,M,false"
            })
    void createMemberHandleHappyPath(
            String id,
            String personId,
            String email,
            String name,
            String gender,
            String isOwner
    ) throws InterruptedException {

        when(repository.getEventsBy(any())).thenReturn(domainEvents());

        var handle = new CreateMemberHandle()
                .withAggregateId(id)
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "aggregateId", id,
                "name", name,
                "email", email,
                "gender", gender,
                "personId", personId,
                "isOwner", isOwner
        ));

        handle.run();

        Thread.sleep(100);

        verify(subscriber).onNext(eventCaptor.capture());

        var event = eventCaptor.getValue();
        var addedMember = (AddedMember) event;

        Assertions.assertEquals("core.clan.addedmember", addedMember.type);
        Assertions.assertEquals(name, addedMember.getName().value());
        Assertions.assertEquals(gender, addedMember.getGender().value());
        Assertions.assertEquals(personId, addedMember.getPersonId().value());
        Assertions.assertEquals(Boolean.valueOf(isOwner), addedMember.isOwner());

    }


    @ParameterizedTest
    @CsvSource(
            {
                    "xxx-xxx-xx,zzz-zzz-zzz,raul andres alzate,K,false,example@gmail.com,No enum constant co.sideralis.core.clan.values.Gender.Type.K",
                    "xxxxx,,raul andres alzate,M,4,example@gmail.com,Identity can´t be blank",
                    "xxxxx,ffffff,raul andres alzate,M,true,example,The email is no valid"
            })
    public void createStudentHandleSadPath(String id,
                                           String personId,
                                           String name,
                                           String gender,
                                           String isOwner,
                                           String email,
                                           String message
    ) {
        var handle = new CreateMemberHandle().withAggregateId(id);
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            handle.accept(Map.of(
                    "name", name,
                    "gender", gender,
                    "email", email,
                    "personId", personId == null ? " " : personId,
                    "isOwner", isOwner
            ));
        });
        Assertions.assertEquals(message, thrown.getMessage());
    }

    @Test
    void validCreateStudentSadPath_NoMoreMemberAllowed() throws InterruptedException {
        when(repository.getEventsBy(any())).thenReturn(domainEventsFullyMembers());

        var handle = new CreateMemberHandle()
                .withAggregateId("xxx-xxx-xx")
                .withDomainEventRepo(repository)
                .withSubscriberEvent(subscriber)
                .withUseCaseHandler(UseCaseHandler.getInstance());

        handle.accept(Map.of(
                "aggregateId", "xxx-xxx-xx",
                "name", "Raul A. Alzate",
                "gender", "F",
                "email", "example@gmail.com",
                "personId", "xxx-xxx-xx",
                "isOwner", "false"
        ));
        handle.run();
        Thread.sleep(100);

        verify(subscriber).onError(allowedArgumentCaptor.capture());

        var error = allowedArgumentCaptor.getValue();
        Assertions.assertEquals("No more member allowed", error.getMessage());
    }

    private List<DomainEvent> domainEventsFullyMembers() {
        return List.of(
                new CreatedClan(
                        new Name("My Dream Clan"),
                        new Color(Color.Type.BLUE)

                ),
                new CreatedGitGroup(
                        10, "my_dream_clan", "My Dream Clan"
                ),
                new AddedMember(
                        MemberId.of("yyyy-xxx-1"),
                        PersonId.of("yyyy-xxx-1"),
                        new Email("raul.alzate@gmail.com"),
                        new Name("andres felipe"),
                        new Gender(Gender.Type.M)
                ),
                new AddedMember(
                        MemberId.of("yyyy-xxx-2"),
                        PersonId.of("yyyy-xxx-2"),
                        new Email("raul.alzate@gmail.com"),
                        new Name("camilo andres"),
                        new Gender(Gender.Type.M)
                ),
                new AddedMember(
                        MemberId.of("yyyy-xxx-3"),
                        PersonId.of("yyyy-xxx-3"),
                        new Email("raul.alzate@gmail.com"),
                        new Name("sofia"),
                        new Gender(Gender.Type.F)
                ), new AddedMember(
                        MemberId.of("yyyy-xxx-4"),
                        PersonId.of("yyyy-xxx-4"),
                        new Email("raul.alzate@gmail.com"),
                        new Name("sandro mesa"),
                        new Gender(Gender.Type.M)
                ),
                new AddedMember(
                        MemberId.of("yyyy-xxx-5"),
                        PersonId.of("yyyy-xxx-5"),
                        new Email("raul.alzate@gmail.com"),
                        new Name("ana"),
                        new Gender(Gender.Type.F)
                ),
                new AddedMember(
                        MemberId.of("yyyy-xxx-6"),
                        PersonId.of("yyyy-xxx-6"),
                        new Email("raul.alzate@gmail.com"),
                        new Name("laura"),
                        new Gender(Gender.Type.F)
                )
        );
    }

    private List<DomainEvent> domainEvents() {
        return List.of(new CreatedClan(
                        new Name("My Dream Clan"),
                        new Color(Color.Type.BLUE)

                ),
                new CreatedGitGroup(
                        10, "my_dream_clan", "My Dream Clan"
                ),
                new CreatedGitGroup(
                        10, "my_dream_clan", "My Dream Clan"
                ));
    }
}