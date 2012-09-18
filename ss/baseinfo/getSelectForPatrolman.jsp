<%@include file="/common/header.jsp"%>
<!-- ZSH 2004-10-13 -->
<%@page import="org.apache.struts.util.*,com.cabletech.baseinfo.services.RoleManage,com.cabletech.baseinfo.domainobjects.*"%>
<%
  String selectname = request.getParameter("selectname");
  String contractorid = request.getParameter("contractorid");
  System.out.println(selectname+" ++ "+contractorid);
  pageContext.setAttribute("tempCollection", RoleManage.getDeptPatrolmanCollection(contractorid));
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
<html:form action="/AlertreceiverListAction.do">
  <span id="selectSpan">
    <html:select property="<%=selectname%>" styleClass="inputtext" style="width:160px" onchange="getUser()">
      <html:options collection="tempCollection" property="value" labelProperty="label"/>
    </html:select>
  </span>
</html:form>
</body>
</html>
