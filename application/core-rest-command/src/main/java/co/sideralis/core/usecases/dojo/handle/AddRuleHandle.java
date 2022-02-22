package co.sideralis.core.usecases.dojo.handle;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.dojo.Dojo;
import co.sideralis.core.dojo.commands.AddRules;
import co.sideralis.core.dojo.values.DojoId;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.dojo.addrule", aggregate = "dojo")
public class AddRuleHandle extends UseCaseExecutor {
    private RequestCommand<AddRules> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<AddRules> input) {
                var command = input.getCommand();
                var dojo = Dojo.form(
                        DojoId.of(aggregateId()),
                        repository().getEventsBy(aggregateId())
                );
                dojo.addRules(command.getRule());
                emit().onSuccess(new ResponseEvents(dojo.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        request = new RequestCommand<>(
                new AddRules(args.get("rule"))
        );
    }
}
