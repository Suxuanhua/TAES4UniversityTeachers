package org.suxuanhua.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.suxuanhua.ssm.po.curriculum.CurriculumTable;
import org.suxuanhua.ssm.po.curriculum.CurriculumTableCustom;
import org.suxuanhua.ssm.po.curriculum.CurriculumTableQueryVo;
import org.suxuanhua.ssm.po.teacher.TeacherCustom;
import org.suxuanhua.ssm.service.CurriculumService;
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
@RequestMapping ("/curriculum")
public class CurriculumController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CurriculumService curriculumService;

    /**
     * 多条件查询课程[teacherID,teacherName,teacherPhoneNumber,teacherEMail]，为null 表示查询全部
     *
     * @param curriculumTableQueryVo Curriculum 查询封装类
     * @return "forward:/WEB-INF/jsp/adminUserList.jsp"
     */
    @RequestMapping ("/findcurriculumlist")
    public String findCurriculumList(CurriculumTableQueryVo curriculumTableQueryVo, HttpSession session) throws Exception {

        //根据mybatis 中的sql 语句 如果 adminstratorQueryVo 为null，表示查询全部，所以直接访问该controller 就是查找所有
        List<CurriculumTableCustom> curriculumList = curriculumService.findCurriculumList (curriculumTableQueryVo);
        session.setAttribute ("curriculumList", curriculumList);//添加到sesion 中，方便页面传递
        return "forward:/WEB-INF/jsp/curriculumList.jsp";
    }


    /**
     * 课程信息修改页面显示
     *
     * @param curriculumID ID
     * @return 成功跳转到 "forward:/WEB-INF/jsp/editAdminUserInfo.jsp"，失败"redirect:/admin/findadminuserlist.su";//重定向
     */
    //http 请求方法限定，限定请求方式为GET，method 是一个数组，可以存在多个值
    @RequestMapping (value = "/editcurriculuminfo")
    public ModelAndView editCurriculum(final Integer curriculumID, HttpSession session, HttpServletRequest request, ModelAndView mv) throws Exception {
        //直接从session 中获取，不用查询数据库，减轻数据库压力
        //可以防止通过URL 修改进行查询编辑
        List<CurriculumTableCustom> curriculumList = (List<CurriculumTableCustom>) session.getAttribute ("curriculumList");
        CurriculumTableCustom curriculum = null;
        session.setAttribute ("requestReferer", request.getRequestURI ());
        String notice = (String) session.getAttribute ("message");

        if (curriculumList != null)
            for (CurriculumTableCustom sessionCurriculum : curriculumList) {
                if (sessionCurriculum.getCurriculumID ().equals (curriculumID)) {// Integet 之间的对比，要用equals
                    curriculum = sessionCurriculum;
                }
            }


        String url;
        if (curriculum != null) {
            //添加到 session 域
            session.setAttribute ("curriculum", curriculum);
            url = "/WEB-INF/jsp/editCurriculum.jsp";
        } else {
            //将通知存到request 域
            //session.setAttribute ("message", "找不到该课程的信息");
            notice = "找不到课程的信息";
            url = "redirect:/teacher/findcurriculumlist.su";//在ModelAndView使用重定向，要填写全部URL
        }
        TAES4Utils.setNotice (session, notice);
        mv.setViewName (url);
        return mv;
    }

    /**
     * 课程信息修改提交
     *
     * @param curriculumID 课程id
     * @param curriculum   课程信息pojo
     * @return 成功跳转到 "redirect:findadminuserlist.su"，失败跳转到"redirect:editadminuserinfo.su"
     */
    //使用@ModelAttribute 标记 userCustom 对象要用于数据回显
    @RequestMapping ("/editcurriculumsubmit")
    public String editCurriculumSubmit(final Integer curriculumID,//多添加一个ID 方便协作开发
                                       @ModelAttribute ("curriculum") CurriculumTableCustom curriculum,
                                       HttpSession session, String loginVerifyCode) throws Exception {
        String returnUrl;
        String session_vcode = (String) session.getAttribute ("session_vcode");
        String requestReferer = (String) session.getAttribute ("requestReferer");
        String notice;
        if (session_vcode.equalsIgnoreCase (loginVerifyCode)) {
            if (curriculumService.updateCurriculum (curriculumID, curriculum)) {
                notice = "修改成功";
                returnUrl = "redirect:" + requestReferer;
            } else {
                notice = "修改失败";
                returnUrl = "redirect:/curriculum/editcurriculuminfo.su?curriculumID=" + curriculumID;
            }
        } else {
            notice = "验证码错误，正确的是：" + session_vcode;
            returnUrl = "redirect:/curriculum/editcurriculuminfo.su?curriculumID=" + curriculumID;
        }
        TAES4Utils.setNotice (session, notice);
        return returnUrl;
    }


    /**
     * 添加课程
     *
     * @param curriculum      课程信息封装类
     * @param loginVerifyCode 验证码
     * @return "forward:/WEB-INF/jsp/addTeacher.jsp"
     */
    @RequestMapping (value = "/addcurriculum", method = RequestMethod.POST)
    public String addCurriculum(@ModelAttribute ("curriculum") CurriculumTableCustom curriculum,
                                HttpServletRequest request, HttpSession session,
                                final String loginVerifyCode) throws Exception {
        //课程数据回显
        //使用@ModelAttribute ("teacher")，是request 域的 不能重定向，不重定向点击刷新会重复提交表单
        //session.setAttribute ("teacher", teacher);
        //获取系统生成的验证码 对比传进来的验证码
        String session_vcode = (String) session.getAttribute ("session_vcode");
        if (session_vcode.equalsIgnoreCase (loginVerifyCode)) {
            return curriculumService.addCurriculum (curriculum, request, session);
        } else {
            TAES4Utils.setNotice (session, "验证码错误，正确的是：" + session_vcode);
            return "forward:/WEB-INF/jsp/addCurriculum.jsp";
        }

    }


    /**
     * 添加课程
     */
    @RequestMapping (value = "/addcurriculumbyteacher")
    public String addCurriculumByTeacher(final Integer teacherID, HttpSession session) throws Exception {
        if (teacherID!=null && !"".equals (teacherID)) {
            TeacherCustom teacher =teacherService.findTeacherById (teacherID);
            CurriculumTable curriculum = new CurriculumTable ();
            curriculum.setTeacherName (teacher.getTeacherName ());
            curriculum.setTeacherEMail (teacher.getTeacherEMail ());
            curriculum.setTeacherID (teacher.getTeacherID ());
            curriculum.setTeacherPhoneNumber (teacher.getTeacherPhoneNumber ());
            curriculum.setTeacherSex (teacher.getTeacherSex ());

            session.setAttribute ("curriculum",curriculum);
            return "redirect:/main/addcurriculum.su";
        } else {
            TAES4Utils.setNotice (session, "网络异常，请重新尝试");
            return "redirect:/teacher/findteacherlist.su";
        }
    }


    /**
     * 删除查询
     *
     * @param curriculumID 课程ID
     */
    @RequestMapping ("/deletecurriculumquery")
    public ModelAndView deleteCurriculumQuery(final Integer curriculumID, HttpSession session, HttpServletRequest request, ModelAndView mv) {
        //直接从session 中获取，不用查询数据库，减轻数据库压力
        //可以防止通过URL 修改进行删除课程
        List<CurriculumTableCustom> curriculumList = (List<CurriculumTableCustom>) session.getAttribute ("curriculumList");
        String reqReferer = TAES4Utils.getReferer (request);
        CurriculumTableCustom curriculum = null;
        String notice = (String) session.getAttribute ("message");
        if (curriculumList != null)
            for (CurriculumTableCustom sessionCurriculum : curriculumList) {
                if (sessionCurriculum.getCurriculumID ().equals (curriculumID)) {// Integet 之间的对比，要用equals
                    curriculum = sessionCurriculum;
                }
            }

        String url;
        if (curriculum != null) {
            //添加到 session 域
            session.setAttribute ("curriculum", curriculum);
            url = "/WEB-INF/jsp/deleteCurriculum.jsp";
        } else {
            //将通知存到request 域
            notice = "找不到该课程的信息";
            if (reqReferer != null && !"".equals (reqReferer)) {
                //从哪来回哪去
                url = "redirect:" + reqReferer;//在ModelAndView使用重定向，要填写全部URL
            } else
                url = "redirect:/curriculum/findcurriculumlist.su";//重定向
        }
        TAES4Utils.setNotice (session, notice);
        //防止多次跳转，丢失最初的请求头。传递到提交的方法，方便其精准跳转到原来的地址
        session.setAttribute ("requestReferer", reqReferer);
        mv.setViewName (url);
        return mv;
    }

    /**
     * 删除课程
     *
     * @param curriculumID    课程ID
     * @param loginVerifyCode 验证码
     */
    @RequestMapping ("/deletecurriculumsubmit")
    public String deleteCurriculumSubmit(final Integer curriculumID, String loginVerifyCode, HttpSession session) throws Exception {
        String returnUrl;
        String session_vcode = (String) session.getAttribute ("session_vcode");
        String requestReferer = (String) session.getAttribute ("requestReferer");
        String notice;
        Boolean deleteSituation;
        if (session_vcode.equalsIgnoreCase (loginVerifyCode)) {
            //root 课程不能被删除
            if (curriculumID != null && !curriculumID.equals ("")) {
                //删除数据库中课程信息
                deleteSituation = curriculumService.deleteCurriculumById (curriculumID);
                if (deleteSituation) {
                    notice = "删除[ID：" + curriculumID + "]成功";

                    if (requestReferer != null && !"".equals (requestReferer)) {
                        returnUrl = "redirect:" + requestReferer;
                    } else
                        returnUrl = "redirect:/curriculum/findcurriculumlist.su";
                } else {
                    notice = "删除[ID：" + curriculumID + "]失败";
                    returnUrl = "forward:/curriculum/deletecurriculumquery.su?curriculumID=" + curriculumID;
                }
            } else {
                notice = "找不到该对象信息";
                if (requestReferer != null && !"".equals (requestReferer)) {
                    //从哪来回哪去
                    returnUrl = "redirect:" + requestReferer;//在ModelAndView使用重定向，要填写全部URL
                } else
                    returnUrl = "redirect:/curriculum/findcurriculumlist.su";//重定向
            }
        } else {
            notice = "验证码错误，正确的是：" + session_vcode;
            returnUrl = "forward:/curriculum/deletecurriculumquery.su?curriculumID=" + curriculumID;
        }
        TAES4Utils.setNotice (session, notice);
        return returnUrl;
    }
}
