package pl.matcodem.accountquery.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import pl.matcodem.accountcommon.dto.AccountType;
import pl.matcodem.cqrscore.domain.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount extends BaseEntity {

    @Id
    private String id;
    private String accountHolder;
    private LocalDate creationDate;
    private AccountType accountType;
    private BigDecimal balance;
}
