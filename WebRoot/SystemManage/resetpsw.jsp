<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script type="text/javascript">
	function resetPsw(userid){
		if(confirm("确定重设”"+userid+"“用户密码吗？")){
        	var url = "${ctx}/userinfoAction.do?method=resetPsw&userid=" + userid;
        	self.location.replace(url);
   		}
	}
</script>
<body>
<template:titile value="重设用户密码"/>
<display:table name="sessionScope.QUERYRESULT"  id="currentRowObject"   pagesize="18" >
	<display:column property="username" title="用户名"/>
	<display:column property="userid" title="用户ID"/>
	<display:column property="regionname" title="区域"/>
	<display:column media="html" title="操作">
	<% 
		BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String userid = (String) object.get("userid");
	%>
		<a href="javascript:resetPsw('<%=userid%>')">重设密码</a>
	</display:column>
</display:table>
</body>
