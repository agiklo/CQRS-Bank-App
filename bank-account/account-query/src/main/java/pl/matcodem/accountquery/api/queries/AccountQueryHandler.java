package pl.matcodem.accountquery.api.queries;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matcodem.accountquery.api.dto.EqualityType;
import pl.matcodem.accountquery.domain.AccountRepository;
import pl.matcodem.accountquery.domain.BankAccount;
import pl.matcodem.cqrscore.domain.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static pl.matcodem.accountquery.api.dto.EqualityType.*;

@Service
@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler {

    private final AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountList = new LinkedList<>();
        bankAccounts.forEach(bankAccountList::add);
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        return bankAccount.<List<BaseEntity>>map(Collections::singletonList).orElse(null);
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        return bankAccount.<List<BaseEntity>>map(Collections::singletonList).orElse(null);
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        return new ArrayList<>(isLookingForGreaterBalance(query.getEqualityType())
                        ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                        : accountRepository.findByBalanceLessThan(query.getBalance()));
    }

    private boolean isLookingForGreaterBalance(EqualityType type) {
        return type.equals(GREATER_THAN);
    }
}
