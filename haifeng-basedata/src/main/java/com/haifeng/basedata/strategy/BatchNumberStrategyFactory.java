package com.haifeng.basedata.strategy;

import com.haifeng.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 批次号生成策略工厂
 */
@Component
public class BatchNumberStrategyFactory {

    private final Map<String, BatchNumberGenerationStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public BatchNumberStrategyFactory(List<BatchNumberGenerationStrategy> strategies) {
        for (BatchNumberGenerationStrategy strategy : strategies) {
            strategyMap.put(strategy.getStrategyName(), strategy);
        }
    }

    /**
     * 获取指定策略
     *
     * @param strategyName 策略名称
     * @return 批次号生成策略
     */
    public BatchNumberGenerationStrategy getStrategy(String strategyName) {
        BatchNumberGenerationStrategy strategy = strategyMap.get(strategyName);
        if (strategy == null) {
            throw new ServiceException("batch.number.strategy.not.found", strategyName);
        }
        return strategy;
    }

    /**
     * 获取默认策略
     *
     * @return 默认批次号生成策略
     */
    public BatchNumberGenerationStrategy getDefaultStrategy() {
        return getStrategy("DEFAULT");
    }
}
