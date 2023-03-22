package pl.matcodem.cqrscore.handlers;

import pl.matcodem.cqrscore.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);
    T getById(String id);
    void republishEvents();
}
