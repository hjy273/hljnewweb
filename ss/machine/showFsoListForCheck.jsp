<%@include file="/common/header.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />

		<style type="text/css">
			.subject{text-align:center;}
		</style>

		<script type="text/javascript">
			function getOneForm(id,type,tid) {
				var url = "PollingConFsoAction.do?method=getOneAllinfo&pid="+id+"&type="+type+"&tid="+tid;
				window.location.href=url;
			}
		
			function goBackForQuery() {
				var url = "MobileTaskAction.do?method=doQueryForCheck";
				window.location.href=url;
			}
		</script>
	</head>

	<body>
		<br>
		<%
			BasicDynaBean object = null;
			String num = null;
			String id = null;
			String state = null;
		%>
		<template:titile value="����Ѳ���豸��Ϣһ����" />

		<display:table name="sessionScope.oneTaskList" id="currentRowObject"
			pagesize="18">
			<%
							object = (BasicDynaBean) pageContext
							.findAttribute("currentRowObject");
					if (object != null) {
						num = (String) object.get("numerical");
						id = (String) object.get("pid");
						state = (String) object.get("state");
					}
			%>
			<display:column title="���к�" sortable="true" class="subject"
				headerClass="subject" media="html">
					<a href="javascript:getOneForm('<%=id%>','<bean:write name="type"/>','<bean:write name="tid"/>')"><%=id%></a>
			</display:column>
			<display:column property="conname" sortable="true" title="��ά��˾"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="exename" sortable="true" title="ִ����"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="exetime" sortable="true" title="ִ������"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="eaname" sortable="true" title="A���豸����"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="ebname" sortable="true" title="B���豸����"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="equipment_model" sortable="true"
				title="�豸�ͺ�" headerClass="subject" maxLength="20"
				class="subject" />
			<display:column property="machine_no" sortable="true" title="�����"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="power_type" sortable="true" title="�豸/��Դ����"
				headerClass="subject" maxLength="20" class="subject" />
		</display:table>


		<div style="text-align: center; margin-top: 10px;">
				<input type="button" value="����" class="button"
					onclick="goBackForQuery()">
		</div>
	</body>
</html>
