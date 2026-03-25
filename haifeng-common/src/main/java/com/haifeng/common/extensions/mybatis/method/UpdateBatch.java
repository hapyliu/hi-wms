package com.haifeng.common.extensions.mybatis.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 自定义批量更新方法
 */
@Slf4j
public class UpdateBatch extends AbstractMethod {

    public UpdateBatch() {
        super("updateBatch");
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass,
                                                 TableInfo tableInfo) {
        String sql = "<script>\n<foreach collection=\"list\" item=\"item\" separator=\";" +
                "\">\nupdate %s %s where %s=#{%s} %s\n</foreach>\n</script>";
        String additional = tableInfo.isWithVersion() ?
                tableInfo.getVersionFieldInfo().getVersionOli("item", "item.") :
                "" + tableInfo.getLogicDeleteSql(true, true);
        String setSql = sqlSet(tableInfo.isWithLogicDelete(), false, tableInfo, false, "item",
                "item.");
        String sqlResult = String.format(sql, tableInfo.getTableName(), setSql,
                tableInfo.getKeyColumn(), "item." + tableInfo.getKeyProperty(), additional);
        //log.debug("sqlResult----->{}", sqlResult);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        // 第三个参数必须和RootMapper的自定义方法名一致
        return this.addUpdateMappedStatement(mapperClass, modelClass, "updateBatch", sqlSource);

    }
}