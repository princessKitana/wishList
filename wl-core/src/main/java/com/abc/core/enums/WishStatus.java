package com.abc.core.enums;

import java.util.Arrays;

public enum WishStatus {
    NEW,
    DONE;

    public static Boolean isValidStatus(String value) {
        return Arrays.asList(WishStatus.values()).stream().anyMatch(s -> s.name().equals(value));
    }
}
