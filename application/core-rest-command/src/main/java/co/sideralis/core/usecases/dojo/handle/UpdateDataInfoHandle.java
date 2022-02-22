package co.sideralis.core.usecases.dojo.handle;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.annotation.ExtensionService;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.application.service.RestQueryDojoService;
import co.sideralis.core.dojo.Dojo;
import co.sideralis.core.dojo.commands.UpdateDataInfo;
import co.sideralis.core.dojo.values.DataInfo;
import co.sideralis.core.dojo.values.DojoId;
import co.sideralis.core.usecases.dojo.QueryDojoService;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.dojo.updatedatainfo", aggregate = "dojo")
@ExtensionService({RestQueryDojoService.class})
public class UpdateDataInfoHandle extends UseCaseExecutor {
    private RequestCommand<UpdateDataInfo> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<UpdateDataInfo> input) {
                var command = input.getCommand();
                var dojo = Dojo.form(
                        DojoId.of(aggregateId()),
                        repository().getEventsBy(aggregateId())
                );
                dojo.updateDataInfo(command.getDataInfo());
                emit().onSuccess(new ResponseEvents(dojo.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        QueryDojoService restQuery = getService(QueryDojoService.class).orElseThrow();

        request = new RequestCommand<>(
                new UpdateDataInfo(new DataInfo(
                        args.get("name"),
                        args.get("legend")
                ))
        );

        if (restQuery.existDojoName(args.get("name"))) {
            throw new IllegalArgumentException("The name is not allowed already exists");
        }
    }
}
