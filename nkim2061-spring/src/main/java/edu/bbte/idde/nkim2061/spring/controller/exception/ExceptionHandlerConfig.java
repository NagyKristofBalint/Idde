package edu.bbte.idde.nkim2061.spring.controller.exception;

import edu.bbte.idde.nkim2061.spring.dto.outgoing.SimpleMessageDTO;
import edu.bbte.idde.nkim2061.spring.dto.outgoing.SimpleMessagesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerConfig {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleMessageDTO handleNotFoundException(NotFoundException e) {
        log.warn("NotFoundException: {}", e.getMessage());
        return new SimpleMessageDTO(e.getMessage());
    }

    @ExceptionHandler(ControllerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleControllerException(ControllerException e) {
        log.warn("ControllerException: {}", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleMessagesDTO handleBadRequestException(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException: {}", e.getMessage());
        return new SimpleMessagesDTO(e.getBindingResult().getFieldErrors().stream()
                .map(it -> it.getField() + " " + it.getDefaultMessage()).toList());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleMessageDTO handleBadRequestException(BadRequestException e) {
        log.warn("BadRequestException {}", e.getMessage());
        return new SimpleMessageDTO(e.getMessage());
    }
}
