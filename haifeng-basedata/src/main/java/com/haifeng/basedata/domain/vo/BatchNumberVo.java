package com.haifeng.basedata.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 批次号响应
 */
@Data
@Schema(description = "批次号响应")
public class BatchNumberVo {

    /**
     * 主键 ID
     */
    @Schema(description = "主键 ID")
    private Long id;

    /**
     * 批次号
     */
    @Schema(description = "批次号")
    private String batchNumber;

    /**
     * 批次属性列表
     */
    @Schema(description = "批次属性列表")
    private List<BatchAttributeItem> attributes;

    /**
     * 批次属性项
     */
    @Data
    @Schema(description = "批次属性项")
    public static class BatchAttributeItem {
        /**
         * 批次属性编码
         */
        @Schema(description = "批次属性编码")
        private String attrCode;

        /**
         * 批次属性值
         */
        @Schema(description = "批次属性值")
        private String attrValue;

        /**
         * 属性顺序
         */
        @Schema(description = "属性顺序")
        private Integer attrIndex;
    }
}
