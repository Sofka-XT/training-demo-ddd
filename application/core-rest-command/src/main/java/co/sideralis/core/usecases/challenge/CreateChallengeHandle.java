package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.challenge.Challenge;
import co.sideralis.core.challenge.commands.CreateChallenge;
import co.sideralis.core.challenge.values.Assesment;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.dojo.values.DojoId;
import co.sideralis.core.values.Name;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.challenge.create", aggregate = "challenge")
public class CreateChallengeHandle extends UseCaseExecutor {
    private RequestCommand<CreateChallenge> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
           @Override
           public void executeUseCase(RequestCommand<CreateChallenge> requestCommand) {
               var command = requestCommand.getCommand();
               var aggregateId = command.getId();
               var challenge = new Challenge(aggregateId,
                       command.getName(),
                       command.getDojoId(),
                       command.getAssesment(),
                       command.getDurationDays()
               );
               emit().onSuccess(new ResponseEvents(challenge.getUncommittedChanges()));
           }
        },request);

    }

    @Override
    public void accept(Map<String, String> args) {
        var id = ChallengeId.of(aggregateId());
        DojoId dojoId = DojoId.of(args.get("dojoId"));

        request = new RequestCommand<>(
                new CreateChallenge(
                        id,
                        new Name(args.get("name")),
                        Integer.valueOf(args.get("durationDays")),
                        new Assesment(Integer.valueOf(args.get("degreeOfDifficulty")), args.get("summary")),
                        dojoId
                )
        );
    }
}
