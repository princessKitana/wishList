package com.abc.api.common.v1;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsersRequest {
    @ApiModelProperty(notes = "users", required = true)
    List<UserDTO> users;
}
