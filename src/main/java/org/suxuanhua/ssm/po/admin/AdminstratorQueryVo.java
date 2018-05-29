package org.suxuanhua.ssm.po.admin;

import java.util.List;

/**
 * Adminstrator 查询类
 *
 * @author XuanhuaSu
 * @version 2018/4/22
 */
public class AdminstratorQueryVo {
    private AdministratorCustom administratorCustom;

    private List<AdministratorCustom> administratorCustomList;

    public AdministratorCustom getAdministratorCustom() {
        return administratorCustom;
    }

    public void setAdministratorCustom(AdministratorCustom administratorCustom) {
        this.administratorCustom = administratorCustom;
    }

    public List<AdministratorCustom> getAdministratorCustomList() {
        return administratorCustomList;
    }

    public void setAdministratorCustomList(List<AdministratorCustom> administratorCustomList) {
        this.administratorCustomList = administratorCustomList;
    }
}
