<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.linecut.beans.LineCutBean"%>
<html>
	<head>
		<title>showQueryRes</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
			.subject{text-align:center;}
		</style>
		<script type="text/javascript">
			doExportExcel=function() {
				var url="${ctx}/LineCutReAction.do?method=exportCountByLevel";
				self.location.replace(url);
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="���ͳ����Ϣһ����" />
		<display:table name="sessionScope.queryRes" id="currentRowObject" pagesize="18">
			<display:column property="contractorname" sortable="true" title="��ӵ�λ" headerClass="subject" maxLength="10" class="subject"/>
			<display:column property="one" sortable="true" title="һ��" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="two" sortable="true" title="����" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="three" sortable="true" title="��ۻ�" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="four" sortable="true" title="������" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="five" sortable="true" title="�Ǹɲ�" headerClass="subject" maxLength="10"  class="subject"/>
			<display:column property="totalnum" sortable="true" title="�������" headerClass="subject" maxLength="10"  class="subject"/>
		</display:table>
		<div style="width: 100%; text-align: center;">
			<%--<%
	  			String graphURL = (String)request.getAttribute("graphURL");
	  			String filename = (String)request.getAttribute("filename");
	  		 %>
  			<img src="<%=graphURL %>" width=500 height=300 border=0  usemap="#<%=filename %>">
			--%>
			<input name="action" class="button" value="����ΪExcel"
						onclick="doExportExcel()" type="button" />
		</div>
	</body>
</html>