
package com.haifeng.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.haifeng.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MyBatis Plus 元对象字段处理器
 * 用于自动填充创建时间和更新时间等字段
 *
 * @author haifeng
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        String username = SecurityUtils.getUsername();
        if (username != null) {
            // 自动填充创建人
            this.strictInsertFill(metaObject, "createBy", String.class, username);
            this.strictUpdateFill(metaObject, "updateBy", String.class, username);
        }
        // 自动填充创建时间
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());

        // 自动填充 delFlag 字段，默认值为 "0"（正常状态）
        this.strictInsertFill(metaObject, "delFlag", String.class, "0");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String username = SecurityUtils.getUsername();
        if (username != null) {
            // 自动填充修改人
            this.strictUpdateFill(metaObject, "updateBy", String.class, username);
        }
        // 自动填充更新时间
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}