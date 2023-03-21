package pl.matcodem.accountquery;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.matcodem.accountquery.api.queries.*;
import pl.matcodem.cqrscore.infrastructure.QueryDispatcher;

@SpringBootApplication
@RequiredArgsConstructor
public class AccountQueryApplication {

	private final QueryDispatcher queryDispatcher;
	private final QueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(AccountQueryApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountWithBalanceQuery.class, queryHandler::handle);
	}
}
