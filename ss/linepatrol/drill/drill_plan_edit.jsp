<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>

<html>
	<head>
		<title>修改演练方案</title>
		<script type="text/javascript">
			var beginDate="";
			var endDate="";
			function checkForm(state){
				if(state=="1"){
					if(valCharLength($("address").value)>500){
						alert("实际演练地点不能超过500个字符！");
					    return;
					}
					if(valCharLength($("scenario").value)>500){
						alert("场景设置不能超过500个字符！");
					    return;
					}
					if(valCharLength($("remark").value)>500){
						alert("备注不能超过500个字符！");
					    return;
					}
					$('tempFlag').value="1";
					processBar(editDrillPlan);
					editDrillPlan.submit();
					return;
				}
				if(getTrimValue("creator")==getTrimValue("userId")){
					if(getTrimValue("name").length==0){
						alert("演练名称不能为空！");
						getElement("name").focus();
						return;
					}
				}
				if(getTrimValue("realBeginTime").length==0){
					alert("实际演练开始时间不能为空！");
					getElement("realBeginTime").focus();
					return;
				}
				if(getTrimValue("realEndTime").length==0){
					alert("实际演练结束时间不能为空！");
					getElement("realEndTime").focus();
					return;
				}
				if(getTrimValue("personNumber").length==0){
					alert("演练投入人数不能为空！");
					getElement("personNumber").focus();
					return;
				}
				if(valiD("personNumber")==false){
					alert("投入人数必须为整数！");
					getElement("personNumber").focus();
					return;
				}
				if(getTrimValue("carNumber").length==0){
					alert("演练投入车辆数不能为空！");
					getElement("carNumber").focus();
					return;
				}
				if(valiD("carNumber")==false){
					alert("投入车辆数必须为整数！");
					getElement("carNumber").focus();
					return;
				}
				if(getTrimValue("equipmentNumber").length==0){
					alert("计划投入设备数数不能为空！");
					getElement("equipmentNumber").focus();
					return;
				}
				if(valiD("equipmentNumber")==false){
					alert("计划投入设备数数必须为整数！");
					getElement("equipmentNumber").focus();
					return;
				}
				if(getTrimValue("address").length==0){
					alert("实际演练地点不能为空！");
					getElement("address").focus();
					return;
				}
				if(valCharLength($("address").value)>500){
					alert("实际演练地点不能超过500个字符！");
				    return;
				}
				if(getTrimValue("scenario").length==0){
					alert("场景设置不能为空！");
					getElement("scenario").focus();
					return;
				}
				if(valCharLength($("scenario").value)>500){
					alert("场景设置不能超过500个字符！");
				    return;
				}
				if(getTrimValue("remark").length==0){
					alert("备注不能为空！");
					getElement("remark").focus();
					return;
				}
				if(valCharLength($("remark").value)>500){
					alert("备注不能超过500个字符！");
				    return;
				}
				if(getTrimValue("approveId").length==0){
					alert("必须选择审核人！");
					return;
				}
				processBar(editDrillPlan);
				editDrillPlan.submit();
			}
			function getTrimValue(value){
				return document.getElementById(value).value.trim();
			}
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g,"");
			}
			function getElement(value){
				return document.getElementById(value);
			}
			//判断是否为数字
			function valiD(id){
				var mysplit = /^\d{1,20}$/;
				var obj = document.getElementById(id);
				if(mysplit.test(obj.value)){
					//return true;
				}
				else{
					obj.focus();
					return false;
				}
			}
			//判断字符长度
			function valCharLength(Value){
				var j=0;
				var s = Value;
				for(var i=0; i<s.length; i++) {
					if (s.substr(i,1).charCodeAt(0)>255) {
						j  = j + 2;
					} else { 
						j++;
					}
				}
				return j;
			}
		</script>
	</head>
	<body>
		<c:if test="${empty flag}">
			<template:titile value="修改演练方案"/>
		</c:if>
		<c:if test="${not empty flag}">
			<template:titile value="添加演练方案"/>
		</c:if>
		<html:form action="/drillPlanAction.do?method=editDrillPlan" enctype="multipart/form-data" styleId="editDrillPlan">
			<input type="hidden" id="userId" value="${creator }"/>
			<input type="hidden" id="flag" name="flag" value="${flag }"/>
			<input type="hidden" id="creator" name="taskCreator" value="${drillTask.creator }"/>
			<input type="hidden" value="<c:out value='${drillTask.name }'/>" name="name">
			<input type="hidden" value="<c:out value='${drillPlan.id }'/>" name="id">
			<input type="hidden" value="<c:out value='${drillPlan.taskId }'/>" name="taskId">
			<input type="hidden" value="<c:out value='${drillPlan.unapproveNumber }'/>" name="unapproveNumber">
			<input type="hidden" value="<c:out value='${drillTask.drillType }'/>" name="drillType">
			<input type="hidden" value="<bean:write name='drillPlan' property='deadline' format='yyyy/MM/dd HH:mm:ss'/>" name="deadline">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<c:if test="${userId==drillTask.creator}">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">演练名称：</td>
						<td class="tdulright">
							<input type="text" name="name" id="name" class="inputtext" style="width:150px;" value="<c:out value='${drillTask.name }'/>" />
							<font color="red">*</font>
						</td>
					</tr>
				</c:if>
				<c:if test="${userId!=drillTask.creator}">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">演练名称：</td>
						<td class="tdulright">
							<c:out value="${drillTask.name }"/>
						</td>
					</tr>
					<tr class="trcolor">
						<fmt:formatDate  value="${drillTask.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
						<fmt:formatDate  value="${drillTask.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
						<input type="hidden" value="<c:out value='${formatBeginTime }' />" id="beginTime"/>
						<input type="hidden" value="<c:out value='${formatEndTime }' />" id="endTime"/>
						<td class="tdulleft" style="width:20%;">建议演练时间：</td>
						<td class="tdulright">
							<c:out value="${formatBeginTime }"/> - <c:out value="${formatEndTime }"/>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">演练地点：</td>
						<td class="tdulright">
							<c:out value="${drillTask.locale }" />
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">演练要求：</td>
						<td class="tdulright">
							<c:out value="${drillTask.demand}"></c:out>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">演练备注：</td>
						<td class="tdulright">
							<c:out value="${drillTask.remark}"></c:out>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">任务附件：</td>
						<td class="tdulright">
							<apptag:upload cssClass="" entityId="${drillTask.id}" entityType="LP_DRILLTASK" state="look"/>
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<fmt:formatDate  value="${drillPlan.realBeginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime"/>
					<fmt:formatDate  value="${drillPlan.realEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
					<td class="tdulleft" style="width:20%;">计划演练时间：</td>
					<td class="tdulright">
						<input name="realBeginTime" class="Wdate" id="realBeginTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly value="${formatRealBeginTime }"/>
						―
						<input name="realEndTime" class="Wdate" id="realEndTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'realBeginTime\')}'})" readonly value="${formatRealEndTime }"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">计划投入人数：</td>
					<td class="tdulright">
						<input type="text" name="personNumber" id="personNumber" class="inputtext" style="width:150px;" value="<c:out value='${drillPlan.personNumber }'/>" /> 人
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">计划投入车辆：</td>
					<td class="tdulright">
						<input type="text" name="carNumber" id="carNumber" class="inputtext" style="width:150px;" value="<c:out value='${drillPlan.carNumber }'/>" /> 辆
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">计划投入设备数：</td>
					<td class="tdulright">
						<input type="text" name="equipmentNumber" id="equipmentNumber" class="inputtext" style="width:150px;" value="<c:out value='${drillPlan.equipmentNumber }'/>" /> 套
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">实际演练地点：</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="address" name="address"><c:out value="${drillPlan.address}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">场景设置：</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="scenario" name="scenario"><c:out value="${drillPlan.scenario}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">备注：</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="remark" name="remark"><c:out value="${drillPlan.remark}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
					<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">方案附件：</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="${drillPlan.id }" entityType="LP_DRILLPLAN" state="edit"/>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="审核人" inputName="approveId,mobile"
							spanId="approverSpan" inputType="radio" colSpan="2" notAllowName="reader"
							approverType="approver" objectId="${drillPlan.id }" objectType="LP_DRILLPLAN" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="抄送人" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="2"
							approverType="reader" objectId="${drillPlan.id }" objectType="LP_DRILLPLAN" />
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">所有项均为必填项，若无内容描述请填写“无”</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" name="tempFlag" id="tempFlag" value="0">
				<html:button property="action" styleClass="button" onclick="checkForm(0)">提交</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">暂存</html:button> &nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</html:form>
	</body>
</html>
