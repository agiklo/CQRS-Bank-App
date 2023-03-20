package pl.matcodem.accountcmd.api.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.matcodem.accountcommon.dto.AccountType;
import pl.matcodem.cqrscore.commands.BaseCommand;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private BigDecimal openingBalance;
}
