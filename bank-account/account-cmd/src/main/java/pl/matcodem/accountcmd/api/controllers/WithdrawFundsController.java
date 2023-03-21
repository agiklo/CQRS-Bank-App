package pl.matcodem.accountcmd.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.matcodem.accountcmd.api.commands.WithdrawFundsCommand;
import pl.matcodem.accountcommon.dto.BaseResponse;
import pl.matcodem.cqrscore.exceptions.AggregateNotFoundException;
import pl.matcodem.cqrscore.infrastructure.CommandDispatcher;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/account")
public class WithdrawFundsController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping("/withdraw/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable("id") String id,
                                                     @RequestBody WithdrawFundsCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds have been withdrawn successfully."), OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn(format("Client made a bad request - {0}.", e.toString()), e);
            return new ResponseEntity<>(new BaseResponse(e.toString()), BAD_REQUEST);
        } catch (Exception e) {
            String safeErrorMessage = format("Error while processing request to withdraw funds from bank account with id {0}!", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), INTERNAL_SERVER_ERROR);
        }
    }
}
