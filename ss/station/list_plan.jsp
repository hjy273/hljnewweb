<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
        toGetForm=function(idValue){
			var flag = 3;
        	var url = ""+idValue;
        	self.location.replace(url);
		}
		addGoBack=function(){
			var url = "";
			self.location.replace(url);
		}
		exportList=function(){
			var url="<%=request.getContextPath()%>/station_plan.do?method=exportList";
			self.location.replace(url);
		}
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />
		<%
		BasicDynaBean object=null;
		 %>
		<template:titile value="中继站计划列表" />
		<display:table name="sessionScope.PLAN_LIST" requestURI="${ctx}/plan_patrol_result.do?method=list" id="currentRowObject" pagesize="18">
			<display:column property="begin_date_dis" sortable="true"
				title="开始日期" headerClass="subject" maxLength="10" />
			<display:column property="end_date_dis" sortable="true" title="结束日期"
				headerClass="subject" maxLength="10" />
			<display:column property="regionname" sortable="true" title="所属地州"
				headerClass="subject" maxLength="10" />
			<display:column media="html" title="计划名称" headerClass="subject"
				style="subject" sortable="true">
				<%
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				%>
				<a
					href="<%=request.getContextPath()%>/station_plan.do?method=viewAll&plan_id=<%=(String) object.get("tid")%>"><%=(String) object.get("plan_name")%>
				</a>
			</display:column>
			<display:column media="html" title="计划状态" headerClass="subject"
				style="subject" sortable="true">
				<logic:equal value="01" name="currentRowObject" property="plan_state">
					未提交
				</logic:equal>
				<logic:equal value="02" name="currentRowObject" property="plan_state">
					待审核
				</logic:equal>
				<logic:equal value="03" name="currentRowObject" property="plan_state">
					正在填写
				</logic:equal>
				<logic:equal value="04" name="currentRowObject" property="plan_state">
					审核不通过
				</logic:equal>
				<logic:equal value="05" name="currentRowObject" property="plan_state">
					填写完毕
				</logic:equal>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="导出为Excel"
						onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
