package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haifeng.basedata.domain.BaseContainer;
import com.haifeng.basedata.domain.BaseContainerModel;
import com.haifeng.basedata.domain.BaseLocationContainer;
import com.haifeng.basedata.domain.request.ContainerBatchCreateRequest;
import com.haifeng.basedata.mapper.BaseContainerMapper;
import com.haifeng.basedata.service.IBaseContainerModelService;
import com.haifeng.basedata.service.IBaseContainerService;
import com.haifeng.basedata.service.IBaseLocationContainerService;
import com.haifeng.common.constant.UserConstants;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 容器Service业务层处理
 * 
 * @author wangww
 * @date 2026-02-27
 */
@Service
public class BaseContainerServiceImpl implements IBaseContainerService
{
    @Autowired
    private BaseContainerMapper baseContainerMapper;

    @Autowired
    private IBaseContainerModelService baseContainerModelService;

    @Autowired
    private IBaseLocationContainerService baseLocationContainerService;

    /**
     * 查询容器
     * 
     * @param id 容器主键
     * @return 容器
     */
    @Override
    public BaseContainer selectBaseContainerById(Long id)
    {
        return baseContainerMapper.selectBaseContainerById(id);
    }

    /**
     * 查询容器列表
     * 
     * @param baseContainer 容器
     * @return 容器
     */
    @Override
    public List<BaseContainer> selectBaseContainerList(BaseContainer baseContainer)
    {
        return baseContainerMapper.selectBaseContainerList(baseContainer);
    }

    /**
     * 新增容器
     * 
     * @param baseContainer 容器
     * @return 结果
     */
    @Override
    public int insertBaseContainer(BaseContainer baseContainer)
    {
        return baseContainerMapper.insertBaseContainer(baseContainer);
    }

    /**
     * 修改容器
     * 
     * @param baseContainer 容器
     * @return 结果
     */
    @Override
    public int updateBaseContainer(BaseContainer baseContainer)
    {
        return baseContainerMapper.updateBaseContainer(baseContainer);
    }

    /**
     * 批量删除容器
     * 
     * @param ids 需要删除的容器主键
     * @return 结果
     */
    @Override
    public int deleteBaseContainerByIds(Long[] ids)
    {
        for (Long id : ids) {
            //删除前校验库位是否已经填容器
            BaseContainer baseContainer = selectBaseContainerById(id);
            if (StringUtils.isNotNull(baseContainer)) {
                List<BaseLocationContainer> baseLocationContainerList = baseLocationContainerService.selectLocationContainerList(BaseLocationContainer.builder().containerCode(baseContainer.getContainerCode()).build());
                if (CollectionUtils.isNotEmpty(baseLocationContainerList)) {
                    throw new ServiceException(MessageUtils.message("container.delete.location.exists", baseContainer.getContainerCode()));
                }
            }
        }
        return baseContainerMapper.deleteBaseContainerByIds(ids);
    }

    /**
     * 删除容器信息
     * 
     * @param id 容器主键
     * @return 结果
     */
    @Override
    public int deleteBaseContainerById(Long id)
    {
        return baseContainerMapper.deleteBaseContainerById(id);
    }

    @Override
    public boolean checkContainerCodeUnique(BaseContainer baseContainer) {
        List<BaseContainer> baseContainerList = baseContainerMapper.selectBaseContainerList(BaseContainer.builder().containerCode(baseContainer.getContainerCode()).build());
        baseContainerList = baseContainerList.stream().filter(b -> !b.getId().equals(baseContainer.getId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(baseContainerList)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public boolean checkContainerModelCodeExists(String containerModelCode) {
        BaseContainerModel baseContainerModel = baseContainerModelService.getByCode(containerModelCode);
        return ObjectUtils.isNotEmpty(baseContainerModel);
    }

    @Override
    public int batchCreateContainers(ContainerBatchCreateRequest request) {
        // 1. 校验容器模型编号是否存在
        if (!checkContainerModelCodeExists(request.getContainerModelCode())) {
            throw new ServiceException(MessageUtils.message("container.model.code.not.exists", request.getContainerModelCode()));
        }

        // 2. 校验序号范围：结束序号必须大于等于开始序号
        if (request.getEndSequence() < request.getStartSequence()) {
            throw new ServiceException(MessageUtils.message("container.sequence.range.invalid"));
        }

        // 3. 校验序号格式：必须是 15 位数字
        String startSeqStr = String.format("%015d", request.getStartSequence());
        String endSeqStr = String.format("%015d", request.getEndSequence());
        
        // 验证转换为字符串后是否为 15 位（如果输入的数字超过 15 位，格式化后会超过 15 位）
        if (startSeqStr.length() != 15 || endSeqStr.length() != 15) {
            throw new ServiceException(MessageUtils.message("container.sequence.format.invalid"));
        }

        // 4. 生成所有待创建的容器编号并校验唯一性
        List<String> containerCodes = generateContainerCodes(request.getPrefix(), request.getStartSequence(), request.getEndSequence());
        checkContainerCodesUnique(containerCodes);

        // 5. 构建容器列表并批量插入
        List<BaseContainer> containers = containerCodes.stream()
                .map(containerCode -> BaseContainer.builder()
                        .containerCode(containerCode)
                        .containerModelCode(request.getContainerModelCode())
                        .build())
                .collect(Collectors.toList());
        
        return baseContainerMapper.batchInsertBaseContainer(containers);
    }

    /**
     * 生成容器编号列表
     *
     * @param prefix 前缀
     * @param startSequence 开始序号
     * @param endSequence 结束序号
     * @return 容器编号列表
     */
    private List<String> generateContainerCodes(String prefix, Integer startSequence, Integer endSequence) {
        return IntStream.rangeClosed(startSequence, endSequence)
                .mapToObj(seq -> prefix + String.format("%015d", seq))
                .collect(Collectors.toList());
    }

    /**
     * 校验容器编号是否唯一
     *
     * @param containerCodes 容器编号列表
     */
    private void checkContainerCodesUnique(List<String> containerCodes) {
        // 查询数据库中已存在的容器编号
        List<BaseContainer> existingContainers = baseContainerMapper.selectBaseContainerListByCodes(containerCodes);
        
        if (CollectionUtils.isNotEmpty(existingContainers)) {
            // 收集已存在的编号
            String duplicateCodes = existingContainers.stream()
                    .map(BaseContainer::getContainerCode)
                    .collect(Collectors.joining(", "));
            throw new ServiceException(MessageUtils.message("container.batch.code.already.exists", duplicateCodes));
        }
    }

    @Override
    public BaseContainer selectContainerByCode(String containerCode) {
        return baseContainerMapper.selectBaseContainerByCode(containerCode);
    }

    @Override
    public void updateContainerByCtrCode(BaseContainer baseContainer) {
        UpdateWrapper<BaseContainer> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(BaseContainer::getContainerCode, baseContainer.getContainerCode())
                .set(BaseContainer::getPositionType, baseContainer.getPositionType())
                .set(BaseContainer::getPositionCode, baseContainer.getPositionCode())
                .set(BaseContainer::getStationCode, baseContainer.getStationCode());
        baseContainerMapper.update(null, wrapper);
    }
}
