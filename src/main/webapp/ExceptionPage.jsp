<%--
  Created by IntelliJ IDEA.
  User: clamour
  Date: 17-9-11
  Time: 上午8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<html>
<head>
    <title><%=response.getStatus()%>&nbsp;发生了错误</title>
    <link href="${pageContext.request.contextPath }/img/bar_ico_color.png" rel="shortcut icon"/>
    <meta http-equiv="Content-Type" content="text/html">
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/top_nav.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/postContent.css">
</head>
<body>
<%--导航--%>
<%@include file="/top_nav.jsp" %>
<%--需要判断内容是否为空，需要获取通知，显示新发帖成功信息--%>
<%--<jsp:include page="top_nav.jsp" flush="true"/>--%>
<div class="post">
    <div class="warning">
        <p class="warning_Title"><%=response.getStatus()%>&nbsp;&nbsp;错误的请求，使服务器发生了异常。</p>
        <hr class="warning_hr">
        <p>请发送信息到该邮箱：suxuanhua@foxmail.com</p>
    </div>
</div>
</body>
</html>