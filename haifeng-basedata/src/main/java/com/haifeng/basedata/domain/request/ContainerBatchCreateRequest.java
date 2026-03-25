package com.haifeng.basedata.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 容器批量创建请求对象
 * 
 * @author wangww
 * @date 2026-03-05
 */
@Data
@Schema(description = "容器批量创建请求")
public class ContainerBatchCreateRequest {

    /**
     * 容器模型编号
     */
    @NotBlank(message = "{container.model.code.not.blank}")
    @Schema(description = "容器模型编号")
    private String containerModelCode;

    /**
     * 前缀
     */
    @NotBlank(message = "{container.prefix.not.blank}")
    @Schema(description = "容器编号前缀")
    private String prefix;

    /**
     * 开始序号
     */
    @NotNull(message = "{container.start.sequence.not.null}")
    @Schema(description = "开始序号")
    private Integer startSequence;

    /**
     * 结束序号
     */
    @NotNull(message = "{container.end.sequence.not.null}")
    @Schema(description = "结束序号")
    private Integer endSequence;
}
