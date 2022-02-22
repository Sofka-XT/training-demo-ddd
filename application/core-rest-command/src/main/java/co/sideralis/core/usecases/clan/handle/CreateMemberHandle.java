package co.sideralis.core.usecases.clan.handle;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.clan.Clan;
import co.sideralis.core.clan.commands.AddNewMember;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.usecases.clan.exception.NoMoreMemberAllowed;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.clan.createmember", aggregate = "clan")
public class CreateMemberHandle extends UseCaseExecutor {

    private RequestCommand<AddNewMember> request;

    @Override
    public void accept(Map<String, String> args) {
        var id = ClanId.of(aggregateId());
        var personId = args.get("personId");
        var isOwner = args.getOrDefault("isOwner", "false");
        var memberId = args.getOrDefault("memberId", new MemberId().value());
        var email = args.get("email");

        request = new RequestCommand<>(new AddNewMember(
                id,
                MemberId.of(memberId),
                PersonId.of(personId),
                new Email(email),
                new Name(args.getOrDefault("name", "")),
                new Gender(Gender.Type.valueOf(args.get("gender"))),
                Boolean.valueOf(isOwner)
        ));

    }

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<AddNewMember> objectInput) {
                var command = objectInput.getCommand();
                var aggregateId = command.getClanId();

                var events = repository().getEventsBy(aggregateId.value());
                var clan = Clan.from(aggregateId, events);

                var size = clan.members().size();

                if (size >= 5) {
                    throw new NoMoreMemberAllowed(aggregateId.value());
                }

                clan.addNewMember(
                        command.getMemberId(),
                        command.getPersonId(),
                        command.getEmail(),
                        command.getName(),
                        command.getGender(),
                        command.isOwner()
                );
                emit().onSuccess(new ResponseEvents(clan.getUncommittedChanges()));
            }
        }, request);
    }

}
