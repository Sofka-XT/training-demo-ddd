package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.challenge.Challenge;
import co.sideralis.core.challenge.commands.UpdateChallengeDuration;
import co.sideralis.core.challenge.values.ChallengeId;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.challenge.updatechallengeduration", aggregate = "challenge")
public class UpdateChallengeDurationHandle extends UseCaseExecutor {
    private RequestCommand<UpdateChallengeDuration> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<UpdateChallengeDuration> requestCommand) {
                var command = requestCommand.getCommand();
                var events = repository().getEventsBy(command.getChallengeId().value());
                var challenge = Challenge.from(command.getChallengeId(), events);
                challenge.updateChallengeDuration(command.getNewDurationDays());
                emit().onSuccess(new ResponseEvents(challenge.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        request = new RequestCommand<>(
                new UpdateChallengeDuration(
                        ChallengeId.of(aggregateId()),
                        Integer.valueOf(args.get("newDurationDays"))
                )
        );
    }
}
