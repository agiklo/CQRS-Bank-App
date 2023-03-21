package pl.matcodem.accountcmd.domain;

import lombok.NoArgsConstructor;
import pl.matcodem.accountcmd.api.commands.OpenAccountCommand;
import pl.matcodem.accountcommon.events.AccountClosedEvent;
import pl.matcodem.accountcommon.events.AccountOpenedEvent;
import pl.matcodem.accountcommon.events.FundsDepositedEvent;
import pl.matcodem.accountcommon.events.FundsWithdrawnEvent;
import pl.matcodem.cqrscore.domain.AggregateRoot;

import java.math.BigDecimal;

import static java.time.LocalDate.*;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private boolean isActive;
    private BigDecimal balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(now())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.isActive = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(BigDecimal amount) {
        if (!isActive) {
            throw new IllegalStateException("Funds cannot be deposited into a closed account!");
        }
        if (amount == null || amount.doubleValue() <= 0) {
            throw new IllegalStateException("The deposit amount must be greater than zero!");
        }
        raiseEvent(FundsDepositedEvent.builder()
                .id(this.getId())
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance = balance.add(event.getAmount());
    }

    public void withdrawFunds(BigDecimal amount) {
        if (!isActive) {
            throw new IllegalStateException("Funds cannot be withdraw from a closed account!");
        }
        if (amount == null || amount.doubleValue() <= 0) {
            throw new IllegalStateException("The withdraw amount must be greater than zero!");
        }
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.getId())
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance = balance.subtract(event.getAmount());
    }

    public void closeAccount() {
        if (!isActive) {
            throw new IllegalStateException("The bank account has already been closed!");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.getId())
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.isActive = false;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
