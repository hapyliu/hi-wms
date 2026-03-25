package com.haifeng.flow.config;

import org.flowable.dmn.spring.SpringDmnEngineConfiguration;
import org.flowable.eventregistry.spring.SpringEventRegistryEngineConfiguration;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Flowable 引擎代码级配置：Process / DMN / Event Registry
 */
@Configuration
public class FlowableConfig {

    /** Process 引擎 */
    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> flowableConfigurer() {
        return configuration -> {
            configuration.setDatabaseSchemaUpdate("false");
            configuration.setAsyncExecutorActivate(true);
            configuration.setDisableIdmEngine(true);
            configuration.setActivityFontName("Microsoft YaHei");
            configuration.setLabelFontName("Microsoft YaHei");
            configuration.setAnnotationFontName("Microsoft YaHei");
        };
    }

    /** DMN 决策引擎 */
    @Bean
    public EngineConfigurationConfigurer<SpringDmnEngineConfiguration> dmnEngineConfigurer() {
        return configuration -> {
            configuration.setDatabaseSchemaUpdate("false");
        };
    }

    /** Event Registry 事件注册引擎 */
    @Bean
    public EngineConfigurationConfigurer<SpringEventRegistryEngineConfiguration> eventRegistryConfigurer() {
        return configuration -> {
            configuration.setDatabaseSchemaUpdate("false");
        };
    }
}
