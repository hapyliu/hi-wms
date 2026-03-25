package com.haifeng.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 属性值类型枚举
 * @author Haifeng
 */
@Getter
@AllArgsConstructor
public enum AttrValueTypeEnum implements IEnum<String> {
    
    /**
     * 字符类型
     */
    CHARACTER("0", "字符"),
    
    /**
     * 数字类型
     */
    NUMBER("1", "数字"),
    
    /**
     * 枚举类型
     */
    ENUM("2", "枚举"),
    
    /**
     * 日期类型
     */
    DATE("3", "日期"),
    
    /**
     * 日期时间类型
     */
    DATETIME("4", "日期时间");
    
    @JsonValue
    private final String value;
    
    private final String description;
    
    /**
     * 判断是否是枚举类型
     * @param value 属性值类型
     * @return true-是枚举类型，false-不是枚举类型
     */
    public static boolean isEnumType(String value) {
        return ENUM.value.equals(value);
    }
    
    /**
     * 根据值获取枚举
     * @param value 属性值类型
     * @return 对应的枚举，如果不存在则返回 null
     */
    public static AttrValueTypeEnum getByValue(String value) {
        for (AttrValueTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
}
