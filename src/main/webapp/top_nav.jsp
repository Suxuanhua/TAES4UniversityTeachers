<%@ page pageEncoding="UTF-8" errorPage="/ExceptionPage.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--导航部分-->
<div id="index">
    <div id="title">
        <div id="title-div-ico">
            <li style="margin-top:15px"><a href="#" title="回到首页"><img src="${pageContext.request.contextPath }/img/ico_color.png " width="auto" height="25px"></a></li>
        </div>
        <div id="title-div">
            <ul>
                <li style="width:40%"><a href="${pageContext.request.contextPath }/index.jsp">TAES4University-Teachers</a></li>
                <li >动态
                    <ul >
                        <a href="${pageContext.request.contextPath }/post/findallpost.su"><li >全部动态</li></a>
                        <a href="${pageContext.request.contextPath }/post/findallpost.su"><li >活动情况</li></a>
                        <a href="${pageContext.request.contextPath }/post/findallpost.su"><li >奖惩通知</li></a>
                        <a href="${pageContext.request.contextPath }/post/findallpost.su"><li >论文列表</li></a>
                        <a href="${pageContext.request.contextPath }/post/findallpost.su"><li >出版专著</li></a>
                    </ul>
                </li>
                <li >作品展示
                    <ul >
                        <a href="#"><li >毕业设计</li></a>
                        <a href="#"><li >获奖信息</li></a>
                        <a href="#"><li >最新研究</li></a>
                        <a href="#"><li >教学成果</li></a>
                    </ul>
                </li>
                <li >
                    <c:if test="${admin ==null }"><a href="${pageContext.request.contextPath }/main/adminlogin.su">登录</a></c:if>
                    <c:if test="${admin !=null }">${admin.adminName }
                        <ul >
                            <a href="${pageContext.request.contextPath }/main/managecenter.su"><li >管理中心</li></a>
                            <c:if test="${admin.adminPermissions ne 'general' }">
                                <a href="${pageContext.request.contextPath }/main/posteditor.su"><li >发布信息</li></a>
                                <a href="#"><li >系统设置</li></a>
                            </c:if>
                            <a href="${pageContext.request.contextPath }/admin/adminlogout.su"><li >用户退出</li></a>
                        </ul>
                    </c:if>
                </li>
            </ul>
        </div>
        <c:if test="${admin !=null }">
            <div id="title_search">
                <ui>
                    <form target="_blank" method="post" action="${pageContext.request.contextPath }/utils/findall.su">
                        <li><input id="title_search_in" spellcheck="false" type="search" name="searchWord" placeholder="人名&nbsp;职称&nbsp;课程&nbsp;ID"></li>
                        <li><input id="title_search_bt" type="submit" value=""></li>
                    </form>
                </ui>
            </div>
        </c:if>
        <div class="top_nav_notice">
            <c:if test="${message !=null}">
                <span>通知：${message }</span>
                <% session.setAttribute ("message", null);
                    session.removeAttribute ("message");
                %>
            </c:if>
        </div>
    </div>
</div>
