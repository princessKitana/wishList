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
public class WishDTO {
    @ApiModelProperty(notes = "ID", example = "1236")
    private String id;

    @ApiModelProperty(notes = "wish", example = "Coffee mug", required = true)
    private String wish;

    @ApiModelProperty(notes = "status", example = "DONE")
    private String status;
}
