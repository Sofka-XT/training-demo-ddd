package co.sideralis.core.usecases.clan.handle;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.clan.Clan;
import co.sideralis.core.clan.commands.UpdateMember;
import co.sideralis.core.clan.values.ClanId;
import co.sideralis.core.clan.values.MemberId;
import co.sideralis.core.usecases.clan.exception.NoPresentMemberException;
import co.sideralis.core.values.Name;

import java.util.Map;
import java.util.NoSuchElementException;

@CommandHandles
@CommandType(name = "core.clan.updatemember", aggregate = "clan")
public class UpdateMemberHandle extends UseCaseExecutor {
    private RequestCommand<UpdateMember> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<UpdateMember> objectInput) {
                var command = objectInput.getCommand();
                var clan = Clan.from(command.getClanId(),
                        repository().getEventsBy(command.getClanId().value())
                );
                try {
                    clan.updateMemberName(command.getMemberId(), command.getName());
                    emit().onSuccess(new ResponseEvents(clan.getUncommittedChanges()));
                } catch (NoSuchElementException e) {
                    emit().onError(new NoPresentMemberException(aggregateId(), "No member present"));
                }

            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        var memberId = args.get("memberId");
        var name = args.get("name");
        request = new RequestCommand<>(
                new UpdateMember(
                        ClanId.of(aggregateId()),
                        MemberId.of(memberId),
                        new Name(name)
                )
        );
    }
}
