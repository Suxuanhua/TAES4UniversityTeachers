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
    <title>管理员列表</title>
</head>
<%@include file="/globalNotification.jsp" %>
<%@include file="/top_nav.jsp" %>
<body>
<div class="contextPage">
    <div class="contextPageTile">管理员列表&nbsp;[&nbsp;${fn:length(adminUserList)}&nbsp;]</div>
    <div class="context">
        <div class="contextInfoTitle">
            <ul>
                <li style="width: 10%">权限</li>
                <li style="width: 10%">ID</li>
                <li>姓名</li>
                <li>手机号</li>
                <li style="width: 10%">最后登陆IP</li>
                <li>最后登录日期</li>
                <li style="width: 15%">备注</li>
                <c:if test="${admin.adminPermissions eq 'root' }">
                    <li>操作</li>
                </c:if>
            </ul>
        </div>
        <br>
        <hr class="table_hr_margin">
        <c:forEach items="${adminUserList }" var="admininfo">
            <div class="ContextInfo">
                <ul>
                    <li style="width: 10%">
                        <c:if test="${admininfo.adminPermissions eq 'root'}">
                            <span style="color: #ff415a;">超级管理员</span>
                        </c:if>
                        <c:if test="${admininfo.adminPermissions eq 'administrator'}">
                            <span style="color: #0053c4;">管理员</span>
                        </c:if>
                        <c:if test="${admininfo.adminPermissions eq 'general'}">
                            <span style="color: #207a47;">审阅者</span>
                        </c:if>
                    </li>
                    <li style="width: 10%">${admininfo.adminID }</li>
                    <li>${admininfo.adminName }</li>
                    <li>${admininfo.adminPhoneNumber }</li>
                    <li style="width: 10%">${admininfo.adminLoginIp }</li>
                    <li><fmt:formatDate value="${admininfo.adminLoginTime}" pattern="yyyy-MM-dd HH:mm"/></li>
                    <li style="width: 15%">${admininfo.adminNote }<c:if
                            test="${admininfo.adminNote eq '' || admininfo.adminNote ==null }">&nbsp;</c:if></li>
                    <%-- 注意：这里时 admin，是当前登录用户，admininfo是查询到的信息--%>
                    <c:if test="${admin.adminPermissions eq 'root' }">
                        <li>
                            <a style="color: #207a47"
                               href="${pageContext.request.contextPath }/admin/editadminuserinfo.su?auid=${admininfo.adminID}">编辑</a>
                            <a style="color: #ff415a"
                               href="${pageContext.request.contextPath }/admin/deleteadminquery.su?auid=${admininfo.adminID}">删除</a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <br>
            <hr class="table_hr_margin">
        </c:forEach>
    </div>
</div>
</body>

</html>