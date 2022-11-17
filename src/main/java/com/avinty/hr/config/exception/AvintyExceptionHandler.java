package com.avinty.hr.config.exception;

import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Specific exception handling for frontend apps
 * @author mredly
 */
@ControllerAdvice
public class AvintyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AvintyException.class)
    public final ResponseEntity<?> handleAvintyExceptions(AvintyException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleException(ServiceException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
