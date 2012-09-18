<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			 toViewForm=function(idValue,isread){
	            	window.location.href = "${ctx}/testYearPlanAction.do?method=viewYearPlanForm&planid="+idValue+"&query=no&isread="+isread;
			 }
			 
			 toEditForm=function(idValue){
	            	window.location.href = "${ctx}/testYearPlanAction.do?method=editYearPlanForm&planid="+idValue;
			 }
			 
			  toApproveForm=function(idValue,act){
	            	window.location.href = "${ctx}/testYearPlanAction.do?method=approverPlanForm&planid="+idValue+"&act="+act;
			 }
			 
			  toDelete=function(idValue){
			       if(confirm("ȷ��Ҫɾ����?")){
	            		window.location.href = "${ctx}/testYearPlanAction.do?method=deleteYearPlan&planid="+idValue;
	            	}
			 }
			
			function  goBack(){
				var url="${ctx}/expensesAction.do?method=addCableTypePrice";
				self.location.replace(url);
			}
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />

		<template:titile value="������ƻ�һ����" />
		<display:table name="sessionScope.yearplans" id="currentRowObject" pagesize="15">
      		<display:column property="contractorname" title="��ά��λ" headerClass="subject"  sortable="true"/>	
			<display:column property="plan_name" title="�ƻ�����" headerClass="subject"  sortable="true"/>	
			<display:column property="year" sortable="true" title="�ƻ����" headerClass="subject" />
			<display:column property="create_time" sortable="true" title="����ʱ��" headerClass="subject" />
			 <display:column media="html" title="����" >
			 <bean:define id="tid" name="currentRowObject" property="id"></bean:define>
			 <bean:define id="read" name="currentRowObject" property="isread"></bean:define>
			  <a href="javascript:toViewForm('<%=tid%>','<%=read%>')">�鿴</a>
			 <logic:equal value="1" property="deptype" name="LOGIN_USER">
			 	<logic:equal value="false" property="isread" name="currentRowObject">
					 <a href="javascript:toApproveForm('<%=tid%>','approve')">|���</a>
					  <a href="javascript:toApproveForm('<%=tid%>','transfer')">|ת��</a>
			 	</logic:equal>
			 </logic:equal>
			  <logic:equal value="2" property="deptype" name="LOGIN_USER">
				 <a href="javascript:toEditForm('<%=tid%>')">|�޸�</a>
				 <a href="javascript:toDelete('<%=tid%>')">|ɾ��</a>
			 </logic:equal>
            </display:column>
		</display:table>
	</body>
</html>
