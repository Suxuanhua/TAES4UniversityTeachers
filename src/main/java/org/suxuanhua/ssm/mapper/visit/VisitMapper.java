package org.suxuanhua.ssm.mapper.visit;

import org.suxuanhua.ssm.po.visit.Visit;

import java.util.List;

/**
 * MyBatis Dao 原始开发方式
 *
 * @author XuanhuaSu
 * @version 2018/4/2m
 */
public interface VisitMapper {

    List<Visit> find(final String ip) throws Exception;

    Integer insert(final Visit visit) throws Exception;

    Integer delete(final String ip) throws Exception;

    Integer addVisits(final String ip) throws Exception;
}