package pl.matcodem.accountcmd;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.matcodem.accountcmd.api.commands.*;
import pl.matcodem.cqrscore.infrastructure.CommandDispatcher;

@SpringBootApplication
@RequiredArgsConstructor
public class AccountCommandApplication {

	private final CommandDispatcher commandDispatcher;
	private final CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(AccountCommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
	}

}
