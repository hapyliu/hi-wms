package com.haifeng.system.i18n;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国际化常量
 */
public class I18nConstants {

    /** 语言代码 */
    public static final String LANGUAGE_ZH_CN = "zh_CN";
    public static final String LANGUAGE_EN_US = "en_US";

    public static final List<String> SUPPORTED_LANGUAGES = Arrays.asList(
            LANGUAGE_ZH_CN, LANGUAGE_EN_US
    );

    /** 消息分类 */
    public static final String CATEGORY_SYSTEM = "system";
    public static final String CATEGORY_ERROR = "error";
    public static final String CATEGORY_VALIDATION = "validation";
    public static final String CATEGORY_BUSINESS = "business";
    public static final String CATEGORY_PERMISSION = "permission";
    public static final String CATEGORY_DICT = "dict";

    public static final List<Map<String, String>> CATEGORIES = Arrays.asList(
            createCategory(CATEGORY_SYSTEM, "系统消息"),
            createCategory(CATEGORY_ERROR, "错误消息"),
            createCategory(CATEGORY_VALIDATION, "验证消息"),
            createCategory(CATEGORY_BUSINESS, "业务消息"),
            createCategory(CATEGORY_PERMISSION, "权限消息"),
            createCategory(CATEGORY_DICT, "字典消息")
    );

    private static Map<String, String> createCategory(String value, String label) {
        Map<String, String> map = new HashMap<>();
        map.put("value", value);
        map.put("label", label);
        return map;
    }
}
