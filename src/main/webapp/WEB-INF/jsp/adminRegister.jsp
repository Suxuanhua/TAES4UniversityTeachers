<%@page pageEncoding="UTF-8" errorPage="../../ExceptionPage.jsp" %>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/uRegister.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/uLogin.css">
    <!--js-->
    <%-- 不能引入JS文件，引入JS ，下面的代码 会失效--%>
    <script language="javascript">
        //还需要通过正则表达式控制用户名、手机号的开头等、邮箱的开头等
        function check() {
            //判断带 * 号的输入框是否为空
            if (document.Register_form.adminName.value == ""
                || document.Register_form.adminPassword.value == ""
                || document.Register_form.adminPassword_again.value == ""
                || document.Register_form.adminPhoneNumber.value == ""
                || document.Register_form.adminEMail.value == ""
                || document.Register_form.loginVerifyCode.value == ""
            ) {
                alert("带 * 项不能为空！");
                return false;
            }
            //判断两次输入的密码是否相同
            else if (document.Register_form.adminPassword.value !== document.Register_form.adminPassword_again.value) {
                alert("两次密码输入不相同！");
                return false;
            }

            var imgSize = document.Register_form.pictureFile.files[0].size;
            //alert("file size：" + imgSize + "B");
            if (imgSize > 1024 * 1024 * 5) {
                alert("注意：图片文件大小必须 < 5m，请重新选择");
                return false;
            }
            else return true;
        }

        function check_Length() {
//            var pwd=document.getElementById("user_password").value.length;
            var uName_length = document.Register_form.adminName.value.length;
            var uPwd_length = document.Register_form.adminPassword.value.length;
            var ph_Num_length = document.Register_form.adminPhoneNumber.value.length;
            var uNote = document.Register_form.adminNote.value.length;
            //判断用户名长度
            if (uName_length < 2 || uName_length > 16) {
                alert("用户名长度不能小于两位，不能大于16位，" + "当前用户名长度是：" + uName_length);
                return false;
            }
            //判断密码长度
            else if (uPwd_length < 6 || uPwd_length > 16) {
                alert("密码长度不能小于六位，不能大于16位，" + "当前密码长度是：" + uPwd_length);
                return false;
            }
            //判断手机号码长度
            else if (ph_Num_length !== 11) {
                alert("手机号码长度只能是 11 位，" + "当前的号码长度是：" + ph_Num_length);
                return false;
            }
            //判断备注的长度
            else if (uNote > 200) {
                alert("备注长度不能超过200字，" + "当前的备注长度是：" + uNote);
                return false;
            }
            else return true;
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
    <title>管理员注册</title>
</head>
<%@include file="../../globalNotification.jsp" %>
<body>
<div class="icon">
    <a href="${pageContext.request.contextPath }/index.jsp" title="回到首页">
        <img src="${pageContext.request.contextPath }/img/ico_color.png"></a>
</div>
<div class="reg_page">
    <p style="margin-bottom: 20px;color: crimson">
        注册管理员账号
        <c:if test="${message !=null}">
            <br/>通知：${message }
            <% session.setAttribute ("message", null);
                session.removeAttribute ("message");%>
        </c:if>
    </p>
    <hr class="table_hr_margin">
    <!-- 还需要添加选择头像功能 -->
    <div class="reg_page_text">
        <ul>
            <li><span>*</span>用户名：</li>
            <li><span>*</span>密码：</li>
            <li><span>*</span>确认密码：</li>
            <li><span>*</span>手机号：</li>
            <li><span>*</span>邮箱：</li>
            <li>备注：</li>
            <li>选择头像：</li>
            <li><span>*</span>验证码：</li>
        </ul>
    </div>

    <form name="Register_form" method="post" enctype="multipart/form-data"
          action="${pageContext.request.contextPath }/admin/adminregister.su">
        <div class="reg_content">
            <ul>
                <li><input name="adminName" type="text" placeholder="新管理员的姓名" value="${administrator.adminName }"/></li>
                <li><input name="adminPassword" type="password" placeholder="新管理员的密码"
                           value="${administrator.adminPassword }"></li>
                <li><input name="adminPassword_again" type="password" placeholder="再次输入相同的密码"
                           value="${administrator.adminPassword }"></li>

                <li><input name="adminPhoneNumber" type="tel" placeholder="需要绑定的手机号码"
                           value="${administrator.adminPhoneNumber }"
                           required pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}">
                </li>
                <li><input name="adminEMail" type="email" spellcheck="false" placeholder="需要绑定的邮箱"
                           value="${administrator.adminEMail }"></li>
                <li><input name="adminNote" type="text" placeholder="新管理员备注" value="${administrator.adminNote }"></li>
                <li>
                    <a href="javascript:;" class="a-upload">
                        <input type="file" name="pictureFile" class="a-upload" accept="image/jpeg,image/jpg,image/png">点击这里&nbsp;选择上传的图片（<5m）
                    </a>
                </li>
                <li style="margin-top: 5px;">
                    <input type="text" name="loginVerifyCode" autocomplete="off" placeholder="输入下面验证码"/>
                </li>
                <li>
                    <img class="vcode" id="vcode_img" src="${pageContext.request.contextPath }/utils/getv_code.su"><%--返回一个.jpg的图片--%>
                    <a href="javascript:_change()">换一张</a><br/>
                </li>
            </ul>
        </div>
        <hr class="table_hr_margin">
        <div class="button" style="width: 220px">
            <input type="button" value="前往登录" onclick="location.href='${pageContext.request.contextPath }/main/adminlogin.su'">
            <!--            type=button 就单纯是按钮功能,type=submit 是发送表单-->
            <input type="reset" value="重置">
            <!--            注意onSubmit的写法，千万不要写成：“check()”，这样当检验不能通过的时候也会提交表单。-->
            <input type="submit" name="bt_Register" value="注册" onclick="return check() && check_Length()">
        </div>
    </form>
</div>
</body>
</html>