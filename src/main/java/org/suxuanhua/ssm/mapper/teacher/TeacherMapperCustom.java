package org.suxuanhua.ssm.mapper.teacher;

import org.suxuanhua.ssm.po.teacher.TeacherCustom;
import org.suxuanhua.ssm.po.teacher.TeacherQueryVo;

import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/12
 */
public interface TeacherMapperCustom {
    // 查询用户列表
    //根据UserQueryVo 提供的属性去查，可一条可多条。具体情况查看对应的xml 中的方法
    public List<TeacherCustom> findTeacherList(TeacherQueryVo teacherQueryVo) throws Exception;
}
