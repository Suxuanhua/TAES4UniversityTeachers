package org.suxuanhua.ssm.mapper.visit;

import org.suxuanhua.ssm.po.visit.VisitCustom;
import org.suxuanhua.ssm.po.visit.VisitQueryVo;

import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/12
 */
public interface VisitMapperCustom {
    // 查询用户列表
    //根据UserQueryVo 提供的属性去查，可一条可多条。具体情况查看对应的xml 中的方法
    public List<VisitCustom> findVisitList(VisitQueryVo visitQueryVo) throws Exception;
}
