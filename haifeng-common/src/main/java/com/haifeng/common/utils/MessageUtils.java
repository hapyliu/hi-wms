package com.haifeng.common.utils;

import com.haifeng.common.utils.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取i18n资源文件
 * 
 * @author haifeng
 */
public class MessageUtils
{
    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args)
    {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * 解析 ${}中括号中的值
     *
     * @param input 包含 ${} 占位符的字符串
     * @return 解析后的值（如果国际化文件中不存在对应的value，则会返回Key）
     */
    public static String parseMessage(String input)
    {
        // 匹配 ${} 占位符的正则表达式
        Pattern pattern = Pattern.compile("\\$\\{([^\\}]+)\\}");
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer();
        // 逐个匹配并替换 ${} 中的值
        while (matcher.find())
        {
            String key = matcher.group(1); // 匹配到的括号中的值
            // tag 国际化
            String replacement = MessageUtils.message(key);
            // 替换占位符
            matcher.appendReplacement(result, replacement);
        }
        // 处理剩余部分
        matcher.appendTail(result);
        return result.toString();
    }
}
