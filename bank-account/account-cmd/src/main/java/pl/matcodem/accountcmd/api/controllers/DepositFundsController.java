package pl.matcodem.accountcmd.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.matcodem.accountcmd.api.commands.DepositFundsCommand;
import pl.matcodem.accountcommon.dto.BaseResponse;
import pl.matcodem.cqrscore.exceptions.AggregateNotFoundException;
import pl.matcodem.cqrscore.infrastructure.CommandDispatcher;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/account")
public class DepositFundsController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping("/deposit/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable("id") String id,
                                                     @RequestBody DepositFundsCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds have been deposited successfully."), OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn(format("Client made a bad request - {0}.", e.toString()), e);
            return new ResponseEntity<>(new BaseResponse(e.toString()), BAD_REQUEST);
        } catch (Exception e) {
            String safeErrorMessage = format("Error while processing request to deposit funds to bank account with id {0}!", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), INTERNAL_SERVER_ERROR);
        }
    }
}
