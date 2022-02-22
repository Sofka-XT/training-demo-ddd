package co.sideralis.core.usecases.challenge;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.challenge.Challenge;
import co.sideralis.core.challenge.commands.AssignRepoURL;
import co.sideralis.core.challenge.values.ChallengeId;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.challenge.assignrepourl", aggregate = "challenge")
public class AssignRepoURLHandle extends UseCaseExecutor {
    private RequestCommand<AssignRepoURL> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<AssignRepoURL> requestCommand) {
                var command = requestCommand.getCommand();
                var events = repository().getEventsBy(command.getChallengeId().value());
                var challenge = Challenge.from(command.getChallengeId(), events);
                challenge.assignRepoUrl(command.getRepoUrl());
                emit().onSuccess(new ResponseEvents(challenge.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        request = new RequestCommand<>(
                new AssignRepoURL(
                        ChallengeId.of(aggregateId()),
                        args.get("repoUrl")
                )
        );
    }
}
