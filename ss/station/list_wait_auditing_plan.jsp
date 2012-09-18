<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
        toAuditingForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/station_plan.do?method=auditingForm&query_method=listWaitAuditingPlan&plan_id="+idValue;
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
		BasicDynaBean object = null;
		%>
		<template:titile value="������м�վ�ƻ��б�" />
		<display:table name="sessionScope.PLAN_LIST" requestURI="${ctx}/station_plan.do?method=listWaitAuditingPlan" id="currentRowObject"
			pagesize="18">
			<display:column property="begin_date_dis" sortable="true"
				title="��ʼ����" headerClass="subject" maxLength="10" />
			<display:column property="end_date_dis" sortable="true" title="��������"
				headerClass="subject" maxLength="10" />
			<display:column property="regionname" sortable="true" title="��������"
				headerClass="subject" maxLength="10" />
			<display:column media="html" title="�ƻ�����" headerClass="subject"
				style="subject" sortable="true">
				<%
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				%>
				<a
					href="<%=request.getContextPath()%>/station_plan.do?method=view&list_method=listWaitAuditingPlan&plan_id=<%=(String) object.get("tid")%>"><%=(String) object.get("plan_name")%>
				</a>
			</display:column>
			<display:column media="html" title="����" headerClass="subject"
				style="subject">
				<apptag:checkpower thirdmould="23101">
					<logic:equal value="02" name="currentRowObject"
						property="plan_state">
						<a
							href="javascript:toAuditingForm('<%=(String) object.get("tid")%>');">���
						</a>
					</logic:equal>
				</apptag:checkpower>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border:0px">
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="����ΪExcel"
						onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
