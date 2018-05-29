<%@ page import="org.suxuanhua.ssm.po.post.Post" %>
<%@ page import="java.io.*" %>
<%@ page import="org.suxuanhua.ssm.tools.TAES4Utils" %>
<%--
  Created by IntelliJ IDEA.
  User: clamour
  Date: 17-9-11
  Time: 上午8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/ExceptionPage.jsp"%>
<html>
<head>
    <title>${sessionScope.post.postTitle}</title>
    <link href="${pageContext.request.contextPath }/img/bar_ico_color.png" rel="shortcut icon" />
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
<%@include file="/globalNotification.jsp" %>
<%--需要判断内容是否为空，需要获取通知，显示新发帖成功信息--%>
<%--<jsp:include page="top_nav.jsp" flush="true"/>--%>
<div class="post">
<%
    Post post = (Post)session.getAttribute ("post");
    Reader reader=null;
    BufferedReader bufr = null;
    File postContentFile;
    if (post !=null) {

        try{
            //配置帖子目录
            postContentFile = new File (
                    TAES4Utils.getPropertiesValue ("/TAES4UniversityTeachers-Configuration.properties",
                            "TAES4UT_POST_PATH")+"/"+ post.getPostContentAddr ());
            if (postContentFile.exists () && !post.getPostTitle ().isEmpty ()) {//isEmpty() 原理就是判断字符长度是否为0
%>
            <h1 class="postTitle">${sessionScope.post.postTitle}</h1>
            <div class="postContent">
                <%--bug 动态包含只能用 项目内的相对路径--%>
                <%--<jsp:include page="${sessionScope.post.postContentAddr}" flush="true"/>--%>
                <%--用IO流将项目外的字符信息读取到打印到这--%>
                <%
                    reader = new InputStreamReader (new FileInputStream (postContentFile),"UTF-8");
                    bufr = new BufferedReader (reader);
                    String strBufffered;
                    while((strBufffered=bufr.readLine ())!=null){
                        out.write (strBufffered);
                    }
                %>
            </div>
            <%}else{%>
            <div class="warning">
            <p class="warning_Title">服务器内部文件丢失，找不到帖子内容</p>
            <hr class="warning_hr">
            <p>请发送信息到该邮箱：suxuanhua@foxmail.com</p>
            </div>
            <%}
        }finally {
           if (reader!=null)
               reader.close ();
           else if(bufr!=null)
               bufr.close ();
        }
    }else{%>
    <div class="warning">
          <p class="warning_Title">您要找的帖子不存在</p>
          <hr class="warning_hr">
          <p>请发送信息到该邮箱：suxuanhua@foxmail.com</p>
    </div>
    <%}%>
</div>
</body>
</html>