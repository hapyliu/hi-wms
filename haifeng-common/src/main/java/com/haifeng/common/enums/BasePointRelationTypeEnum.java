package com.haifeng.common.enums;

public enum BasePointRelationTypeEnum {
    LOC_OVERLAP("0", "库位重合"),
    ENTER_APPLY("1", "进入申请"),
    EXIT_APPLY("2", "退出申请"),
    QUEUE_WAIT("3", "排队等待"),
    IN_BOUND_REL("4", "入库关联"),
    OUT_BOUND_REL("5", "出库关联"),
    IN_OUT_BOUND_REL("6", "出入库关联");

    private final String code;
    private final String info;

    BasePointRelationTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static BasePointRelationTypeEnum getByCode(String code) {
        for (BasePointRelationTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
