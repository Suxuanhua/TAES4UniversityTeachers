package org.suxuanhua.ssm.mapper.teacher;

import org.suxuanhua.ssm.po.teacher.Teacher;

import java.util.List;

/**
 * MyBatis Dao 原始开发方式
 *
 * @author XuanhuaSu
 * @version 2018/4/2m
 */
public interface TeacherMapper {
    public List<Integer> seleteAllId() throws Exception;

    public List<String> seleteAllEMail() throws Exception;

    public List<String> seleteAllPhoneNumber() throws Exception;

    public Teacher findTeacherById(final Integer tid) throws Exception;

    public List<Teacher> findTeacherList_ByName(final String name) throws Exception;

    public Integer insertTeacher(Teacher teacher) throws Exception;

    public boolean deleteTeacherById(final Integer tid) throws Exception;

    public boolean updateTeacher(Teacher teacher) throws Exception;
}