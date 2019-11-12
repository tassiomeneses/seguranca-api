package com.example.seguranca.exception.handler;



import com.example.seguranca.exception.payload.ApiError;
import com.example.seguranca.util.MessageUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, "Malformed JSON request", ex));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, getErrorConstraintViolation(extractRootCause(ex)), ex));
    }

    @ExceptionHandler(RollbackException.class)
    protected ResponseEntity<Object> handleConstraintViolation(RollbackException ex) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, getErrorConstraintViolation(extractRootCause(ex)), ex));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException ex) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, getErrorDataIntegrityViolation(extractRootCause(ex)), ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private Throwable extractRootCause(Throwable error) {
        return Objects.nonNull(error.getCause()) ? extractRootCause(error.getCause()) : error;
    }

    private String getErrorConstraintViolation(Throwable error) {
        ConstraintViolationException exception = (ConstraintViolationException) error;
        return asStream(exception.getConstraintViolations().iterator())
                .map(ConstraintViolation::getMessageTemplate)
                .map(this::getMessageProperty)
                .collect(Collectors.joining("\n"));
    }

    private Stream<ConstraintViolation<?>> asStream(Iterator<ConstraintViolation<?>> iterator) {
        Iterable<ConstraintViolation<?>> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), Boolean.FALSE);
    }

    private String getErrorDataIntegrityViolation(Throwable error) {
        Throwable exception = error.getCause();
        return Objects.nonNull(exception) ? this.getErrorDataIntegrityViolation(exception) : error.getMessage();
    }

    private String getMessageProperty(String property) {
        ResourceBundle bundle = MessageUtil.getResourceBundle();
        try {
            return bundle.getString(property);
        } catch (MissingResourceException e) {
            return property;
        }
    }
}
