package com.haifeng.framework.config;

import com.haifeng.system.service.ISysI18nMessageService;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * 数据库消息源实现
 * 从数据库读取国际化消息，支持参数替换和缓存
 */
@Component
@Primary
public class DatabaseMessageSource extends AbstractMessageSource {

    private static final Logger logger = Logger.getLogger(DatabaseMessageSource.class.getName());

    private final ISysI18nMessageService messageService;

    public DatabaseMessageSource(ISysI18nMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        try {
            String languageCode = getLanguageCode(locale);
            var message = messageService.selectMessageByKeyAndLanguage(code, languageCode);

            if (message != null && message.getMessageValue() != null) {
                return message.getMessageValue();
            }

            // Fallback to Chinese if not found
            if (!languageCode.equals("zh_CN")) {
                message = messageService.selectMessageByKeyAndLanguage(code, "zh_CN");
                if (message != null && message.getMessageValue() != null) {
                    return message.getMessageValue();
                }
            }
        } catch (Exception e) {
            logger.warning("Failed to resolve message code: " + code + ", locale: " + locale + ", error: " + e.getMessage());
        }

        return null;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = resolveCodeWithoutArguments(code, locale);
        if (msg != null) {
            return createMessageFormat(msg, locale);
        }
        return null;
    }

    /**
     * Convert Locale to language code
     * e.g., Locale.SIMPLIFIED_CHINESE -> "zh_CN"
     */
    private String getLanguageCode(Locale locale) {
        if (locale == null) {
            return "zh_CN";
        }

        String language = locale.getLanguage();
        String country = locale.getCountry();

        if (country != null && !country.isEmpty()) {
            return language + "_" + country;
        }

        // Default mappings
        switch (language) {
            case "zh":
                return "zh_CN";
            case "en":
                return "en_US";
            default:
                return language + "_" + language.toUpperCase();
        }
    }
}
