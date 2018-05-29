package org.suxuanhua.ssm.service;

import org.suxuanhua.ssm.po.curriculum.CurriculumTableCustom;
import org.suxuanhua.ssm.po.curriculum.CurriculumTableQueryVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/22
 */
public interface CurriculumService {

    public Boolean idIsexist(Integer cid) throws Exception;

    //定义Service 接口时，要遵循单一职责（一个方法尽量干一件事），将业务参数细化（不要使用包装类型，比如map）让调用方清楚的知道要传的参数。

    //用户查询
    public List<CurriculumTableCustom> findCurriculumList(CurriculumTableQueryVo curriculumTableQueryVo) throws Exception;

    /**
     * 更新用户信息
     *
     * @param cid             ID
     * @param curriculumTable 信息（这里已经包含了用户ID，为什么还要独立出来传一个ID呢？让调用的人清楚的知道要传一个ID进来，如果只传userCustom，调用方可能忘了传ID）
     * @return
     */
    public boolean updateCurriculum(final Integer cid, CurriculumTableCustom curriculumTable) throws Exception;

    public String addCurriculum(CurriculumTableCustom curriculumTable, HttpServletRequest request, HttpSession session) throws Exception;

    public boolean deleteCurriculumById(final Integer uid) throws Exception;
}
