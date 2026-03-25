package com.haifeng.common.config;

import com.haifeng.common.utils.spring.SpringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author haifeng
 */
@Component
@ConfigurationProperties(prefix = "haifeng")
public class HaifengConfig
{
    /** 项目名称 */
    private String projectName;

    /** 上传路径 */
    private static String profile;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    /** 验证码类型 */
    private static String captchaType;

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public static String getVersion()
    {
        GitProperties gitProperties = SpringUtils.getBean(GitProperties.class);
        return gitProperties.getBranch() + ":" + gitProperties.getShortCommitId();
    }


    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        HaifengConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        HaifengConfig.addressEnabled = addressEnabled;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        HaifengConfig.captchaType = captchaType;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
