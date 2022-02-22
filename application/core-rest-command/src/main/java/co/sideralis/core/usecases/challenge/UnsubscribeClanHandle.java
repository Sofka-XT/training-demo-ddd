package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.challenge.Challenge;
import co.sideralis.core.challenge.commands.UnsubscribeClan;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.clan.values.ClanId;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.challenge.unsubscribeclan", aggregate = "challenge")
public class UnsubscribeClanHandle extends UseCaseExecutor {
    private RequestCommand<UnsubscribeClan> request;


    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<UnsubscribeClan> requestCommand) {
                var command = requestCommand.getCommand();
                var events = repository().getEventsBy(command.getChallengeId().value());
                var challenge = Challenge.from(command.getChallengeId(), events);
                challenge.unsubscribeClan(command.getClanId());
                emit().onSuccess(new ResponseEvents(challenge.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        request = new RequestCommand<>(
                new UnsubscribeClan(
                        ChallengeId.of(aggregateId()),
                        ClanId.of(args.get("clanId"))
                )
        );
    }
}
