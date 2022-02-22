package co.sideralis.core.usecases.clan.handle;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.clan.Clan;
import co.sideralis.core.clan.commands.RevokeMember;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.MemberId;

import java.util.Map;


@CommandHandles
@CommandType(name = "core.clan.revokemember", aggregate = "clan")
public class RevokeMemberHandle extends UseCaseExecutor {

    private RequestCommand<RevokeMember> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<RevokeMember> requestCommand) {
                var command = requestCommand.getCommand();
                var events = repository().getEventsBy(command.getClanId().value());
                var clan = Clan.from(command.getClanId(), events);
                clan.revokeMember(command.getMemberId());
                emit().onSuccess(new ResponseEvents(clan.getUncommittedChanges()));
            }

        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        var memberId = args.get("memberId");
        request = new RequestCommand<>(
                new RevokeMember(
                        ClanId.of(aggregateId()),
                        MemberId.of(memberId)
                )
        );
    }
}
