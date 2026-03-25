package com.haifeng.system.mapper;

import com.haifeng.system.domain.SysI18nMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 国际化消息 Mapper
 */
@Mapper
public interface SysI18nMessageMapper {

    /**
     * 查询消息列表
     */
    List<SysI18nMessage> selectMessageList(SysI18nMessage message);

    /**
     * 根据ID查询消息
     */
    SysI18nMessage selectMessageById(Long messageId);

    /**
     * 根据消息键和语言代码查询消息
     */
    SysI18nMessage selectMessageByKeyAndLanguage(@Param("messageKey") String messageKey, @Param("languageCode") String languageCode);

    /**
     * 查询指定语言的所有消息
     */
    List<SysI18nMessage> selectMessagesByLanguage(@Param("languageCode") String languageCode);

    /**
     * 查询指定语言和分类的消息
     */
    List<SysI18nMessage> selectMessagesByLanguageAndCategory(@Param("languageCode") String languageCode, @Param("category") String category);

    /**
     * 查询所有启用的消息
     */
    List<SysI18nMessage> selectEnabledMessages();

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
     * 检查消息是否存在
     */
    int checkMessageExists(@Param("messageKey") String messageKey, @Param("languageCode") String languageCode);

    /**
     * 批量插入消息
     */
    int batchInsertMessages(List<SysI18nMessage> messages);

    /**
     * 批量查询消息，返回Map格式
     * @param messageKeys 消息key列表
     * @param languageCode 语言代码
     * @return key->value的Map
     */
    Map<String, String> selectMessagesAsMap(@Param("messageKeys") List<String> messageKeys, @Param("languageCode") String languageCode);
}
