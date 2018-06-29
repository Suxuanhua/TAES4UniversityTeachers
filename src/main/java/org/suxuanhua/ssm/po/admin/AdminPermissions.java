package org.suxuanhua.ssm.po.admin;

/**
 * @author XuanhuaSu
 * @version 2018/4/22
 */
public enum AdminPermissions {
    //root 超级管理员 administrator 管理员，general 访客
    root(10000),administrator(11000),general(11100);

    private Integer userCode;
    AdminPermissions(Integer userCode){
        this.userCode = userCode;
    }

    public Integer getUserCode() {
        return userCode;
    }
}
