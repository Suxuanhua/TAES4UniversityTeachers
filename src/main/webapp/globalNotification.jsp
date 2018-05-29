<%--
  Created by IntelliJ IDEA.
  User: XuanhuaSu
  Date: 2018/4/23
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String notice = (String) session.getAttribute ("notice");
    if (notice != null && !notice.equals ("null") && !notice.equals ("")) {
        out.println ("<script type=\"text/javascript\">alert(\" " + notice + "\");</script>");
        session.setAttribute ("notice", null);
        session.removeAttribute ("notice");
    }
%>