<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script type="text/javascript">
	function resetPsw(userid){
		if(confirm("ȷ�����衱"+userid+"���û�������")){
        	var url = "${ctx}/userinfoAction.do?method=resetPsw&userid=" + userid;
        	self.location.replace(url);
   		}
	}
</script>
<body>
<template:titile value="�����û�����"/>
<display:table name="sessionScope.QUERYRESULT"  id="currentRowObject"   pagesize="18" >
	<display:column property="username" title="�û���"/>
	<display:column property="userid" title="�û�ID"/>
	<display:column property="regionname" title="����"/>
	<display:column media="html" title="����">
	<% 
		BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String userid = (String) object.get("userid");
	%>
		<a href="javascript:resetPsw('<%=userid%>')">��������</a>
	</display:column>
</display:table>
</body>
