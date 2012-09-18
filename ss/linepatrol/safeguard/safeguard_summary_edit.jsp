<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>修改保障总结</title>
		<script type="text/javascript">
			function checkForm(state){
				if(state=="1"){
					if(valCharLength($("summary").value)>2048){
						alert("保障总结不能超过2048个字符！");
					    return;
					}
					$('saveflag').value="1";
					editsafeguardsummary.submit();
					return;
				}
				if(getTrimValue("factResponder").length==0){
					alert("实际出动人员数不能为空！");
					getElement("factResponder").focus();
					return;
				}
				if(valiD("factResponder")==false){
					alert("实际出动人员数必须为整数！");
					getElement("factResponder").focus();
					return;
				}
				if(getTrimValue("factRespondingUnit").length==0){
					alert("实际出动车辆数不能为空！");
					getElement("factRespondingUnit").focus();
					return;
				}
				if(valiD("factRespondingUnit")==false){
					alert("实际出动车辆数必须为整数！");
					getElement("factRespondingUnit").focus();
					return;
				}
				if(getTrimValue("equipmentNumber").length==0){
					alert("实际投入设备数不能为空！");
					getElement("equipmentNumber").focus();
					return;
				}
				if(valiD("equipmentNumber")==false){
					alert("实际投入设备数必须为整数！");
					getElement("equipmentNumber").focus();
					return;
				}
				if(getTrimValue("summary").length==0){
					alert("保障总结不能为空！");
					getElement("summary").focus();
					return;
				}
				if(valCharLength($("summary").value)>2048){
					alert("保障总结不能超过2048个字符！");
				    return;
				}
				if(getTrimValue("approveId").length==0){
					alert("至少选择一个审核人！");
					return;
				}
				editsafeguardsummary.submit();
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
			<template:titile value="修改保障总结"/>
		</c:if>
		<c:if test="${not empty flag}">
			<template:titile value="添加保障总结"/>
		</c:if>
		<jsp:include page="safeguard_task_base.jsp"></jsp:include>
		<jsp:include page="safeguard_plan_base.jsp"></jsp:include>
		<html:form action="/safeguardSummaryAction.do?method=editSafeguardSummary" enctype="multipart/form-data" styleId="editsafeguardsummary">
			<input type="hidden" name="taskId" value="${safeguardTask.id }"/>
			<input type="hidden" name="flag" value="${flag }"/>
			<input type="hidden" name="conId" value="${safeguardPlan.contractorId }"/>
			<input type="hidden" name="id" value="${safeguardSummary.id }"/>
			<input type="hidden" name="planId" value="${safeguardSummary.planId }"/>
			<input type="hidden" name="auditingNum" value="${safeguardSummary.auditingNum }"/>
			<table align="center" cellpadding="1" style="border-top:0px;" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">实际出动人员：</td>
					<td class="tdulright">
						<input type="text" name="factResponder" value="${safeguardSummary.factResponder }" class="inputtext" style="width:150px;" /> 人
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">实际出动车辆：</td>
					<td class="tdulright">
						<input type="text" name="factRespondingUnit" value="${safeguardSummary.factRespondingUnit }" class="inputtext" style="width:150px;" /> 辆
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">实际投入设备数：</td>
					<td class="tdulright">
						<input type="text" name="equipmentNumber" value="${safeguardSummary.equipmentNumber }" id="equipmentNumber" class="inputtext" style="width:150px;" /> 套
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<fmt:formatDate  value="${safeguardSummary.realStartTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealStartTime"/>
					<fmt:formatDate  value="${safeguardSummary.realEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
					<td class="tdulleft" style="width:20%;">实际保障时间：</td>
					<td class="tdulright">
						<input name="realStartTime" class="Wdate" id="realStartTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})" readonly value="${formatRealStartTime }"/>
						―
						<input name="realEndTime" class="Wdate" id="realEndTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'realStartTime\')}'})" readonly value="${formatRealEndTime }"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">保障总结：</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" name="summary"><c:out value="${safeguardSummary.summary }"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">总结附件：</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="${safeguardSummary.id }" entityType="LP_SAFEGUARD_SUMMARY" state="edit"/>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect inputName="approveId,mobiles" colSpan="2" 
							inputType="radio" label="审核人" notAllowName="reader"
							approverType="approver" objectId="${safeguardSummary.id }" objectType="LP_SAFEGUARD_SUMMARY" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="抄送人" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="2" 
							approverType="reader" objectId="${safeguardSummary.id }" objectType="LP_SAFEGUARD_SUMMARY" />
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">所有项均为必填项，若无内容描述请填写“无”</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" name="saveflag" id="saveflag" value="0">
				<html:button property="action" styleClass="button" onclick="checkForm(0)">提交</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">暂存</html:button>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</html:form>
	</body>
</html>
