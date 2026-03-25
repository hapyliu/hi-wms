package com.haifeng.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.haifeng.common.config.HaifengConfig;
import com.haifeng.common.utils.StringUtils;

/**
 * 首页
 *
 * @author haifeng
 */
@RestController
public class SysIndexController
{
    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("欢迎使用Hi-WMS，当前版本：{}，请通过前端地址访问。",
                HaifengConfig.getVersion());
    }
}
