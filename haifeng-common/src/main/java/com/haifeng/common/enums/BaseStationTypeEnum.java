package com.haifeng.common.enums;

public enum BaseStationTypeEnum {
    在线("0", "在线（关联点位）"),
    离线("1", "离线（关联库位）");

    private final String code;
    private final String info;

    BaseStationTypeEnum(String code, String info)
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
