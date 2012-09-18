<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		exportList=function(){
			var url="${ctx}/materialYearStatAction.do?method=exportYearStatList";
			self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/materialYearStatAction.do?method=materialStateForm";
			self.location.replace(url);
		}
		</script>
		<title>材料类型</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />		
		<template:titile value="材料年统计一览表" />
		<display:table name="sessionScope.mtstatyear" id="currentRowObject" pagesize="18">
			<display:column property="modelname" title="材料类型" headerClass="subject"  sortable="true"/>	
			<display:column property="typename" sortable="true" title="材料规格" headerClass="subject" maxLength="10"/>
			<display:column property="unit" sortable="true" title="计量单位" headerClass="subject" maxLength="10"/>
			<display:column value="0" title="计划规模" headerClass="subject" maxLength="10"/>
			<display:column property="use_number" sortable="true" title="累计使用数量" headerClass="subject" maxLength="10"/>	
			<display:column property="jan" sortable="true" title="1月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="feb" sortable="true" title="2月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="mar" sortable="true" title="3月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="apr" sortable="true" title="4月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="may" sortable="true" title="5月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="june" sortable="true" title="6月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="july" sortable="true" title="7月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="aug" sortable="true" title="8月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="sep" sortable="true" title="9月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="oct" sortable="true" title="10月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="nov" sortable="true" title="11月份" headerClass="subject" maxLength="10"/>	   
			<display:column property="dece" sortable="true" title="12月份" headerClass="subject" maxLength="10"/>	   
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="mtstatyear">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
				<!-- <input name="action" class="button" value="返回"
						onclick="goBack();" type="button" />
						-->
						<input type="button" class="button" onclick="history.back()" value="返回"/>
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
