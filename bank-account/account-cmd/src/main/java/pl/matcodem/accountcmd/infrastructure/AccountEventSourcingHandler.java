package pl.matcodem.accountcmd.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matcodem.accountcmd.domain.AccountAggregate;
import pl.matcodem.cqrscore.domain.AggregateRoot;
import pl.matcodem.cqrscore.events.BaseEvent;
import pl.matcodem.cqrscore.handlers.EventSourcingHandler;
import pl.matcodem.cqrscore.infrastructure.EventStore;
import pl.matcodem.cqrscore.producers.EventProducer;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;
    private final EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            Optional<Integer> latestVersion = events.stream()
                    .map(BaseEvent::getVersion)
                    .max(Comparator.naturalOrder());
            latestVersion.ifPresent(aggregate::setVersion);
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        aggregateIds.forEach(aggregateId -> {
            var aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.isActive()) return;
            var events = eventStore.getEvents(aggregateId);
            events.forEach(event -> eventProducer.produce(event.getClass().getSimpleName(), event));
        });
    }
}
