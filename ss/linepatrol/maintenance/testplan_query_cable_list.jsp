<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			function toViewForm(id){
				window.location.href="${ctx}/testApproveAction.do?method=viewCableDataById&id="+id;
			}
		
	       	function  goBack(){
				var url="${ctx}/testPlanQueryStatAction.do?method=queryTestPlanForm";
				self.location.replace(url);
			}
		</script>
		<title>����һ����</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		 <bean:size id="size" name="cables" />
		<template:titile value="������Ϣһ����  (<font color='red'>��${size}��</font>)" />
		<display:table name="sessionScope.cables" id="object" pagesize="13">
			<logic:notEmpty name="object">
				<bean:define id="pid" name="object" property="id"></bean:define>
			</logic:notEmpty>
			<display:column property="contractorname" sortable="true" title="��ά��λ" headerClass="subject" />
			<display:column property="lable" sortable="true" title="���¼��� " headerClass="subject" />
			 <display:column media="html" title="�������� " >
			   <a href="javascript:toViewForm('${pid}')"><bean:write name="object" property="segmentname"/></a>
			 </display:column>
			<display:column property="fact_test_time" sortable="true" title="����ʱ��" headerClass="subject"/>
			 <display:column media="html" title="����" >
				<a href="javascript:toViewForm('${pid}')">�鿴</a>
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="����"
						onclick="goBack();" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
