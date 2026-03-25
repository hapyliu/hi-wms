package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.haifeng.basedata.domain.BaseBatchAttribute;
import com.haifeng.basedata.domain.BaseBatchNumber;
import com.haifeng.basedata.domain.request.BatchNumberCreateRequest;
import com.haifeng.basedata.domain.vo.BatchNumberVo;
import com.haifeng.basedata.mapper.BaseBatchNumberMapper;
import com.haifeng.basedata.service.IBaseBatchAttributeService;
import com.haifeng.basedata.service.IBaseBatchNumberService;
import com.haifeng.basedata.strategy.BatchNumberGenerationStrategy;
import com.haifeng.basedata.strategy.BatchNumberStrategyFactory;
import com.haifeng.common.core.domain.entity.SysDictData;
import com.haifeng.common.enums.AttrValueTypeEnum;
import com.haifeng.common.enums.EnableOrDisableEnum;
import com.haifeng.common.enums.YesOrNoEnum;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.StringUtils;
import com.haifeng.system.service.ISysDictTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【base_batch_number(批次号表)】的数据库操作 Service 实现
* @createDate 2026-03-11 09:11:46
*/
@Service
public class BaseBatchNumberServiceImpl extends ServiceImpl<BaseBatchNumberMapper, BaseBatchNumber>
    implements IBaseBatchNumberService {

    @Autowired
    private BatchNumberStrategyFactory strategyFactory;

    @Autowired
    private IBaseBatchAttributeService batchAttributeService;

    @Autowired
    private ISysDictTypeService sysDictTypeService;

    @Autowired
    private BaseBatchNumberMapper baseBatchNumberMapper;

    /**
     * 创建批次号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBatchNumber(BatchNumberCreateRequest request) {
        // 1. 获取批次属性列表并填充 attrValue
        List<BaseBatchAttribute> attributes = loadBatchAttributes(request.getAttributes());

        // 2. 校验必填属性(如果属性值类型为枚举，判断属性的值是否是字典定义的数据枚举)
        validateRequiredAttributes(attributes);

        // 3. 获取策略并生成批次号
        BatchNumberGenerationStrategy strategy = StringUtils.isNotEmpty(request.getStrategyName()) 
                ? strategyFactory.getStrategy(request.getStrategyName()) 
                : strategyFactory.getDefaultStrategy();
        String batchNumber = strategy.generateBatchNumber(attributes);

        if (StringUtils.isEmpty(batchNumber)) {
            throw new ServiceException(MessageUtils.message("batch.number.generation.failed"));
        }
        // 校验批次号是否已存在
        if (checkBatchNumberExists(batchNumber)) {
            throw new ServiceException(MessageUtils.message("batch.number.duplicate.validate"));
        }

        // 4. 构建批次号实体
        BaseBatchNumber batchNumberEntity = buildBatchNumberEntity(batchNumber, attributes);

        // 5. 保存到数据库
        save(batchNumberEntity);

        // 6. 构建响应
        //return buildBatchNumberResponse(batchNumberEntity, attributes);
    }

    private boolean checkBatchNumberExists(String batchNumber) {
        LambdaQueryWrapper<BaseBatchNumber> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseBatchNumber::getBatchNumber, batchNumber);
        BaseBatchNumber baseBatchNumber = baseBatchNumberMapper.selectOne(queryWrapper);
        return StringUtils.isNotNull(baseBatchNumber);
    }

    /**
     * 查询批次号列表
     */
    @Override
    public List<BaseBatchNumber> selectBaseBatchNumberList(BaseBatchNumber baseBatchNumber) {
        LambdaQueryWrapper<BaseBatchNumber> queryWrapper = buildQueryWrapper(baseBatchNumber);
        List<BaseBatchNumber> list = list(queryWrapper);
        return list;
    }

    /**
     * 查询批次号列表（分页）
     */
    @Override
    public Page<BatchNumberVo> selectBaseBatchNumberPage(BaseBatchNumber baseBatchNumber) {
        // MyBatis-Plus 的分页会从 ThreadLocal 中自动获取分页参数
        Page<BaseBatchNumber> page = new Page<>();
        LambdaQueryWrapper<BaseBatchNumber> queryWrapper = buildQueryWrapper(baseBatchNumber);
        Page<BaseBatchNumber> resultPage = page(page, queryWrapper);
        
        Page<BatchNumberVo> responsePage = new Page<>();
        BeanUtils.copyProperties(resultPage, responsePage, "records");
        responsePage.setRecords(convertToResponseList(resultPage.getRecords()));
        return responsePage;
    }

    /**
     * 根据 ID 查询批次号详细信息
     */
    @Override
    public BatchNumberVo getBatchNumberById(Long id) {
        BaseBatchNumber batchNumber = getById(id);
        if (batchNumber == null) {
            throw new ServiceException("batch.number.not.found", id);
        }
        return buildBatchNumberResponse(batchNumber, null);
    }

    /**
     * 加载批次属性并设置属性值
     */
    private List<BaseBatchAttribute> loadBatchAttributes(List<BatchNumberCreateRequest.BatchAttributeItem> items) {
        List<BaseBatchAttribute> attributes = new ArrayList<>();
        for (BatchNumberCreateRequest.BatchAttributeItem item : items) {
            BaseBatchAttribute attribute = new BaseBatchAttribute();
            // 如果提供了 attributeId，从数据库加载
            if (item.getAttributeId() != null) {
                BaseBatchAttribute dbAttr = batchAttributeService.getById(item.getAttributeId());
                if (dbAttr == null) {
                    throw new ServiceException("batch.attribute.not.found", item.getAttributeId());
                }
                BeanUtils.copyProperties(dbAttr, attribute);
            } else if (StringUtils.isNotEmpty(item.getAttrCode())) {
                // 如果提供了 attrCode，从数据库加载
                LambdaQueryWrapper<BaseBatchAttribute> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(BaseBatchAttribute::getAttrCode, item.getAttrCode());
                BaseBatchAttribute dbAttr = batchAttributeService.getOne(queryWrapper);
                if (dbAttr == null) {
                    throw new ServiceException("batch.attribute.not.found", item.getAttrCode());
                }
                BeanUtils.copyProperties(dbAttr, attribute);
            }
            // 设置属性值
            attribute.setAttrValue(item.getAttrValue());
            attributes.add(attribute);
        }
        return attributes;
    }

    /**
     * 校验必填属性
     */
    private void validateRequiredAttributes(List<BaseBatchAttribute> attributes) {
        for (BaseBatchAttribute attr : attributes) {
            if (YesOrNoEnum.isTrue(attr.getAttrRequired()) && StringUtils.isBlank(attr.getAttrValue())) {
                throw new ServiceException("batch.attribute.required", attr.getAttrCode());
            }
            if (AttrValueTypeEnum.isEnumType(attr.getAttrType())) {
                List<SysDictData> sysDictDataList = Optional.ofNullable(sysDictTypeService.selectDictDataByType(attr.getAttrEnum())).orElse(Lists.newArrayList());
                List<String> dictValueList = sysDictDataList.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
                if (!dictValueList.contains(attr.getAttrValue())) {
                    throw new ServiceException("batch.attribute.value.invalid", attr.getAttrCode());
                }
            }
        }
    }

    /**
     * 构建批次号实体
     */
    private BaseBatchNumber buildBatchNumberEntity(String batchNumber, List<BaseBatchAttribute> attributes) {
        BaseBatchNumber entity = new BaseBatchNumber();
        entity.setBatchNumber(batchNumber);
        // 按照 attrIndex 排序
        List<BaseBatchAttribute> sortedAttributes = attributes.stream()
                .sorted(Comparator.comparing(BaseBatchAttribute::getAttrIndex))
                .collect(Collectors.toList());
        
        // 将属性值存储到对应的字段
        for (int i = 0; i < sortedAttributes.size() && i < 15; i++) {
            BaseBatchAttribute attr = sortedAttributes.get(i);
            switch (i + 1) {
                case 1: entity.setBatchAttribute1(attr.getAttrValue()); break;
                case 2: entity.setBatchAttribute2(attr.getAttrValue()); break;
                case 3: entity.setBatchAttribute3(attr.getAttrValue()); break;
                case 4: entity.setBatchAttribute4(attr.getAttrValue()); break;
                case 5: entity.setBatchAttribute5(attr.getAttrValue()); break;
                case 6: entity.setBatchAttribute6(attr.getAttrValue()); break;
                case 7: entity.setBatchAttribute7(attr.getAttrValue()); break;
                case 8: entity.setBatchAttribute8(attr.getAttrValue()); break;
                case 9: entity.setBatchAttribute9(attr.getAttrValue()); break;
                case 10: entity.setBatchAttribute10(attr.getAttrValue()); break;
                case 11: entity.setBatchAttribute11(attr.getAttrValue()); break;
                case 12: entity.setBatchAttribute12(attr.getAttrValue()); break;
                case 13: entity.setBatchAttribute13(attr.getAttrValue()); break;
                case 14: entity.setBatchAttribute14(attr.getAttrValue()); break;
                case 15: entity.setBatchAttribute15(attr.getAttrValue()); break;
            }
        }
        
        return entity;
    }

    /**
     * 构建批次号响应
     */
    private BatchNumberVo buildBatchNumberResponse(BaseBatchNumber entity, List<BaseBatchAttribute> attributes) {
        BatchNumberVo response = new BatchNumberVo();
        response.setId(entity.getId());
        response.setBatchNumber(entity.getBatchNumber());
        
        // 如果没有传入 attributes，从实体中解析
        if (attributes == null) {
            attributes = parseAttributesFromEntity(entity);
        }
        
        // 构建响应中的属性列表
        List<BatchNumberVo.BatchAttributeItem> attributeItems = new ArrayList<>();
        for (BaseBatchAttribute attr : attributes) {
            BatchNumberVo.BatchAttributeItem item = new BatchNumberVo.BatchAttributeItem();
            item.setAttrCode(attr.getAttrCode());
            item.setAttrValue(attr.getAttrValue());
            item.setAttrIndex(attr.getAttrIndex());
            attributeItems.add(item);
        }
        response.setAttributes(attributeItems);
        
        return response;
    }

    /**
     * 从实体中解析批次属性
     */
    private List<BaseBatchAttribute> parseAttributesFromEntity(BaseBatchNumber entity) {
        List<BaseBatchAttribute> attributes = new ArrayList<>();
        
        // 查询所有已配置的批次属性（按索引排序）
        LambdaQueryWrapper<BaseBatchAttribute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseBatchAttribute::getStatus, EnableOrDisableEnum.ENABLE.getValue())  // 只查询启用的属性
                    .orderByAsc(BaseBatchAttribute::getAttrIndex);
        List<BaseBatchAttribute> allAttributes = batchAttributeService.list(queryWrapper);
        
        if (allAttributes.isEmpty()) {
            return attributes;
        }
        
        // 根据批次属性配置，从实体中提取对应的值
        for (BaseBatchAttribute attr : allAttributes) {
            BaseBatchAttribute attributeItem = new BaseBatchAttribute();
            BeanUtils.copyProperties(attr, attributeItem);
            
            // 根据 attrIndex 从实体中获取对应的值
            String value = getAttributeValueByIndex(entity, attr.getAttrIndex());
            attributeItem.setAttrValue(value);
            
            attributes.add(attributeItem);
        }
        
        return attributes;
    }

    /**
     * 根据索引从批次号实体中获取属性值
     */
    private String getAttributeValueByIndex(BaseBatchNumber entity, Integer index) {
        if (index == null || index < 1 || index > 15) {
            return null;
        }
        
        switch (index) {
            case 1: return entity.getBatchAttribute1();
            case 2: return entity.getBatchAttribute2();
            case 3: return entity.getBatchAttribute3();
            case 4: return entity.getBatchAttribute4();
            case 5: return entity.getBatchAttribute5();
            case 6: return entity.getBatchAttribute6();
            case 7: return entity.getBatchAttribute7();
            case 8: return entity.getBatchAttribute8();
            case 9: return entity.getBatchAttribute9();
            case 10: return entity.getBatchAttribute10();
            case 11: return entity.getBatchAttribute11();
            case 12: return entity.getBatchAttribute12();
            case 13: return entity.getBatchAttribute13();
            case 14: return entity.getBatchAttribute14();
            case 15: return entity.getBatchAttribute15();
            default: return null;
        }
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<BaseBatchNumber> buildQueryWrapper(BaseBatchNumber baseBatchNumber) {
        LambdaQueryWrapper<BaseBatchNumber> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(baseBatchNumber.getBatchNumber())) {
            queryWrapper.like(BaseBatchNumber::getBatchNumber, baseBatchNumber.getBatchNumber());
        }
        
        // TODO: 可以根据批次属性值进行查询
        
        return queryWrapper;
    }

    /**
     * 转换为响应列表
     */
    public List<BatchNumberVo> convertToResponseList(List<BaseBatchNumber> list) {
        return list.stream()
                .map(entity -> buildBatchNumberResponse(entity, null))
                .collect(Collectors.toList());
    }
}




