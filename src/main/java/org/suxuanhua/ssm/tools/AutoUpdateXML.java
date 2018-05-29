package org.suxuanhua.ssm.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.suxuanhua.ssm.mapper.post.PostMapper;
import org.suxuanhua.ssm.po.post.Post;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Suxuanhua
 * @version 2017/11/13 0013
 */

//创建ServletContext 监听器
@Component("autoupdatexml")
public class AutoUpdateXML {
    @Autowired
    private PostMapper postMapper;
    private Lock lock;
    Logger logger = LogManager.getLogger (LogManager.ROOT_LOGGER_NAME);
    int i = 0;//记录执行次数。堆中的变量都有默认值，栈中没有。

    @Scheduled (cron = "0/5 * * * * ?")
    public void upXML() {

        //以下是定时更新帖子列表的定时更新器。存在问题，如果帖子变多，xml文件就存在负担，所以需要根据分页，生成多个xml文件。
        lock = new ReentrantLock ();

        //获取项目根目录
        String postListPath = TAES4Utils.getPropertiesValue (
                "/TAES4UniversityTeachers-Configuration.properties", "TAES4UT_XML_POSTLIST_PATH");

        //获取该类运行的目录
        try {
            lock.lock ();//加锁，同步代码块
            List<Post> allPost = postMapper.findAllPost ();
            TAES4Utils.createPostsXML (allPost, postListPath, "TAES4UT_POSTLIST");
            i++;
            if (i < 3) {//显示更新信息，用于判断是否正常运行。
                System.out.println (new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").format (new Date ()) + "\tTAES4UT_POSTLIST.xml\t第" + i + "次更新");
            }

        } catch (Exception e) {
            logger.warn ("定时更新POST_LIST_XML发生异常：" + e);
            e.printStackTrace ();
        } finally {
            lock.unlock ();//解锁
        }
    }
}