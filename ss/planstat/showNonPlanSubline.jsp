<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<body style="width: 95%">
	<display:table name="sessionScope.nonplansubline" id="currentRowObject"
		pagesize="8">
		<display:column property="patrolname" title="Ѳ����" sortable="true"></display:column>
		<display:column property="linename" title="Ѳ����·" sortable="true"></display:column>
		<display:column property="sublinename" title="Ѳ���߶�" sortable="true"></display:column>
		<display:column property="linetype" title="��·����" sortable="true"></display:column>
	</display:table>
</body>
