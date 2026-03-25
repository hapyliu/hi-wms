package com.haifeng.framework.web.domain;

import org.springframework.boot.info.GitProperties;

/**
 * 代码仓库版本相关信息
 *
 * @author haifeng
 */
public class GitVersion {

    /**
     * 构建时间
     */
    private String buildTime;

    /**
     * 分支
     */
    private String branch;

    /**
     * 构建版本
     */
    private String buildVersion;

    /**
     * 提交记录 短id
     */
    private String commitIdAbbrev;

    /**
     * 提交时间
     */
    private String commitTime;

    /**
     * 提交信息
     */
    private String commitMessageShort;

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getCommitIdAbbrev() {
        return commitIdAbbrev;
    }

    public void setCommitIdAbbrev(String commitIdAbbrev) {
        this.commitIdAbbrev = commitIdAbbrev;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getCommitMessageShort() {
        return commitMessageShort;
    }

    public void setCommitMessageShort(String commitMessageShort) {
        this.commitMessageShort = commitMessageShort;
    }

    public GitVersion (GitProperties properties) {
        this.setBranch(properties.get("branch"));
        this.setCommitTime(properties.get("commit.time"));
        this.setBuildTime(properties.get("build.time"));
        this.setBuildVersion(properties.get("build.version"));
        this.setCommitIdAbbrev(properties.get("commit.id.abbrev"));
        this.setCommitMessageShort(properties.get("commit.message.short"));
    }

}
