package com.abc.core.serviceTests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationError {
    private String field;
    private String description;
    private String severity;
    private String type;
    private String statusCode;
    private String debugStacktrace;
}
