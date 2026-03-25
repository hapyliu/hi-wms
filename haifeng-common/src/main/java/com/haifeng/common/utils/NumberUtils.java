package com.haifeng.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
public class NumberUtils {
    public static final String JAVA_STRING = "String";
    public static final String JAVA_STRING_FULL = "java.lang.String";
    public static final String JAVA_DATE = "Date";
    public static final String JAVA_DATE_FULL = "java.util.Date";
    public static final String JAVA_INTEGER = "Integer";
    public static final String JAVA_INTEGER_FULL = "java.lang.Integer";
    public static final String JAVA_LONG = "Long";
    public static final String JAVA_LONG_FULL = "java.lang.Long";
    public static final String JAVA_BIGDECIMAL = "BigDecimal";
    public static final String JAVA_BIGDECIMAL_FULL = "java.math.BigDecimal";

    public static final String DTYPE_JSON = "json";


    /**
     * 比较两个数值是否相等。注意比较的两个数值类型必须完全相同。
     *
     * @param num1
     *            待比较数值1
     * @param num2
     *            待比较数值2
     * @return 数值相等或同时为null时，返回true；否则返回false
     */
    public static <T extends Number> boolean equals(T num1, T num2) {
        return compareTo(num1, num2) == 0;
    }

    public static <T extends Number> boolean notEquals(T num1, T num2) {
        return compareTo(num1, num2) != 0;
    }

    /**
     * equal any
     * @param num
     * @param nums
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean equalsAny(T num, T... nums) {
        if (num == null || nums == null) {
            return false;
        }
        return Stream.of(nums).anyMatch(t -> equals(num, t));
    }

    /**
     * 判断num1是否大于num2
     * @param num1
     * @param num2
     * @param <T>
     * @return
     */

    public static <T extends Number> boolean greaterThan(T num1, T num2) {
        return compareTo(num1, num2) > 0;
    }

