package com.abc.core.controller;

import com.abc.api.error.v1.ApplicationExceptionDTO;
import com.abc.core.enums.BusinessRules;
import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.ApplicationException;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.abc.core.enums.BusinessRules.BR_3001;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = {ApplicationException.class})
    protected ResponseEntity<Object> handleApplicationException(ApplicationException ex, WebRequest request) {
        log.info(new GsonBuilder().setPrettyPrinting().create().toJson(ex));

        ApplicationExceptionDTO appExDTO = new ApplicationExceptionDTO(ex.getErrors());
        if (appExDTO.getErrors().stream().anyMatch(e -> e.getStatusCode().equals(BR_3001.name()))) {
            return handleExceptionInternal(ex, appExDTO,
                    new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        }

        return handleExceptionInternal(ex, appExDTO,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error(String.valueOf(ex));
        List<ApplicationError> errors = new ArrayList<>();

        ApplicationError error = new ApplicationError();
        error.setStatusCode(BusinessRules.BR_9999.name());
        error.setDescription(BusinessRules.BR_9999.getMessage());
        error.setType("ERROR");
        error.setSeverity("FATAL");
        error.setDebugStacktrace(Arrays.toString(ex.getStackTrace()));

        errors.add(error);
        ApplicationExceptionDTO appExDTO = new ApplicationExceptionDTO(errors);
        log.error(new GsonBuilder().setPrettyPrinting().create().toJson(appExDTO));

        return handleExceptionInternal(ex, appExDTO,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
