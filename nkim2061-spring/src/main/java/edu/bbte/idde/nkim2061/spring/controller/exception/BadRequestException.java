package edu.bbte.idde.nkim2061.spring.controller.exception;

import java.text.MessageFormat;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message, Object... params) {
        super(params.length > 0 ? new MessageFormat(message).format(params) : message);
    }

    public BadRequestException(String message, Throwable cause, Object... params) {
        super(params.length > 0 ? new MessageFormat(message).format(params) : message, cause);
    }
}
