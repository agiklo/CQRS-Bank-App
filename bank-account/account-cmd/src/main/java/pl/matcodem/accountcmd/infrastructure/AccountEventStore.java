package pl.matcodem.accountcmd.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matcodem.accountcmd.domain.AccountAggregate;
import pl.matcodem.accountcmd.domain.EventStoreRepository;
import pl.matcodem.cqrscore.events.BaseEvent;
import pl.matcodem.cqrscore.events.EventModel;
import pl.matcodem.cqrscore.exceptions.AggregateNotFoundException;
import pl.matcodem.cqrscore.exceptions.ConcurrencyException;
import pl.matcodem.cqrscore.infrastructure.EventStore;
import pl.matcodem.cqrscore.producers.EventProducer;

import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        int version = expectedVersion;
        for (BaseEvent event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(now())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Wrong account id provided!");
        }
        return eventStream.stream().map(EventModel::getEventData).toList();
    }

    @Override
    public List<String> getAggregateIds() {
        var events = eventStoreRepository.findAll();
        if (events.isEmpty()) {
            throw new IllegalStateException("Could not retrieve events from the event store.");
        }
        return events.stream()
                .map(EventModel::getAggregateIdentifier)
                .distinct()
                .toList();
    }
}
