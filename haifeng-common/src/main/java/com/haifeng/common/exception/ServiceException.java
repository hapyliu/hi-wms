package com.haifeng.common.exception;

/**
 * 业务异常
 * 
 * @author haifeng
 */
public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 国际化key
     */
    private String i18nCode;

    /**
     * 参数
     */
    private Object[] i18nArgs;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException()
    {
    }

    /**
     * 先放 code，真正 message 在 handler 里翻译
     */
    public ServiceException(String i18nCode, Object... i18nArgs) {
        super(i18nCode);
        this.i18nCode = i18nCode;
        this.i18nArgs = i18nArgs;
    }

    /**
     * 带错误码的国际化构造函数
     */
    public ServiceException(Integer code, String i18nCode, Object... i18nArgs) {
        super(i18nCode);
        this.code = code;
        this.i18nCode = i18nCode;
        this.i18nArgs = i18nArgs;
    }

    public ServiceException(String message)
    {
        this.message = message;
    }

    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public String getI18nCode() { return i18nCode; }

    public Object[] getI18nArgs() { return i18nArgs; }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }
}