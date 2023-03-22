package pl.matcodem.accountcmd.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matcodem.accountcmd.api.commands.RestoreReadDBCommand;
import pl.matcodem.accountcommon.dto.BaseResponse;
import pl.matcodem.cqrscore.infrastructure.CommandDispatcher;

import java.text.MessageFormat;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/restoreReadDB")
public class RestoreReadDBController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDB() {
        try {
            commandDispatcher.send(new RestoreReadDBCommand());
            return new ResponseEntity<>(new BaseResponse("Read database restore request completed successfully."), CREATED);
        } catch (IllegalStateException e) {
            log.warn(MessageFormat.format("Client made a bad request - {0}.", e.toString()), e);
            return new ResponseEntity<>(new BaseResponse(e.toString()), BAD_REQUEST);
        } catch (Exception e) {
            String safeErrorMessage = "Error while processing request to restore read database!";
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), INTERNAL_SERVER_ERROR);
        }
    }
}
