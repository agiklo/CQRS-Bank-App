package pl.matcodem.accountcmd.api.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.matcodem.cqrscore.commands.BaseCommand;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class DepositFundsCommand extends BaseCommand {
    private BigDecimal amount;
}
