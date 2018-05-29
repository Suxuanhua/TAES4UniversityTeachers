package org.suxuanhua.ssm.mapper.admin;

import org.suxuanhua.ssm.po.admin.AdministratorCustom;
import org.suxuanhua.ssm.po.admin.AdminstratorQueryVo;

import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/22
 */
public interface AdministratorMapperCustom {
    //用户查询
    public List<AdministratorCustom> findAdminUserList(final AdminstratorQueryVo adminstratorQueryVo) throws Exception;
}
