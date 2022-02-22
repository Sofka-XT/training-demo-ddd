package co.sideralis.core.application.repository;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.DeserializeEventException;
import co.com.sofka.infraestructure.event.EventSerializer;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.store.StoredEvent;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FirebaseEventStoreRepository implements EventStoreRepository {

    private final Firestore firestore;


    @Autowired
    private FirebaseEventStoreRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public List<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId) {
        var iterator = firestore
                .collection(aggregateName)
                .document(aggregateRootId)
                .getCollections()
                .iterator();

        var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), true)
                .flatMap(collectionReference -> {
                    try {
                        return collectionReference.get().get().getDocuments().stream();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new FirebaseIntegrationException(e.getMessage());
                    }
                });

        return stream.map((Function<QueryDocumentSnapshot, DomainEvent>) queryDocumentSnapshot -> {
            var map = (Map) queryDocumentSnapshot.getData().get("storedEvent");
            var eventBody = map.get("eventBody").toString();
            var typeName = map.get("typeName").toString();
            try {
                return EventSerializer
                        .instance()
                        .deserialize(eventBody, Class.forName(typeName));
            } catch (ClassNotFoundException e) {
                throw new DeserializeEventException(e.getCause());
            }
        }).sorted(Comparator.comparing(event -> event.when))
                .collect(Collectors.toList());

    }

    @Override
    public void saveEvent(String aggregateName, String aggregateRootId, StoredEvent storedEvent) {
        var document = new DocumentEventStored();
        var event = storedEvent.deserializeEvent();
        document.setAggregateRootId(aggregateRootId);
        document.setStoredEvent(storedEvent);
        try {
            firestore.collection(aggregateName)
                    .document(aggregateRootId)
                    .collection(event.type)
                    .document(event.uuid.toString())
                    .set(document)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new FirebaseIntegrationException(e.getMessage());
        }
    }
}

