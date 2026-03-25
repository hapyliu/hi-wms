package com.haifeng.common.extensions.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义方法mapper接口
 * @param <T>
 */
public interface RootMapper<T> extends BaseMapper<T> {
    /**
     * 自定义批量插入
     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
     */
    int insertBatch(@Param("list") List<T> list);

    /**
     * 自定义批量更新，条件为主键
     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
     */
    int updateBatch(@Param("list") List<T> list);

    /**
     * MybatisPlus内置的批量插入
     * @param list
     */
    int insertBatchSomeColumn(@Param("list") List<T> list);

    /**
     * 全字段更新不会忽略null值
     * @param entity
     * @return
     */
    int alwaysUpdateSomeColumnById(@Param("et") T entity);

}
