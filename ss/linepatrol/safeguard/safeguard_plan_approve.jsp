<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>保障方案审核</title>
		<script type="text/javascript">
			function checkForm(){
				if(getTrimValue("operator")=="approve"){
					if(submitForm1.approveResult[0].checked){
						if(getTrimValue("deadline").length==0){
							alert("总结提交时限不能为空！");
							return false;
						}
					}
				}
				if(getTrimValue("operator")=="transfer"){
					if(getTrimValue("approvers").length==0){
						alert("转审人不能为空！");
						return false;
					}
					return true;
				}
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
			<template:titile value="保障方案审核"/>
		</c:if>
		<c:if test="${operator=='transfer'}">
			<template:titile value="保障方案转审"/>
		</c:if>
		<jsp:include page="safeguard_task_base.jsp"></jsp:include>
		<jsp:include page="safeguard_plan_base.jsp"></jsp:include>
		<html:form action="/safeguardPlanAction.do?method=approveSafeguardPlan" onsubmit="return checkForm();" styleId="submitForm1" enctype="multipart/form-data">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" style="border-top: 0px;" width="90%">
				<input type="hidden" name="safeguardId" value="${safeguardPlan.safeguardId }"/>
				<input type="hidden" name="contractorId" value="${safeguardPlan.contractorId }"/>
				<input type="hidden" id="id" name="id" value="${safeguardPlan.id }"/>
				<input type="hidden" name="maker" value="${safeguardPlan.maker }"/>
				<input type="hidden" name="operator" value="${operator }"/>
				<c:if test="${operator=='approve'}">
					<tr class="trcolor">
						<td class="tdulright" colspan="2">
							<c:if test="${safeguardPlan.makeDate>safeguardTask.deadline}">
								<font color="red">保障方案提交超时，超时<bean:write name="days" format="#.##"/>小时</font>
							</c:if>
							<c:if test="${safeguardPlan.makeDate<=safeguardTask.deadline}">
								<font color="blue">保障方案提交提前，提前<bean:write name="days" format="#.##"/>小时</font>
							</c:if>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">总结提交时限：</td>
						<td class="tdulright">
							<c:if test="${empty safeguardPlan.deadline}">
								<input name="deadline" class="Wdate" id="deadline" style="width:200"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
							</c:if>
							<c:if test="${not empty safeguardPlan.deadline}">
								<input name="deadline" class="Wdate" id="deadline" style="width:200"
									onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly value="<bean:write name='safeguardPlan' property='deadline' format='yyyy/MM/dd HH:mm:ss' />"/>
							</c:if>
					</tr>
					<tr class="trcolor">
						<apptag:approve labelClass="tdulleft" valueClass="tdulright" colSpan="3" areaClass="textarea max-length-1024" />
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center">
							<html:submit property="action" styleClass="button" onclick="checkForm()">提交</html:submit> &nbsp;&nbsp;
							<html:reset property="action" styleClass="button">重写</html:reset>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
						</td>
					</tr>
				</c:if>
				<c:if test="${operator=='transfer'}">
					<tr class="trcolor">
						<apptag:approverselect label="转审人" colSpan="4" inputName="approvers,mobiles" inputType="radio" spanId="approverSpan" />
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
						<td colspan="4" align="center">
							<html:submit property="action" styleClass="button">转审</html:submit>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
						</td>
					</tr>
				</c:if>
			</table>
		</html:form>
	</body>
</html>
