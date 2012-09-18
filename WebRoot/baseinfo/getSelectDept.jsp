<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<!-- ZSH 2004-10-13 -->
<%@page import="com.cabletech.baseinfo.services.RoleManage,com.cabletech.baseinfo.domainobjects.*"%>
<%
  String selectname = request.getParameter("selectname");
  String regionid = request.getParameter("regionid");
  int depOrCont = Integer.parseInt(request.getParameter("depType"));
  UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
  if(!regionid.equals("") || regionid != null){
    regionid = userInfo.getRegionID();
  }
  System.out.println("regionid : " + regionid + "depOrCont : " + depOrCont);
  pageContext.setAttribute("tempCollection", RoleManage.getDeptCollection(regionid, depOrCont));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache">
<title>移动传输线路巡检管理系统</title>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--

function init(){

	parent.selectSpan.innerHTML = selectSpan.innerHTML ;

}

//-->
</script>
<body onload="init()">
<html:form action="/departAction.do">
  <span id="selectSpan">
    <html:select property="<%=selectname%>" styleClass="inputtext" style="width:200">
      <html:options collection="tempCollection" property="value" labelProperty="label"/>
    </html:select>
  </span>
</html:form>
</body>
</html>
