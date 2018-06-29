package org.suxuanhua.ssm.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.suxuanhua.ssm.exception.CustomException;
import org.suxuanhua.ssm.mapper.teacher.TeacherMapper;
import org.suxuanhua.ssm.mapper.teacher.TeacherMapperCustom;
import org.suxuanhua.ssm.po.teacher.Teacher;
import org.suxuanhua.ssm.po.teacher.TeacherCustom;
import org.suxuanhua.ssm.po.teacher.TeacherQueryVo;
import org.suxuanhua.ssm.service.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TeacherMapperCustom teacherMapperCustom;

    /**
     * 统计所有个数
     *
     * @param
     * @return List<String>
     */
    @Override
    public List<String> seleteAllEMail() throws Exception {
        return teacherMapper.seleteAllEMail ();
    }

    @Override
    public Boolean eMailIsexist(String eMail) throws Exception {
        Boolean existSituation = false;
        List<String> emailList = teacherMapper.seleteAllEMail ();
        for (String email : emailList) {
            if (eMail.equals (email))
                existSituation = true;
        }
        return existSituation;
    }

    @Override
    public Boolean phoneNumberIsexist(String phoneNumber) throws Exception {
        Boolean existSituation = false;
        List<String> phoneNumberList = teacherMapper.seleteAllPhoneNumber ();
        for (String phonenumber : phoneNumberList) {
            if (phoneNumber.equals (phonenumber))
                existSituation = true;
        }
        return existSituation;
    }

    @Override
    public Boolean idIsexist(Integer idNumber) throws Exception {
        Boolean existSituation = false;
        List<Integer> idList = teacherMapper.seleteAllId ();
        for (Integer id : idList) {
            if (idNumber.equals (id))
                existSituation = true;
        }
        return existSituation;
    }

    @Override
    public List<TeacherCustom> findTeacherList(TeacherQueryVo teacherQueryVo) throws Exception {
        return teacherMapperCustom.findTeacherList (teacherQueryVo);
    }

    @Override
    public TeacherCustom findTeacherById(final Integer auid) throws Exception {
        Teacher teacher = teacherMapper.findTeacherById (auid);
        TeacherCustom tc = new TeacherCustom ();
        BeanUtils.copyProperties (teacher,tc);
        return tc;
    }

    @Override
    public boolean updateTeacher(final Integer tid, TeacherCustom teacherCustom, MultipartFile pictureFile) throws Exception {
//        String fileRootPath = "D:\\TAES4UT-Pictures";
        String fileRootPath =
                TAES4Utils.getPropertiesValue (
                        "/TAES4UniversityTeachers-Configuration.properties", "TAES4UT_IMG_PATH");
        boolean updateSituation = true;
        //return userMapper.updateUser (userCustom); //不能这样，一定要写业务代码，对关键的业务数据的非空校验
        if (tid == null) {
            //抛出异常，提示调用接口的用户ID不能为空
            throw new CustomException ("uid 不能为空", "error");
        }
        if (teacherCustom == null) {
            throw new CustomException ("userCustom 不能为空", "error");
        }

        //查找是否有该用户
        Boolean existSituation = idIsexist (tid);

        if (!existSituation) {
            updateSituation = false;
        } else {
            //判断两个ID是否一致
            if (!tid.equals (teacherCustom.getTeacherID ()))
                updateSituation = false;
        }

        //图片上传，IDEA遇到pictureFile 不可能为null 当前情况，因为IDEA会赋值一个uploadxxxx.tem size 的文件，
        //if (suffix.equals ("jpg") || suffix.equals ("jpeg") || suffix.equals ("png")) 这样判断会造成 用户不修改头像信息修改失败的情况
        //所以遇到.tem 文件时，需要额外的添加if，updateSituation 的值不能被修改
        if (updateSituation == true && pictureFile != null) {
            String path = TAES4Utils.uploadHeaderImage (fileRootPath, "/uHeader_default/Teacher/", teacherCustom.getTeacherID ().toString (), pictureFile);
            //因为更新失败会返回默认值所以要判断
            if (!"/uHeader_default/uHeader_default.jpg".equals (path)) {
                TAES4Utils.deleteAlreadyExistingFile (fileRootPath + teacherCustom.getTeacherHeader_default ());
                teacherCustom.setTeacherHeader_default (path);
            }
        }


        if (updateSituation) {//如果是true
            teacherCustom.setTeacherUpdateTime (new Date ());
            return teacherMapper.updateTeacher (teacherCustom);//不管头像是否符合修改要求，都要修改一遍信息，万一又修改图片，又修改信息，图片不符合要求，信息还是要更新的
        } else
            return updateSituation;//运行到else 的时候，updateSituation 肯定是false，所以直接返回
    }

    // 大型企业项目不能在Service 层使用 View 层的对象，要保持独立，不方便测试。
    @Override
    public String addTeacher(TeacherCustom teacherCustom, HttpServletRequest request, HttpSession session, MultipartFile pictureFile) throws Exception {

        Boolean addUserSituation = false;
        Boolean eMailSituation = false;
        Boolean phoneNumberSituation = false;
        Boolean idSituation = true;
        String reqReferer = (String) session.getAttribute ("reqReferer");

        //System.out.println (request.getRequestURI ());//该方法获取到的是contorller 的后缀名，adminregister.su
        //System.out.println (TAES4Utils.getReferer (request));
        if (TAES4Utils.getReferer (request).contains ("/addteacher.su") && teacherCustom != null) {
            if (teacherCustom.getTeacherName () != null
                    && !teacherCustom.getTeacherName ().isEmpty ()

                    && teacherCustom.getTeacherTitle () != null
                    && !teacherCustom.getTeacherTitle ().isEmpty ()

                    && teacherCustom.getTeacherEMail () != null
                    && !teacherCustom.getTeacherEMail ().isEmpty ()

                    && teacherCustom.getTeacherPhoneNumber () != null
                    && !teacherCustom.getTeacherPhoneNumber ().isEmpty ()

                    && teacherCustom.getTeacherSex () != null
                    && !"".equals (teacherCustom.getTeacherSex ())
                    ) {
                //ID
                Integer id = TAES4Utils.createID (8);
                //检测数据库是否已经存在该ID，存在则重新生成
                while (idIsexist (id)) {
                    int tempNum = 0;
                    id = TAES4Utils.createID (8);
                    tempNum++;
                    if (tempNum > 10) {
                        idSituation = false;
                        break;
                    }
                }

                //是否已经存在邮箱
                eMailSituation = eMailIsexist (teacherCustom.getTeacherEMail ());
                //是否已经存在手机号
                phoneNumberSituation = phoneNumberIsexist (teacherCustom.getTeacherPhoneNumber ());
                //加快执行速度
                //当邮箱不存在，手机号也不存在，id 创建不超过10 次，idSituation = false 时，进入if
                if (!eMailSituation == true && !phoneNumberSituation == true && idSituation == true) {

                    teacherCustom.setTeacherID (id);
                    //日期
                    teacherCustom.setTeacherUpdateTime (new Date ());
                    //如果图片上传成功就使用新的
                    //否则使用默认的
                    if (pictureFile != null) {
                        String fileRootPath =
                                TAES4Utils.getPropertiesValue (
                                        "/TAES4UniversityTeachers-Configuration.properties", "TAES4UT_IMG_PATH");
                        String path = TAES4Utils.uploadHeaderImage (fileRootPath, "/uHeader_default/Teacher/", teacherCustom.getTeacherID ().toString (), pictureFile);
                        teacherCustom.setTeacherHeader_default (path);

                    } else {
                        teacherCustom.setTeacherHeader_default ("/uHeader_default/uHeader_default.jpg");
                    }

                    if (teacherMapper.insertTeacher (teacherCustom) > 0)
                        addUserSituation = true;
                }
            }
        }
        if (addUserSituation) {
            TAES4Utils.setNotice (session, "添加成功");
            if (reqReferer.trim () != null && !"".equals (reqReferer.trim ())) {
                //从哪来回哪去
                return "redirect:" + reqReferer;//在ModelAndView使用重定向，要填写全部URL
            } else
                return "redirect:/teacher/findteacherlist.su";
        } else {
            String message = null;
            String notice = null;
            if (eMailSituation == true) {
                notice = "您输入的邮箱已经被添加";
            } else if (phoneNumberSituation == true) {
                notice = "您输入的手机号已经被添加";
            } else if (idSituation == false) {
                notice = "超时，请重新添加";
            } else if (addUserSituation == false) {
                notice = "ERROR STRING：DB-INSERT-FALSE-108";
            }

            session.setAttribute ("message", message);
            TAES4Utils.setNotice (session, notice);
            return "forward:/main/addteacher.su";
        }
    }

    @Override
    public boolean deleteTeacherById(final Integer uid, String HeaderImagePath) throws Exception {
        if (idIsexist (uid)) {
            if (teacherMapper.deleteTeacherById (uid)) {
                //如果不包含此字符串就删除，包含代表是默认的头像文件
                if (!HeaderImagePath.contains ("/uHeader_default/uHeader_default.jpg"))
                    TAES4Utils.deleteAlreadyExistingFile (HeaderImagePath);

                return true;
            } else
                return false;
        } else
            return false;
    }
}
