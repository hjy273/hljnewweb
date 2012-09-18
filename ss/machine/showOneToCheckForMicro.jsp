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
			function addCheck(pid,tid,type) {
				var url = "PollingConMicroAction.do?method=checkAEqu&pid="+pid+"&tid="+tid+"&type="+type;
				window.location.href=url;
			}
			
			function goBack() {
				var url = "MobileTaskAction.do?method=showTaskForCheck";
				window.location.href = url;
			}
			
			function getOneForm(id, type) {
				
			}
		</script>
	</head>
	
	<body>
		<%
			String num = null;
			BasicDynaBean object = null;
			String pid = null;
			String state = null;
		 %>
		<template:titile value="机房巡检设备核查一览表"/>
		<display:table name="sessionScope.oneTaskList" id="currentRowObject" pagesize="18">
			<%
				object = (BasicDynaBean) pageContext.getAttribute("currentRowObject");
				if(object != null) {
					num = (String)object.get("numerical");
					pid = (String)object.get("pid");
					state = (String)object.get("state");
				}
			 %>
			 <display:column property="numerical" sortable="true" class="subject" title="序列号" headerClass="subject">
			 	<%if("1".equals(state)) {%>
					<a href="javascript:getOneForm('<%=pid %>','<bean:write name="type" />')"><%=num %></a>
				<%} else {%>
					<%=num %>
				<%} %>
			 </display:column>
			  <display:column property="conname" sortable="true" title="代维公司" headerClass="subject" maxLength="20" class="subject"/>
			 <display:column property="exename" sortable="true" title="执行人" headerClass="subject" maxLength="20" class="subject"/>
			 <display:column property="exetime" sortable="true" title="执行日期" headerClass="subject" maxLength="20" class="subject"/>
			 <display:column property="eaname" sortable="true" title="A端设备名称" headerClass="subject" maxLength="20" class="subject" />
			 <display:column property="ebname" sortable="true" title="B端设备名称" headerClass="subject" maxLength="20" class="subject" />
			 <display:column media="html" class="subject" title="操作" headerClass="subject" >
				<a href="javascript:addCheck('<%=pid %>','<bean:write name="tid"/>','<bean:write name="type"/>');">添加核查内容</a>
			</display:column>
		</display:table>
		
		<div style="text-align: center; margin-top: 10px;">
			<input type="button" value="返回" class="button" onclick="goBack()">	
		</div>
	</body>
</html>