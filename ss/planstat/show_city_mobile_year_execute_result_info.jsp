<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		${CMMonthlyStatConBean.regionName }各代维公司${CMMonthlyStatConBean.endYear
		}年计划执行情况
	</div>
	<hr width='100%' size='1'>
	<display:table
		name="sessionScope.CITY_MOBILE_YEAR_STAT_CONTRACTOR_PATROL_LIST"
		id="currentRowObject" pagesize="12">
		<display:column property="contractorname" title="代维公司" sortable="true" />
		<display:column property="planpoint" title="计划巡检点次" sortable="true" />
		<display:column property="factpoint" title="实际巡检点次" sortable="true" />
		<display:column property="patrolp" title="巡检率(%)" sortable="true" />
		<display:column property="sublinekm" title="计划路由长度（km）"
			sortable="true" />
		<display:column property="patrolkm" title="巡回路由长度（km）" sortable="true" />
		<display:column property="plannum" title="制定计划数量" sortable="true" />
		<display:column property="noexecuteplannum" title="未执行计划数量"
			sortable="true" />
		<display:column property="nocompleteplannum" title="未完成计划数量"
			sortable="true" />
		<display:column property="completeplannum" title="完成计划数量"
			sortable="true" />
		<display:column media="html" title="考核结果">
			<bean:define id="examineResult" name="currentRowObject"
				property="examineresult" />
			<img src="${ctx}/images/${examineResult }.jpg" height="10" width="50" />
		</display:column>
	</display:table>
</body>