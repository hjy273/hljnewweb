<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			 toEditTypForm=function(idValue){
	            	window.location.href = "${ctx}/expensesFactorAction.do?method=editFactorForm&tid="+idValue;
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

		<template:titile value="ȡ��ϵ��һ����" />
		<display:table name="sessionScope.list" id="currentRowObject" pagesize="15">
      		<display:column property="contractorname" title="��ά��λ" headerClass="subject"  sortable="true"/>	
			<display:column property="explain" title="˵��" headerClass="subject"  sortable="true"/>	
			<display:column property="cablelen" sortable="true" title="���³���" headerClass="subject" />
			<display:column property="factor" title="ϵ��" headerClass="subject"  sortable="true"/>	
			 <display:column media="html" title="����" >
			 <logic:equal value="1" property="deptype" name="LOGIN_USER">
				 <bean:define id="tid" name="currentRowObject" property="id"></bean:define>
				 <a href="javascript:toEditTypForm('<%=tid%>')">�޸�</a>
			 </logic:equal>
            </display:column>
		</display:table>
	</body>
</html>
