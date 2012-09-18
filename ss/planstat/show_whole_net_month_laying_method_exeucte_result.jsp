<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />

<script language="javascript" type="text/javascript">
	
</script>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<div class='title2' style='font-size: 14px; font-weight: 600; bottom: 1'
	align='center'>
	全网<bean:write name="CMMonthlyStatConBean" property="endYear" />
	年<bean:write name="CMMonthlyStatConBean" property="endMonth" />月不同级别统计详细信息
</div>
<hr width='100%' size='1'>
<br>
<display:table name="month_laying_method_exeucte_result_list"
	id="currentRowObject">
	<display:column property="name" title="线路级别"  />
	<display:column property="planpoint" title="计划巡检点次" sortable="true" />
	<display:column property="factpoint" title="实际巡检点次" sortable="true" />
	<display:column property="patrolp" title="巡检率(%)" sortable="true" />
	<display:column property="sublinekm" title="计划路由长度（km）" sortable="true" />
	<display:column property="patrolkm" title="巡回路由长度（km）" sortable="true" />
</display:table>