    /**
     * 判断num1是否大于等于num2
     * @param num1
     * @param num2
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean greaterThanEqual(T num1, T num2) {
        return compareTo(num1, num2) >= 0;
    }

    /**
     * 判断bd1是否大于0
     *
     * @param bd1
     * @return
     */
    public static boolean greaterThanZero(BigDecimal bd1) {
        if (bd1 == null) {
            return false;
        }
        return bd1.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     *
     *
     * @param num
     * @param valueType
     * @return
     * @deprecated
     */
    public static <T extends Number> boolean greaterThanZero(T num, Class<T> valueType) {
        if (num == null) {
            return false;
        }
        return compareTo(num, getInitValue(valueType,null)) > 0;
    }

    public static <T extends Number> boolean greaterThanZero(T num) {
        if (num == null) {
            return false;
        }
        return compareTo(num, getInitValue(num.getClass(),null)) > 0;
    }

    /**
     * 是否在指定区间，包左不包右
     * @param compareVal
     * @param from
     * @param to
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean between(T compareVal, T from, T to) {
        return greaterThanEqual(compareVal, from) && lessThan(compareVal, to);
    }

    /**
     * 是否在指定区间，包左不包右
     * @param compareVal
     * @param from
     * @param to
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean betweenClose(T compareVal, T from, T to) {
        return greaterThanEqual(compareVal, from) && lessThanEqual(compareVal, to);
    }


    /**
     * 判断bd1是否大于等于0
     *
     * @param bd1
     * @return
     */
    public static boolean greaterThanEqualZero(BigDecimal bd1) {
        return bd1.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     *
     *
     * @param num
     * @param valueType
     * @return
     * @deprecated
     */
    public static <T extends Number> boolean greaterThanEqualZero(T num, Class<T> valueType) {
        return compareTo(num,getInitValue(valueType,null)) >= 0;
    }

    public static <T extends Number> boolean greaterThanEqualZero(T num) {
        return compareTo(num, getInitValue(num.getClass(),null)) >= 0;
    }

    /**
     * 判断num1是否小于num2
     * @param num1
     * @param num2
     * @return
     */
    public static <T extends Number> boolean lessThan(T num1, T num2) {
        return compareTo(num1, num2) < 0;
    }

    /**
     * 判断num1是否小于等于num2
     * @param num1
     * @param num2
     * @return
     */
    public static <T extends Number> boolean lessThanEqual(T num1, T num2) {
        return compareTo(num1, num2) <= 0;
    }

    /**
     * 判断bd1是否小于0
     *
     * @param bd1
     * @return
     */
    public static boolean lessThanZero(BigDecimal bd1) {
        return bd1.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean lessThanZero(Long num) {
        return num != null && num < 0L;
    }

    /**
     *
     *
     * @author fengdg
     * @param num
     * @param valueType
     * @return
     * @deprecated
     */
    public static <T extends Number> boolean lessThanZero(T num, Class<T> valueType) {
        return compareTo(num, getInitValue(valueType,null)) < 0;
    }

    public static <T extends Number> boolean lessThanZero(T num) {
        return compareTo(num, getInitValue(num.getClass(),null)) < 0;
    }

    /**
     *
     *
     * @author fengdg
     * @param num
     * @param valueType
     * @return
     * @deprecated
     */
    public static <T extends Number> boolean lessThanEqualZero(T num, Class<T> valueType) {
        return compareTo(num, getInitValue(valueType,null)) <= 0;
    }

    public static <T extends Number> boolean lessThanEqualZero(T num) {
        return compareTo(num,getInitValue(num.getClass(),null)) <= 0;
    }


    /**
     * 判断bd1是否小于等于0
     *
     * @param bd1
     * @return
     */
    public static boolean lessThanEqualZero(BigDecimal bd1) {
        return bd1.compareTo(BigDecimal.ZERO) <= 0;
    }


    public static boolean greaterThanZero(Long num) {
        return num != null && num > 0L;
    }

    public static boolean greaterThanZero(Integer num) {
        return num != null && num > 0;
    }


    /**
     * 求多个BigDecimal之和
     *
     * @param vals
     *            需要相加的BigDecimal数值
     * @return 相加的结果
     */
    public static BigDecimal add(BigDecimal... vals) {
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal val : vals) {
            if (val != null) {
                result = result.add(val);
            }
        }

        return result;
    }

    /**
     * 求多个int之和
     * @param values
     *  需要相加的int数值
     * @return
     */
    public static Integer add(Integer... values) {
        Integer result = 0;
        for (Integer val : values) {
            if (val != null) {
                result += val;
            }
        }
        return result;
    }

    public static Double add(Double... values) {
        Double result = 0D;
        for (Double val : values) {
            if (val != null) {
                result += val;
            }
        }
        return result;
    }

    public static boolean isNotEmpty(Number number) {
        return !isEmpty(number);
    }

    /**
     * 数值类型的空值比较
     *
     * @param number
     * @return
     */
    public static boolean isEmpty(Number number) {
        if (number == null) {
            return true;
        } else {
            Number zeroValue = (Number) getInitValue(number.getClass());
            return equals(number, zeroValue);
        }
    }

    /**
     * 是否空值
     *
     * @param number
     * @return
     */
    public static boolean isEmpty(Long number) {
        return number == null || number == 0L;
    }

    /**
     * 是否空值
     *
     * @param number
     * @return
     */
    public static boolean isEmpty(Integer number) {
        return number == null || number == 0;
    }

    /**
     * 比较两个数值，两个数值的类型必须完全相同，就算一个为Long，一个为Integer也不行。
     * 1. num1大于num2返回大于0的值。num1小于num2返回-1。相等时返回0。
     * 2. null约定为最小值。num1、num2都为null时，返回0,否则认为非null的值大于null值。
     *
     * @param num1
     *            待比较的数值1
     * @param num2
     *            待比较的数值2
     * @return 比较结果。num1大于num2返回大于0的值。num1小于num2返回-1。相等时返回0。
     */
    public static <T extends Number> int compareTo(T num1, T num2) {
        if (num1 != null && num2 != null) {
            return ((Comparable)num1).compareTo(num2);
        } else if (num1 == null && num2 == null) {
            return 0;
        } else {
            return num1 == null ? -1 : 1;
        }
    }


    public static <T> T getInitValue(Class<T> valueType, String dataType) {
        if (String.class.equals(valueType)) {
            //json格式返回null
            if (DTYPE_JSON.equals(dataType)) {
                return null;
            } else {
                return (T) "";
            }
        } else if (Boolean.class.equals(valueType)) {
            return (T) (Boolean)true;//true
        } else if (Date.class.equals(valueType)) {
            return (T) new Date(0L);
        } else if (Number.class.isAssignableFrom(valueType)) {
            return (T) getInitValue(valueType);
        } else {
            return null;
        }
    }

    public static <T> T getInitValue(String valueType) {
        if (JAVA_STRING_FULL.equals(valueType) ||
                JAVA_STRING.equals(valueType)) {
            return (T) "";
        } else if (JAVA_DATE_FULL.equals(valueType) ||
               JAVA_DATE.equals(valueType)) {
            return (T) new Date(0L);
        } else if (JAVA_INTEGER_FULL.equals(valueType) ||
                JAVA_INTEGER.equals(valueType)) {
            return (T) (Integer)0;
        } else if (JAVA_LONG_FULL.equals(valueType) ||
                JAVA_LONG.equals(valueType)) {
            return (T) (Long)0L;
        } else if (JAVA_BIGDECIMAL_FULL.equals(valueType) ||
                JAVA_BIGDECIMAL.equals(valueType)) {
            return (T) BigDecimal.ZERO;
        } else {
            return null;//json也为null
        }
    }

    /**
     * 获取指定类型的初始值
     *
     * @param cls
     * @return
     */
    public static Object getInitValue(Class<?> cls) {
        if (Integer.class.isAssignableFrom(cls)) {
            return 0;
        } else if (Long.class.isAssignableFrom(cls)) {
            return 0L;
        } else if (Short.class.isAssignableFrom(cls)) {
            return 0;
        } else if (Double.class.isAssignableFrom(cls)) {
            return 0D;
        } else if (Float.class.isAssignableFrom(cls)) {
            return 0.0f;
        } else if (BigDecimal.class.isAssignableFrom(cls)) {
            return BigDecimal.ZERO;
        } else {
            return null;
        }
    }

    /**
     * BigDecimal累加，null默认0
     * @param arr
     * @return
     */
    public static BigDecimal sumNullable(BigDecimal... arr) {
        if (ArrayUtils.isEmpty(arr)) {
            return BigDecimal.ZERO;
        }
        return Stream.of(arr).filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /**
     * 小于10的日期补0
     * @param number
     */
    public static String fillZero(String number){
        try {
            if (StringUtils.isBlank(number)) {
                return StringUtils.EMPTY;
            }
            if (Integer.parseInt(number) < 10 && !number.startsWith("0")){
                return  "0"+ number;
            }
            return number;
        } catch (Exception e) {
            log.error("fillZero失败, number=" + number, e);
        }
        return StringUtils.EMPTY;
    }

    public static <T extends Number> T max(T num1, T num2) {
        if (num1 == null) {
            return num2;
        }
        if (num2 == null) {
            return num1;
        }
        return greaterThan(num1, num2) ? num1 : num2;
    }

    /**
     * BigDecimal缩减，设置精度，去掉末尾多余的0
     * @param source
     * @param scale
     * @param mode
     * @return
     */
    public static BigDecimal decimalCut(BigDecimal source, Integer scale, RoundingMode mode) {
        if (source == null) {
            return null;
        }
        if (scale == null) {
            return decimalCut(source);
        }
        BigDecimal result = source.setScale(scale, Optional.ofNullable(mode).orElse(RoundingMode.HALF_UP));
        return decimalCut(result);
    }

    /**
     * BigDecimal缩减，去掉末尾多余的0
     * @param source
     * @return
     */
    public static BigDecimal decimalCut(BigDecimal source) {
        String value = String.valueOf(source);
        if (!StringUtils.contains(value, ".")) {
            return source;
        }
        String result = value.replaceAll("(0)+$", StringUtils.EMPTY);
        if (result.endsWith(".")) {
            result = StringUtils.substring(result, 0, result.length() - 1);
        }
        return new BigDecimal(result);
    }

    /**
     * 判断一个字符串是否是数值
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        return Pattern.matches("[+, -]?\\d+(\\.\\d+)?$", str);
    }

    public static BigDecimal defaultZero(BigDecimal val) {
        return val != null ? val : BigDecimal.ZERO;
    }

    public static Integer defaultZero(Integer val) {
        return val != null ? val : 0;
    }

    public static Long defaultZero(Long val) {
        return val != null ? val : 0L;
    }

    public static String percentage(BigDecimal value, BigDecimal total) {
        if (BigDecimal.ZERO.equals(total)) {
            return BigDecimal.ZERO.toString();
        }
        BigDecimal percentage = value.divide(total, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
        return format(percentage);
    }

    public static String format(BigDecimal value) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(0);
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(value.doubleValue());
    }
}
