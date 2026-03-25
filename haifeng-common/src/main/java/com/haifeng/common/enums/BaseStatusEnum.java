package com.haifeng.common.enums;

public enum BaseStatusEnum {
    OPEN("0", "启用"),
    STOP("1", "停用");

    private final String code;
    private final String info;

    BaseStatusEnum(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
