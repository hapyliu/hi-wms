package com.haifeng.common.utils.bean;


import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean 工具类
 * 
 * @author haifeng
 */
public class BeanUtils extends org.springframework.beans.BeanUtils
{
    /** Bean方法名中属性名开始的下标 */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * 匹配getter方法的正则表达式 */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * 匹配setter方法的正则表达式 */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Bean属性复制工具方法。
     * 
     * @param dest 目标对象
     * @param src 源对象
     */
    public static void copyBeanProp(Object dest, Object src)
    {
        try
        {
            copyProperties(src, dest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象的setter方法。
     * 
     * @param obj 对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj)
    {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<Method>();

        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找setter方法

        for (Method method : methods)
        {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1))
            {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }

    /**
     * 获取对象的getter方法。
     * 
     * @param obj 对象
     * @return 对象的getter方法列表
     */

    public static List<Method> getGetterMethods(Object obj)
    {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找getter方法
        for (Method method : methods)
        {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0))
            {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     * 
     * @param m1 方法名1
     * @param m2 方法名2
     * @return 属性名一样返回true，否则返回false
     */

    public static boolean isMethodPropEquals(String m1, String m2)
    {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }

    /**
     * 创建并拷贝bean
     * @param source   源对象
     * @param targetClass  目标对象class
     * @return  目标对象
     */
    public static <T> T copyBean(Object source, Class<T> targetClass) {
        return copyBean(source, targetClass, (String[]) null);
    }

    /**
     * 创建并拷贝bean
     * @param source   源对象
     * @param targetClass  目标对象class
     * @return  目标对象
     */
    public static <T> T copyBean(Object source, Class<T> targetClass, String... ignoreFields) {
        if (source == null) {
            return null;
        }
        if (targetClass == null) {
            throw new IllegalArgumentException("parameter targetClass should not be null");
        }
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            copyProperties(source, target, ignoreFields);
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建并拷贝bean列表
     * @param sourceList   源对象列表
     * @param targetClass   目标对象class
     * @return  目标对象列表
     */
    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClass) {
        if (sourceList == null) {
            return null;
        }
        List<T> targetList = Lists.newArrayListWithCapacity(sourceList.size());
        for (Object source : sourceList) {
            T target = copyBean(source, targetClass);
            targetList.add(target);
        }
        return targetList;
    }
}
