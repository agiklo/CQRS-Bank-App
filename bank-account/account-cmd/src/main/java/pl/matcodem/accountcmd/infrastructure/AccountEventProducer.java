package pl.matcodem.accountcmd.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.matcodem.cqrscore.events.BaseEvent;
import pl.matcodem.cqrscore.producers.EventProducer;

@Service
@RequiredArgsConstructor
public class AccountEventProducer implements EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topicName, BaseEvent event) {
        kafkaTemplate.send(topicName, event);
    }
}
