package com.haifeng.basedata.strategy.impl;

import com.haifeng.basedata.domain.BaseBatchAttribute;
import com.haifeng.basedata.strategy.BatchNumberGenerationStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 默认批次号生成策略：使用短杠 (-) 拼接批次属性值
 */
@Component
public class DefaultBatchNumberStrategy implements BatchNumberGenerationStrategy {

    private static final String SEPARATOR = "-";

    @Override
    public String generateBatchNumber(List<BaseBatchAttribute> attributes) {
        if (CollectionUtils.isEmpty(attributes)) {
            return "";
        }

        // 按照 attrIndex 排序
        List<BaseBatchAttribute> sortedAttributes = attributes.stream()
                .sorted(Comparator.comparing(BaseBatchAttribute::getAttrIndex))
                .collect(Collectors.toList());

        // 提取属性值并用短杠拼接
        return sortedAttributes.stream()
                .map(BaseBatchAttribute::getAttrValue)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public String getStrategyName() {
        return "DEFAULT";
    }
}
