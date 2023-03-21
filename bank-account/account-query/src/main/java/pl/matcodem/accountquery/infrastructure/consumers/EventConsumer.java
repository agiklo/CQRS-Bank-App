package pl.matcodem.accountquery.infrastructure.consumers;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import pl.matcodem.accountcommon.events.AccountClosedEvent;
import pl.matcodem.accountcommon.events.AccountOpenedEvent;
import pl.matcodem.accountcommon.events.FundsDepositedEvent;
import pl.matcodem.accountcommon.events.FundsWithdrawnEvent;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);
    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
