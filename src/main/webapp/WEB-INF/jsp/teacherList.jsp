<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%--禁用缓存--%>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="${pageContext.request.contextPath }/img/bar_ico_color.png" rel="shortcut icon"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/top_nav.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/contextListPage.css">
    <title>职工列表</title>
</head>
<%@include file="/globalNotification.jsp" %>
<%@include file="/top_nav.jsp" %>
<body>
<div class="contextPage">
    <%--获取LIST 的长度--%>
    <c:if test="${fn:length(teacherList) gt 0}">
        <div class="contextPageTile">
            <a style="color: #ff415a" title="点击返回查看所有教师信息"
               href="${pageContext.request.contextPath }/teacher/findteacherlist.su">
                职工列表&nbsp;[&nbsp;${fn:length(teacherList)}&nbsp;]
            </a>
        </div>
        <div class="context">
            <div class="contextInfoTitle">
                <ul>
                    <li style="width: 8%">ID</li>
                    <li>姓名</li>
                    <li style="width: 5%">性别</li>
                    <li style="width: 8%">职称</li>
                    <li>手机号</li>
                    <li style="width: 20%">备注</li>
                    <li>最后修改日期</li>
                    <c:if test="${admin.adminPermissions ne 'general' }">
                        <li>操作</li>
                    </c:if>
                </ul>
            </div>
            <br>
            <hr class="table_hr_margin">

            <c:forEach items="${teacherList }" var="teacher">
                <div class="ContextInfo">
                    <ul>
                        <li style="width: 8%">${teacher.teacherID }</li>
                        <li>${teacher.teacherName }</li>

                        <li style="width: 5%">
                            <c:if test="${teacher.teacherSex eq '1' }">
                                <span>
                                    <a title="点击查找所有男性教师" style="color: #0053c4"
                                       href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherSex=${teacher.teacherSex }">
                                        男
                                    </a>
                                </span>
                            </c:if>
                            <c:if test="${teacher.teacherSex eq '2' }">
                                <span>
                                    <a title="点击查找所有女性教师" style="color: #ff415a"
                                       href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherSex=${teacher.teacherSex }">
                                        女
                                    </a>
                                </span>
                            </c:if>
                        </li>
                        <li style="width: 8%">
                            <a title="点击查找所有${teacher.teacherTitle }"
                               href="${pageContext.request.contextPath }/teacher/findteacherlist.su?teacherCustom.teacherTitle=${teacher.teacherTitle }">
                                    ${teacher.teacherTitle }
                            </a>
                        </li>

                        <li>${teacher.teacherPhoneNumber }</li>

                        <li style="width: 20%">${teacher.teacherNote }<c:if
                                test="${teacher.teacherNote eq '' || teacher.teacherNote ==null }">&nbsp;</c:if></li>
                        <li><fmt:formatDate value="${teacher.teacherUpdateTime}" pattern="yyyy-MM-dd HH:mm"/></li>
                        <c:if test="${admin.adminPermissions ne 'general' }">
                            <li>
                                <a style="color: #207a47"
                                   href="${pageContext.request.contextPath }/teacher/editteacherinfo.su?teacherID=${teacher.teacherID }">编辑</a>
                                <a style="color: #ff415a"
                                   href="${pageContext.request.contextPath }/teacher/deleteteacherquery.su?teacherID=${teacher.teacherID }">删除</a>
                                <a style="color: #000000"
                                   href="${pageContext.request.contextPath }/curriculum/addcurriculumbyteacher.su?teacherID=${teacher.teacherID }">添加课程</a>
                            </li>
                        </c:if>
                    </ul>
                    <br>
                    <hr class="table_hr_margin">
                </div>
            </c:forEach>
        </div>
    </c:if>

    <c:if test="${fn:length(teacherList) lt 1}">
        <p class="warning_Title">没有找到信息</p>
        <hr class="table_hr_margin">
        <p class="warning_Info">系统不存在该您搜索的信息</p>
    </c:if>
</div>
</body>

</html>