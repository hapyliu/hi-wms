package com.haifeng.web.controller.monitor;

import com.haifeng.common.core.domain.AjaxResult;
import com.haifeng.common.utils.spring.SpringUtils;
import com.haifeng.framework.web.domain.GitVersion;
import com.haifeng.framework.web.domain.Server;
import org.springframework.boot.info.GitProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码版本查看
 * 
 * @author haifeng
 */
@RestController
@RequestMapping("/monitor/git")
public class GitVersionController
{
    @PreAuthorize("@ss.hasPermi('monitor:git:view')")
    @GetMapping()
    public AjaxResult getGitVersion()
    {
        GitProperties gitProperties = SpringUtils.getBean(GitProperties.class);
        GitVersion gv = new GitVersion(gitProperties);
        return AjaxResult.success(gv);
    }
}
