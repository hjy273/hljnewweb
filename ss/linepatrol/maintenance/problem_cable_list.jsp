<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			handelProblemForm=function(id,act){
			window.location.href="${ctx}/problemCableAction.do?method=handleProblemForm&pid="+id+"&act="+act;
			//self.location.replace(url);
		}
				
				
		exportList=function(){
			var url="${ctx}/testPlanQueryStatAction.do?method=exportStatTestPlans";
			self.location.replace(url);
		}
				
		</script>
		<title>�������һ����</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<%	int i = 1; %>
		 <bean:size id="size"  name="problems" />
		<template:titile value="�������һ����  (<font color='red'>��${size}��</font>)" />
		<display:table name="sessionScope.problems" id="currentRowObject" pagesize="15">
	    	<logic:notEmpty name="problems">
			    <bean:define id="pid" name="currentRowObject" property="id"></bean:define>
				<display:column value="<%=i%>" title="���" style="5%"></display:column>
				<display:column media="html" title="�ƻ�����" >
					<a href="javascript:handelProblemForm('<%=pid%>','view');"><bean:write name="currentRowObject" property="test_plan_name"/></a>
	            </display:column>
				<display:column property="segmentname" sortable="true" title="�м̶�����" headerClass="subject" maxLength="25"/>
				<display:column property="problem_description" sortable="true" title="��������" headerClass="subject" maxLength="25"/>
				<display:column property="tester" sortable="true" title="������" headerClass="subject" maxLength="25"/>
				<display:column property="state" sortable="true" title="״̬" headerClass="subject" />
				<display:column media="html" title="����" >
					<% i++;	%>
					<logic:equal value="2" name="LOGIN_USER" property="deptype">
		            	<a href="javascript:handelProblemForm('<%=pid%>','')">�����¼</a>
		            </logic:equal>
	            </display:column>
           </logic:notEmpty>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="ffff">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
		</table>
	</body>
</html>
