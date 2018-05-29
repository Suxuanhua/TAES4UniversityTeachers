package org.suxuanhua.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.suxuanhua.ssm.po.admin.Administrator;
import org.suxuanhua.ssm.po.curriculum.CurriculumTableCustom;
import org.suxuanhua.ssm.po.curriculum.CurriculumTableQueryVo;
import org.suxuanhua.ssm.po.teacher.TeacherCustom;
import org.suxuanhua.ssm.po.teacher.TeacherQueryVo;
import org.suxuanhua.ssm.po.visit.Visit;
import org.suxuanhua.ssm.service.CurriculumService;
import org.suxuanhua.ssm.service.TeacherService;
import org.suxuanhua.ssm.service.VisitService;
import org.suxuanhua.ssm.tools.TAES4Utils;
import org.suxuanhua.ssm.tools.VerifyCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/29
 */
@Controller
@RequestMapping ("/utils")
public class UtilsController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CurriculumService curriculumService;
    @Autowired
    private VisitService visitService;

    /**
     * 通过URL 记录页面访问信息，记录访问量
     *
     * @param
     * @return
     */
    public void recordVisits(HttpServletRequest request) throws Exception {
        //获取请求头的各种信息
        String requestMethod = request.getMethod ();
        String requestURI = TAES4Utils.getReferer (request);
        String userAgent = request.getHeader ("User-Agent");
        String accept = request.getHeader ("Accept");
        String accept_Language = request.getHeader ("Accept-Language");
        String accept_charset = request.getCharacterEncoding ();
        String ip = TAES4Utils.getRemoteHost (request);


        Administrator session_Admin = (Administrator) request.getSession ().getAttribute ("admin");

        Integer session_Id;

        if (session_Admin != null) {
            session_Id = session_Admin.getAdminID ();
        } else
            session_Id = -1;

        Visit visit = new Visit ();
        visit.setVid (TAES4Utils.randomUUID ());
        visit.setComeToVisitId (session_Id);
        visit.setRequestMethod (requestMethod);
        visit.setRequestURI (requestURI);
        visit.setComeToVisitIp (ip);
        visit.setRequestTime (new Date ());
        visit.setRequestUserAgent (userAgent);
        visit.setRequestAccept (accept);
        visit.setAcceptLanguage (accept_Language);
        visit.setAcceptCharset (accept_charset);
        visit.setRequestNumber (1);


        List<Visit> vslist = visitService.findVisitByIp (ip);
        //如果数据库中已经有了这个IP的信息
        if (visit != null && vslist.size () > 0) {
            boolean foreachSituation = false;
            for (Visit vs : vslist) {
                //对比各种信息是否相同，相同访问量 +1
                Integer vs_id = vs.getComeToVisitId ();

                if (vs.getRequestMethod () != null && vs.getRequestMethod ().equals (requestMethod)
                        && vs.getRequestURI () != null && vs.getRequestURI ().equals (requestURI)
                        && vs.getRequestUserAgent () != null && vs.getRequestUserAgent ().equals (userAgent)
                        && vs.getRequestAccept () != null && vs.getRequestAccept ().equals (accept)
                        && vs.getAcceptLanguage () != null && vs.getAcceptLanguage ().equals (accept_Language)
                        && vs.getAcceptCharset () != null && vs.getAcceptCharset ().equals (accept_charset)) {

                    //无论userid 是否 等于 null , 是要相等就 +1
                    if (vs_id.equals (session_Id)) {
                        visitService.addVisits (ip);
                    } else {
                        visitService.addVisitInfo (visit);
                    }
                    foreachSituation = true;
                    break;
                }
            }
            //遍历完之后条件不满足的操作
            if (!foreachSituation)
                visitService.addVisitInfo (visit);
        } else {
            visitService.addVisitInfo (visit);
        }
    }


    /**
     * 获取验证码
     * 直接将片输出到调用的位置
     */
    //需要将此方法移到一个UtilsController 中
    @RequestMapping ("/getv_code")
    public void getVerificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
         * 1. 生成图片
         * 2. 保存图片上的文本到session域中
         * 3. 把图片响应给客户端
         */
        VerifyCode vc = new VerifyCode ();
        BufferedImage image = vc.getImage ();
        //保存图片上的文本到session域
        request.getSession ().setAttribute ("session_vcode", vc.getText ());
        //通过response.getOutputStream()获取输出流。该流输出到客户端
        VerifyCode.output (image, response.getOutputStream ());
    }

    @RequestMapping ("/findall")
    public String findAll(String searchWord, HttpServletRequest request, HttpSession session) throws Exception {
        String url = "redirect:" + TAES4Utils.getReferer (request);
        if (searchWord != null && url.contains ("/TAES4University-Teachers/")) {

            //教师相关信息搜索
            TeacherQueryVo tv = new TeacherQueryVo ();
            TeacherCustom teacher = new TeacherCustom ();
            List<TeacherCustom> teacherList = new LinkedList<> ();
            try {
                Integer id = Integer.parseInt (searchWord);
                teacher.setTeacherID (id);
            } catch (Exception e) {
                if (searchWord.contains ("@")) {
                    teacher.setTeacherEMail (searchWord);
                } else if (searchWord.contains ("教授")
                        || searchWord.contains ("副教授")
                        || searchWord.contains ("讲师")
                        || searchWord.contains ("助教")) {
                    String cd;
                    if (searchWord.contains ("副教授")) {
                        cd = "副教授";
                    } else if (searchWord.contains ("教授")) {
                        cd = "教授";
                    } else if (searchWord.contains ("讲师")) {
                        cd = "讲师";
                    } else if (searchWord.contains ("助教")) {
                        cd = "助教";
                    } else
                        cd = "";

                    //结束位置 = 第一次出现的位置+它的长度= 结束的位置
                    String title = searchWord.substring (searchWord.indexOf (cd), searchWord.indexOf (cd) + cd.length ());
                    teacher.setTeacherTitle (title);
                    String name = searchWord.substring (0, searchWord.indexOf (cd));
                    if (!"".equalsIgnoreCase (name) && name != null)
                        teacher.setTeacherName (name.trim ());
                } else {
                    teacher.setTeacherName (searchWord);
                }
            } finally {
                tv.setTeacherCustom (teacher);
                for (TeacherCustom t : teacherService.findTeacherList (tv)) {
                    teacherList.add (t);
                }
            }


            //课程相关信息收缩
            CurriculumTableQueryVo ctv = new CurriculumTableQueryVo ();
            CurriculumTableCustom curriculum = new CurriculumTableCustom ();

            List<CurriculumTableCustom> curriculumList = new LinkedList<> ();
            try {
                Integer id = Integer.parseInt (searchWord);
                curriculum.setCurriculumID (id);
                ctv.setCurriculumTable (curriculum);
            } catch (Exception e) {
                if (searchWord.contains ("@")) {
                    curriculum.setTeacherEMail (searchWord);

                } else if (TAES4Utils.isContainNumber (searchWord) && searchWord.contains ("班")) {
                    curriculum.setClassName (searchWord);
                } else {
                    curriculum.setCurriculumName (searchWord);
                }
            } finally {
                ctv.setCurriculumTable (curriculum);
                for (CurriculumTableCustom c : curriculumService.findCurriculumList (ctv)) {
                    curriculumList.add (c);
                }
            }
            session.setAttribute ("requestReferer", url);
            session.setAttribute ("teacherList", teacherList);//添加到sesion 中，方便页面传递
            session.setAttribute ("curriculumList", curriculumList);//添加到sesion 中，方便页面传递
            url = "forward:/WEB-INF/jsp/searchList.jsp";
        } else
            return url;
        return url;
    }
}
