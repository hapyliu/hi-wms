package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.BaseEntity;
import com.haifeng.common.enums.BaseStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "应用信息")
public class BaseApp extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 应用编码 */
    @Excel(name = "应用编码")
    @NotBlank(message = "app.code.not.blank")
    private String appCode;

    /** 应用名称 */
    @Excel(name = "应用名称")
    @NotBlank(message = "app.name.not.blank")
    private String appName;

    /** 应用IP */
    @Excel(name = "应用IP")
    private String appIp;

    /** 应用端口 */
    @Excel(name = "应用端口")
    private Integer appPort;

    /** 应用公共前缀地址 */
    @Excel(name = "应用公共前缀地址")
    private String appUrl;

    /** 应用状态0-启用1-禁用 */
    @Excel(name = "应用状态")
    private String appStatus = BaseStatusEnum.OPEN.getCode();

    @TableLogic(value = "0", delval = "2")
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;
}
