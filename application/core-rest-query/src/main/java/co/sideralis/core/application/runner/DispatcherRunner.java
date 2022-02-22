package co.sideralis.core.application.runner;


import co.com.sofka.infraestructure.bus.serialize.SuccessNotificationSerializer;
import io.nats.client.Nats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;


@Component
public class DispatcherRunner implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(DispatcherRunner.class.getName());
    @Value("${spring.nats.uri}")
    private String natsUri;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run(String... args) throws IOException, InterruptedException {
        Nats.connect(natsUri).createDispatcher(m -> {
            var message = new String(m.getData());
            var notification = SuccessNotificationSerializer.instance().deserialize(message);
            var event = notification.deserializeEvent();
            logger.info("Recibe message form " + m.getSubject() + " -- " + m.getSID());
            applicationEventPublisher.publishEvent(event);
        }).subscribe("core.>", "materialize");
    }
}
