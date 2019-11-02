package info.lamth.biddingsystem.controller;

import info.lamth.biddingsystem.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class ResponseExceptionAdviceController {
    @ExceptionHandler({IllegalArgumentException.class})
    public final ResponseEntity handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(buildResponse(HttpStatus.BAD_REQUEST, ex));
    }

    @ExceptionHandler({RuntimeException.class})
    public final ResponseEntity handleInternalException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    private ErrorResponse buildResponse(HttpStatus status, Exception ex) {
        return ErrorResponse.builder()
                .status(status)
                .timestamp(LocalDate.now())
                .message(ex.getMessage())
                .detailMessage(ex.getLocalizedMessage())
                .build();
    }
}
