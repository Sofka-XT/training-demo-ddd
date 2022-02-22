package co.sideralis.core.usecases.clan.handle;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.clan.Clan;
import co.sideralis.core.clan.commands.UpdateClan;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.values.Name;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.clan.updateclan", aggregate = "clan")
public class UpdateClanHandle extends UseCaseExecutor {
    private RequestCommand<UpdateClan> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<UpdateClan> req) {
                var command = req.getCommand();
                var clan = Clan.from(command.getClanId(),
                        repository().getEventsBy(command.getClanId().value())
                );
                clan.updateName(command.getName());
                emit().onSuccess(new ResponseEvents(clan.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        var name = args.get("name");
        request = new RequestCommand<>(
                new UpdateClan(ClanId.of(aggregateId()), new Name(name))
        );
    }
}
