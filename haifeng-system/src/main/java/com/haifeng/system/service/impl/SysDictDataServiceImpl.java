package com.haifeng.system.service.impl;

import com.haifeng.common.core.domain.entity.SysDictData;
import com.haifeng.common.utils.DictUtils;
import com.haifeng.system.mapper.SysDictDataMapper;
import com.haifeng.system.service.ISysDictDataService;
import com.haifeng.system.service.ISysI18nMessageService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典 业务层处理
 * 
 * @author haifeng
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService
{
    private final SysDictDataMapper dictDataMapper;
    private final ISysI18nMessageService sysI18nMessageService;

    public SysDictDataServiceImpl(SysDictDataMapper dictDataMapper,
                                  ISysI18nMessageService sysI18nMessageService) {
        this.dictDataMapper = dictDataMapper;
        this.sysI18nMessageService = sysI18nMessageService;
    }

    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData)
    {
        List<SysDictData> list = dictDataMapper.selectDictDataList(dictData);
        // 添加国际化标签
        this.addI18nLabel(dictData.getDictType(), list);
        return list;
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     * 
     * @param dictCodes 需要删除的字典数据ID
     */
    @Override
    public void deleteDictDataByIds(Long[] dictCodes)
    {
        for (Long dictCode : dictCodes)
        {
            SysDictData data = selectDictDataById(dictCode);
            dictDataMapper.deleteDictDataById(dictCode);
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
    }

    /**
     * 新增保存字典数据信息
     * 
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData data)
    {
        int row = dictDataMapper.insertDictData(data);
        if (row > 0)
        {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 修改保存字典数据信息
     * 
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData data)
    {
        int row = dictDataMapper.updateDictData(data);
        if (row > 0)
        {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    private List<SysDictData> addI18nLabel(String dictType, List<SysDictData> dictDataList) {
        if (dictDataList == null || dictDataList.isEmpty()) {
            return dictDataList;
        }

        // 构建所有需要查询的key
        List<String> i18nKeys = dictDataList.stream()
                .map(d -> "dict." + dictType + "." + d.getDictValue())
                .collect(Collectors.toList());

        // 一次性批量查询所有消息
        String languageCode = LocaleContextHolder.getLocale().toString().replace("-", "_");
        Map<String, String> i18nMessages = sysI18nMessageService.selectMessagesByKeys(i18nKeys, languageCode);

        // 为每个字典设置国际化标签
        for (SysDictData dictData : dictDataList) {
            String i18nKey = "dict." + dictType + "." + dictData.getDictValue();
            String i18nLabel = i18nMessages.get(i18nKey);

            if (i18nLabel != null) {
                dictData.setI18nLabel(i18nLabel);
            } else {
                dictData.setI18nLabel(dictData.getDictLabel());
            }
        }

        return dictDataList;
    }
}
