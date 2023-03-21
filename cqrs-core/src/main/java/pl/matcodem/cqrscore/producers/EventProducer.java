package pl.matcodem.cqrscore.producers;

import pl.matcodem.cqrscore.events.BaseEvent;

public interface EventProducer {
    void produce(String topicName, BaseEvent event);
}
