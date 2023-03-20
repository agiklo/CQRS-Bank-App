package pl.matcodem.cqrscore.infrastructure;

import pl.matcodem.cqrscore.commands.BaseCommand;
import pl.matcodem.cqrscore.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
