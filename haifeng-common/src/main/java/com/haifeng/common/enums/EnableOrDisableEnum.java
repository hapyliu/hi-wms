package com.haifeng.common.enums;

/**
 * 启用/停用枚举
 * @author wangww
 * @version 1.0
 * @description TODO
 * @date 2026-03-11 16:48:37
 */
public enum EnableOrDisableEnum {

    ENABLE(0, "启用"),
    DISABLE(1, "停用");

    private Integer value;

    private String name;

    EnableOrDisableEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
