package co.sideralis.core.application.bus;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.bus.notification.ErrorNotification;
import co.com.sofka.infraestructure.bus.notification.SuccessNotification;
import co.com.sofka.infraestructure.bus.serialize.ErrorNotificationSerializer;
import co.com.sofka.infraestructure.bus.serialize.SuccessNotificationSerializer;
import co.com.sofka.infraestructure.event.ErrorEvent;
import com.google.cloud.firestore.Firestore;
import io.nats.client.Connection;
import io.nats.client.Nats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class NATSEventBus implements EventBus {
    private static final String ORIGIN = "co.sideralis.core";
    private static final String TOPIC_ERROR = "co.sideralis.core.error";
    private static final Logger logger = Logger.getLogger(NATSEventBus.class.getName());

    private final Connection nc;
    private final Firestore firestore;

    @Autowired
    public NATSEventBus(
            @Value("${spring.nats.uri}") String natsUri,
            Firestore firestore
    ) throws IOException, InterruptedException {
        this.nc = Nats.connect(natsUri);
        this.firestore = firestore;
    }

    @Override
    public void publish(DomainEvent event) {
        var notification = SuccessNotification.wrapEvent(ORIGIN, event);
        var notificationSerialization = SuccessNotificationSerializer.instance().serialize(notification);
        nc.publish(event.type + "/" + event.aggregateRootId(), notificationSerialization.getBytes());
        var log = String.format("Event published to %s/%s", event.type, event.aggregateRootId());
        logger.info(log);
    }

    @Override
    public void publishError(ErrorEvent errorEvent) {
        var notification = ErrorNotification.wrapEvent(ORIGIN, errorEvent);
        var notificationSerialization = ErrorNotificationSerializer.instance().serialize(notification);
        nc.publish(TOPIC_ERROR + "/" + errorEvent.identify, notificationSerialization.getBytes());
        logger.warning("Error Event published to " + TOPIC_ERROR);
        logger.log(Level.SEVERE, errorEvent.error.getMessage());
        try {
            firestore.collection(ORIGIN)
                    .document(errorEvent.identify)
                    .set(notification)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

}
