package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseBatchAttribute;
import com.haifeng.basedata.domain.request.EnableOrDisableRequest;
import com.haifeng.basedata.mapper.BaseBatchAttributeMapper;
import com.haifeng.basedata.service.IBaseBatchAttributeService;
import com.haifeng.common.enums.AttrValueTypeEnum;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.StringUtils;
import com.haifeng.common.utils.bean.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
* @author Administrator
* @description 针对表【base_batch_attribute(批次属性表)】的数据库操作 Service 实现
* @createDate 2026-03-11 09:11:46
*/
@Service
public class BaseBatchAttributeServiceImpl extends ServiceImpl<BaseBatchAttributeMapper, BaseBatchAttribute>
    implements IBaseBatchAttributeService {

    @Override
    public List<BaseBatchAttribute> selectBaseBatchAttributeList(BaseBatchAttribute baseBatchAttribute) {
        LambdaQueryWrapper<BaseBatchAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(baseBatchAttribute.getAttrCode() != null, 
                   BaseBatchAttribute::getAttrCode, baseBatchAttribute.getAttrCode())
               .eq(baseBatchAttribute.getStatus() != null, 
                   BaseBatchAttribute::getStatus, baseBatchAttribute.getStatus())
                .orderByAsc(BaseBatchAttribute::getAttrIndex);
        return list(wrapper);
    }

    @Override
    public boolean insertBaseBatchAttribute(BaseBatchAttribute baseBatchAttribute) {
        validate(baseBatchAttribute);
        return save(baseBatchAttribute);
    }

    @Override
    public boolean updateBaseBatchAttribute(BaseBatchAttribute baseBatchAttribute) {
        // 校验批次属性编码是否唯一（排除自身）
        validate(baseBatchAttribute);
        return updateById(baseBatchAttribute);
    }

    @Override
    public int deleteBaseBatchAttributeByIds(Long[] ids) {

        return removeByIds(Arrays.asList(ids)) ? ids.length : 0;
    }

    private void validate(BaseBatchAttribute baseBatchAttribute) {
        // 校验批次属性编码是否唯一
        if (!checkAttrCodeUnique(baseBatchAttribute)) {
            throw new ServiceException(MessageUtils.message("batch.attr.code.duplicate"));
        }
        // 校验批次属性顺序是否唯一
        if (!checkAttrIndexUnique(baseBatchAttribute)) {
            throw new ServiceException(MessageUtils.message("batch.attr.index.duplicate"));
        }
        // 如果属性值类型是枚举，校验属性枚举必传
        if (AttrValueTypeEnum.isEnumType(baseBatchAttribute.getAttrType())) {
            if (StringUtils.isBlank(baseBatchAttribute.getAttrEnum())) {
                throw new ServiceException(MessageUtils.message("batch.attr.enum.required"));
            }
        }
    }

    /**
     * 通用唯一性校验方法
     * @param baseBatchAttribute 批次属性信息
     * @param fieldValue 要校验的字段值
     * @param fieldGetter 字段获取函数
     * @return true-不重复，false-重复
     */
    private <T> boolean checkFieldUnique(BaseBatchAttribute baseBatchAttribute, 
                                         T fieldValue, 
                                         SFunction<BaseBatchAttribute, T> fieldGetter) {
        Long id = baseBatchAttribute.getId();
        
        LambdaQueryWrapper<BaseBatchAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(fieldGetter, fieldValue)
               .ne(id != null, BaseBatchAttribute::getId, id);
        
        List<BaseBatchAttribute> list = list(wrapper);
        boolean isUnique = CollectionUtils.isEmpty(list);
        return isUnique;
    }
    
    /**
     * 校验批次属性编码是否唯一
     * @param baseBatchAttribute 批次属性信息
     * @return true-不重复，false-重复
     */
    @Override
    public boolean checkAttrCodeUnique(BaseBatchAttribute baseBatchAttribute) {
        return checkFieldUnique(baseBatchAttribute, 
                               baseBatchAttribute.getAttrCode(), 
                               BaseBatchAttribute::getAttrCode);
    }

    /**
     * 校验批次属性顺序是否唯一
     * @param baseBatchAttribute 批次属性信息
     * @return true-不重复，false-重复
     */
    @Override
    public boolean checkAttrIndexUnique(BaseBatchAttribute baseBatchAttribute) {
        return checkFieldUnique(baseBatchAttribute, 
                               baseBatchAttribute.getAttrIndex(), 
                               BaseBatchAttribute::getAttrIndex);
    }

    @Override
    public int changeStatus(EnableOrDisableRequest enableOrDisableRequest) {
        BaseBatchAttribute baseBatchAttribute = BeanUtils.copyBean(enableOrDisableRequest, BaseBatchAttribute.class);
        return getBaseMapper().updateById(baseBatchAttribute);
    }
}




