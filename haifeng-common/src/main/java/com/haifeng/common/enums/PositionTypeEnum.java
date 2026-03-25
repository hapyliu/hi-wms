package com.haifeng.common.enums;

public enum PositionTypeEnum {
    LOCATION("0", "库位"),
    ROBOT("1", "机器人"),
    FREEZING("2", "游离");

    private final String code;
    private final String info;

    PositionTypeEnum(String code, String info)
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
