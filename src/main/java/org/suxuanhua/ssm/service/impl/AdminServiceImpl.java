package org.suxuanhua.ssm.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.suxuanhua.ssm.exception.CustomException;
import org.suxuanhua.ssm.mapper.admin.AdministratorMapper;
import org.suxuanhua.ssm.mapper.admin.AdministratorMapperCustom;
import org.suxuanhua.ssm.po.admin.AdminPermissions;
import org.suxuanhua.ssm.po.admin.AdministratorCustom;
import org.suxuanhua.ssm.po.admin.AdminstratorQueryVo;
import org.suxuanhua.ssm.service.AdminService;
import org.suxuanhua.ssm.tools.TAES4Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/4/22
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdministratorMapper adminstratorMapper;
    @Autowired
    private AdministratorMapperCustom adminstratorMapperCustom;

    Logger logger = LogManager.getLogger (LogManager.ROOT_LOGGER_NAME);


    @Override
    public Boolean eMailIsexist(String eMail) throws Exception {
        Boolean existSituation = false;
        List<String> emailList = adminstratorMapper.seleteAllEMail ();
        for (String email : emailList) {
            if (eMail.toLowerCase ().equals (email))
                existSituation = true;
        }
        return existSituation;
    }

    @Override
    public Boolean phoneNumberIsexist(String phoneNumber) throws Exception {
        Boolean existSituation = false;
        List<String> phoneNumberList = adminstratorMapper.seleteAllPhoneNumber ();
        for (String phonenumber : phoneNumberList) {
            if (phoneNumber.equals (phonenumber))
                existSituation = true;
        }
        return existSituation;
    }

    @Override
    public Boolean idIsexist(Integer idNumber) throws Exception {
        Boolean existSituation = false;
        List<Integer> idList = adminstratorMapper.seleteAllId ();
        for (Integer id : idList) {
            if (idNumber.equals (id))
                existSituation = true;
        }
        return existSituation;
    }

    @Override
    public List<AdministratorCustom> findAdminUserList(AdminstratorQueryVo adminstratorQueryVo) throws Exception {
        return adminstratorMapperCustom.findAdminUserList (adminstratorQueryVo);
    }

    @Override
    public AdministratorCustom findAdminUserByEmail(final String email) throws Exception {
        return adminstratorMapper.findAdminUserByEmail (email.toLowerCase ());
    }

    public AdministratorCustom findAdminUserById(final Integer auid) throws Exception {

        return adminstratorMapper.findAdminUserById (auid);
    }

    @Override
    public boolean updateAdminUser(final Integer adminID, AdministratorCustom administratorCustom, MultipartFile pictureFile) throws Exception {
//        String fileRootPath = "D:\\TAES4UT-Pictures";
        String fileRootPath =
                TAES4Utils.getPropertiesValue (
                        "/TAES4UniversityTeachers-Configuration.properties", "TAES4UT_IMG_PATH");

        boolean updateSituation = true;
        //return userMapper.updateUser (userCustom); //不能这样，一定要写业务代码，对关键的业务数据的非空校验
        if (adminID == null) {
            //抛出异常，提示调用接口的用户ID不能为空
            throw new CustomException ("uid 不能为空", "error");
        }
        if (administratorCustom == null) {
            throw new CustomException ("userCustom 不能为空", "error");
        }

        //查找是否有该用户
        Boolean existSituation = idIsexist (adminID);

        if (!existSituation) {
            updateSituation = false;
        } else {
            //判断两个ID是否一致
            if (!adminID.equals (administratorCustom.getAdminID ()))
                updateSituation = false;
        }

        //图片上传，IDEA遇到pictureFile 不可能为null 当前情况，因为IDEA会赋值一个uploadxxxx.tem size 的文件，
        //if (suffix.equals ("jpg") || suffix.equals ("jpeg") || suffix.equals ("png")) 这样判断会造成 用户不修改头像信息修改失败的情况
        //所以遇到.tem 文件时，需要额外的添加if，updateSituation 的值不能被修改
        if (updateSituation == true && pictureFile != null) {
            String path = TAES4Utils.uploadHeaderImage (fileRootPath, "/uHeader_default/Admin/", administratorCustom.getAdminID ().toString (), pictureFile);
            //因为更新失败会返回默认值所以要判断
            if (!path.equals ("/uHeader_default/uHeader_default.jpg")) {
                TAES4Utils.deleteAlreadyExistingFile (fileRootPath + administratorCustom.getAdminHeader_default ());
                administratorCustom.setAdminHeader_default (path);
            }
        }


        if (updateSituation) {//如果是true
            return adminstratorMapper.updateAdminUser (administratorCustom);//不管头像是否符合修改要求，都要修改一遍信息，万一又修改图片，又修改信息，图片不符合要求，信息还是要更新的
        } else
            return updateSituation;//运行到else 的时候，updateSituation 肯定是false，所以直接返回
    }

    // 大型企业项目不能在Service 层使用 View 层的对象，要保持独立，不方便测试。
    @Override
    public String addAdminUser(AdministratorCustom administrator, HttpServletRequest request, HttpSession session, MultipartFile pictureFile) throws Exception {

        Boolean addUserSituation = false;
        Boolean eMailSituation = false;
        Boolean phoneNumberSituation = false;
        Boolean idSituation = true;

        //System.out.println (request.getRequestURI ());//该方法获取到的是contorller 的后缀名，adminregister.su
        System.out.println (TAES4Utils.getReferer (request));
        if (TAES4Utils.getReferer (request).contains ("/adminregister.su") && administrator != null) {
            if (administrator.getAdminName () != null
                    && !administrator.getAdminName ().isEmpty ()
                    && administrator.getAdminPassword () != null
                    && !administrator.getAdminPassword ().isEmpty ()
                    && administrator.getAdminPhoneNumber () != null
                    && !administrator.getAdminPhoneNumber ().isEmpty ()
                    && administrator.getAdminEMail () != null
                    && !administrator.getAdminEMail ().isEmpty ()
                    ) {
                //ID
                Integer id = TAES4Utils.createID (8);
                //检测数据库是否已经存在该ID，存在则重新生成
                while (idIsexist (id)) {
                    int temp = 0;
                    id = TAES4Utils.createID (8);
                    temp++;
                    if (temp > 10) {
                        idSituation = false;
                        break;
                    }
                }

                //是否已经存在邮箱
                eMailSituation = eMailIsexist (administrator.getAdminEMail ().toLowerCase ());
                //设置成小写
                administrator.setAdminEMail (administrator.getAdminEMail ().toLowerCase ());
                //是否已经存在手机号
                phoneNumberSituation = phoneNumberIsexist (administrator.getAdminPhoneNumber ());
                //加快执行速度
                //当邮箱不存在，手机号也不存在，id 创建不超过10 次，idSituation = false 时，进入if
                if (!eMailSituation == true && !phoneNumberSituation == true && idSituation == true) {

                    administrator.setAdminID (id);
                    //IP
                    administrator.setAdminLoginIp (TAES4Utils.getRemoteHost (request));
                    //日期
                    administrator.setAdminLoginTime (new Date ());
                    //设置权限
                    administrator.setAdminPermissions (AdminPermissions.general.toString ());
                    //如果图片上传成功就使用新的
                    //否则使用默认的
                    if (pictureFile != null) {
                        String fileRootPath =
                                TAES4Utils.getPropertiesValue (
                                        "/TAES4UniversityTeachers-Configuration.properties", "TAES4UT_IMG_PATH");

                        String path = TAES4Utils.uploadHeaderImage (fileRootPath, "/uHeader_default/Admin/", administrator.getAdminID ().toString (), pictureFile);
                        administrator.setAdminHeader_default (path);

                    } else {
                        administrator.setAdminHeader_default ("/uHeader_default/uHeader_default.jpg");
                    }

                    if (adminstratorMapper.insertAdminUser (administrator) > 0)
                        addUserSituation = true;
                }
            }
        }
        if (addUserSituation) {
            logger.info ("新用户注册成功："+administrator.getAdminID ()+"-"+administrator.getAdminName ());
            TAES4Utils.setNotice (session, "注册成功");
            return "redirect:/main/adminlogin.su";
        } else {
            String message = null;
            String notice = null;
            if (eMailSituation == true) {
                notice = "您输入的邮箱已经被注册";
            } else if (phoneNumberSituation == true) {
                notice = "您输入的手机号已经被注册";
            } else if (idSituation == false) {
                notice = "超时，请重新注册";
            } else if (addUserSituation == false) {
                notice = "ERROR STRING：DB-INSERT-FALSE-108";
            }

            session.setAttribute ("message", message);
            TAES4Utils.setNotice (session, notice);
            return "forward:/main/adminregister.su";
        }
    }

    @Override
    public boolean deleteAdminUserById(final Integer uid, String adminPermissions, String HeaderImagePath) throws Exception {
        //如果不是root 用户
        if (!adminPermissions.equals (AdminPermissions.root.toString ())) {
            if (idIsexist (uid)) {
                if (adminstratorMapper.deleteAdminUserById (uid)) {
                    //如果不包含此字符串就删除，包含代表是默认的头像文件
                    if (!HeaderImagePath.contains ("/uHeader_default/uHeader_default.jpg"))
                        TAES4Utils.deleteAlreadyExistingFile (HeaderImagePath);

                    return true;
                } else
                    return false;
            } else
                return false;
        } else {
            return false;
        }
    }

    @Override
    public Boolean setLoginTimeByEmail(Date loginTime, String email) throws Exception {
        AdministratorCustom admin = findAdminUserByEmail (email);
        admin.setAdminLoginTime (loginTime);
        if (adminstratorMapper.setLoginTime (admin) > 0)
            return true;
        else
            return false;
    }

    public Boolean setLoginIpByEmail(String loginIp, String email) throws Exception {
        AdministratorCustom admin = findAdminUserByEmail (email);
        admin.setAdminLoginIp (loginIp);
        if (adminstratorMapper.setLoginIp (admin) > 0)
            return true;
        else
            return false;
    }
}
