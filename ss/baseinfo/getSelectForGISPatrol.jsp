<%@include file="/common/header.jsp"%>
<!-- ZSH 2004-10-13 -->
<%@page import="com.cabletech.baseinfo.services.RoleManage"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>


<%
  String apppath = SmsConInfo.newInstance().getWholePath();
  String commonSmsPath = apppath + SmsConInfo.newInstance().getCommonSmsPath();

  UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
  String regionid = userinfo.getRegionID();

  pageContext.setAttribute("tempCollection", RoleManage.getPatrlAvailableList(regionid));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache">
<title>移动传输线路巡检管理系统</title>
<script language="javascript" src="${ctx}/realtime/realtime.js" type=""></script>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">

<body>
<template:titile value="定位调度信息发送"/>
<br>
<br>
<html:form action="/terminalAction.do">
  <span id="selectSpan">
    <html:select property="simNumber" styleClass="inputtext" style="width:160px">
      <html:options collection="tempCollection" property="value" labelProperty="label"/>
    </html:select>
  </span>
</html:form>

<template:formSubmit>
  <td>
    <input type="button" name="action" value="发送短信" class="lbutton" onclick="toCommonSMS('<%=commonSmsPath%>')">
  </td>
</template:formSubmit>

</body>
</html>
