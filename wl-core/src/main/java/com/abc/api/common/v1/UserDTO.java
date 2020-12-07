package com.abc.api.common.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    @ApiModelProperty(notes = "type", example = "User", required = true)
    private String type;

    @ApiModelProperty(notes = "id", example = "123", required = true)
    private Long id;

    @ApiModelProperty(notes = "name", example = "johnsmith", required = true)
    private String name;

    @ApiModelProperty(notes = "email", example = "jsmith@example.com", required = true)
    private String email;
}
