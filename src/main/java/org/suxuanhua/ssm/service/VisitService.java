package org.suxuanhua.ssm.service;

import org.suxuanhua.ssm.po.visit.Visit;

import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/5/7
 */
public interface VisitService {

    Integer addVisitInfo(Visit visit) throws Exception;

    List<Visit> findVisitByIp(String ip) throws Exception;

    Integer deleteVisitByIp(String ip) throws Exception;

    Integer addVisits(String ip) throws Exception;
}
