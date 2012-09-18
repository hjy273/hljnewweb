<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.linecut.beans.LineCutBean"%>
<html>
	<head>
		<title>showQueryRes</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
			.subject{text-align:center;}
		</style>
		<script type="text/javascript">
			doExportExcel=function() {
				var url="${ctx}/LineCutReAction.do?method=exportTimeByLevel";
				self.location.replace(url);
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="割接统计信息一览表(单位：分钟)" />
		<display:table name="sessionScope.queryRes" id="currentRowObject" pagesize="18">
			<display:column property="contractorname" sortable="true" title="割接单位" headerClass="subject" maxLength="10" class="subject"/>
			<display:column property="one" sortable="true" title="一干" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="two" sortable="true" title="二干" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="three" sortable="true" title="汇聚环" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="four" sortable="true" title="接入网" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="five" sortable="true" title="骨干层" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="totalnum" sortable="true" title="割接总耗时" headerClass="subject" maxLength="10"  class="subject"/>
		</display:table>
		<div style="width: 100%; text-align: center;">
			<input name="action" class="button" value="导出为Excel"
						onclick="doExportExcel()" type="button" />
		</div>
	</body>
</html>