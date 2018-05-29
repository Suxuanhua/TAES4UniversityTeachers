package org.suxuanhua.ssm.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.suxuanhua.ssm.po.admin.AdministratorCustom;
import org.suxuanhua.ssm.po.admin.AdminstratorQueryVo;
import org.suxuanhua.ssm.po.initconfig.Config;
import org.suxuanhua.ssm.service.AdminService;
import org.suxuanhua.ssm.tools.TAES4Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 管理员Controller
 *
 * @author XuanhuaSu
 * @version 2018/4/22
 */
@Controller
@RequestMapping ("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private Config systemConfig;

    private Logger logger = LogManager.getLogger (LogManager.ROOT_LOGGER_NAME);

    /**
     * 多条件查询用户[uID,uName,uPhoneNumber,uE_Mail]，为null 表示查询全部
     *
     * @param adminstratorQueryVo Adminstrator 查询封装类
     * @return "forward:/WEB-INF/jsp/adminUserList.jsp"
     */
    @RequestMapping ("/findadminuserlist")
    public String findAdminUserList(AdminstratorQueryVo adminstratorQueryVo, HttpSession session) throws Exception {

        //根据mybatis 中的sql 语句 如果 adminstratorQueryVo 为null，表示查询全部，所以直接访问该controller 就是查找所有
        List<AdministratorCustom> adminUserList = adminService.findAdminUserList (adminstratorQueryVo);
        session.setAttribute ("adminUserList", adminUserList);//添加到sesion 中，方便页面传递
        return "forward:/WEB-INF/jsp/adminUserList.jsp";
    }

    /**
     * 用户信息修改页面显示
     *
     * @param auid 管理员ID
     * @return 成功跳转到 "forward:/WEB-INF/jsp/editAdminUserInfo.jsp"，失败"redirect:/admin/findadminuserlist.su";//重定向
     */
    //http 请求方法限定，限定请求方式为GET，method 是一个数组，可以存在多个值
    @RequestMapping (value = "/editadminuserinfo")
    public ModelAndView editAdminUser(final Integer auid, HttpServletRequest request, HttpSession session, ModelAndView mv) throws Exception {
        //System.out.println (request.getRequestURI ());//请求的Handler
        //System.out.println (TAES4Utils.getReferer (request));//请求头
        //AdministratorCustom adminUser = adminService.findAdminUserById (auid);
        //直接从session 中获取，不用查询数据库，减轻数据库压力
        //可以防止通过URL 修改进行查询编辑
        List<AdministratorCustom> adminUserList = (List<AdministratorCustom>) session.getAttribute ("adminUserList");
        String url;
        String reqReferer = TAES4Utils.getReferer (request);

        AdministratorCustom adminUser = null;

        String notice = (String) session.getAttribute ("message");
        if (adminUser == null) {
            adminUser = adminService.findAdminUserById (auid);
        }
        if (adminUserList != null)
            for (AdministratorCustom sessionAdminUser : adminUserList) {
                if (sessionAdminUser.getAdminID ().equals (auid)) {// Integet 之间的对比，要用equals
                    adminUser = sessionAdminUser;
                }
            }

        if (adminUser != null) {
            //添加到 session 域
            session.setAttribute ("adminUser", adminUser);
            //获取图片虚拟目录，并存到域中
            //获取图片虚拟目录，并存到域中
            session.setAttribute ("pic_virtual",systemConfig.getTAES4UT_IMG_VIRTUAL_PATH ());
            url = "/WEB-INF/jsp/editAdminUserInfo.jsp";
        } else {
            //将通知存到request 域
            //session.setAttribute ("message", "找不到该管理员的信息");
            notice = "找不到该管理员的信息";
            logger.warn (notice + "：" + auid);
            if (reqReferer != null && !"".equals (reqReferer)) {
                //从哪来回哪去
                url = "redirect:" + reqReferer;//在ModelAndView使用重定向，要填写全部URL
            } else
                url = "redirect:/admin/findadminuserlist.su";//在ModelAndView使用重定向，要填写全部URL
        }
        TAES4Utils.setNotice (session, notice);
        //从那来，回哪去，放在这里方便传输到提交的方法，用于提交的方法的跳转
        session.setAttribute ("requestReferer", reqReferer);//防止多次跳转，丢失最初的请求头
        mv.setViewName (url);
        return mv;
    }

    /**
     * 用户信息修改提交
     *
     * @param adminID             用户id
     * @param administratorCustom 用户信息pojo
     * @param pictureFile         用户信息头像图片上传类，自动绑定jsp 中的<input type="file" name="pictureFile">
     * @return 成功跳转到 "redirect:findadminuserlist.su"，失败跳转到"redirect:editadminuserinfo.su"
     */
    //使用@ModelAttribute 标记 userCustom 对象要用于数据回显
    @RequestMapping ("/editadminusersubmit")
    public String editAdminUserSubmit(final Integer adminID,//多添加一个ID 方便协作开发
                                      @ModelAttribute ("administratorCustom") AdministratorCustom administratorCustom,
                                      MultipartFile pictureFile, HttpSession session,
                                      String loginVerifyCode) throws Exception {
        AdministratorCustom admin = (AdministratorCustom) session.getAttribute ("admin");

        String returnUrl;
        String session_vcode = (String) session.getAttribute ("session_vcode");
        String requestReferer = (String) session.getAttribute ("requestReferer");
        String notice;

        System.out.println (administratorCustom.toString ());

        if (session_vcode.equalsIgnoreCase (loginVerifyCode)) {
            if (adminService.updateAdminUser (adminID, administratorCustom, pictureFile)) {
                //修改成功后，查询重新覆盖原有的信息，会造成修改别人的管理员用户时，当前管理员用户被修改的覆盖，编程登录的时被修改的
                if (admin.getAdminID ().equals (adminID))
                    session.setAttribute ("admin", adminService.findAdminUserById (adminID));

                notice = "修改成功";
                if (requestReferer != null && !"".equals (requestReferer)) {
                    returnUrl = "redirect:" + requestReferer;
                } else
                    returnUrl = "redirect:findadminuserlist.su";

                logger.info (administratorCustom.getAdminID () + "-" + administratorCustom.getAdminName () + notice);

            } else {
                notice = "修改失败";
                logger.warn (administratorCustom.getAdminID () + "-" + administratorCustom.getAdminName () + notice);
                returnUrl = "redirect:editadminuserinfo.su?auid=" + adminID;
            }
        } else {
            notice = "验证码错误，正确的是：" + session_vcode;
            returnUrl = "redirect:editadminuserinfo.su?auid=" + adminID;
        }
        TAES4Utils.setNotice (session, notice);
        return returnUrl;
    }

    /**
     * 管理员登陆验证方法
     *
     * @param adminEMail      管理员Email
     * @param adminPassword   管理员密码
     * @param loginVerifyCode 登陆验证码
     * @return
     */
    @RequestMapping (value = "/adminlogin", method = RequestMethod.POST)
    public String adminlogin(final String adminEMail,
                             final String adminPassword, final String loginVerifyCode,
                             HttpServletRequest request, HttpSession session) throws Exception {

        String url = "redirect:" + TAES4Utils.getReferer (request);
        //获取系统生成的验证码 对比传进来的验证码
        String session_vcode = (String) session.getAttribute ("session_vcode");
        //需要添加判断，当前是否存在登录用户，如果存在，直接跳转登录页面
        if (session_vcode.equalsIgnoreCase (loginVerifyCode)
                && TAES4Utils.getReferer (request).contains ("/adminlogin.su")
                && !adminEMail.equals ("")
                && adminEMail != null
                && !adminPassword.equals ("")
                && adminPassword != null
                ) {
            //调用Service 校验用户账号 和 密码的 正确性
            AdministratorCustom admin = adminService.findAdminUserByEmail (adminEMail.toLowerCase ());
            if (admin != null) {
                if (admin.getAdminPassword ().equals (adminPassword)) {
                    //如果校验通过，将用户的身份记录到session，用户在 LoginInterceptor 35行中放行。
                    boolean upIpSituation = adminService.setLoginIpByEmail (TAES4Utils.getRemoteHost (request), adminEMail);
                    boolean upTimeSituation = adminService.setLoginTimeByEmail (new Date (), adminEMail);
                    //校验通过
                    if (upIpSituation && upTimeSituation) {
                        session.setAttribute ("admin", admin);
                        url = "redirect:/main/managecenter.su";
                    } else {
                        if (!upIpSituation) {
                            TAES4Utils.setNotice (session, "获取用户IP失败，请重新尝试");
                            logger.warn ("用户的登录，获取用户IP失败");
                        }
                        if (!upTimeSituation) {
                            TAES4Utils.setNotice (session, "未知错误，请重新尝试");
                            logger.warn ("用户的登录，修改登录时间失败");
                        }
                    }

                } else {
                    TAES4Utils.setNotice (session, "密码错误，请重新输入");
                }

            } else {
                TAES4Utils.setNotice (session, "该邮箱未注册，找不到该用户");
            }
        } else
            TAES4Utils.setNotice (session, "验证码错误，请重新输入（或者数据丢失）");

        return url;
    }

    /**
     * 管理员退出
     *
     * @return "redirect:/adminLogin.jsp"
     */
    @RequestMapping ("/adminlogout")
    public String adminlogout(HttpSession session, HttpServletRequest request) throws Exception {
        session.setAttribute ("admin", null);
        session.invalidate ();//session 失效
        session = request.getSession ();
        TAES4Utils.setNotice (session, "登出成功");
        return "redirect:/main/adminlogin.su";
    }

    /**
     * 添加管理员
     *
     * @param administrator   管理员信息封装类
     * @param pictureFile     图片上传
     * @param loginVerifyCode 验证码
     * @return "forward:/adminRegister.jsp"
     */
    @RequestMapping (value = "/adminregister", method = RequestMethod.POST)
    public String adminRegister(@ModelAttribute ("administrator") AdministratorCustom administrator,
                                HttpServletRequest request, HttpSession session, MultipartFile pictureFile,
                                final String loginVerifyCode) throws Exception {

        //获取系统生成的验证码 对比传进来的验证码
        String session_vcode = (String) session.getAttribute ("session_vcode");
        if (session_vcode.equalsIgnoreCase (loginVerifyCode)) {
            //记录日志
            logger.info ("来自" + administrator.getAdminLoginIp () + "用户注册：" + administrator.getAdminName () + "-" + administrator.getAdminPhoneNumber ());
            return adminService.addAdminUser (administrator, request, session, pictureFile);
        } else {
            TAES4Utils.setNotice (session, "验证码错误，正确的是：" + session_vcode);
            return "forward:/main/adminregister.su";
        }

    }

    /**
     * 删除查询
     *
     * @param auid 用户ID
     */
    @RequestMapping ("/deleteadminquery")
    public ModelAndView deleteAdminQuery(final Integer auid, HttpSession session, HttpServletRequest request, ModelAndView mv) {
        //直接从session 中获取，不用查询数据库，减轻数据库压力
        //可以防止通过URL 修改进行删除用户
        List<AdministratorCustom> adminUserList = (List<AdministratorCustom>) session.getAttribute ("adminUserList");
        String reqReferer = TAES4Utils.getReferer (request);
        AdministratorCustom adminUser = null;
        String notice = (String) session.getAttribute ("message");
        if (adminUserList != null)
            for (AdministratorCustom sessionAdminUser : adminUserList) {
                if (sessionAdminUser.getAdminID ().equals (auid)) {// Integet 之间的对比，要用equals
                    adminUser = sessionAdminUser;
                }
            }

        String url;
        if (adminUser != null) {
            //添加到 session 域
            session.setAttribute ("adminUser", adminUser);
            //获取图片虚拟目录，并存到域中
            session.setAttribute ("pic_virtual",systemConfig.getTAES4UT_IMG_VIRTUAL_PATH ());
            url = "/WEB-INF/jsp/deleteAdminUserInfo.jsp";
        } else {
            //将通知存到request 域
            //session.setAttribute ("message", "找不到该管理员的信息");
            notice = "找不到该管理员的信息：" + auid;
            if (reqReferer != null && !"".equals (reqReferer)) {
                //从哪来回哪去
                url = "redirect:" + reqReferer;//在ModelAndView使用重定向，要填写全部URL
            } else
                url = "redirect:/admin/findadminuserlist.su";//重定向

            //记录日志
            logger.warn (notice);
        }
        TAES4Utils.setNotice (session, notice);
        //防止多次跳转，丢失最初的请求头，用户传递到提交的方法，方便其精准跳转到原来的地址
        session.setAttribute ("requestReferer", reqReferer);
        mv.setViewName (url);
        return mv;
    }

    /**
     * 删除管理员用户
     *
     * @param adminID         用户ID
     * @param loginVerifyCode 验证码
     */
    @RequestMapping ("/deleteadminuser")
    public String deleteAdminUser(final Integer adminID, final String adminPermissions, final String adminHeader_default, String loginVerifyCode, HttpSession session) throws Exception {
        String returnUrl;
        String session_vcode = (String) session.getAttribute ("session_vcode");
        String requestReferer = (String) session.getAttribute ("requestReferer");
        String notice;
        Boolean deleteSituation;
        //String fileRootPath = "D:\\TAES4UT-Pictures";
        String fileRootPath =
                TAES4Utils.getPropertiesValue (
                        "/TAES4UniversityTeachers-Configuration.properties", "TAES4UT_IMG_PATH");

        if (session_vcode.equalsIgnoreCase (loginVerifyCode)) {
            //root 用户不能被删除
            if (adminID != null && !adminID.equals ("")) {
                //删除数据库中用户信息
                deleteSituation = adminService.deleteAdminUserById (adminID, adminPermissions, fileRootPath + adminHeader_default);
                if (deleteSituation) {
                    notice = "删除[ID：" + adminID + "]用户成功";

                    if (requestReferer != null && !"".equals (requestReferer)) {
                        returnUrl = "redirect:" + requestReferer;
                    } else
                        returnUrl = "redirect:/admin/findadminuserlist.su";
                } else {
                    notice = "删除[ID：" + adminID + "]用户失败（注意：超级管理员，不能被删除）";
                    returnUrl = "forward:/admin/deleteadminquery.su?auid=" + adminID;
                }
            } else {
                notice = "找不到该用户：" + adminID;
                if (requestReferer != null && !"".equals (requestReferer)) {
                    //从哪来回哪去
                    returnUrl = "redirect:" + requestReferer;//在ModelAndView使用重定向，要填写全部URL
                } else
                    returnUrl = "redirect:/admin/findadminuserlist.su";//重定向
            }
            //记录日志
            logger.warn (notice);

        } else {
            notice = "验证码错误，正确的是：" + session_vcode;
            returnUrl = "forward:/admin/deleteadminquery.su?auid=" + adminID;
        }
        TAES4Utils.setNotice (session, notice);
        return returnUrl;
    }
}
