package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseLocationLock {
    private static final long serialVersionUID = 1L;

    @Schema(description = "库位编码")
    private String locationCode;

    /** 锁定源 */
    @NotBlank(message = "锁定源不能为空")
    private String lockSource;

}
