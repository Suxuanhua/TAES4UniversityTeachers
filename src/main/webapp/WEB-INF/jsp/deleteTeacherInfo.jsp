<%@page pageEncoding="UTF-8" errorPage="/ExceptionPage.jsp" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="${pageContext.request.contextPath }/img/bar_ico_color.png" rel="shortcut icon"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/editAdminiUserInfo.css">
    <!--js-->
    <script language="javascript">
        function check() {
            if (document.Register_form.loginVerifyCode.value == "") {
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

    <title>${teacher.teacherName }&nbsp;信息删除</title>
</head>
<%@include file="/globalNotification.jsp" %>
<body>
<div class="icon">
    <a target="_blank" href="${pic_virtual }/${teacher.teacherHeader_default }" title="查看头像大图"><img
            src="${pic_virtual }/${teacher.teacherHeader_default }"></a>
</div>
<div class="reg_page">
    <p style="margin-bottom: 20px;color: crimson">
        删除管理员信息
        <c:if test="${sessionScope.message !=null}">
            <br/>通知：${sessionScope.message }
            <% session.setAttribute ("message", null);
                session.removeAttribute ("message");%>
        </c:if>
    </p>
    <hr>
    <!-- 还需要添加选择头像功能 -->
    <div class="reg_page_text">
        <ul>
            <li><span>*</span>ID：</li>
            <li><span>*</span>姓名：</li>
            <li><span>*</span>职称：</li>
            <li><span>*</span>性别：</li>
            <li><span>*</span>手机号：</li>
            <li><span>*</span>邮箱：</li>
            <li>备注：</li>
            <li><span>*</span>验证码：</li>
        </ul>
    </div>

    <form name="Register_form" method="post"
          action="${pageContext.request.contextPath }/teacher/deleteteachersubmit.su">
        <li><input name="teacherID" type="hidden" value="${teacher.teacherID }"></li>
        <li><input name="teacherHeader_default" type="hidden" value="${teacher.teacherHeader_default }"></li>
        <div class="reg_content">
            <ul>
                <li><input disabled name="teacherID" disabled type="text" value="${teacher.teacherID }"/></li>
                <li><input disabled name="teacherName" type="text" value="${teacher.teacherName }"/></li>


                <li><input disabled name="teacherTitle" type="text" value="${teacher.teacherTitle }"></li>

                <li class="radio_ctrl">
                    <input disabled class="radio_ctrl_in" name="teacherSex"
                    <c:if test="${teacher.teacherSex eq '1' }"> checked </c:if>
                           type="radio" value="1"><span style="color: #0053c4;">男</span>
                    <input disabled class="radio_ctrl_in" name="teacherSex"
                    <c:if test="${teacher.teacherSex eq '1' }"> checked </c:if>
                           type="radio" value="0"><span style="color: #ff57b4;">女</span>
                </li>

                <li><input disabled name="teacherPhoneNumber" type="tel" value="${teacher.teacherPhoneNumber }"
                           required pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}">
                </li>
                <%--spellcheck="false" :关闭字符修正提示--%>
                <li><input disabled name="teacherEMail" type="email" value="${teacher.teacherEMail }"
                           spellcheck="false">
                </li>
                <li><input disabled name="teacherNote" type="text" value="${teacher.teacherNote }"></li>
                <li style="margin-top: 5px;">
                    <input type="text" name="loginVerifyCode" autocomplete="off" placeholder="输入下面验证码"/>
                </li>
                <li>
                    <img class="vcode" id="vcode_img" src="${pageContext.request.contextPath }/utils/getv_code.su"><%--返回一个.jpg的图片--%>
                    <a href="javascript:_change()">换一张</a><br/>
                </li>
            </ul>
        </div>
        <hr style="margin-bottom: 2%">
        <div class="button" style="width: 220px">

            <c:if test="${pageContext.request.getHeader('Referer') ne null}">
                <input type="button" value="取消"
                    <%--判断，如果Referer==null 就返回到列表--%>
                       onclick="location.href='${pageContext.request.getHeader("Referer")}'">
            </c:if>
            <c:if test="${pageContext.request.getHeader('Referer') eq null}">
                <input type="button" value="取消"
                       onclick="location.href='${pageContext.request.contextPath }/teacher/findteacherlist.su'">
            </c:if>
            <!--            注意onSubmit的写法，千万不要写成：“check()”，这样当检验不能通过的时候也会提交表单。-->
            <input type="submit" name="bt_Register" value="确认删除" onclick="return check()">
        </div>
    </form>
</div>
</body>
</html>