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
		<template:titile value="���ͳ����Ϣһ����" />
		<display:table name="sessionScope.queryRes" id="currentRowObject" pagesize="18">
			<display:column property="contractorname" sortable="true" title="��ӵ�λ" headerClass="subject" maxLength="10" class="subject"/>
			<display:column property="newcut" sortable="true" title="�½����" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="optimizecut" sortable="true" title="�Ż����" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="changecut" sortable="true" title="Ǩ���Ը��" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="repairecut" sortable="true" title="�޸��Ը��" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="totalnum" sortable="true" title="�������" headerClass="subject" maxLength="10"  class="subject"/>
		</display:table>
		<div style="width: 100%; text-align: center;">
			<input name="action" class="button" value="����ΪExcel"
						onclick="doExportExcel()" type="button" />
		</div>
	</body>
</html>