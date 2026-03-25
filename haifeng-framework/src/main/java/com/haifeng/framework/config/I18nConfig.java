package com.haifeng.framework.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import com.haifeng.common.constant.Constants;

/**
 * 资源文件配置加载
 * 
 * @author haifeng
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer
{
    private final MessageSource messageSource;

    public I18nConfig(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    @Bean
    public LocaleResolver localeResolver()
    {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // 默认语言
        slr.setDefaultLocale(Constants.DEFAULT_LOCALE);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("lang");
        return lci;
    }

    /**
     * 配置 Validator 使用数据库 MessageSource
     * 使 @NotBlank、@Size 等验证注解支持数据库国际化
     */
    @Bean
    public LocalValidatorFactoryBean validator()
    {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
