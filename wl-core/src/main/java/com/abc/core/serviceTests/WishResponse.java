package com.abc.core.serviceTests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WishResponse {
    private boolean success;
    private String id;
    private String wish;
    private String status;
    private List<ApplicationError> applicationErrors;
}
