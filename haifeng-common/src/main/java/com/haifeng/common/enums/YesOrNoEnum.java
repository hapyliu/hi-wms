package com.haifeng.common.enums;

import com.haifeng.common.utils.NumberUtils;
import org.apache.commons.lang3.BooleanUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum YesOrNoEnum {
    YES(1, "是", Boolean.TRUE),
    NO(0, "否", Boolean.FALSE);
    private Integer value;
    private String name;
    private Boolean trueOrFalse;

    YesOrNoEnum(Integer value, String name, Boolean trueOrFalse) {
        this.value = value;
        this.name = name;
        this.trueOrFalse = trueOrFalse;
    }

    public static Boolean getBoolByValue(Integer value) {
        YesOrNoEnum[] values = YesOrNoEnum.values();
        for (YesOrNoEnum yesOrNo : values) {
            if (yesOrNo.getValue().equals(value)) {
                return yesOrNo.trueOrFalse;
            }
        }
        return false;
    }

    public static Boolean getBoolByName(String name) {
        YesOrNoEnum[] values = YesOrNoEnum.values();
        for (YesOrNoEnum yesOrNo : values) {
            if (yesOrNo.getName().equals(name)) {
                return yesOrNo.trueOrFalse;
            }
        }
        return false;
    }

    public static Integer getValueByBool(Boolean bool) {
        if (bool == null) {
            return null;
        }
        return bool? YES.value: NO.value;
    }

    public static boolean isNotFalse(Integer value) {
        return !isFalse(value);
    }

    public static boolean isFalse(Integer value) {
        return  NO.getValue().equals(value);
    }

    public static boolean isTrue(Integer value) {
        return BooleanUtils.isTrue(getBoolByValue(value));
    }

    public static boolean isNotTrue(Integer value) {
        return !isTrue(value);
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Boolean getTrueOrFalse() {
        return trueOrFalse;
    }

    public static String acquireName(Integer value) {
        for (YesOrNoEnum yesOrNo : values()) {
            if (yesOrNo.getValue().equals(value)) {
                return yesOrNo.name;
            }
        }
        return null;
    }

    public static YesOrNoEnum instanceByValue(Integer value) {
        return Stream.of(values()).filter(yesOrNo ->
                NumberUtils.equals(yesOrNo.value, value)).findFirst().orElse(null);
    }

    public static String getName(Integer value) {
        for (YesOrNoEnum yesOrNo : values()) {
            if (yesOrNo.getValue().equals(value)) {
                return yesOrNo.getName();
            }
        }
        return null;
    }

    public static List<String> getNameList() {
        List<String> nameList = Stream.of(values()).map(YesOrNoEnum::getName).collect(Collectors.toList());
        return nameList;
    }
}
