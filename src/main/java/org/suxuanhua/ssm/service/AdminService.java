package org.suxuanhua.ssm.service;

import org.springframework.web.multipart.MultipartFile;
import org.suxuanhua.ssm.po.admin.AdministratorCustom;
import org.suxuanhua.ssm.po.admin.AdminstratorQueryVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/22
 */
public interface AdminService {

    public Boolean eMailIsexist(String eMail) throws Exception;

    public Boolean phoneNumberIsexist(String phoneNumber) throws Exception;

    public Boolean idIsexist(Integer id) throws Exception;

    //定义Service 接口时，要遵循单一职责（一个方法尽量干一件事），将业务参数细化（不要使用包装类型，比如map）让调用方清楚的知道要传的参数。

    //用户查询
    public List<AdministratorCustom> findAdminUserList(AdminstratorQueryVo adminstratorQueryVo) throws Exception;

    //根据用户的ID 查询用户
    public AdministratorCustom findAdminUserById(final Integer uid) throws Exception;

    public AdministratorCustom findAdminUserByEmail(final String email) throws Exception;

    /**
     * 更新用户信息
     *
     * @param uid                 管理员ID
     * @param administratorCustom 管理员信息（这里已经包含了用户ID，为什么还要独立出来传一个ID呢？让调用的人清楚的知道要传一个ID进来，如果只传userCustom，调用方可能忘了传ID）
     * @return
     */
    public boolean updateAdminUser(final Integer uid, AdministratorCustom administratorCustom, MultipartFile pictureFile) throws Exception;

    public String addAdminUser(AdministratorCustom administratorCustom, HttpServletRequest request, HttpSession session, MultipartFile pictureFile) throws Exception;

    public boolean deleteAdminUserById(final Integer uid, String adminPermissions, String HeaderImagePath) throws Exception;

    public Boolean setLoginIpByEmail(String loginIp, String email) throws Exception;

    public Boolean setLoginTimeByEmail(Date loginTime, String email) throws Exception;
}
