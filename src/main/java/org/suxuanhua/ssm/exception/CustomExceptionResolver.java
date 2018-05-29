package org.suxuanhua.ssm.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常处理器
 *
 * @author XuanhuaSu
 * @version 2018/4/20
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {//实现 HandlerExceptionResolver 接口才能让springmvc 认出这个是统一异常处理类

    /**
     * 前端控制器 DispatcherServlet 在进行 HandlerMapping、调用 HandlerAdapter 执行 Handler 过程中，
     * 如果遇到异常，执行此方法
     *
     * @param handler 要执行的handler，HandlerMethod 类，handler 基于方法开发，一个Handler 基于一个method。
     * @param ex      异常信息
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //记录日志到文件
        Logger logger = LogManager.getLogger (LogManager.ROOT_LOGGER_NAME);
        logger.error (ex);

        //输出异常
        ex.printStackTrace ();
        //统一的异常处理器代码

        //异常信息
        String message;

        CustomException customException;

        //异常处理
        //针对系统自定义异常 CustomException ，可以直接从异常类中获取异常信息，将异常处理在错误页面展示。
        if (ex instanceof CustomException) {
            //如果ex 是系统自定义异常，直接取出异常信息
            customException = (CustomException) ex;
        } else {
            //针对非 CustomException 异常，需要重新构造成 CustomException，异常信息为“未知错误”，此类错误需要在系统测试阶段排除。
            customException = new CustomException ("发生了未知的错误，请联系管理员。", "/ExceptionPage.jsp");
        }

        //异常信息
        message = customException.getMessage ();
        //将异常信息存到request 域
        //request.setAttribute ("message",message);
        //request.getRequestDispatcher ("/ExceptionPage.jsp").forward (request,response);//定义错误跳转页面
        ModelAndView modelAndView = new ModelAndView ();
        modelAndView.addObject ("message", message);
        //定义错误跳转页面，让ModelAndView 实现跳转。
        modelAndView.setViewName ("redirect:" + customException.getForwardTo ());
        return modelAndView;
    }
}
