package com.haifeng.common.exception.user;

/**
 * 黑名单IP异常类
 * 
 * @author haifeng
 */
public class BlackListException extends UserException
{
    private static final long serialVersionUID = 1L;

    public BlackListException()
    {
        super("login.blocked", null);
    }
}
