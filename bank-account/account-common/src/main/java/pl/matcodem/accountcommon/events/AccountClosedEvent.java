package pl.matcodem.accountcommon.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.matcodem.cqrscore.events.BaseEvent;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class AccountClosedEvent extends BaseEvent {
}
