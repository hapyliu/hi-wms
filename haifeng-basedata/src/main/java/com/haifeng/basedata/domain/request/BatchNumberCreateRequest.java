package com.haifeng.basedata.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 批次号创建请求
 */
@Data
@Schema(description = "批次号创建请求")
public class BatchNumberCreateRequest {

    /**
     * 批次属性列表
     */
    @NotEmpty(message = "{batch.number.attributes.not.empty}")
    @Schema(description = "批次属性列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private List<BatchAttributeItem> attributes;

    /**
     * 批次号生成策略名称（可选，默认使用 DEFAULT）
     */
    @Schema(description = "批次号生成策略名称 (可选，默认使用 DEFAULT)")
    private String strategyName = "DEFAULT";

    /**
     * 批次属性项
     */
    @Data
    @Schema(description = "批次属性项")
    public static class BatchAttributeItem {
        /**
         * 批次属性ID
         */
        @Schema(description = "批次属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{batch.attribute.id.not.null}")
        private Long attributeId;

        /**
         * 批次属性编码
         */
        @Schema(description = "批次属性编码", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{batch.attribute.code.not.blank}")
        private String attrCode;

        /**
         * 批次属性值
         */
        @Schema(description = "批次属性值")
        private String attrValue;

        /**
         * 属性顺序
         */
        @Schema(description = "属性顺序", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{batch.attribute.index.not.null}")
        private Integer attrIndex;
    }
}
