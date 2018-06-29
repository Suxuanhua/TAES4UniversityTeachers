package org.suxuanhua.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.suxuanhua.ssm.exception.CustomException;
import org.suxuanhua.ssm.mapper.curriculum.CurriculumTableCustomMapper;
import org.suxuanhua.ssm.mapper.curriculum.CurriculumTableMapper;
import org.suxuanhua.ssm.po.curriculum.CurriculumTableCustom;
import org.suxuanhua.ssm.po.curriculum.CurriculumTableQueryVo;
import org.suxuanhua.ssm.service.CurriculumService;
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
public class CurriculumServiceImpl implements CurriculumService {

    @Autowired
    private CurriculumTableMapper curriculumTableMapper;
    @Autowired
    private CurriculumTableCustomMapper curriculumTableCustomMapper;

    @Override
    public Boolean idIsexist(Integer idNumber) throws Exception {
        Boolean existSituation = false;
        List<Integer> idList = curriculumTableMapper.seleteAllId ();
        for (Integer id : idList) {
            if (idNumber.equals (id))
                existSituation = true;
        }
        return existSituation;
    }

    @Override
    public List<CurriculumTableCustom> findCurriculumList(CurriculumTableQueryVo curriculumTableQueryVo) throws Exception {
        return curriculumTableCustomMapper.findCurriculumTableList (curriculumTableQueryVo);
    }


    @Override
    public boolean updateCurriculum(final Integer cid, CurriculumTableCustom curriculumTableCustom) throws Exception {
        boolean updateSituation = true;
        //return userMapper.updateUser (userCustom); //不能这样，一定要写业务代码，对关键的业务数据的非空校验
        if (cid == null) {
            throw new CustomException ("tid 不能为空", "/ExceptionPage.jsp");
        }
        if (curriculumTableCustom == null) {
            throw new CustomException ("curriculumTableCustom 不能为空", "/ExceptionPage.jsp");
        }

        //查找是否有该用户
        Boolean existSituation = idIsexist (cid);

        if (!existSituation) {
            updateSituation = false;
        } else {
            //判断两个ID是否一致
            if (!cid.equals (curriculumTableCustom.getCurriculumID ()))
                updateSituation = false;
        }


        if (updateSituation) {//如果是true
            curriculumTableCustom.setCurriculumUpdateTime (new Date ());
            if (curriculumTableMapper.updateCurriculum (curriculumTableCustom) > 0) {//不管头像是否符合修改要求，都要修改一遍信息，万一又修改图片，又修改信息，图片不符合要求，信息还是要更新的
                return true;
            } else
                return false;
        } else
            return updateSituation;//运行到else 的时候，updateSituation 肯定是false，所以直接返回
    }

    // 大型企业项目不能在Service 层使用 View 层的对象，要保持独立，不方便测试。
    @Override
    public String addCurriculum(CurriculumTableCustom curriculumTable, HttpServletRequest request, HttpSession session) throws Exception {

        Boolean addUserSituation = false;
        Boolean idSituation = true;
        String reqReferer = (String) session.getAttribute ("reqReferer");

        //System.out.println (request.getRequestURI ());//该方法获取到的是contorller 的后缀名，adminregister.su
        //System.out.println (TAES4Utils.getReferer (request));
        if (TAES4Utils.getReferer (request).contains ("/addcurriculum.su") && curriculumTable != null) {
            if (curriculumTable.getCurriculumName () != null
                    && !curriculumTable.getCurriculumName ().isEmpty ()

                    && curriculumTable.getGrade () != null
                    && !"".equals (curriculumTable.getGrade ())

                    && curriculumTable.getClassName () != null
                    && !"".equals (curriculumTable.getClassName ())

                    && curriculumTable.getTeacherID () != null
                    && !"".equals (curriculumTable.getTeacherID ())

                    && curriculumTable.getTeacherName () != null
                    && !curriculumTable.getTeacherName ().isEmpty ()

                    && curriculumTable.getTeacherEMail () != null
                    && !curriculumTable.getTeacherEMail ().isEmpty ()

                    && curriculumTable.getTeacherPhoneNumber () != null
                    && !curriculumTable.getTeacherPhoneNumber ().isEmpty ()

                    && curriculumTable.getTeacherSex () != null
                    && !"".equals (curriculumTable.getTeacherSex ())

                    && curriculumTable.getClassHours () != null
                    && !"".equals (curriculumTable.getClassHours ())

                    && curriculumTable.getTeachingNumber () != null
                    && !"".equals (curriculumTable.getTeachingNumber ())
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

                //加快执行速度
                //当邮箱不存在，手机号也不存在，id 创建不超过10 次，idSituation = false 时，进入if
                if (idSituation == true) {

                    curriculumTable.setCurriculumID (id);
                    //日期
                    curriculumTable.setCurriculumUpdateTime (new Date ());

                    if (curriculumTableMapper.insertCurriculum (curriculumTable) > 0)
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
            if (idSituation == false) {
                notice = "超时，请重新添加";
            } else if (addUserSituation == false) {
                notice = "ERROR STRING：DB-INSERT-FALSE-108";
            }

            session.setAttribute ("message", message);
            TAES4Utils.setNotice (session, notice);
            return "forward:/main/addcurriculum.su";
        }
    }

    @Override
    public boolean deleteCurriculumById(final Integer cid) throws Exception {
        if (idIsexist (cid)) {
            if (curriculumTableMapper.deleteCurriculumById (cid) > 0) {
                return true;
            } else
                return false;
        } else
            return false;
    }
}
