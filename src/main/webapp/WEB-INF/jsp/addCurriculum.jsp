<%@page pageEncoding="UTF-8" errorPage="/ExceptionPage.jsp" %>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/addCurriculum.css">
    <!--js-->
    <%-- 不能引入JS文件，引入JS ，下面的代码 会失效--%>
    <script language="javascript">
        //还需要通过正则表达式控制用户名、手机号的开头等、邮箱的开头等
        function check() {
            //判断带 * 号的输入框是否为空
            if (document.Register_form.curriculumName.value == ""
                || document.Register_form.teacherPhoneNumber.value == ""
                || document.Register_form.teacherEMail.value == ""
                || document.Register_form.loginVerifyCode.value == ""
            ) {
                alert("带 * 项不能为空！");
                return false;
            }
            else return true;
        }

        function check_Length() {
            var uName_length = document.Register_form.curriculumName.value.length;
            var ph_Num_length = document.Register_form.teacherPhoneNumber.value.length;
            var uNote = document.Register_form.curriculumNote.value.length;
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
            //每次点击浏览器要处理的都是一条新的链接，都会去请求服务器，而服务器又不会处理该可变化参数（没使用参数绑定，对服务器不会又影响）。
            //new Date().getTime() 是获取当前时间，毫秒形式。imgEle.src 中的链接地址，每一秒都是一条不同的链接。
            imgEle.src = "${pageContext.request.contextPath }/utils/getv_code.su?a=" + new Date().getTime();
        }
    </script>
    <title>添加课程信息</title>
</head>
<%@include file="/globalNotification.jsp" %>
<body>
<div class="icon">
    <a href="${pageContext.request.contextPath }/index.jsp" title="回到首页">
        <img src="${pageContext.request.contextPath }/img/ico_color.png"></a>
</div>
<div class="reg_page">
    <p style="margin-bottom: 20px;color: crimson">
        添加课程信息
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
            <li><span>*</span>课程名称：</li>
            <li><span>*</span>课时：</li>
            <li><span>*</span>授课年级：</li>
            <li><span>*</span>授课班级：</li>
            <li><span>*</span>授课人数：</li>
            <li><span>*</span>教师ID：</li>
            <li><span>*</span>教师姓名：</li>
            <li><span>*</span>教师性别：</li>
            <li><span>*</span>教师手机号：</li>
            <li><span>*</span>教师邮箱：</li>
            <li>课程备注：</li>
            <li><span>*</span>验证码：</li>
        </ul>
    </div>

    <form name="Register_form" method="post" enctype="multipart/form-data"
          action="${pageContext.request.contextPath }/curriculum/addcurriculum.su">
        <div class="reg_content">
            <ul>
                <li><input name="curriculumName" type="text" placeholder="新课程名称" value="${curriculum.curriculumName }"/>
                </li>
                <li><input name="classHours" type="text" placeholder="新课程课时" value="${curriculum.classHours }"/>
                </li>
                <li><input name="grade" type="text" placeholder="新课程授课年级" value="${curriculum.grade }"/></li>
                <li><input name="className" type="text" placeholder="新课程授课班级" value="${curriculum.className }"/>
                </li>
                <li><input name="teachingNumber" type="text" placeholder="新课程授课人数"
                           value="${curriculum.teachingNumber }"/></li>
                <%--直接通过老师条目添加课程，该处可省--%>
                <li>
                    <input name="teacherID" type="text" placeholder="新课程教师ID" value="${curriculum.teacherID }"/>
                </li>
                <%--直接通过老师条目添加课程，该处可省--%>
                <li><input name="teacherName" type="text" placeholder="新课程教师姓名"
                           value="${curriculum.teacherName }"/></li>
                <%--直接通过老师条目添加课程，该处可省--%>
                <li class="radio_ctrl">
                    <input name="teacherSex" class="radio_ctrl_in" type="radio" value="1"
                            <c:if test="${curriculum.teacherSex eq '1' }"> checked </c:if> />男
                    <input name="teacherSex" class="radio_ctrl_in" type="radio" value="0"
                            <c:if test="${curriculum.teacherSex eq '2' }"> checked </c:if> />女
                </li>
                <%--直接通过老师条目添加课程，该处可省--%>
                <li style="margin-top: 10px;">
                    <input name="teacherPhoneNumber" type="tel" placeholder="新课程教师手机号"
                           value="${curriculum.teacherPhoneNumber }"
                           required pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}">
                </li>
                <%--直接通过老师条目添加课程，该处可省--%>
                <li><input name="teacherEMail" type="email" spellcheck="false" placeholder="新课程教师邮箱"
                           value="${curriculum.teacherEMail }"></li>
                <li><input name="curriculumNote" type="text" placeholder="新课程备注" value="${curriculum.curriculumNote }">
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
        <hr class="table_hr_margin">
        <div class="button" style="width: 220px">
            <c:if test="${pageContext.request.getHeader('Referer') ne null}">
                <input type="button" value="返回"
                    <%--判断，如果Referer==null 就返回到列表--%>
                       onclick="location.href='${pageContext.request.getHeader("Referer")}'">
            </c:if>
            <c:if test="${pageContext.request.getHeader('Referer') eq null}">
                <input type="button" value="返回"
                       onclick="location.href='${pageContext.request.contextPath }/curriculum/findcurriculumlist.su'">
            </c:if>
            <!--            type=button 就单纯是按钮功能,type=submit 是发送表单-->
            <input type="reset" value="重置">
            <!--            注意onSubmit的写法，千万不要写成：“check()”，这样当检验不能通过的时候也会提交表单。-->
            <input type="submit" name="bt_Register" value="添加" onclick="return check() && check_Length()">
        </div>
    </form>
</div>
</body>
</html>