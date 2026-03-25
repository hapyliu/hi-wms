package com.haifeng.system.service.impl;

import com.haifeng.common.core.redis.RedisCache;
import com.haifeng.common.utils.DateUtils;
import com.haifeng.system.domain.SysI18nMessage;
import com.haifeng.system.i18n.I18nConstants;
import com.haifeng.system.mapper.SysI18nMessageMapper;
import com.haifeng.system.service.ISysI18nMessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * 国际化消息 Service 实现
 */
@Service
public class SysI18nMessageServiceImpl implements ISysI18nMessageService {

    private static final Logger logger = Logger.getLogger(SysI18nMessageServiceImpl.class.getName());

    private final SysI18nMessageMapper messageMapper;
    private final RedisCache redisCache;

    private static final String I18N_MESSAGE_CACHE_PREFIX = "i18n:message:";
    private static final Integer CACHE_EXPIRE_HOURS = 24;

    public SysI18nMessageServiceImpl(SysI18nMessageMapper messageMapper, RedisCache cache) {
        this.messageMapper = messageMapper;
        this.redisCache = cache;
    }

    @Override
    public List<SysI18nMessage> selectMessageList(SysI18nMessage message) {
        return messageMapper.selectMessageList(message);
    }

    @Override
    public SysI18nMessage selectMessageById(Long messageId) {
        return messageMapper.selectMessageById(messageId);
    }

    @Override
    public SysI18nMessage selectMessageByKeyAndLanguage(String messageKey, String languageCode) {
        String cacheKey = buildCacheKey(languageCode, messageKey);

        // Try to get from cache
        SysI18nMessage cached = redisCache.getCacheObject(cacheKey);
        if (cached != null) {
            return cached;
        }

        // Get from database
        SysI18nMessage message = messageMapper.selectMessageByKeyAndLanguage(messageKey, languageCode);

        // Cache the result
        if (message != null) {
            redisCache.setCacheObject(cacheKey, message, CACHE_EXPIRE_HOURS, TimeUnit.HOURS);
        }

        return message;
    }

    @Override
    public List<SysI18nMessage> selectMessagesByLanguage(String languageCode) {
        return messageMapper.selectMessagesByLanguage(languageCode);
    }

    @Override
    public int insertMessage(SysI18nMessage message) {
        message.setCreateTime(DateUtils.getNowDate());
        message.setStatus(1);
        return messageMapper.insertMessage(message);
    }

    @Override
    public int updateMessage(SysI18nMessage message) {
        message.setUpdateTime(DateUtils.getNowDate());
        int result = messageMapper.updateMessage(message);

        // Clear cache
        if (result > 0) {
            clearMessageCache(message.getMessageKey(), message.getLanguageCode());
        }

        return result;
    }

    @Override
    public int deleteMessageById(Long messageId) {
        SysI18nMessage message = messageMapper.selectMessageById(messageId);
        int result = messageMapper.deleteMessageById(messageId);

        // Clear cache
        if (result > 0 && message != null) {
            clearMessageCache(message.getMessageKey(), message.getLanguageCode());
        }

        return result;
    }

    @Override
    public int deleteMessageByIds(Long[] messageIds) {
        int result = messageMapper.deleteMessageByIds(messageIds);

        // Clear all message cache
        if (result > 0) {
            clearAllMessageCache();
        }

        return result;
    }

    @Override
    public int batchInsertMessages(List<SysI18nMessage> messages) {
        if (messages == null || messages.isEmpty()) {
            return 0;
        }

        for (SysI18nMessage message : messages) {
            message.setCreateTime(DateUtils.getNowDate());
            message.setStatus(1);
        }

        return messageMapper.batchInsertMessages(messages);
    }

    @Override
    public void clearMessageCache(String messageKey, String languageCode) {
        String cacheKey = buildCacheKey(languageCode, messageKey);
        redisCache.deleteObject(cacheKey);
    }

    @Override
    public void clearLanguageCache(String languageCode) {
        // Clear all messages for this language
        String pattern = I18N_MESSAGE_CACHE_PREFIX + languageCode + ":*";
        redisCache.deleteObject(redisCache.keys(pattern));
    }

    @Override
    public void warmupCache() {
        // Load all enabled messages into cache
        List<SysI18nMessage> messages = messageMapper.selectEnabledMessages();
        for (SysI18nMessage message : messages) {
            String cacheKey = buildCacheKey(message.getLanguageCode(), message.getMessageKey());
            redisCache.setCacheObject(cacheKey, message, CACHE_EXPIRE_HOURS, TimeUnit.HOURS);
        }
    }

    @Override
    public List<String> getSupportedLanguages() {
        return I18nConstants.SUPPORTED_LANGUAGES;
    }

    @Override
    public List<Map<String, String>> getCategories() {
        return I18nConstants.CATEGORIES;
    }

    @Override
    public Map<String, String> selectMessagesByKeys(List<String> messageKeys, String languageCode) {
        if (messageKeys == null || messageKeys.isEmpty()) {
            return Map.of();
        }

        Map<String, String> map = messageMapper.selectMessagesAsMap(messageKeys, languageCode);
        if (null == map) {
            return Map.of();
        }
        return map;
    }

    private String buildCacheKey(String languageCode, String messageKey) {
        return I18N_MESSAGE_CACHE_PREFIX + languageCode + ":" + messageKey;
    }

    private void clearAllMessageCache() {
        String pattern = I18N_MESSAGE_CACHE_PREFIX + "*";
        redisCache.deleteObject(redisCache.keys(pattern));
    }
}
