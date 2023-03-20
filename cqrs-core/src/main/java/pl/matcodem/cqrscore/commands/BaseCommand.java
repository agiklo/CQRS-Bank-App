package pl.matcodem.cqrscore.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.matcodem.cqrscore.messages.Message;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public abstract class BaseCommand extends Message {
    protected BaseCommand(String id) {
        super(id);
    }
}
