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
import co.sideralis.core.dojo.commands.CreateDojo;
import co.sideralis.core.dojo.values.*;
import co.sideralis.core.usecases.dojo.QueryDojoService;
import co.sideralis.core.values.Email;
import co.sideralis.core.values.Name;
import co.sideralis.core.values.PersonId;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@CommandHandles
@CommandType(name = "core.dojo.create", aggregate = "dojo")
@ExtensionService({RestQueryDojoService.class})
public class CreateDojoHandle extends UseCaseExecutor {
    private RequestCommand<CreateDojo> request;

    @Override
    public void run() {
        runUseCase(new UseCase<>() {
            @Override
            public void executeUseCase(RequestCommand<CreateDojo> request) {
                var command = request.getCommand();
                Optional.ofNullable(command.getLocation()).ifPresentOrElse(location -> {
                    var dojo = new Dojo(
                            command.getEntityId(),
                            command.getCoachId(),
                            command.getEmail(),
                            command.getPersonId(),
                            command.getDataInfo(),
                            command.getLocation(),
                            command.getCouchName(),
                            command.getSpecialty()
                    );
                    emit().onSuccess(new ResponseEvents(dojo.getUncommittedChanges()));
                }, () -> {
                    var dojo = new Dojo(
                            command.getEntityId(),
                            command.getCoachId(),
                            command.getEmail(),
                            command.getPersonId(),
                            command.getDataInfo(),
                            command.getCouchName(),
                            command.getSpecialty()
                    );
                    emit().onSuccess(new ResponseEvents(dojo.getUncommittedChanges()));
                });

            }
        }, request);
    }

    @Override
    public void accept(Map<String, String> args) {
        QueryDojoService restQuery = getService(QueryDojoService.class).orElseThrow();

        request = new RequestCommand<>(
                new CreateDojo(
                        DojoId.of(aggregateId()),
                        CoachId.of(args.getOrDefault("coachId", new CoachId().value())),
                        new Email(args.get("email")),
                        PersonId.of(args.get("personId")),
                        new DataInfo(
                                args.get("name"),
                                args.get("legend")
                        ),
                        getLocation(args),
                        new Name(args.get("coachName")),
                        new Specialty(args.get("coachSpecialty"))
                )
        );

        if (restQuery.existDojoName(args.get("name"))) {
            throw new IllegalArgumentException("The name is not allowed already exists");
        }
    }

    private Location getLocation(Map<String, String> args) {
        var fields = new String[]{
                "location",
                "locationDescription",
                "openHour",
                "closeHour",
                "openingFrequency"
        };
        var hasProsRequired = Arrays.stream(fields)
                .anyMatch(args::containsKey);

        if (!hasProsRequired) {
            return null;
        }

        return new Location(
                args.get("urlMeet"),
                args.get("location"),
                args.get("locationDescription"),
                new OpeningHours(
                        Integer.valueOf(args.get("openHour")),
                        Integer.valueOf(args.get("closeHour")),
                        OpeningHours.Frequency.valueOf(args.get("openingFrequency"))
                )
        );
    }


}
