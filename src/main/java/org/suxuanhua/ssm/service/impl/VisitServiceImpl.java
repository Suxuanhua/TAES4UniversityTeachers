package org.suxuanhua.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.suxuanhua.ssm.mapper.visit.VisitMapper;
import org.suxuanhua.ssm.po.visit.Visit;
import org.suxuanhua.ssm.service.VisitService;

import java.util.List;


/**
 * @author XuanhuaSu
 * @version 2018/5/21
 */
public class VisitServiceImpl implements VisitService {
    @Autowired
    private VisitMapper visitMapper;

    @Override
    public Integer addVisitInfo(Visit visit) throws Exception {

        return visitMapper.insert (visit);
    }

    @Override
    public List<Visit> findVisitByIp(String ip) throws Exception {
        return visitMapper.find (ip);
    }

    @Override
    public Integer deleteVisitByIp(String ip) throws Exception {
        return visitMapper.delete (ip);
    }

    @Override
    public Integer addVisits(String ip) throws Exception {
        return visitMapper.addVisits (ip);
    }
}
