package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.challenge.Challenge;
import co.sideralis.core.challenge.commands.SubscribeClan;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.clan.values.ClanId;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.challenge.subscribeclan", aggregate = "challenge")
public class SubscribeClanHandle extends UseCaseExecutor {
    private RequestCommand<SubscribeClan> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<SubscribeClan> requestCommand) {
                var command = requestCommand.getCommand();
                var events = repository().getEventsBy(command.getChallengeId().value());
                var challenge = Challenge.from(command.getChallengeId(), events);
                challenge.subscribeClan(command.getClanId());
                emit().onSuccess(new ResponseEvents(challenge.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        request = new RequestCommand<>(
                new SubscribeClan(
                        ChallengeId.of(aggregateId()),
                        ClanId.of(args.get("clanId"))
                )
        );
    }
}
