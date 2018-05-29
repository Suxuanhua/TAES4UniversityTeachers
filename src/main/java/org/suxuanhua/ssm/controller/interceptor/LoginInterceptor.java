package org.suxuanhua.ssm.controller.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.suxuanhua.ssm.po.admin.AdministratorCustom;
import org.suxuanhua.ssm.tools.TAES4Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户登陆身份验证验证拦截器
 *
 * @author XuanhuaSu
 * @version 2018/4/21
 */
@Controller
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean urlSituation = false;
        //获取请求URL（如果请求的是指定的地址，就放行）
        //String url = request.getRequestURI ();

        //在properties 文件中配置 公开地址
        //判断是否是公开地址
        //使用map 形式，为什么不使用spring 注入，因为该文件是可以随意增减的。
        List<String> openList = (List<String>) request.getServletContext ().getAttribute ("openList");
        if (openList == null) {
            openList = TAES4Utils.getPropertiesValueList ("/openURL.properties");
            request.getServletContext ().setAttribute ("openList", openList);
        }

//        System.out.println (openList.toString ());
//        System.out.println (request.getRequestURI ());

        //判断用户身份在session 是否存在
        HttpSession session = request.getSession ();
        AdministratorCustom admin = (AdministratorCustom) session.getAttribute ("admin");

        for (String url : openList) {
            if (request.getRequestURI ().contains (url)) {
                //是公开地址，放行
//                System.out.println (url + "权限判断：true");
                //如果是登录地址，判断是否已经存在登录用户，存在则跳转至管理页面
                if (request.getRequestURI ().contains ("/adminlogin.su") && admin != null) {
                    response.sendRedirect ("managecenter.su");
                } else
                    urlSituation = true;
                //要跳出，否则一直循环到结束，urlSituation会被改变
                break;
            }
        }

        //如果用户的身份在 session 中存在，放行
        if (admin != null) {
            urlSituation = true;
        }

        if (urlSituation) {
            return urlSituation;
        } else {
            //否则进行拦截
            response.sendRedirect ("adminlogin.su");
            return urlSituation;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
