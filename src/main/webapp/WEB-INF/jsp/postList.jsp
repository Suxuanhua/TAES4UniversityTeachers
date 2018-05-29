<%@ page import="org.suxuanhua.ssm.po.post.Post" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: clamour
  Date: 17-9-11
  Time: 上午8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" errorPage="/ExceptionPage.jsp"%>
<html>
<head>
    <title>论文列表</title>
    <link href="${pageContext.request.contextPath }/img/bar_ico_color.png" rel="shortcut icon" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/top_nav.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/postList.css">
</head>
<%
//    BolomiServlet bs = new BolomiServlet ();
    //需要判断allPost是不是等于null
//    List<Post> allPost = bs.findAllPost ();
    List<Post> allPost = (List<Post>) session.getAttribute ("postList");
%>
<body>
<%--导航--%>
<%@include file="/top_nav.jsp" %>
<%@include file="/globalNotification.jsp" %>
<div class="postList">
    <%
        if (allPost!=null && allPost.size ()>0 ){
            for (Post p : allPost){ %>
    <div class="postTopic">
        <ul>
            <hr class="postTopic_hr">
            <li>
                <img src="/taes4ut-pic/<%=p.getuHeader_default()%>">
            </li>
            <li>
                <div class="postInfo">
                    <%-- .substring 截取多余字符串 --%>
                    <p><%="<span>"+p.getuName ()+"</span>\t"+p.getPostDate()%></p>

                    <a href="${pageContext.request.contextPath }/post/findpost.su?postID=<%=p.getPostID()%>" target="_blank">
                        <p><%=p.getPostTitle()%></p>
                    </a>
                </div>
            </li>
            <li>
                <p class="info">
                    <%="访问："+ p.getPostReplies ()%>
                </p>
                <p class="info">
                    <%="回复："+ p.getPostVisits ()  %>
                </p>
            </li>
        </ul>
    </div><%
    }
}else{%>
                <div class="warning">
                    <%if (allPost==null){%>
                        <p class="warning_Title">服务器内部错误</p>
                        <hr class="postTopic_hr">
                        <p>请发送信息到该邮箱：suxuanhua@foxmail.com</p>
                    <%}else{%>
                        <p class="warning_Title">没有找到帖子</p>
                        <hr class="postTopic_hr">
                        <p>还没有用户在该栏目发过帖子</p>
                    <%}%>
                </div>
            <%}%>
</div>
</body>
</html>
