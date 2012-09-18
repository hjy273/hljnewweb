<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		toWriteForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/plan_patrol_result.do?method=viewWaitWrite&plan_id="+idValue;
		    self.location.replace(url);
		}
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
		<template:titile value="填写中继站计划执行信息" />
		<display:table name="sessionScope.PLAN_LIST" id="currentRowObject" pagesize="18">
			<display:column property="begin_date_dis" sortable="true"
				title="开始日期" headerClass="subject" maxLength="10" />
			<display:column property="end_date_dis" sortable="true" title="结束日期"
				headerClass="subject" maxLength="10" />
			<display:column property="regionname" sortable="true" title="所属地州"
				headerClass="subject" maxLength="10" />
			<display:column media="html" title="计划名称" headerClass="subject"
				class="subject" sortable="true">
				<%
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				%>
				<a
					href="<%=request.getContextPath()%>/station_plan.do?method=view&list_method=listNotFinishedPlan&plan_id=<%=(String) object.get("tid")%>"><%=(String) object.get("plan_name")%>
				</a>
			</display:column>
			<display:column media="html" title="操作" headerClass="subject"
				class="subject">
				<apptag:checkpower thirdmould="23201">
					<logic:equal value="03" name="currentRowObject"
						property="plan_state">
						<a
							href="javascript:toWriteForm('<%=(String) object.get("tid")%>');">填写
						</a>
					</logic:equal>
				</apptag:checkpower>
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
