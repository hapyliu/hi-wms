package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseLocationLock;

public interface IBaseLocationLockService {
    /**
     * 新增
     */
    void insertLocationLock(BaseLocationLock baseLocationLock);
    /**
     * 删除
     */
    void deleteLocationLock(BaseLocationLock baseLocationContainer);
}
