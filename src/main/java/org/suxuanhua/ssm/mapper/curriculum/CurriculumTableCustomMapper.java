package org.suxuanhua.ssm.mapper.curriculum;

import org.suxuanhua.ssm.po.curriculum.CurriculumTableCustom;
import org.suxuanhua.ssm.po.curriculum.CurriculumTableQueryVo;

import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/12
 */
public interface CurriculumTableCustomMapper {
    // 查询用户列表
    //根据UserQueryVo 提供的属性去查，可一条可多条。具体情况查看对应的xml 中的方法
    public List<CurriculumTableCustom> findCurriculumTableList(CurriculumTableQueryVo curriculumTableQueryVo) throws Exception;
}
