<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>演练方案变更审核</title>
		<script type="text/javascript">
			function checkForm(){
				if(getTrimValue("operator")=="transfer"){
					if(getTrimValue("approvers").length==0){
						alert("转审人不能为空！");
						return false;
					}
					processBar(submitForm1);
					return true;
				}
				processBar(submitForm1);
				return true;
			}
			function getTrimValue(value){
				return document.getElementById(value).value.trim();
			}
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g,"");
			}
		</script>
	</head>
	<body>
		<c:if test="${operator=='approve'}">
			<template:titile value="演练方案变更审核"/>
		</c:if>
		<c:if test="${operator=='transfer'}">
			<template:titile value="演练方案变更转审"/>
		</c:if>
		<html:form action="/drillPlanModifyAction.do?method=approveDrillPlanModify" onsubmit="return checkForm()" styleId="submitForm1" enctype="multipart/form-data">
			<jsp:include page="drill_plan_base.jsp"></jsp:include>
			<jsp:include page="drill_plan_modify_base.jsp"></jsp:include>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout">
				<input type="hidden" name="id" value="${drillPlanModify.id }"/>
				<input type="hidden" name="planId" value="${drillPlanModify.planId }"/>
				<input type="hidden" name="taskId" value="${drillPlan.taskId }"/>
				<input type="hidden" name="conId" value="${drillPlan.contractorId }"/>
				<input type="hidden" name="modifyMan" value="${drillPlanModify.modifyMan }"/>
				<fmt:formatDate  value="${drillPlanModify.nextStartTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatNextStartTime"/>
				<fmt:formatDate  value="${drillPlanModify.nextEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatNextEndTime"/>
				<input type="hidden" name="nextStartTime" value="${formatNextStartTime }"/>
				<input type="hidden" name="nextEndTime" value="${formatNextEndTime }"/>
				<c:if test="${operator=='approve'}">
					<tr class="trcolor" id="deadlineArea">
						<td class="tdulleft" style="width:20%;">总结提交时限：</td>
						<td class="tdulright">
							<input name="deadline" class="Wdate" id="deadline" style="width:200"
								onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly value="<bean:write name='drillPlan' property='deadline' format='yyyy/MM/dd HH:mm:ss'/>"/>
					</tr>
					<tr class="trcolor">
						<input type="hidden" name="operator" id="operator" value="approve"/>
						<apptag:approve labelClass="tdulleft" valueClass="tdulright" colSpan="2" areaClass="textarea max-length-1024" />
					</tr>
					<tr class="trcolor">
						<td colspan="2" align="center" class="tdc">
							<html:submit property="action" styleClass="button">提交</html:submit> &nbsp;&nbsp;
							<html:reset property="action" styleClass="button">重写</html:reset>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
						</td>
					</tr>
				</c:if>
				<c:if test="${operator=='transfer'}">
					<input type="hidden" name="operator" id="operator" value="transfer"/>
					<tr class="trcolor">
						<apptag:approverselect label="转审人" colSpan="4" inputName="approvers,mobiles" inputType="radio" spanId="approverSpan"
								approverType="transfer" objectId="${drillPlan.id }" objectType="LP_DRILLPLAN" />
					</tr>
					<tr class="trcolor">
						<td height="25" style="text-align: right;" class="tdulleft" style="width:20%;">
							转审说明：<input type="hidden" name="approveResult" value="2" />
						</td>
						<td colspan="3" style="text-align: left;" class="tdulright">
							<textarea name="approveRemark" rows="6" style="width: 500px;"></textarea>
						</td>
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center" class="tdc">
							<html:submit property="action" styleClass="button">转审</html:submit>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
						</td>
					</tr>
				</c:if>
			</table>
		</html:form>
	</body>
</html>
