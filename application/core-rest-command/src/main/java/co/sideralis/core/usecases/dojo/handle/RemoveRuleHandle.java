package co.sideralis.core.usecases.dojo.handle;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.dojo.Dojo;
import co.sideralis.core.dojo.commands.RemoveRules;
import co.sideralis.core.dojo.values.DojoId;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.dojo.removerule", aggregate = "dojo")
public class RemoveRuleHandle extends UseCaseExecutor {
    private RequestCommand<RemoveRules> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<RemoveRules> input) {
                var command = input.getCommand();
                var dojo = Dojo.form(
                        DojoId.of(aggregateId()),
                        repository().getEventsBy(aggregateId())
                );
                dojo.removeRules(command.getRule());
                emit().onSuccess(new ResponseEvents(dojo.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        request = new RequestCommand<>(
                new RemoveRules(args.get("rule"))
        );
    }
}
