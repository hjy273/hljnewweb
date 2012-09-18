<%@include file="/common/header.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />

		<style type="text/css">
			.subject{text-align:center;}
		</style>

		<script type="text/javascript">
			function goBack() {
				var url = "MobileTaskAction.do?method=showTaskForCheck";
				window.location.href = url;
			}
			
			function addCheck(pid,tid,type) {
				var url = "PollingConFsoAction.do?method=checkAEqu&pid="+pid+"&tid="+tid+"&type="+type;
				window.location.href=url;
			}
		</script>
	</head>

	<body>
		<br>
		<%
			String num = null;
			BasicDynaBean object = null;
			String pid = null;
			String state = null;
		 %>
		<template:titile value="����Ѳ���豸�˲�һ����" />

		<display:table name="sessionScope.oneTaskList" id="currentRowObject"
			pagesize="18">
			<%
				object = (BasicDynaBean) pageContext.getAttribute("currentRowObject");
				if(object != null) {
					num = (String)object.get("numerical");
					pid = (String)object.get("pid");
					state = (String)object.get("state");
				}
			 %>
			 <display:column media="html" sortable="true" class="subject" title="���к�" headerClass="subject" >
				<%if("1".equals(state)) {%>
					<a href="javascript:getOneForm('<%=pid %>','<bean:write name="type" />')"><%=num %></a>
				<%} else {%>
					<%=num %>
				<%} %>
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
			<display:column media="html" class="subject" title="����" headerClass="subject" >
				<a href="javascript:addCheck('<%=pid %>','<bean:write name="tid"/>','<bean:write name="type"/>');">��Ӻ˲�����</a>
			</display:column>
			
		</display:table>

		<div style="text-align: center; margin-top: 10px;">
			<input type="button" value="����" class="button" onclick="goBack()">
		</div>
	</body>
</html>
