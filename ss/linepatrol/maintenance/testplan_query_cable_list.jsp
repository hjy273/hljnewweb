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
		<title>光缆一览表</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		 <bean:size id="size" name="cables" />
		<template:titile value="光缆信息一览表  (<font color='red'>共${size}条</font>)" />
		<display:table name="sessionScope.cables" id="object" pagesize="13">
			<logic:notEmpty name="object">
				<bean:define id="pid" name="object" property="id"></bean:define>
			</logic:notEmpty>
			<display:column property="contractorname" sortable="true" title="代维单位" headerClass="subject" />
			<display:column property="lable" sortable="true" title="光缆级别 " headerClass="subject" />
			 <display:column media="html" title="光缆名称 " >
			   <a href="javascript:toViewForm('${pid}')"><bean:write name="object" property="segmentname"/></a>
			 </display:column>
			<display:column property="fact_test_time" sortable="true" title="测试时间" headerClass="subject"/>
			 <display:column media="html" title="操作" >
				<a href="javascript:toViewForm('${pid}')">查看</a>
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="返回"
						onclick="goBack();" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
