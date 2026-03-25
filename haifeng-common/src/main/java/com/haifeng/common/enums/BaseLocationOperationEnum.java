package com.haifeng.common.enums;

public enum BaseLocationOperationEnum {
    none,
    //绑定
    bind,
    //解绑
    unbind,
    //加锁
    lock,
    //解锁
    unlock,
    //绑定且加锁
    bindAndLock,
    //绑定且解锁
    bindAndUnlock,
    //解绑且解锁
    unbindAndUnlock,
    //解绑但加锁
    unbindAndLock;
}
