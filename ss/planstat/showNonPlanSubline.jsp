<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<body style="width: 95%">
	<display:table name="sessionScope.nonplansubline" id="currentRowObject"
		pagesize="8">
		<display:column property="patrolname" title="巡检组" sortable="true"></display:column>
		<display:column property="linename" title="巡检线路" sortable="true"></display:column>
		<display:column property="sublinename" title="巡检线段" sortable="true"></display:column>
		<display:column property="linetype" title="线路级别" sortable="true"></display:column>
	</display:table>
</body>
