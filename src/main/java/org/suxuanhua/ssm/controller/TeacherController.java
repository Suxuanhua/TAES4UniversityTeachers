package org.suxuanhua.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.suxuanhua.ssm.po.initconfig.Config;
import org.suxuanhua.ssm.po.teacher.Teacher;
import org.suxuanhua.ssm.po.teacher.TeacherCustom;
import org.suxuanhua.ssm.po.teacher.TeacherQueryVo;
import org.suxuanhua.ssm.service.TeacherService;
import org.suxuanhua.ssm.tools.TAES4Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/29
 */
@Controller
@RequestMapping ("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private Config systemConfig;

    /**
     * 多条件查询用户[teacherID,teacherName,teacherPhoneNumber,teacherEMail]，为null 表示查询全部
     *
     * @param teacherQueryVo teacher 查询封装类
     * @return "forward:/WEB-INF/jsp/adminUserList.jsp"
     */
    @RequestMapping ("/findteacherlist")
    public String findTeacherList(TeacherQueryVo teacherQueryVo, HttpSession session) throws Exception {

        //根据mybatis 中的sql 语句 如果 adminstratorQueryVo 为null，表示查询全部，所以直接访问该controller 就是查找所有
        List<TeacherCustom> teacherList = teacherService.findTeacherList (teacherQueryVo);
        session.setAttribute ("teacherList", teacherList);//添加到sesion 中，方便页面传递
        return "forward:/WEB-INF/jsp/teacherList.jsp";
    }


    /**
     * 用户信息修改页面显示
     *
     * @param teacherID 管理员ID
     * @return 成功跳转到 "forward:/WEB-INF/jsp/editAdminUserInfo.jsp"，失败"redirect:/admin/findadminuserlist.su";//重定向
     */
    //http 请求方法限定，限定请求方式为GET，method 是一个数组，可以存在多个值
    @RequestMapping (value = "/editteacherinfo")
    public ModelAndView editTeacher(final Integer teacherID, HttpSession session, ModelAndView mv) throws Exception {
        //直接从session 中获取，不用查询数据库，减轻数据库压力
        //可以防止通过URL 修改进行查询编辑
        List<TeacherCustom> teacherList = (List<TeacherCustom>) session.getAttribute ("teacherList");

        TeacherCustom teacher = null;
        String notice = (String) session.getAttribute ("message");

        if (teacherList != null)
            for (TeacherCustom sessionTeacher : teacherList) {
                if (sessionTeacher.getTeacherID ().equals (teacherID)) {// Integet 之间的对比，要用equals
                    teacher = sessionTeacher;
                }
            }


        String url;
        if (teacher != null) {
            //添加到 session 域
            session.setAttribute ("teacher", teacher);
            //获取图片虚拟目录，并存到域中
            session.setAttribute ("pic_virtual",systemConfig.getTAES4UT_IMG_VIRTUAL_PATH ());
            url = "/WEB-INF/jsp/editTeacherInfo.jsp";
        } else {
            //将通知存到request 域
            //session.setAttribute ("message", "找不到该管理员的信息");
            notice = "找不到该教师的信息";
            url = "redirect:/teacher/findteacherlist.su";//在ModelAndView使用重定向，要填写全部URL
        }
        TAES4Utils.setNotice (session, notice);
        mv.setViewName (url);
        return mv;
    }

    /**
     * 用户信息修改提交
     *
     * @param teacherID     用户id
     * @param teacherCustom 用户信息pojo
     * @param pictureFile   用户信息头像图片上传类，自动绑定jsp 中的<input type="file" name="pictureFile">
     * @return 成功跳转到 "redirect:findadminuserlist.su"，失败跳转到"redirect:editadminuserinfo.su"
     */
    //使用@ModelAttribute 标记 userCustom 对象要用于数据回显
    @RequestMapping ("/editteachersubmit")
    public String editTeacherSubmit(final Integer teacherID,//多添加一个ID 方便协作开发
                                    @ModelAttribute ("teacherCustom") TeacherCustom teacherCustom,
                                    MultipartFile pictureFile, HttpSession session,
                                    String loginVerifyCode) throws Exception {
        String returnUrl;
        String session_vcode = (String) session.getAttribute ("session_vcode");
        String notice;
        if (session_vcode.equalsIgnoreCase (loginVerifyCode)) {
            if (teacherService.updateTeacher (teacherID, teacherCustom, pictureFile)) {
                notice = "修改成功";
                returnUrl = "redirect:findteacherlist.su";
            } else {
                notice = "修改失败";
                returnUrl = "redirect:editteacherinfo.su?tid=" + teacherID;
            }
        } else {
            notice = "验证码错误，正确的是：" + session_vcode;
            returnUrl = "redirect:editteacherinfo.su?tid=" + teacherID;
        }
        TAES4Utils.setNotice (session, notice);
        return returnUrl;
    }


    /**
     * 添加管理员
     *
     * @param teacher         教师信息封装类
     * @param pictureFile     图片上传
     * @param loginVerifyCode 验证码
     * @return "forward:/WEB-INF/jsp/addTeacher.jsp"
     */
    @RequestMapping (value = "/addteacher", method = RequestMethod.POST)
    public String addTeacher(@ModelAttribute ("teacher") TeacherCustom teacher,
                             HttpServletRequest request, HttpSession session, MultipartFile pictureFile,
                             final String loginVerifyCode) throws Exception {
        //用户数据回显
        //使用@ModelAttribute ("teacher")，是request 域的 不能重定向，不重定向点击刷新会重复提交表单
        //session.setAttribute ("teacher", teacher);
        //获取系统生成的验证码 对比传进来的验证码
        String session_vcode = (String) session.getAttribute ("session_vcode");
        if (session_vcode.equalsIgnoreCase (loginVerifyCode)) {
            return teacherService.addTeacher (teacher, request, session, pictureFile);
        } else {
            TAES4Utils.setNotice (session, "验证码错误，正确的是：" + session_vcode);
            return "forward:/WEB-INF/jsp/addTeacher.jsp";
        }

    }

    /**
     * 删除查询
     *
     * @param teacherID 用户ID
     */
    @RequestMapping ("/deleteteacherquery")
    public ModelAndView deleteTeacherQuery(final Integer teacherID, HttpSession session, HttpServletRequest request, ModelAndView mv) {
        //直接从session 中获取，不用查询数据库，减轻数据库压力
        //可以防止通过URL 修改进行删除用户
        List<Teacher> teacherList = (List<Teacher>) session.getAttribute ("teacherList");
        String reqReferer = TAES4Utils.getReferer (request);
        Teacher teacher = null;
        String notice = (String) session.getAttribute ("message");
        if (teacherList != null)
            for (Teacher sessionTeacher : teacherList) {
                if (sessionTeacher.getTeacherID ().equals (teacherID)) {// Integet 之间的对比，要用equals
                    teacher = sessionTeacher;
                }
            }

        String url;
        if (teacher != null) {
            //添加到 session 域
            session.setAttribute ("teacher", teacher);
            //获取图片虚拟目录，并存到域中
            session.setAttribute ("pic_virtual",systemConfig.getTAES4UT_IMG_VIRTUAL_PATH ());
            url = "/WEB-INF/jsp/deleteTeacherInfo.jsp";
        } else {
            //将通知存到request 域
            notice = "找不到该管理员的信息";
            if (reqReferer != null && !"".equals (reqReferer)) {
                //从哪来回哪去
                url = "redirect:" + reqReferer;//在ModelAndView使用重定向，要填写全部URL
            } else
                url = "redirect:/teacher/findteacherlist.su";//重定向
        }
        TAES4Utils.setNotice (session, notice);
        //防止多次跳转，丢失最初的请求头。传递到提交的方法，方便其精准跳转到原来的地址
        session.setAttribute ("requestReferer", reqReferer);
        mv.setViewName (url);
        return mv;
    }

    /**
     * 删除管理员用户
     *
     * @param teacherID       教师ID
     * @param loginVerifyCode 验证码
     */
    @RequestMapping ("/deleteteachersubmit")
    public String deleteTeacherSubmit(final Integer teacherID, final String teacherHeader_default, String loginVerifyCode, HttpSession session) throws Exception {
        String returnUrl;
        String session_vcode = (String) session.getAttribute ("session_vcode");
        String requestReferer = (String) session.getAttribute ("requestReferer");
        String notice;
        Boolean deleteSituation;
//        String fileRootPath = "D:\\TAES4UT-Pictures";

        String fileRootPath =
                TAES4Utils.getPropertiesValue (
                        "/TAES4UniversityTeachers-Configuration.properties", "TAES4UT_IMG_PATH");

        if (session_vcode.equalsIgnoreCase (loginVerifyCode)) {
            //root 用户不能被删除
            if (teacherID != null && !teacherID.equals ("")) {
                //删除数据库中用户信息
                deleteSituation = teacherService.deleteTeacherById (teacherID, fileRootPath + teacherHeader_default);
                if (deleteSituation) {
                    notice = "删除[ID：" + teacherID + "]成功";

                    if (requestReferer != null && !"".equals (requestReferer)) {
                        returnUrl = "redirect:" + requestReferer;
                    } else
                        returnUrl = "redirect:/teacher/findteacherlist.su";
                } else {
                    notice = "删除[ID：" + teacherID + "]失败";
                    returnUrl = "forward:/teacher/deleteteacherquery.su?teacherID=" + teacherID;
                }
            } else {
                notice = "找不到该对象信息";
                if (requestReferer != null && !"".equals (requestReferer)) {
                    //从哪来回哪去
                    returnUrl = "redirect:" + requestReferer;//在ModelAndView使用重定向，要填写全部URL
                } else
                    returnUrl = "redirect:/teacher/findteacherlist.su";//重定向
            }
        } else {
            notice = "验证码错误，正确的是：" + session_vcode;
            returnUrl = "forward:/teacher/deleteteacherquery.su?teacherID=" + teacherID;
        }
        TAES4Utils.setNotice (session, notice);
        return returnUrl;
    }
}
