package com.haifeng.framework.listener;

import com.haifeng.system.service.ISysI18nMessageService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * 应用启动监听器
 * 用于初始化缓存和其他启动任务
 */
@Component
public class ApplicationStartupListener {

    private static final Logger logger = Logger.getLogger(ApplicationStartupListener.class.getName());

    private final ISysI18nMessageService messageService;

    public ApplicationStartupListener(ISysI18nMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 应用启动完成后执行
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {
            logger.info("Starting application initialization tasks...");
            warmupI18nCache();
            logger.info("Application initialization completed successfully");
        } catch (Exception e) {
            logger.severe("Error during application initialization: " + e.getMessage());
        }
    }

    /**
     * 预热国际化消息缓存
     */
    private void warmupI18nCache() {
        try {
            logger.info("Warming up i18n message cache...");
            messageService.warmupCache();
            logger.info("I18n message cache warmed up successfully");
        } catch (Exception e) {
            logger.warning("Failed to warm up i18n cache: " + e.getMessage());
        }
    }
}
