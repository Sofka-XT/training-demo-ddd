package co.sideralis.core.application;


import co.com.sofka.application.ApplicationEventDrive;
import co.com.sofka.infraestructure.bus.serialize.SuccessNotificationSerializer;
import io.nats.client.Nats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class DispatcherRunner implements CommandLineRunner {

    @Value("${spring.nats.uri}")
    private String natsUri;

    @Autowired
    private ApplicationEventDrive applicationEventDrive;

    @Override
    public void run(String... args) throws IOException, InterruptedException {
        Nats.connect(natsUri).createDispatcher(m -> {
            var message = new String(m.getData());
            var notification = SuccessNotificationSerializer.instance().deserialize(message);
            var event = notification.deserializeEvent();
            applicationEventDrive.fire(event);
        }).subscribe("core.>", "applicationEventDrive");
    }
}
