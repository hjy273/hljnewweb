<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<display:table name="sessionScope.sublineNeverStart"
		id="currentRowObject" pagesize="10">
		<display:column property="sublinename" title="Ѳ���߶�����" sortable="true" />
		<display:column property="linetype" title="������·����" sortable="true" />
		<display:column property="planpointtimes" title="ӦѲ����"
			sortable="true" />
	</display:table>
</body>