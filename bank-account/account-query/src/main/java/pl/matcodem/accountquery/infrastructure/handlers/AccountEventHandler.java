package pl.matcodem.accountquery.infrastructure.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matcodem.accountcommon.events.AccountClosedEvent;
import pl.matcodem.accountcommon.events.AccountOpenedEvent;
import pl.matcodem.accountcommon.events.FundsDepositedEvent;
import pl.matcodem.accountcommon.events.FundsWithdrawnEvent;
import pl.matcodem.accountquery.domain.AccountRepository;
import pl.matcodem.accountquery.domain.BankAccount;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountEventHandler implements EventHandler {

    private final AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .creationDate(event.getCreatedDate())
                .balance(event.getOpeningBalance())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        Optional<BankAccount> bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) return;
        BigDecimal currentBalance = bankAccount.get().getBalance();
        BigDecimal latestBalance = currentBalance.add(event.getAmount());
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        Optional<BankAccount> bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) return;
        BigDecimal currentBalance = bankAccount.get().getBalance();
        BigDecimal latestBalance = currentBalance.subtract(event.getAmount());
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
