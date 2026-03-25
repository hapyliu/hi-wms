package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseMaterial;
import com.haifeng.basedata.domain.BaseMaterialAttrExt;
import com.haifeng.basedata.mapper.BaseMaterialAttrExtMapper;
import com.haifeng.basedata.mapper.BaseMaterialMapper;
import com.haifeng.basedata.service.IBaseMaterialAttrExtService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【base_material_attr_ext(物料属性扩展表)】的数据库操作 Service 实现
 * @createDate 2026-03-11 09:11:45
 */
@Service
public class BaseMaterialAttrExtServiceImpl extends ServiceImpl<BaseMaterialAttrExtMapper, BaseMaterialAttrExt>
    implements IBaseMaterialAttrExtService {

    @Autowired
    private BaseMaterialMapper baseMaterialMapper;

    @Override
    public List<BaseMaterialAttrExt> selectByMaterialId(Long materialId) {
        LambdaQueryWrapper<BaseMaterialAttrExt> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseMaterialAttrExt::getMaterialId, materialId);
        return list(wrapper);
    }

    @Override
    public boolean insertBaseMaterialAttrExt(BaseMaterialAttrExt baseMaterialAttrExt) {
        // 校验物料id是否存在
        checkMaterialIsExists(baseMaterialAttrExt);
        // 校验同一物料下属性名是否重复
        if (!checkAttrNameUnique(baseMaterialAttrExt)) {
            throw new ServiceException(MessageUtils.message("material.attr.name.duplicate"));
        }
        return save(baseMaterialAttrExt);
    }

    private void checkMaterialIsExists(BaseMaterialAttrExt baseMaterialAttrExt) {
        BaseMaterial baseMaterial = baseMaterialMapper.selectById(baseMaterialAttrExt.getMaterialId());
        Assert.notNull(baseMaterial, MessageUtils.message("material.id.not.exists"));
    }

    @Override
    public boolean updateBaseMaterialAttrExt(BaseMaterialAttrExt baseMaterialAttrExt) {
        // 校验物料id是否存在
        checkMaterialIsExists(baseMaterialAttrExt);
        // 校验同一物料下属性名是否重复（排除自身）
        if (!checkAttrNameUnique(baseMaterialAttrExt)) {
            throw new ServiceException(MessageUtils.message("material.attr.name.duplicate"));
        }
        return updateById(baseMaterialAttrExt);
    }

    /**
     * 校验同一物料下属性名是否重复
     * @param baseMaterialAttrExt 物料属性扩展信息
     * @return true-不重复，false-重复
     */
    @Override
    public boolean checkAttrNameUnique(BaseMaterialAttrExt baseMaterialAttrExt) {
        Long id = baseMaterialAttrExt.getId();
        Long materialId = baseMaterialAttrExt.getMaterialId();
        String attrName = baseMaterialAttrExt.getMaterialAttrName();

        LambdaQueryWrapper<BaseMaterialAttrExt> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseMaterialAttrExt::getMaterialId, materialId)
               .eq(BaseMaterialAttrExt::getMaterialAttrName, attrName)
                // 如果是修改操作，需要排除自身
               .ne(id != null, BaseMaterialAttrExt::getId, id);
        List<BaseMaterialAttrExt> list = list(wrapper);
        return CollectionUtils.isEmpty(list);
    }
}




