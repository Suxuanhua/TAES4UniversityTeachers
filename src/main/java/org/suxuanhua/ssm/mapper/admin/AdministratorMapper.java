package org.suxuanhua.ssm.mapper.admin;

import org.suxuanhua.ssm.po.admin.Administrator;
import org.suxuanhua.ssm.po.admin.AdministratorCustom;

import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/22
 */
public interface AdministratorMapper {
    public List<Integer> seleteAllId() throws Exception;

    public List<String> seleteAllEMail() throws Exception;

    public List<String> seleteAllPhoneNumber() throws Exception;
    public Integer setLoginTime(final Administrator administrator) throws Exception;
    public Integer setLoginIp(final Administrator administrator) throws Exception;

    public AdministratorCustom findAdminUserById(final Integer uid) throws Exception;
    public AdministratorCustom findAdminUserByEmail(final String email) throws Exception;

    public Integer insertAdminUser(final AdministratorCustom administratorCustom) throws Exception;

    public boolean updateAdminUser(final AdministratorCustom administratorCustom) throws Exception;

    public boolean deleteAdminUserById(final Integer uid);
}
