<%--格式化输出日期要引入这两个文件，否则造成无法解析的错误--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: XuanhuaSu
  Date: 2018/4/22
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%--禁用缓存--%>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="${pageContext.request.contextPath }/img/bar_ico_color.png" rel="shortcut icon"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/top_nav.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/manageCenter.css">
    <title>管理中心</title>
</head>
<%--导航--%>
<%@include file="/top_nav.jsp" %>
<%@include file="/globalNotification.jsp" %>
<body>
<!--外框-->
<div class="mainCenter">
    <div class="mainCenter_topLeft">
        <img class="login_HeaderImage" src="${pic_virtual }/${admin.adminHeader_default }">
    </div>

    <div class="mainCenter_topCenter">
        <c:if test="${admin.adminPermissions eq 'root' }">
            <div class="loginInfo">
            <span style="color: #23769d">♞&nbsp;${admin.adminName }
                <a title="点击编辑当前管理员相关信息"
                   href="${pageContext.request.contextPath }/admin/editadminuserinfo.su?auid=${admin.adminID }">
                    &nbsp;编辑
                </a>
                <a title="退出登录当前用户"
                   href="${pageContext.request.contextPath }/admin/adminlogout.su">&nbsp;&nbsp;&nbsp;&nbsp;->&nbsp;退出登录</a>
            </span>
                <span style="color: #6c918b">♘&nbsp;
                <c:if test="${admin.adminPermissions eq 'root'}">超级管理员</c:if>
                <c:if test="${admin.adminPermissions eq 'administrator'}">管理员</c:if>
                <c:if test="${admin.adminPermissions eq 'general'}">审阅者</c:if>
                <a title="点击编辑所用管理员相关信息" target="_blank"
                   href="${pageContext.request.contextPath }/admin/findadminuserlist.su">
                &nbsp;编辑全部管理员信息
                </a>
                <a title="添加系统管理员" target="_blank" href="${pageContext.request.contextPath }/main/adminregister.su">&nbsp;+&nbsp;添加管理员</a>
            </span>
                <span>♞&nbsp;上次登陆时间：<fmt:formatDate value="${admin.adminLoginTime }" pattern="yyyy-MM-dd HH:mm"/></span>
                <span>♘&nbsp;上次登陆IP：${admin.adminLoginIp }</span>
                <span style="color: #911c4d">♞&nbsp;留言：<a title="点击编辑留言" href="#">&nbsp;编辑留言</a>
                <hr class="table_hr_margin">
                <span class="loginInfo_note">
                        ${admin.adminNote }
                </span>
                <hr class="table_hr_margin">
            </span>
            </div>
        </c:if>
        <c:if test="${admin.adminPermissions ne 'root' }">
            <div class="loginInfo">
            <span style="color: #23769d">♞&nbsp;${admin.adminName }
                <a title="点击编辑当前管理员相关信息"
                   href="${pageContext.request.contextPath }/admin/editadminuserinfo.su?auid=${admin.adminID }">
                    &nbsp;编辑
                </a>
                <a title="退出登录当前用户"
                   href="${pageContext.request.contextPath }/admin/adminlogout.su">&nbsp;&nbsp;&nbsp;&nbsp;->&nbsp;退出登录</a>
            </span>
                <span style="color: #6c918b">♘&nbsp;
                <c:if test="${admin.adminPermissions eq 'root'}">超级管理员</c:if>
                <c:if test="${admin.adminPermissions eq 'administrator'}">管理员</c:if>
                <c:if test="${admin.adminPermissions eq 'general'}">审阅者</c:if>
            </span>
                <span>♞&nbsp;上次登陆时间：<fmt:formatDate value="${admin.adminLoginTime }" pattern="yyyy-MM-dd HH:mm"/></span>
                <span>♘&nbsp;上次登陆IP：${admin.adminLoginIp }</span>
                <span style="color: #911c4d">♞&nbsp;留言：
                <hr class="table_hr_margin">
                <span class="loginInfo_note">
                        ${admin.adminNote }
                </span>
                <hr class="table_hr_margin">
            </span>
            </div>
        </c:if>

    </div>
    <div class="mainCenter_topRight">

        <c:if test="${admin.adminPermissions ne 'general' }">
            <%--教师信息面板/显示教授、副教授、讲师、助教 各有多少名--%>
            <div class="allTeacherNumber">
            <span style="font-size:large">全部职工<a title="编辑全部职工信息" target="_blank"
                                                 href="${pageContext.request.contextPath }/teacher/findteacherlist.su">&nbsp;编辑全部职工</a></span>
                <span>${professorNum+associateProfessor+instructor+teachingAssistant }</span>
            </div>
            <div class="teacherNum">
                <ul>
                    <li>教授：<span>${professorNum }
                    <a title="点击编辑关于教授相关信息" target="_blank"
                       href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherTitle=教授">&nbsp;编辑</a>
                </span>
                    </li>
                    <li>副教授：<span>${associateProfessor }
                    <a title="点击编辑关于副教授相关信息" target="_blank"
                       href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherTitle=副教授">&nbsp;编辑</a>
                </span>
                    </li>
                    <li>讲师：<span>${instructor }
                    <a title="点击编关于辑讲师相关信息" target="_blank"
                       href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherTitle=讲师">&nbsp;编辑</a>
                </span>
                    </li>
                    <li>助教：<span>${teachingAssistant }
                    <a title="点击编辑关于助教相关信息" target="_blank"
                       href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherTitle=助教">&nbsp;编辑</a>
                </span>
                    </li>
                    <li>
                        <a title="点击添加教师信息" target="_blank"
                           href="${pageContext.request.contextPath }/main/addteacher.su">
                            +&nbsp;添加教师信息
                        </a>
                    </li>
                </ul>
            </div>
        </c:if>

        <c:if test="${admin.adminPermissions eq 'general' }">
            <%--教师信息面板/显示教授、副教授、讲师、助教 各有多少名--%>
            <div class="allTeacherNumber">
            <span style="font-size:large">全部职工<a title="查看全部职工信息" target="_blank"
                                                 href="${pageContext.request.contextPath }/teacher/findteacherlist.su">&nbsp;查看全部职工</a></span>
                <span>${professorNum+associateProfessor+instructor+teachingAssistant }</span>
            </div>
            <div class="teacherNum">
                <ul>
                    <li>教授：<span>${professorNum }
                    <a title="点击查看关于教授相关信息" target="_blank"
                       href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherTitle=教授">&nbsp;查看</a>
                </span>
                    </li>
                    <li>副教授：<span>${associateProfessor }
                    <a title="点击查看关于副教授相关信息" target="_blank"
                       href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherTitle=副教授">&nbsp;查看</a>
                </span>
                    </li>
                    <li>讲师：<span>${instructor }
                    <a title="点击查看关于讲师相关信息" target="_blank"
                       href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherTitle=讲师">&nbsp;查看</a>
                </span>
                    </li>
                    <li>助教：<span>${teachingAssistant }
                    <a title="点击查看关于助教相关信息" target="_blank"
                       href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherTitle=助教">&nbsp;查看</a>
                </span>
                    </li>
                </ul>
            </div>
        </c:if>
    </div>


    <div class="mainCenter_downLeft">
        <c:if test="${admin.adminPermissions ne 'general' }">
            <%--教学工作量统计面板/显示学校一共有多少门课程、每个系有多少门课、每个年级有多少门课、平均每个老师有多少课时--%>
            <div class="gradeCurriculumNum">
                <ul>
                        <%-- 因为每年都会有毕业，所以每年都会变更，因为要显示四个年级，所以直接获取系统当前年进行对比--%>
                    <li>${grade.university_Four }级&nbsp;：<span>${university_Four }
                    <a target="_blank" title="点击编辑关于${grade.university_Four }级课程相关信息"
                       href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.grade=${grade.university_Four }">&nbsp;编辑</a>
                </span>
                    </li>
                    <li>${grade.university_Three }级&nbsp;：<span>${university_Three }
                    <a target="_blank" title="点击编辑关于$${grade.university_Three }级课程相关信息"
                       href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.grade=${grade.university_Three }">&nbsp;编辑</a>
                </span>
                    </li>
                    <li>${grade.university_Two }级&nbsp;：<span>${university_Two }
                    <a target="_blank" title="点击编辑关于${grade.university_Two }级课程相关信息"
                       href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.grade=${grade.university_Two }">&nbsp;编辑</a>
                </span>
                    </li>
                    <li>${grade.university_One }级&nbsp;：<span>${university_One }
                    <a target="_blank" title="点击编辑关于${grade.university_One }级课程相关信息"
                       href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.grade=${grade.university_One }">&nbsp;编辑</a>
                </span>
                    </li>
                    <li>
                        <a title="点击添加级课程相关信息" target="_blank"
                           href="${pageContext.request.contextPath }/main/addcurriculum.su">+&nbsp;添加课程</a>
                    </li>
                </ul>
            </div>
            <div class="allCurriculumNumber">
            <span style="font-size:large">全部课程<a target="_blank" title="编辑全部课程信息"
                                                 href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su">&nbsp;编辑全部课程</a></span>
                <span>${university_One+university_Two+university_Three+university_Four}</span>
            </div>
        </c:if>

        <c:if test="${admin.adminPermissions eq 'general' }">
            <%--教学工作量统计面板/显示学校一共有多少门课程、每个系有多少门课、每个年级有多少门课、平均每个老师有多少课时--%>
            <div class="gradeCurriculumNum">
                <ul>
                        <%-- 因为每年都会有毕业，所以每年都会变更，因为要显示四个年级，所以直接获取系统当前年进行对比--%>
                    <li>${grade.university_Four }级&nbsp;：<span>${university_Four }
                    <a target="_blank" title="点击查看关于${grade.university_Four }级课程相关信息"
                       href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.grade=${grade.university_Four }">&nbsp;查看</a>
                </span>
                    </li>
                    <li>${grade.university_Three }级&nbsp;：<span>${university_Three }
                    <a target="_blank" title="点击查看关于$${grade.university_Three }级课程相关信息"
                       href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.grade=${grade.university_Three }">&nbsp;查看</a>
                </span>
                    </li>
                    <li>${grade.university_Two }级&nbsp;：<span>${university_Two }
                    <a target="_blank" title="点击查看关于${grade.university_Two }级课程相关信息"
                       href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.grade=${grade.university_Two }">&nbsp;查看</a>
                </span>
                    </li>
                    <li>${grade.university_One }级&nbsp;：<span>${university_One }
                    <a target="_blank" title="点击查看关于${grade.university_One }级课程相关信息"
                       href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.grade=${grade.university_One }">&nbsp;查看</a>
                </span>
                    </li>
                </ul>
            </div>
            <div class="allCurriculumNumber">
            <span style="font-size:large">全部课程<a target="_blank" title="查看全部课程信息"
                                                 href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su">&nbsp;查看全部课程</a></span>
                <span>${university_One+university_Two+university_Three+university_Four}</span>
            </div>
        </c:if>
    </div>


    <div class="mainCenter_downCenter">
        <%--科研工作面板--%>
    </div>
    <%--if permissions == root 显示 URL，否则不显示--%>
    <div class="mainCenter_downRight">
        <%--系统设置面板/配置系统的各个properties，控制tomcat 的重启--%>
        <img src="${pageContext.request.contextPath }/img/settings.png">
    </div>
</div>
</body>
</html>
