package pl.matcodem.accountquery.infrastructure.handlers;

import pl.matcodem.accountcommon.events.AccountClosedEvent;
import pl.matcodem.accountcommon.events.AccountOpenedEvent;
import pl.matcodem.accountcommon.events.FundsDepositedEvent;
import pl.matcodem.accountcommon.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
