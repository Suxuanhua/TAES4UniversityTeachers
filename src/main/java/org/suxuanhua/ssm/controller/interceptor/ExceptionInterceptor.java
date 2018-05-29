package org.suxuanhua.ssm.controller.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * 根据 springMVC.xml 拦截器配置中 配置的 <mvc:mapping path="/**"/> 决定对那个路径下的 Handler 进行拦截
 *
 * @author XuanhuaSu
 * @version 2018/4/21
 */
public class ExceptionInterceptor implements HandlerInterceptor {
    //在 Handler 执行前执行
    //应用场景：用户的认证校验、用户权限校验
    //多个 HandlerInterceptor 该方法顺序执行，只要该方法不放心，拦截到的Controller 方法不能执行完成
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //返回false 表示拦截不继续执行Handler，true 表示放行
        return true;
    }


    //在 执行Handler，返回 ModelAndView 之前执行
    //应用场景：对ModelAndView 下手，向页面提供一些公用的数据 或者 配置一些视图的信息
    //多个 HandlerInterceptor 该方法逆序执行，preHandle() 不放行，该方法不会执行。
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }


    //在 执行Handler 之后执行此方法
    //应用场景：系统的统一异常处理、
    //进行方法执行性能监控（在preHandle() 中 和afterCompletion() 两个方法获取时间点，
    // 计算两个时间的差，就是执行的时长）、
    //系统运行日志统一记录
    //多个 HandlerInterceptor 该方法逆序执行，preHandle() 不放行，该方法不会执行。
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //记录日志到文件
        Logger logger = LogManager.getLogger (LogManager.ROOT_LOGGER_NAME);
        if (ex != null) {
            logger.error (ex);
            System.out.println (ex);
        }
    }
}
