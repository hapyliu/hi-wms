package com.haifeng.system.service;


import com.haifeng.system.domain.SysI18nMessage;

import java.util.List;
import java.util.Map;

/**
 * 国际化消息 Service
 */
public interface ISysI18nMessageService {

    /**
     * 查询消息列表
     */
    List<SysI18nMessage> selectMessageList(SysI18nMessage message);

    /**
     * 查询消息详情
     */
    SysI18nMessage selectMessageById(Long messageId);

    /**
     * 根据消息键和语言代码查询消息
     */
    SysI18nMessage selectMessageByKeyAndLanguage(String messageKey, String languageCode);

    /**
     * 查询指定语言的所有消息
     */
    List<SysI18nMessage> selectMessagesByLanguage(String languageCode);

    /**
     * 新增消息
     */
    int insertMessage(SysI18nMessage message);

    /**
     * 修改消息
     */
    int updateMessage(SysI18nMessage message);

    /**
     * 删除消息
     */
    int deleteMessageById(Long messageId);

    /**
     * 批量删除消息
     */
    int deleteMessageByIds(Long[] messageIds);

    /**
     * 批量插入消息
     */
    int batchInsertMessages(List<SysI18nMessage> messages);

    /**
     * 清除消息缓存
     */
    void clearMessageCache(String messageKey, String languageCode);

    /**
     * 清除指定语言的所有消息缓存
     */
    void clearLanguageCache(String languageCode);

    /**
     * 预热缓存
     */
    void warmupCache();

    /**
     * 获取支持的语言列表
     */
    List<String> getSupportedLanguages();

    /**
     * 获取消息分类列表
     */
    List<Map<String, String>> getCategories();

    /**
     * 批量查询消息
     * @param messageKeys 消息key列表
     * @param languageCode 语言代码
     * @return key->value的Map
     */
    Map<String, String> selectMessagesByKeys(List<String> messageKeys, String languageCode);
}

