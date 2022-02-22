package co.sideralis.core.usecases.clan.handle;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.annotation.ExtensionService;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.application.service.RestQueryClanService;
import co.sideralis.core.clan.Clan;
import co.sideralis.core.clan.commands.CreateClan;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.Gender;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.usecases.clan.QueryClanService;
import co.sideralis.core.values.Color;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

import java.util.Map;


@CommandHandles
@CommandType(name = "core.clan.create", aggregate = "clan")
@ExtensionService({RestQueryClanService.class})
public class CreateClanHandle extends UseCaseExecutor {
    private RequestCommand<CreateClan> request;

    @Override
    public void accept(Map<String, String> args) {
        QueryClanService restQuery = getService(QueryClanService.class).orElseThrow();
        var id = ClanId.of(aggregateId());
        var personId = PersonId.of(args.get("personId"));

        request = new RequestCommand<>(
                new CreateClan(
                        id,
                        new Name(args.get("name")),
                        new Color(Color.Type.valueOf(args.get("color"))),
                        new Name(args.get("memberName")),
                        new Email(args.get("email")),
                        new Gender(Gender.Type.valueOf(args.get("memberGender"))),
                        personId
                )
        );

        if (restQuery.existClanName(args.get("name"))) {
            throw new IllegalArgumentException("The name is not allowed already exists");
        }
    }


    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<CreateClan> requestCommand) {
                var command = requestCommand.getCommand();
                var aggregateId = command.getId();
                var clan = new Clan(aggregateId, command.getName(), command.getColor());

                clan.addNewMember(
                        new MemberId(),
                        command.getPersonId(),
                        command.getEmail(),
                        command.getMemberName(),
                        command.getMemberGender(),
                        true
                );

                emit().onSuccess(new ResponseEvents(clan.getUncommittedChanges()));
            }

        }, request);
    }
}
