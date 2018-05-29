package org.suxuanhua.ssm.mapper.curriculum;

import org.suxuanhua.ssm.po.curriculum.CurriculumTable;

import java.util.List;

/**
 * MyBatis Dao 原始开发方式
 *
 * @author XuanhuaSu
 * @version 2018/4/2m
 */
public interface CurriculumTableMapper {

    public List<Integer> seleteAllId() throws Exception;

    public CurriculumTable findCurriculumById(final Integer cid) throws Exception;

    public CurriculumTable findCurriculumByName(final String curriculumName) throws Exception;

    public Integer updateCurriculum(CurriculumTable curriculumTable) throws Exception;

    public Integer insertCurriculum(CurriculumTable curriculumTable) throws Exception;

    public Integer deleteCurriculumById(final Integer uid) throws Exception;
}