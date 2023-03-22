package pl.matcodem.accountquery.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.matcodem.accountquery.api.dto.AccountLookupResponse;
import pl.matcodem.accountquery.api.dto.EqualityType;
import pl.matcodem.accountquery.api.queries.FindAccountByHolderQuery;
import pl.matcodem.accountquery.api.queries.FindAccountByIdQuery;
import pl.matcodem.accountquery.api.queries.FindAccountWithBalanceQuery;
import pl.matcodem.accountquery.api.queries.FindAllAccountsQuery;
import pl.matcodem.accountquery.domain.BankAccount;
import pl.matcodem.cqrscore.infrastructure.QueryDispatcher;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/account")
public class AccountLookupController {

    private final QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccount> bankAccounts = queryDispatcher.send(new FindAllAccountsQuery());
            if (bankAccounts == null || bankAccounts.isEmpty()) return new ResponseEntity<>(null, NO_CONTENT);
            var response = AccountLookupResponse.builder()
                    .message("Successfully retrieved all bank accounts!")
                    .bankAccounts(bankAccounts)
                    .build();
            return new ResponseEntity<>(response, OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all accounts request!";
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("id") String id) {
        try {
            List<BankAccount> bankAccounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if (bankAccounts == null || bankAccounts.isEmpty()) return new ResponseEntity<>(null, NO_CONTENT);
            var response = AccountLookupResponse.builder()
                    .message(MessageFormat.format("Successfully retrieved bank account with id {0}!", id))
                    .bankAccounts(bankAccounts)
                    .build();
            return new ResponseEntity<>(response, OK);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Failed to complete getting bank account by id {0} request!", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/holder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable("accountHolder") String accountHolder) {
        try {
            List<BankAccount> bankAccounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if (bankAccounts == null || bankAccounts.isEmpty()) return new ResponseEntity<>(null, NO_CONTENT);
            var response = AccountLookupResponse.builder()
                    .message(MessageFormat.format("Successfully retrieved bank account by holder {0}!", accountHolder))
                    .bankAccounts(bankAccounts)
                    .build();
            return new ResponseEntity<>(response, OK);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Failed to complete getting bank account by holder {0} request!", accountHolder);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/balance/{equality}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountsByBalance(@PathVariable("equality") EqualityType equalityType,
                                                                    @PathVariable("balance") BigDecimal balance) {
        try {
            List<BankAccount> bankAccounts = queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType, balance));
            if (bankAccounts == null || bankAccounts.isEmpty()) return new ResponseEntity<>(null, NO_CONTENT);
            var response = AccountLookupResponse.builder()
                    .message("Successfully retrieved bank accounts by balance!")
                    .bankAccounts(bankAccounts)
                    .build();
            return new ResponseEntity<>(response, OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete getting bank accounts by balance request!";
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), INTERNAL_SERVER_ERROR);
        }
    }
}
