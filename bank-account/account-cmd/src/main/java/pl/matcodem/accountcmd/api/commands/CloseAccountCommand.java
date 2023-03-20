package pl.matcodem.accountcmd.api.commands;

import pl.matcodem.cqrscore.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
