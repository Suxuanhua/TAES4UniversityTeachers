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
        //还需要通过正则表达式控制用户名、手机号的开头等、邮箱的开头等
        function check() {
            //判断带 * 号的输入框是否为空
            if (document.Register_form.teacherName.value == ""
                || document.Register_form.teacherPhoneNumber.value == ""
                || document.Register_form.teacherEMail.value == ""
                || document.Register_form.loginVerifyCode.value == ""
            ) {
                alert("带 * 项不能为空！");
                return false;
            }
            //判断图片大小是否合适
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
            var uName_length = document.Register_form.teacherName.value.length;
            var ph_Num_length = document.Register_form.teacherPhoneNumber.value.length;
            var uNote = document.Register_form.teacherNote.value.length;
            //判断用户名长度
            if (uName_length < 2 || uName_length > 16) {
                alert("用户名长度不能小于两位，不能大于16位，" + "当前用户名长度是：" + uName_length);
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

    <title>${teacher.teacherName }&nbsp;信息编辑</title>
</head>
<%@include file="/globalNotification.jsp" %>
<body>
<div class="icon">
    <a target="_blank" href="${pic_virtual }/${teacher.teacherHeader_default }" title="查看头像大图"><img
            src="${pic_virtual }/${teacher.teacherHeader_default }"></a>
</div>
<div class="reg_page">
    <p style="margin-bottom: 20px;color: crimson">
        修改管理员信息
        <c:if test="${message !=null}">
            <br/>通知：${message }
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
            <li>修改头像：</li>
            <li><span>*</span>验证码：</li>
        </ul>
    </div>

    <form name="Register_form" method="post" enctype="multipart/form-data"
          action="${pageContext.request.contextPath }/teacher/editteachersubmit.su">

        <input name="teacherHeader_default" type="hidden" value="${teacher.teacherHeader_default }"/>
        <input name="teacherID" type="hidden" value="${teacher.teacherID }">
        <div class="reg_content">
            <ul>
                <li><input name="teacherID" disabled type="text" value="${teacher.teacherID }"/></li>
                <li><input name="teacherName" type="text" value="${teacher.teacherName }"/></li>
                <li><input name="teacherTitle" type="text" value="${teacher.teacherTitle }"/></li>

                <li class="radio_ctrl">
                    <input name="teacherSex" class="radio_ctrl_in" type="radio" value="1" <c:if
                            test="${teacher.teacherSex eq '1' }"> checked </c:if> />男
                    <input name="teacherSex" class="radio_ctrl_in" type="radio" value="2" <c:if
                            test="${teacher.teacherSex eq '2' }"> checked </c:if> />女
                </li>

                <li><input name="teacherPhoneNumber" type="tel" value="${teacher.teacherPhoneNumber }"
                           required pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}">
                </li>
                <%--spellcheck="false" :关闭字符修正提示--%>
                <li><input name="teacherEMail" type="email" value="${teacher.teacherEMail }" spellcheck="false"></li>
                <li><input name="teacherNote" type="text" value="${teacher.teacherNote }"></li>
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
        <hr style="margin-bottom: 2%">
        <div class="button" style="width: 220px">
            <c:if test="${pageContext.request.getHeader('Referer') ne null}">
                <input type="button" value="返回"
                    <%--判断，如果Referer==null 就返回到列表--%>
                       onclick="location.href='${pageContext.request.getHeader("Referer")}'">
            </c:if>
            <c:if test="${pageContext.request.getHeader('Referer') eq null}">
                <input type="button" value="返回"
                       onclick="location.href='${pageContext.request.contextPath }/teacher/findteacherlist.su'">
            </c:if>


            <!--            type=button 就单纯是按钮功能,type=submit 是发送表单-->
            <input type="reset" value="重置">
            <!--            注意onSubmit的写法，千万不要写成：“check()”，这样当检验不能通过的时候也会提交表单。-->
            <input type="submit" name="bt_Register" value="保存" onclick="return check() && check_Length();">
        </div>
    </form>
</div>
</body>
</html>