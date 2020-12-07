package com.abc.core.serviceTests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ApplicationException extends RuntimeException {
    private List<ApplicationError> errors;
}
