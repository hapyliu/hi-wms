package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haifeng.common.core.domain.CommonEntity;
import lombok.Data;

/**
 * 批次号表
 * @TableName base_batch_number
 */
@TableName(value ="base_batch_number")
@Data
public class BaseBatchNumber extends CommonEntity {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 批次号
     */
    private String batchNumber;

    /**
     * 批次属性1
     */
    private String batchAttribute1;

    /**
     * 批次属性2
     */
    private String batchAttribute2;

    /**
     * 批次属性3
     */
    private String batchAttribute3;

    /**
     * 批次属性4
     */
    private String batchAttribute4;

    /**
     * 批次属性5
     */
    private String batchAttribute5;

    /**
     * 批次属性6
     */
    private String batchAttribute6;

    /**
     * 批次属性7
     */
    private String batchAttribute7;

    /**
     * 批次属性8
     */
    private String batchAttribute8;

    /**
     * 批次属性9
     */
    private String batchAttribute9;

    /**
     * 批次属性10
     */
    private String batchAttribute10;

    /**
     * 批次属性11
     */
    private String batchAttribute11;

    /**
     * 批次属性12
     */
    private String batchAttribute12;

    /**
     * 批次属性13
     */
    private String batchAttribute13;

    /**
     * 批次属性14
     */
    private String batchAttribute14;

    /**
     * 批次属性15
     */
    private String batchAttribute15;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

}