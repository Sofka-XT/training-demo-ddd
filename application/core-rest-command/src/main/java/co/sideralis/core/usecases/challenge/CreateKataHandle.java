package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.challenge.Challenge;
import co.sideralis.core.challenge.commands.AddNewKata;
import co.sideralis.core.challenge.values.ChallengeId;
import co.sideralis.core.challenge.values.KataId;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.challenge.createkata", aggregate = "challenge")
public class CreateKataHandle extends UseCaseExecutor {

    private RequestCommand<AddNewKata> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<AddNewKata> objectInput) {
                var command = objectInput.getCommand();
                var aggregateId = command.getChallengeId();

                var events = repository().getEventsBy(aggregateId.value());
                var challenge = Challenge.from(aggregateId, events);

                //TODO: Validate max Katas

                challenge.addNewKata(
                        command.getKataId(),
                        command.getPurpose(),
                        command.getLimitOfTime()
                );
                emit().onSuccess(new ResponseEvents(challenge.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {

        var id = ChallengeId.of(aggregateId());
        var kataId = args.getOrDefault("kataId", new KataId().value());

        request = new RequestCommand<>(new AddNewKata(
                id,
                KataId.of(kataId),
                args.get("purpose"),
                Integer.valueOf(args.get("limitOfTime"))
        ));
    }
}
