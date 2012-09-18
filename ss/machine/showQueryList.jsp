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
			getOneForm=function(id,type) {
				var flag = 1;
				var url="${ctx}/PollingTaskAction.do?method=showOneListInfo&id="+id+"&type="+type+"&flag="+flag;
				
				window.location.href=url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<%
			BasicDynaBean object = null;
			String id = null;
			String type = null;
			String num = null;
			String title = null;
		 %>
		<template:titile value="������Ϣһ����"/>
		<display:table name="sessionScope.queryList" requestURI="${ctx}/MobileTaskAction.do?method=doQuery" id="currentRowObject" pagesize="18">
			<%
				object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				if(object != null) {
					id = (String)object.get("tid");
					type = (String)object.get("type");
					num = (String)object.get("numerical");
					title = (String)object.get("tasktitle");
				}
			%>
			<display:column media="html" title="���к�" class="subject" headerClass="subject">
				<a href="javascript:getOneForm('<%=id %>','<%=type %>')"><%=num %></a>
			</display:column>
			<display:column media="html" title="����" style="subject" headerClass="subject">
				<a href="javascript:getOneForm('<%=id %>','<%=type %>')"><%=title %></a>
			</display:column>
			<display:column property="conname" sortable="true" title="��ά��˾" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="exename" sortable="true" title="ִ����" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="exetime" sortable="true" title="ִ������" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="checkname" sortable="true" title="�����" headerClass="subject" maxLength="20" class="subject"/>
		</display:table>
		
	</body>
</html>