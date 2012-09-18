<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<!-- ZSH 2004-10-13 -->
<%@page import="com.cabletech.baseinfo.services.RoleManage"%>
<%
  String selectname = request.getParameter("selectname");
  String regionid = request.getParameter("regionid");
  int depOrCont = Integer.parseInt(request.getParameter("depType"));
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
<html:form action="/contractorAction.do">
  <span id="selectSpan">
    <html:select property="<%=selectname%>" styleClass="inputtext" style="width:160">
      <html:option value=" ">无</html:option>
      <html:options collection="tempCollection" property="value" labelProperty="label"/>
    </html:select>
  </span>
</html:form>
</body>
</html>
