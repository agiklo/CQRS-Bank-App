package pl.matcodem.accountquery.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.cqrscore.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<BankAccount, String> {
    Optional<BankAccount> findByAccountHolder(String accountHolder);
    List<BaseEntity> findByBalanceGreaterThan(BigDecimal balance);
    List<BaseEntity> findByBalanceLessThan(BigDecimal balance);
}
