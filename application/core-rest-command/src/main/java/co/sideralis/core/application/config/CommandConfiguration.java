package co.sideralis.core.application.config;

import co.com.sofka.application.ApplicationCommandExecutor;
import co.com.sofka.application.ApplicationEventDrive;
import co.com.sofka.infraestructure.asyn.SubscriberEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;


@Configuration
public class CommandConfiguration {

    private static final String PACKAGE_USE_CASE = "co.sideralis.core.usecases";

    public CommandConfiguration(
            @Value("${spring.gitlab.uri}") String gitlabUri,
            @Value("${spring.queries.url}") String queriesUrl
    ) {
        System.getProperties().putIfAbsent("spring.gitlab.uri", gitlabUri);
        System.getProperties().putIfAbsent("spring.queries.url", queriesUrl);
    }

    @Bean
    public SubscriberEvent subscriberEvent(EventStoreRepository eventStoreRepository, EventBus eventBus) {
        return new SubscriberEvent(eventStoreRepository, eventBus);
    }

    @Bean
    public ApplicationCommandExecutor applicationCommandExecutor(SubscriberEvent subscriberEvent, EventStoreRepository eventStoreRepository) {
        return new ApplicationCommandExecutor(PACKAGE_USE_CASE, subscriberEvent, eventStoreRepository);
    }

    @Bean
    public ApplicationEventDrive applicationEventDrive(SubscriberEvent subscriberEvent, EventStoreRepository eventStoreRepository) {
        return new ApplicationEventDrive(PACKAGE_USE_CASE, subscriberEvent, eventStoreRepository);
    }

    @Bean
    public Firestore configFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("sideralis-firebase.json");

        FirestoreOptions options =
                FirestoreOptions.newBuilder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

        return options.getService();
    }

}
