package pl.matcodem.accountcmd.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matcodem.accountcmd.api.commands.CloseAccountCommand;
import pl.matcodem.accountcommon.dto.BaseResponse;
import pl.matcodem.cqrscore.exceptions.AggregateNotFoundException;
import pl.matcodem.cqrscore.infrastructure.CommandDispatcher;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/account")
public class CloseAccountController {

    private final CommandDispatcher commandDispatcher;

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable("id") String id){
        try {
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("Bank account has been closed successfully."), OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn(format("Client made a bad request - {0}.", e.toString()), e);
            return new ResponseEntity<>(new BaseResponse(e.toString()), BAD_REQUEST);
        } catch (Exception e) {
            String safeErrorMessage = format("Error while processing request to close the bank account with id {0}!", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), INTERNAL_SERVER_ERROR);
        }
    }
}
