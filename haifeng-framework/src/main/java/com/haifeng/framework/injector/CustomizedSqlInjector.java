package com.haifeng.framework.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.haifeng.common.extensions.mybatis.method.InsertBatch;
import com.haifeng.common.extensions.mybatis.method.UpdateBatch;

import java.util.List;

/**
 * 自定义方法 SQL 注入器
 *
 */
public class CustomizedSqlInjector extends DefaultSqlInjector {

    /**
     * 如果只需增加方法，保留 mybatis plus 自带方法，
     * 可以先获取 super.getMethodList()，再添加 add
     *
     * @param mapperClass mapper 接口类
     * @param tableInfo 表信息
     * @return 方法列表
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        methodList.add(new InsertBatch());
        methodList.add(new UpdateBatch());
        methodList.add(new InsertBatchSomeColumn());
        methodList.add(new AlwaysUpdateSomeColumnById());
        return methodList;
    }
}
