<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 toViewForm=function(idValue){
            window.location.href = "${ctx}/testPlanAction.do?method=viewPaln&planId="+idValue;
		}
		
		 toExamForm=function(idValue){
            window.location.href = "${ctx}/testPlanExamAction.do?method=examForm&planid="+idValue;
		}
		</script>
		<title>���Լƻ�һ����</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />
		<%
			BasicDynaBean object=null;
			Object id=null;		
		 %>
		 <bean:size id="size"  name="exams" />
		<template:titile value="�����˲��Լƻ�һ����  (<font color='red'>${size}������</font>)" />
		<display:table name="sessionScope.exams" id="currentRowObject" pagesize="15">
			<display:column property="contractorname" sortable="true" title="��ά��λ" headerClass="subject" />
			<display:column property="test_plan_name" sortable="true" title="���Լƻ�����" headerClass="subject" />
			<display:column property="plantime" sortable="true" title="���Լƻ�ʱ��" headerClass="subject" />
			<display:column property="plantype" sortable="true" title="��������" headerClass="subject"/>
			<display:column property="state" sortable="true" title="״̬" headerClass="subject" />
			 <display:column media="html" title="����" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	           		 if(object != null) {
		      	 		id = object.get("id");
				} %>
				
				<a href="javascript:toViewForm('<%=id%>')">�鿴</a>
	            |<a href="javascript:toExamForm('<%=id%>')">����</a>
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="exams11">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
		</table>
	</body>
</html>
