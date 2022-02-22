package co.sideralis.core.usecases.dojo.handle;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.sideralis.core.dojo.Dojo;
import co.sideralis.core.dojo.commands.UpdateOpeningHours;
import co.sideralis.core.dojo.values.DojoId;
import co.sideralis.core.dojo.values.OpeningHours;

import java.util.Map;

@CommandHandles
@CommandType(name = "core.dojo.updateopeninghour", aggregate = "dojo")
public class UpdateOpeningHourHandle extends UseCaseExecutor {
    private RequestCommand<UpdateOpeningHours> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<UpdateOpeningHours> req) {
                var command = req.getCommand();
                var dojo = Dojo.form(
                        DojoId.of(aggregateId()),
                        repository().getEventsBy(aggregateId())
                );
                dojo.updateOpeningHours(command.getNewOpeningHours());
                emit().onSuccess(new ResponseEvents(dojo.getUncommittedChanges()));
            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        var command = new UpdateOpeningHours(
                new OpeningHours(
                        Integer.valueOf(args.get("openHour")),
                        Integer.valueOf(args.get("closeHour")),
                        OpeningHours.Frequency.valueOf(args.get("openingFrequency"))
                )
        );
        request = new RequestCommand<>(command);
    }
}
