package com.haifeng.common.enums;

public enum BasePointTypeEnum {
    LOCATION("0", "库位"),
    ROAD_POINT("1", "路点"),
    CHARGE_STATION("2", "充电"),
    OBSTACLE_POINT("3", "阻碍点");

    private final String code;
    private final String info;

    BasePointTypeEnum(String code, String info)
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
