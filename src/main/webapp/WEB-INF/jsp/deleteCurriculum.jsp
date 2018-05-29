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
        //切换验证码
        function _change() {
            //1.得到img元素
            //2.修改其src为/VerifyCodeServlet
            var imgEle = document.getElementById("vcode_img");
            //?a="+new Date().getTime() : 因为浏览器会把/VerifyCodeServlet 缓存下载，造成点击换一张图片没反应。
            //解决这个问题的办法就是给/VerifyCodeServlet 加可变化的参数，使imgEle.src 中的链接每一毫秒都不一样。
            //每次点击浏览器要处理的都是一条新的链接，都会去请求服务器，而服务器又不会处理该可变化参数（没使用参数绑定，对服务器不会又影响）。
            //new Date().getTime() 是获取当前时间，毫秒形式。imgEle.src 中的链接地址，每一秒都是一条不同的链接。
            imgEle.src = "/TAES4University-Teachers/utils/getv_code.su?a=" + new Date().getTime();
        }
    </script>
    <title>删除课程信息</title>
</head>
<%@include file="/globalNotification.jsp" %>
<body>
<div class="icon">
    <a href="${pageContext.request.contextPath }/index.jsp" title="回到首页">
        <img src="${pageContext.request.contextPath }/img/ico_color.png"></a>
</div>
<div class="reg_page">
    <p style="margin-bottom: 20px;color: crimson">
        修改课程信息
        <c:if test="${message !=null}">
            <br/>通知：${message }
            <% session.setAttribute ("message", null);
                session.removeAttribute ("message");%>
        </c:if>
    </p>
    <hr class="table_hr_margin">
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

    <form name="Register_form" method="post"
          action="${pageContext.request.contextPath }/curriculum/deletecurriculumsubmit.su">
        <input name="curriculumID" type="hidden" value="${curriculum.curriculumID }"/>
        <div class="reg_content">
            <ul>
                <li><input disabled name="curriculumName" type="text" placeholder="课程名称"
                           value="${curriculum.curriculumName }"/>
                </li>
                <li><input disabled name="classHours" type="text" placeholder="课程课时" value="${curriculum.classHours }"/>
                </li>
                <li><input disabled name="grade" type="text" placeholder="课程授课年级" value="${curriculum.grade }"/></li>
                <li><input disabled name="className" type="text" placeholder="课程授课班级" value="${curriculum.className }"/>
                </li>
                <li><input disabled name="teachingNumber" type="text" placeholder="课程授课人数"
                           value="${curriculum.teachingNumber }"/></li>
                <li>
                    <input disabled name="teacherID" type="text" placeholder="课程教师ID" value="${curriculum.teacherID }"/>
                </li>
                <li><input disabled name="teacherName" type="text" placeholder="课程教师姓名"
                           value="${curriculum.teacherName }"/></li>
                <li class="radio_ctrl">
                    <input disabled name="teacherSex" class="radio_ctrl_in" type="radio" value="1" <c:if
                            test="${curriculum.teacherSex eq '1' }"> checked </c:if> />男
                    <input disabled name="teacherSex" class="radio_ctrl_in" type="radio" value="0" <c:if
                            test="${curriculum.teacherSex eq '0' }"> checked </c:if> />女
                </li>
                <li style="margin-top: 10px;">
                    <input disabled name="teacherPhoneNumber" type="tel" placeholder="课程教师手机号"
                           value="${curriculum.teacherPhoneNumber }"
                           required pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}">
                </li>
                <li><input disabled name="teacherEMail" type="email" spellcheck="false" placeholder="课程教师邮箱"
                           value="${curriculum.teacherEMail }"></li>
                <li><input disabled name="curriculumNote" type="text" placeholder="课程备注"
                           value="${curriculum.curriculumNote }">
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
                <input type="button" value="取消"
                    <%--判断，如果Referer==null 就返回到列表--%>
                       onclick="location.href='${pageContext.request.getHeader("Referer")}'">
            </c:if>
            <c:if test="${pageContext.request.getHeader('Referer') eq null}">
                <input type="button" value="取消"
                       onclick="location.href='${pageContext.request.contextPath }/curriculum/findcurriculumlist.su'">
            </c:if>
            <!--            注意onSubmit的写法，千万不要写成：“check()”，这样当检验不能通过的时候也会提交表单。-->
            <input type="submit" name="bt_Register" value="确认删除" onclick="return check() && check_Length()">
        </div>
    </form>
</div>
</body>
</html>