package com.abc.api.error.v1;


import com.abc.core.serviceTests.ApplicationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationExceptionDTO {
    private List<ApplicationError> errors;
}
