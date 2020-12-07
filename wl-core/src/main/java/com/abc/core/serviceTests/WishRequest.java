package com.abc.core.serviceTests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WishRequest {
    private String id;
    private String wish;
    private String status;
}
