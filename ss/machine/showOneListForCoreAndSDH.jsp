<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
			.subject{text-align:center;}
		</style>
		<script type="text/javascript">
			function getOneForm(id,type,flag) {
			
				var url = "PollingContentAction.do?method=getOneForm&id="+id+"&type="+type+"&flag="+flag;
				
				window.location.href = url;
			}
			
			function goBackForQuery() {
				var url = "MobileTaskAction.do?method=doQuery";
				window.location.href=url;
			}
			
			function goBackForCon() {
				var url = "MobileTaskAction.do?method=getTaskForCon";
				window.location.href=url;
			}
			
			function goBackForRestore() {
				var url = "MobileTaskAction.do?method=showTaskForRestore";
				window.location.href=url;
			}
			
			function goBackForCheck() {
				var url = "MobileTaskAction.do?method=showTaskForCheck";
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
		<template:titile value="����Ѳ���豸��Ϣһ����"/>
		<display:table name="sessionScope.oneTaskList" id="currentRowObject" pagesize="18">
			<%
				object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				if(object != null) {
					id = (String) object.get("pid");
					num = (String) object.get("numerical");
					state = (String) object.get("state");
				}
			 %>
			<display:column media="html" sortable="true"  title="���к�">
				<%if(state.equals("3") || state.equals("4")) { %>
					<a href="javascript:getOneForm('<%=id %>','<bean:write name="type"/>','<bean:write name="flag"/>')"><%=num %></a>
				<%} else {%>
					<%=num %>
				<%} %>
			</display:column>
			<display:column property="conname" sortable="true" title="��ά��˾" headerClass="subject" maxLength="20" />
			<display:column property="exename" sortable="true" title="ִ����" headerClass="subject" maxLength="20" />
			<display:column property="exetime" sortable="true" title="ִ������" headerClass="subject" maxLength="20" />
			<display:column property="ename" sortable="true" title="�豸����" headerClass="subject" maxLength="20"  />
		</display:table>
		
		<div style="text-align: center; margin-top: 10px;">
			<logic:equal value="1" name="flag" scope="request">
				<input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">	
			</logic:equal>
			<logic:equal value="2" name="flag" scope="request">
				<input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">	
			</logic:equal>
			<logic:equal value="3" name="flag" scope="request">
				<input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">	
			</logic:equal>
			<logic:equal value="4" name="flag" scope="request">
				<input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">	
			</logic:equal>
		</div>
	</body>
</html>

