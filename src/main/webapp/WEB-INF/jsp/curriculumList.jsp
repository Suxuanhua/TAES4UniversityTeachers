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
    <title>课程列表</title>
</head>
<%@include file="/globalNotification.jsp" %>
<%@include file="/top_nav.jsp" %>
<body>
<div class="contextPage">
    <%--获取LIST 的长度--%>
    <c:if test="${fn:length(curriculumList) gt 0}">
        <div class="contextPageTile">
            <a style="color: #ff415a" title="点击返回查看所有课程"
               href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su">
                课程列表&nbsp;[&nbsp;${fn:length(curriculumList)}&nbsp;]
            </a>
        </div>
        <div class="context">
            <div class="contextInfoTitle">
                <ul>
                    <li style="width: 15%">课程名称</li>
                    <li style="width: 5%">年级</li>
                    <li>班级</li>
                    <li style="width: 5%">课时</li>
                    <li>授课老师</li>
                    <li style="width: 10%">老师联系方式</li>
                    <li>备注</li>
                    <li>最后修改日期</li>
                    <c:if test="${admin.adminPermissions ne 'general' }">
                        <li>操作</li>
                    </c:if>
                </ul>
            </div>
            <br>
            <hr class="table_hr_margin">

            <c:forEach items="${curriculumList }" var="curriculum">
                <div class="ContextInfo">
                    <ul>
                        <li style="width: 15%">
                            <a title="点击查找${curriculum.curriculumName } 的所有课程"
                               href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.curriculumName=${curriculum.curriculumName }">
                                    ${curriculum.curriculumName }
                            </a>
                        </li>
                        <li style="width: 5%">
                            <a title="点击查找${curriculum.grade } 的所有课程"
                               href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.grade=${curriculum.grade }">
                                    ${curriculum.grade }
                            </a>
                        </li>
                        <li>
                            <a title="点击查找${curriculum.className } 的所有课程"
                               href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.className=${curriculum.className }">
                                    ${curriculum.className }
                            </a>
                        </li>

                        <li style="width: 5%">${curriculum.classHours }</li>

                        <li>
                            <a title="点击查找${curriculum.teacherName } 的所有课程"
                               href="${pageContext.request.contextPath }/curriculum/findcurriculumlist.su?curriculumTable.teacherName=${curriculum.teacherName }">
                                    ${curriculum.teacherName }
                            </a>
                        </li>
                        <li style="width: 10%">${curriculum.teacherPhoneNumber }</li>
                        <li style="color: #ff415a">${curriculum.curriculumNote }<c:if
                                test="${curriculum.curriculumNote eq '' || curriculum.curriculumNote ==null }">&nbsp;</c:if></li>
                        <li><fmt:formatDate value="${curriculum.curriculumUpdateTime}" pattern="yyyy-MM-dd HH:mm"/></li>
                        <c:if test="${admin.adminPermissions ne 'general' }">
                            <li>
                                <a style="color: #207a47"
                                   href="${pageContext.request.contextPath }/curriculum/editcurriculuminfo.su?curriculumID=${curriculum.curriculumID }">编辑</a>
                                <a style="color: #ff415a"
                                   href="${pageContext.request.contextPath }/curriculum/deletecurriculumquery.su?curriculumID=${curriculum.curriculumID }">删除</a>
                            </li>
                        </c:if>
                    </ul>
                    <br>
                    <hr class="table_hr_margin">
                </div>
            </c:forEach>
        </div>
    </c:if>

    <c:if test="${fn:length(curriculumList) lt 1}">
        <p class="warning_Title">没有找到相关信息</p>
        <hr class="table_hr_margin">
        <p class="warning_Info">系统不存在该您搜索的信息</p>
    </c:if>
</div>
</body>

</html>