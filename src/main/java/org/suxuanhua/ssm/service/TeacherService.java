package org.suxuanhua.ssm.service;

import org.springframework.web.multipart.MultipartFile;
import org.suxuanhua.ssm.po.teacher.TeacherCustom;
import org.suxuanhua.ssm.po.teacher.TeacherQueryVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/22
 */
public interface TeacherService {

    public List<String> seleteAllEMail() throws Exception;

    public Boolean eMailIsexist(String eMail) throws Exception;

    public Boolean phoneNumberIsexist(String phoneNumber) throws Exception;

    public Boolean idIsexist(Integer tid) throws Exception;

    //定义Service 接口时，要遵循单一职责（一个方法尽量干一件事），将业务参数细化（不要使用包装类型，比如map）让调用方清楚的知道要传的参数。

    //用户查询
    public List<TeacherCustom> findTeacherList(TeacherQueryVo teacherQueryVo) throws Exception;

    //根据用户的ID 查询用户
    public TeacherCustom findTeacherById(final Integer uid) throws Exception;

    /**
     * 更新用户信息
     *
     * @param tid           管理员ID
     * @param teacherCustom 管理员信息（这里已经包含了用户ID，为什么还要独立出来传一个ID呢？让调用的人清楚的知道要传一个ID进来，如果只传userCustom，调用方可能忘了传ID）
     * @return
     */
    public boolean updateTeacher(final Integer tid, TeacherCustom teacherCustom, MultipartFile pictureFile) throws Exception;

    public String addTeacher(TeacherCustom teacherCustom, HttpServletRequest request, HttpSession session, MultipartFile pictureFile) throws Exception;

    public boolean deleteTeacherById(final Integer uid, String HeaderImagePath) throws Exception;
}
