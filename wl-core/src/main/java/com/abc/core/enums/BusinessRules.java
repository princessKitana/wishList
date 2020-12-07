package com.abc.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BusinessRules {
    /**
     * 1000-1999 Internal
     * 2000-2999 External
     * 3000-3999 Validation
     * 9999 Unknown
     */
    BR_3000("is mandatory"),
    BR_3001("No such record found in DB"),
    BR_3002("must be numeric"),
    BR_3003("Incorrect Status"),

    BR_9999("Unknown");

    private String message;
}
