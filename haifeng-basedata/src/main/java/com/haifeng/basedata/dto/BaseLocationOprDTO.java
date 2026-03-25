package com.haifeng.basedata.dto;

import com.haifeng.common.enums.BaseLocationOperationEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BaseLocationOprDTO {
    //库位编码
    @NotBlank(message = "location.code.not.blank")
    @Schema(description = "库位编码")
    private String locationCode;
    //锁定源
    @Schema(description = "锁定源")
    private String lockSource;
    //容器编码
    @Schema(description = "容器号")
    private String containerCode;
    //库位操作
    @Schema(description = "库位操作", allowableValues = "bind,unbind,lock,unlock,bindAndLock,bindAndUnlock,unbindAndUnlock,unbindAndLock")
    @NotBlank(message = "location.opr.data.absence")
    private BaseLocationOperationEnum opr;
    //是否通知下游
    @Schema(description = "是否通知下游")
    private Boolean isNotify = false;
}
