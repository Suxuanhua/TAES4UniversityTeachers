package org.suxuanhua.ssm.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.suxuanhua.ssm.po.admin.AdministratorCustom;
import org.suxuanhua.ssm.po.post.Post;
import org.suxuanhua.ssm.service.PostService;
import org.suxuanhua.ssm.service.VisitService;
import org.suxuanhua.ssm.tools.TAES4Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author XuanhuaSu
 * @version 2018/5/7
 */
@Controller
@RequestMapping ("/post")
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    private VisitService visitService;
    @Autowired
    private UtilsController utilsController;

    Logger logger = LogManager.getLogger (LogManager.ROOT_LOGGER_NAME);

    @RequestMapping ("/findpost")
    public String findPostByID(String postID, HttpServletRequest request, HttpSession session) throws Exception {
        String referer = TAES4Utils.getReferer (request);
        String url;

        if (postID != null && !"".equals (postID)) {
            Post post = postService.findPostByID (postID);
            if (post != null) {
                //访问量+1
                postService.addVisits (postID);
                //记录访问信息
                utilsController.recordVisits (request);
            }
            //无论帖子是否找到，都设置，显示页面会处理
            session.setAttribute ("post", post);
            url = "forward:/main/postcontent.su";
        } else {
            if (referer != null && "".equals (referer)) {
                url = "redirect:" + referer;
            } else
                url = "redirect:/post/findallpost.su";
        }
        return url;
    }


    @RequestMapping ("/findallpost")
    public String findPostList(HttpSession session,HttpServletRequest request) throws Exception {
        session.setAttribute ("postList", postService.findAllPost ());
        //记录访问信息
        utilsController.recordVisits (request);
        return "forward:/main/postlist.su";
    }


    /**
     * 发表论文
     *
     * @param
     * @return
     */
    @RequestMapping ("/newpost")
    public String newPost(String postTitle, String postContent, HttpServletRequest request, HttpSession session) throws Exception {

        AdministratorCustom admin = (AdministratorCustom) session.getAttribute ("admin");
        String notice;
        String warn = null;
        //String url =  "forward:/WEB-INF/jsp/postEditor.jsp";
        String url = TAES4Utils.getReferer (request);
        //System.out.println (url);
        if (TAES4Utils.getReferer (request).contains ("/posteditor.su")) {
            //适合.html
            //在外建立索引
            String fileName = "";
            String projectPath;
            String filePath = "";
            String fileFormat = "";
            Post post;

            boolean fileSituation = false;

            SimpleDateFormat sim = new SimpleDateFormat ("yyyy-MM-dd");
            String SystemDate = sim.format (new Date ());

            if (postTitle != null && postContent != null) {
                //生成文件名：获取主机日期时间+用户ID+帖子标题
                fileName = SystemDate + "-" + admin.getAdminID () + "-" + postTitle;

                projectPath = TAES4Utils.getPropertiesValue (
                        "/TAES4UniversityTeachers-Configuration.properties", "TAES4UT_POST_PATH");

                //文件路径
                filePath = projectPath + "/" + admin.getAdminID () + "/";
                //如果是.html 需要在web.xml 配置所有的html页面的编码是UTF-8，否则乱码，
                fileFormat = "html";//改成.jsp 后缀不会乱码

                fileSituation = TAES4Utils.newFile (fileName, filePath, fileFormat, postContent);
            }

            //如果帖子保存成功(将信息存到文件中)，就将帖子信息存入数据库，否则跳转到异常页面
            if (fileSituation) {

                post = new Post ();
                //补全信息
                //要注意数据库的类型，不要把字符串存到int里面
                //帖子ID：年月日+随机生成ID
                //帖子ID：主要还要判断数据库中是否存在相同的ID
                post.setPostID (SystemDate + "-" + admin.getAdminID () + "-" + TAES4Utils.randomUUID ());

//                System.out.println (post.getPostID ());//测试ID 创建是否正常
                //帖子对应的用户名
                post.setuName (admin.getAdminName ());
                //帖子对应的用户ID
                post.setuID (admin.getAdminID ().toString ());
                //帖子对应的用户性别
                post.setuSex ("1");
                //帖子对应的用户头像
                post.setuHeader_default (admin.getAdminHeader_default ());
                //帖子标题
                post.setPostTitle (postTitle);
                //帖子内容文件路径
                post.setPostContentAddr ("/" + admin.getAdminID () + "/" + fileName + "." + fileFormat);
                //帖子日期
                post.setPostDate (new Date ().toString ());
                //帖子回复数量
                post.setPostReplies (0l);
                //帖子访问数量
                post.setPostVisits (0l);


                //如果保存信息成功，跳转网页
                if (postService.savePostInfo (post)) {
                    session.setAttribute ("post", post);
                    notice = "发帖成功";

                    url = "redirect:/main/postcontent.su";

                } else {
                    //否则删除已经穿件的文件和文件夹
                    //删除PostFiles文件夹内用户ID文件夹下的文件
                    if (TAES4Utils.deleteAlreadyExistingFile (filePath + fileName + fileFormat)) {
                        //删除PostFiles文件夹下的用户ID文件夹
                        TAES4Utils.deleteAlreadyExistingFile (filePath);
                        notice = "发帖失败，请联系管理员，邮箱：suxuanhua@foxmail.com";
                        warn = "用户：" + post.getuID () + "帖子保存到数据库失败，文件：" + post.getPostID () + "删除成功";
                    } else {
                        //删除失败，文件不存在或者发生异常
                        warn = "用户：" + post.getuID () + "帖子保存到数据库失败，文件：" + post.getPostID () + "删除失败，文件不存在或者发生异常。";
                        notice = "发帖失败，请联系管理员，邮箱：suxuanhua@foxmail.com";
                    }
                }

            } else {
                //如果帖子文件保存失败
                notice = "操作失败，请求检查网络是否异常";
                warn = "帖子文件创建保存失败";
            }

        } else {
            notice = "无效操作";
            warn = "有不属于postEditor的请求:" + TAES4Utils.getRemoteHost (request);
            if (admin == null)
                url = "redirect:/main/adminlogin.su";
            else
                url = "redirect:/main/managecenter.su";
        }
        logger.warn (warn);
        TAES4Utils.setNotice (session, notice);
        return url;
    }
}
