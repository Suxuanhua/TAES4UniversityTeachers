<%--
  Created by IntelliJ IDEA.
  User: Suxuanhua
  Date: 2017/9/6
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="/ExceptionPage.jsp" %>
<html>
<head>
    <title>论文发表</title>
    <link href="img/bar_ico_color.png" rel="shortcut icon"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="${pageContext.request.contextPath }/img/bar_ico_color.png" rel="shortcut icon"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/wangEditor-3.0.9/wangEditor.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/top_nav.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/postEditor.css">
    <script type="text/javascript">
        function check() {
            var postTitle_length = document.postTitleForm.postTitle.value.length;

            if (document.postTitleForm.postTitle.value == "") {
                alert("标题不能为空");
                return false;
            } else if (postTitle_length < 4 || postTitle_length > 50) {
                alert("标题长度不能小于4位，不能大于50位，" + "当前标题长度是：" + postTitle_length);
                return false;
            }
            else {
                return true;
            }
        }
    </script>
</head>
<body>
<%@include file="/top_nav.jsp" %>
<form name="postTitleForm" method="post" action="${pageContext.request.contextPath }/post/newpost.su">
    <%--存储数据 js 获取到的数据--%>
    <input type="hidden" name="postContent" id="postContent">
    <div id="postEditor_top">
        <hr class="login_hr">
        <ul>
            <li>
                <%-- autocomplete="off" 不记录历史输入记录--%>
                <input name="postTitle" type="text" placeholder="在这输入新帖标题" autocomplete="off"/>
            </li>
            <li>
                <div class="button">
                    <input type="reset" value="重置" onclick="resetPostContent();">
                    <%--<input type="button" value="保存" onclick="location.href='uLogin.jsp'">--%>
                    <input type="button" name="bt_Save" value="保存">
                    <%--type=button 就单纯是按钮功能,type=submit 是发送表单--%>
                    <%--注意onSubmit的写法，千万不要写成：“check()”，这样当检验不能通过的时候也会提交表单。--%>
                    <input type="submit" name="bt_Register" value="发布" onclick="return check()&& getPostContent();">
                </div>
            </li>
        </ul>
    </div>
    <div id="editor" name="postContent"></div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath }/wangEditor-3.0.9/wangEditor.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/JavaScript/createWangEditor.js"></script>
</div>
</body>
</html>
