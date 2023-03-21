package pl.matcodem.accountquery.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.matcodem.accountquery.api.dto.EqualityType;
import pl.matcodem.cqrscore.queries.BaseQuery;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private BigDecimal balance;
}
