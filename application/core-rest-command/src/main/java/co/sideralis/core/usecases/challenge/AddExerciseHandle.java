package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.challenge.Challenge;
import co.sideralis.core.challenge.commands.AddExercise;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.challenge.values.Exercise;
import co.sideralis.core.challenge.values.KataId;

import java.util.HashMap;
import java.util.Map;

@CommandHandles
@CommandType(name = "core.challenge.addexercise", aggregate = "challenge")
public class AddExerciseHandle extends UseCaseExecutor {
    private RequestCommand<AddExercise> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<AddExercise> requestCommand) {
                var command = requestCommand.getCommand();
                var events = repository().getEventsBy(command.getChallengeId().value());
                var challenge = Challenge.from(command.getChallengeId(), events);
                challenge.addExercise(command.getKataId(), command.getExercise());
                emit().onSuccess(new ResponseEvents(challenge.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        request = new RequestCommand<>(
                new AddExercise(
                        ChallengeId.of(aggregateId()),
                        KataId.of(args.get("kataId")),
                        new Exercise(Integer.valueOf(args.get("level")),
                                new HashMap<>(),  //TODO: Validate metadata to receive
                                args.get("goal"))
                )
        );
    }
}
