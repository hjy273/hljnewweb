<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
		type="text/css" media="screen, print" />
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		<bean:write name="queryCon" property="conName" />
		公司
		<bean:write name="queryCon" property="endYear" />
		年不同线路级别统计详细信息
	</div>
	<hr width='100%' size='1'>
	<br>
	<display:table name="contractor_year_line_level_execute_result_list"
		id="currentRowObject">
		<display:column property="name" title="线路级别" sortable="true" />
		<display:column property="planpoint" title="计划巡检点次" sortable="true" />
		<display:column property="factpoint" title="实际巡检点次" sortable="true" />
		<display:column property="patrolp" title="巡检率(%)" sortable="true" />
		<display:column property="sublinekm" title="计划路由长度（km）"
			sortable="true" />
		<display:column property="patrolkm" title="巡回路由长度（km）" sortable="true" />
	</display:table>
</body>
