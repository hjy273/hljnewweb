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
				var url="${ctx}/LineCutReAction.do?method=exportCountByType";
				self.location.replace(url);
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="割接统计信息一览表" />
		<display:table name="sessionScope.queryRes" id="currentRowObject" pagesize="18">
			<display:column property="contractorname" sortable="true" title="割接单位" headerClass="subject" maxLength="10" class="subject"/>
			<display:column property="newcut" sortable="true" title="新建割接" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="optimizecut" sortable="true" title="优化割接" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="changecut" sortable="true" title="迁改性割接" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="repairecut" sortable="true" title="修复性割接" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="totalnum" sortable="true" title="割接总数" headerClass="subject" maxLength="10"  class="subject"/>
		</display:table>
		<div style="width: 100%; text-align: center;">
			<input name="action" class="button" value="导出为Excel"
						onclick="doExportExcel()" type="button" />
		</div>
	</body>
</html>