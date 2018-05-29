<%@page pageEncoding="UTF-8" errorPage="../../ExceptionPage.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<head>
    <%--防止页面信息乱码，HTML 也要设置--%>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <title>管理员登录</title>
    <link href="${pageContext.request.contextPath }/img/bar_ico_color.png" rel="shortcut icon"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/uLogin.css">
    <!--js-->
    <script language="javascript">
        function check() {
            if (document.Login_form.adminEMail.value == "") {
                alert("用户名邮箱不能为空");
                return false;
            }
            else if (document.Login_form.adminPassword.value == "") {
                alert("密码不能为空");
                return false;
            }else if (document.Login_form.loginVerifyCode.value == "") {
                alert("验证码不能为空");
                return false;
            }
            else {
                return true;
            }
        }
        //切换验证码
        function _change() {
            //1.得到img元素
            //2.修改其src为/VerifyCodeServlet
            var imgEle = document.getElementById("vcode_img");
            //?a="+new Date().getTime() : 因为浏览器会把/VerifyCodeServlet 缓存下载，造成点击换一张图片没反应。
            //解决这个问题的办法就是给/VerifyCodeServlet 加可变化的参数，使imgEle.src 中的链接每一毫秒都不一样。
            //每次点击浏览器要处理的都是一条新的链接，都会去请求服务器，而服务器又不会处理该可变化参数（对服务器不会又影响）。
            //new Date().getTime() 是获取当前时间，毫秒形式。imgEle.src 中的链接地址，每一秒都是一条不同的链接。
            imgEle.src = "${pageContext.request.contextPath }/utils/getv_code.su?a=" + new Date().getTime();
        }
    </script>
</head>
<%--
bug ：清理谷歌浏览器数据之后，登录之后又跳转登录页面，一直循环。
--%>

<%--静态包含，编译时期复制改文件中内容到这个文件中--%>
<%@include file="/globalNotification.jsp" %>
<body>
<div class="icon">
    <a href="${pageContext.request.contextPath }/index.jsp" title="回到首页">
        <img src="${pageContext.request.contextPath }/img/ico_color.png"></a>
</div>
<div id="out_input">
    <p class="login_tips">
        管理员登陆
        <c:if test="${message !=null}">
            <br/>通知：${message }
            <% session.setAttribute ("message", null);
                session.removeAttribute ("message");
                session.invalidate ();
            %>
        </c:if>
    </p>
    <hr class="login_hr">
    <div class="text">
        <p>邮箱：</p>
        <p>密码：</p>
        <p>验证码：</p>
    </div>
    <%--需要在外面套上表单才能将里面的数据提交到Servlet  onSubmit="return check()"--%>
    <form name="Login_form" method="post" action="${pageContext.request.contextPath }/admin/adminlogin.su">
        <div class="input_text">
            <ul>
                <li>
                    <%--spellcheck="false" :关闭字符修正提示--%>
                    <input type="email" name="adminEMail" maxLength="30" size="35"
                           placeholder="输入已注册用户的邮箱" spellcheck="false">
                </li>
                <li>
                    <input type="password" name="adminPassword" maxLength="20" size="40"
                           placeholder="输入已注册用户的密码" spellcheck="false">
                </li>
                <li>
                    <input type="text" name="loginVerifyCode" autocomplete="off" placeholder="输入下面验证码"/>
                </li>
                <li>
                    <img class="vcode" id="vcode_img" src="${pageContext.request.contextPath }/utils/getv_code.su"><%--返回一个.jpg的图片--%>
                    <a href="javascript:_change()">换一张</a><br/>
                </li>
            </ul>
        </div>
        <div class="button">
            <input type="button" value="回到首页" onclick="location.href='${pageContext.request.contextPath }/index.jsp'">
            <%--type=button 就单纯是按钮功能,type=submit 是发送表单--%>
            <input type="button" value="前往注册" onclick="location.href='${pageContext.request.contextPath }/main/adminregister.su'">
            <%--注意onSubmit的写法，千万不要写成：“check()”，这样当检验不能通过的时候也会提交表单。--%>
            <input type="submit" name="bt_Login" value="登录" onclick="return check()">
        </div>
    </form>
</div>
</body>
</html>