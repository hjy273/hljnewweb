<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>
		</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
			
		<style type="text/css">
			.subject{text-align:center;}
		</style>
		
		<script type="text/javascript">
			function getOneForm(id,type,flag) {
				var url = "PollingConMicroAction.do?method=getOneForm&pid="+id+"&type="+type+"&flag="+flag;
				window.location.href=url;
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
		<template:titile value="机房巡检设备信息一览表"/>
		<display:table name="sessionScope.oneTaskList" id="currentRowObject" pagesize="18">
			<%
				
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				if(object != null) {
					num = (String) object.get("numerical");
					id = (String) object.get("pid");
					state = (String) object.get("state");
				}
			 %>
			 <display:column title="序列号" sortable="true" class="subject" headerClass="subject" media="html">
			 	<%if("3".equals(state) || state.equals("4")) { %>
					<a href="javascript:getOneForm('<%=id %>','<bean:write name="type"/>','<bean:write name="flag"/>')"><%=num %></a>
				<%} else { %>
					<%=num %>
				<%} %>
			 </display:column>
			 <display:column property="conname" sortable="true" title="代维公司" headerClass="subject" maxLength="20" class="subject"/>
			 <display:column property="exename" sortable="true" title="执行人" headerClass="subject" maxLength="20" class="subject"/>
			 <display:column property="exetime" sortable="true" title="执行日期" headerClass="subject" maxLength="20" class="subject"/>
			 <display:column property="eaname" sortable="true" title="A端设备名称" headerClass="subject" maxLength="20" class="subject" />
			 <display:column property="ebname" sortable="true" title="B端设备名称" headerClass="subject" maxLength="20" class="subject" />
		</display:table>
		
		<div style="text-align: center; margin-top: 10px;">
			<logic:equal value="1" name="flag">
				<input type="button" value="返回" class="button" onclick="goBackForQuery()">	
			</logic:equal>
			<logic:equal value="2" name="flag">
				<input type="button" value="返回" class="button" onclick="goBackForCon()">	
			</logic:equal>
			<logic:equal value="3" name="flag" scope="request">
				<input type="button" value="返回" class="button" onclick="goBackForRestore()">	
			</logic:equal>
			<logic:equal value="4" name="flag" scope="request">
				<input type="button" value="返回" class="button" onclick="goBackForCheck()">	
			</logic:equal>
		</div>
	</body>
</html>