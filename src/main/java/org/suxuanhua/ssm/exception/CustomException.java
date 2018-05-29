package org.suxuanhua.ssm.exception;

/**
 * 统一异常处理，自定义异常类型，实际开发中可能要定义多种异常类型
 * @author XuanhuaSu
 * @version 2018/4/20
 */
public class CustomException extends Exception{
    //异常信息
    private String message;
    private String forwardTo;
    /**
     * 自定义异常类型
     * @param message 异常提示信息
     * @param forwardTo 发生异常后转发到的地址，在执行CustomExceptionResolver执行跳转（53行），如果在 视图解析器 配置了前缀和后缀，请在此省略前缀和后缀
     * @return
     */
    public CustomException(String message,String forwardTo){
        //Exception(String message)  构造带指定详细消息的新异常
        super(message);
        this.message = message;
        this.forwardTo = forwardTo;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getForwardTo() {
        return forwardTo;
    }

    public void setForwardTo(String forwardTo) {
        this.forwardTo = forwardTo;
    }
}
