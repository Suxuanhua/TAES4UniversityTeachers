package org.suxuanhua.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.suxuanhua.ssm.po.curriculum.CurriculumTableCustom;
import org.suxuanhua.ssm.po.curriculum.CurriculumTableQueryVo;
import org.suxuanhua.ssm.po.initconfig.Config;
import org.suxuanhua.ssm.po.teacher.TeacherCustom;
import org.suxuanhua.ssm.po.teacher.TeacherQueryVo;
import org.suxuanhua.ssm.service.CurriculumService;
import org.suxuanhua.ssm.service.TeacherService;
import org.suxuanhua.ssm.tools.TAES4Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 专门用于做跳转访问/WEB-INF/JSP 下的JSP
 *
 * @author XuanhuaSu
 * @version 2018/4/29
 */
@Controller
@RequestMapping ("/main")
public class RedirectController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CurriculumService curriculumService;
    @Autowired
    private Config systemConfig;

    @RequestMapping ("/postlist")
    public ModelAndView visitPostListPage(ModelAndView mv, HttpSession session, HttpServletRequest request) {
        session.setAttribute ("reqReferer", TAES4Utils.getReferer (request));
        mv.setViewName ("/WEB-INF/jsp/postList.jsp");
        return mv;
    }

    @RequestMapping ("/posteditor")
    public ModelAndView visitPostEditorPage(ModelAndView mv, HttpSession session, HttpServletRequest request) {
        session.setAttribute ("reqReferer", TAES4Utils.getReferer (request));
        mv.setViewName ("/WEB-INF/jsp/postEditor.jsp");
        return mv;
    }

    @RequestMapping ("/postcontent")
    public ModelAndView visitPostContentPage(ModelAndView mv, HttpSession session, HttpServletRequest request) {
        session.setAttribute ("reqReferer", TAES4Utils.getReferer (request));
        mv.setViewName ("/WEB-INF/jsp/postContent.jsp");
        return mv;
    }

    @RequestMapping ("/addteacher")
    public ModelAndView visitAddTeacherPage(ModelAndView mv, HttpSession session, HttpServletRequest request) {
        session.setAttribute ("reqReferer", TAES4Utils.getReferer (request));
        mv.setViewName ("/WEB-INF/jsp/addTeacher.jsp");
        return mv;
    }

    @RequestMapping ("/addcurriculum")
    public ModelAndView visitaddCurriculumPage(ModelAndView mv, HttpSession session, HttpServletRequest request) {
        session.setAttribute ("reqReferer", TAES4Utils.getReferer (request));
        mv.setViewName ("/WEB-INF/jsp/addCurriculum.jsp");
        return mv;
    }

    @RequestMapping ("/adminlogin")
    public ModelAndView visitAdminLoginPage(ModelAndView mv) {
        mv.setViewName ("/WEB-INF/jsp/adminLogin.jsp");
        return mv;
    }

    @RequestMapping ("/adminregister")
    public ModelAndView visitAdminRegisterPage(ModelAndView mv) {
        mv.setViewName ("/WEB-INF/jsp/adminRegister.jsp");
        return mv;
    }

    @RequestMapping ("/managecenter")
    public ModelAndView visitManageCenterPage(ModelAndView mv, HttpSession session) throws Exception {

        //查找该页面个需要的各种信息，然后存进域中跳转
        //获取特定职称的
        TeacherQueryVo tv = new TeacherQueryVo ();
        TeacherCustom tc = new TeacherCustom ();
        tc.setTeacherTitle ("教授");
        tv.setTeacherCustom (tc);
        session.setAttribute ("professorNum", teacherService.findTeacherList (tv).size ());

        tc.setTeacherTitle ("副教授");
        tv.setTeacherCustom (tc);
        session.setAttribute ("associateProfessor", teacherService.findTeacherList (tv).size ());

        tc.setTeacherTitle ("讲师");
        tv.setTeacherCustom (tc);
        session.setAttribute ("instructor", teacherService.findTeacherList (tv).size ());

        tc.setTeacherTitle ("助教");
        tv.setTeacherCustom (tc);
        session.setAttribute ("teachingAssistant", teacherService.findTeacherList (tv).size ());


        //获取课程表信息
        CurriculumTableCustom ctc = new CurriculumTableCustom ();
        CurriculumTableQueryVo ctv = new CurriculumTableQueryVo ();

        //年级列表
        Map<String,Integer> grade = new HashMap<> ();

        //获取当前系统时间年
        SimpleDateFormat sim = new SimpleDateFormat ("yyyy");
        String systemYear = sim.format (new Date ());
        //将当前系统年转换成 Integer
        Integer yearInteger = Integer.parseInt (systemYear);

        //获取大一的信息
        //存到年级列表
        grade.put ("university_One",yearInteger);
        //获取每个学年的课程总数
        ctc.setGrade (yearInteger);
        ctv.setCurriculumTable (ctc);
        session.setAttribute ("university_One", curriculumService.findCurriculumList (ctv).size ());

        //获取大二的信息
        Integer university_Two = yearInteger-1;
        //存到年级列表
        grade.put ("university_Two",university_Two);
        //获取每个学年的课程总数
        ctc.setGrade (university_Two);
        ctv.setCurriculumTable (ctc);
        session.setAttribute ("university_Two", curriculumService.findCurriculumList (ctv).size ());

        //获取大三的信息
        Integer university_Three = yearInteger-2;
        //存到年级列表
        grade.put ("university_Three",university_Three);
        //获取每个学年的课程总数
        ctc.setGrade (university_Three);
        ctv.setCurriculumTable (ctc);
        session.setAttribute ("university_Three", curriculumService.findCurriculumList (ctv).size ());

        //获取大四的信息
        Integer university_Four = yearInteger-3;
        //存到年级列表
        grade.put ("university_Four",university_Four);
        //获取每个学年的课程总数
        ctc.setGrade (university_Four);
        ctv.setCurriculumTable (ctc);
        session.setAttribute ("university_Four", curriculumService.findCurriculumList (ctv).size ());

        session.setAttribute ("grade",grade);

        //获取图片虚拟目录，并存到域中
        session.setAttribute ("pic_virtual",systemConfig.getTAES4UT_IMG_VIRTUAL_PATH ());

        mv.setViewName ("/WEB-INF/jsp/manageCenter.jsp");
        return mv;
    }
}
