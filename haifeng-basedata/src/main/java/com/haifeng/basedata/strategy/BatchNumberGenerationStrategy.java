
package com.haifeng.basedata.strategy;

import com.haifeng.basedata.domain.BaseBatchAttribute;

import java.util.List;

/**
 * 批次号生成策略接口
 */
public interface BatchNumberGenerationStrategy {

    /**
     * 生成批次号
     *
     * @param attributes 批次属性列表
     * @return 生成的批次号
     */
    String generateBatchNumber(List<BaseBatchAttribute> attributes);

    /**
     * 获取策略名称
     *
     * @return 策略名称
     */
    String getStrategyName();
}
