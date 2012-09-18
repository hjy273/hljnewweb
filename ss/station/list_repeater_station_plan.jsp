<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
        toDeleteForm=function(idValue){
       	 	if(confirm("你确定要执行此次删除操作吗?")){
            	var url = "<%=request.getContextPath()%>/station_plan.do?method=del&plan_id="+idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		toUpdateForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/station_plan.do?method=modForm&plan_id="+idValue;
		    self.location.replace(url);
		}
		toCopyForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/station_plan.do?method=copyForm&plan_id="+idValue;
		    self.location.replace(url);
		}
		toAuditingForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/station_plan.do?method=auditingForm&query_method=list&plan_id="+idValue;
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
		<template:titile value="中继站计划列表" />
		<display:table name="sessionScope.PLAN_LIST" id="currentRowObject"
			pagesize="18">
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
					href="<%=request.getContextPath()%>/station_plan.do?method=view&plan_id=<%=(String) object.get("tid")%>"><%=(String) object.get("plan_name")%>
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
			<display:column media="html" title="操作" headerClass="subject"
				style="subject">
				<apptag:checkpower thirdmould="23002">
					<logic:equal value="01" name="currentRowObject"
						property="plan_state">
						<a
							href="javascript:toUpdateForm('<%=(String) object.get("tid")%>');">修改
						</a>
					</logic:equal>
					<logic:equal value="04" name="currentRowObject"
						property="plan_state">
						<a
							href="javascript:toUpdateForm('<%=(String) object.get("tid")%>');">修改
						</a>
					</logic:equal>
				</apptag:checkpower>
				<apptag:checkpower thirdmould="23003">
					<logic:equal value="01" name="currentRowObject"
						property="plan_state">
						<a
							href="javascript:toDeleteForm('<%=(String) object.get("tid")%>');">删除
						</a>
					</logic:equal>
				</apptag:checkpower>
				<apptag:checkpower thirdmould="23001">
					<a href="javascript:toCopyForm('<%=(String) object.get("tid")%>');">复制
					</a>
				</apptag:checkpower>
				<apptag:checkpower thirdmould="23101">
					<logic:equal value="02" name="currentRowObject"
						property="plan_state">
						<a
							href="javascript:toAuditingForm('<%=(String) object.get("tid")%>');">审核
						</a>
					</logic:equal>
				</apptag:checkpower>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border:0px">
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="导出为Excel"
						onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
