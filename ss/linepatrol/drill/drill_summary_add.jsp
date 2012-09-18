<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>演练总结</title>
		<script type="text/javascript">
			function checkForm(state){
				if(valCharLength($("summary").value)>300){
					alert("演练总结不能超过300个字符！");
				    return;
				}
				if(state=="1"){
					$('flag').value="1";
					processBar(addDrillSummary);
					addDrillSummary.submit();
					return;
				}
				if(getTrimValue("personNumber").length==0){
					alert("实际投入人数不能为空！");
					getElement("personNumber").focus();
					return;
				}
				if(valiD("personNumber")==false){
					alert("实际投入人数必须为整数！");
					getElement("personNumber").focus();
					return;
				}
				if(getTrimValue("personNumber").length>3){
					alert("实际投入人数过大！");
					getElement("personNumber").focus();
					return;
				}
				if(getTrimValue("carNumber").length==0){
					alert("实际投入车辆数不能为空！");
					getElement("carNumber").focus();
					return;
				}
				if(valiD("carNumber")==false){
					alert("实际投入车辆数必须为整数！");
					getElement("carNumber").focus();
					return;
				}
				if(getTrimValue("carNumber").length>3){
					alert("实际投入车辆数过大！");
					getElement("carNumber").focus();
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
				if(getTrimValue("equipmentNumber").length>3){
					alert("实际投入设备数过大！");
					getElement("equipmentNumber").focus();
					return;
				}
				if(getTrimValue("summary").length==0){
					alert("演练总结不能为空！");
					getElement("summary").focus();
					return;
				}
				if(valCharLength($("summary").value)>300){
					alert("演练总结不能超过300个字符！");
				    return;
				}
				if(getTrimValue("approveId").length==0){
					alert("必须选择审核人！");
					return;
				}
				processBar(addDrillSummary);
				addDrillSummary.submit();
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
			toViewDrillPlanModify=function(planId){
            	//window.location.href = "${ctx}/drillPlanModifyAction.do?method=viewDrillPlanModify&planModifyId="+planModifyId;
				var url = "${ctx}/drillPlanModifyAction.do?method=viewDrillPlanModifyByPlanId&planId="+planId;
				win = new Ext.Window({
					layout : 'fit',
					width:650,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
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
		<template:titile value="演练总结"/>
		<html:form action="/drillSummaryAction.do?method=addDrillSummary" styleId="addDrillSummary" enctype="multipart/form-data">
			<table align="center" style="border-bottom:0px;" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">演练名称：</td>
					<td class="tdulright">
						<c:out value="${drillTask.name}"></c:out>
						<input type="hidden" value="<c:out value='${drillTask.id }' />" name="taskId">
						<input type="hidden" value="<c:out value='${drillPlan.id }' />" name="planId">
						<input type="hidden" value="<c:out value='${drillTask.name }' />" name="name">
					</td>
				</tr>
				<tr class="trcolor">
					<fmt:formatDate  value="${drillTask.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
					<fmt:formatDate  value="${drillTask.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
					<td class="tdulleft" style="width:20%;">建议演练时间：</td>
					<td class="tdulright">
						<c:out value="${formatBeginTime}"></c:out> - <c:out value="${formatEndTime}"></c:out>
						<input type="hidden" value="${formatBeginTime }" id="beginTime">
						<input type="hidden" value="${formatEndTime }" id="endTime">
					</td>
				</tr>
				<tr class="trcolor">
					<fmt:formatDate  value="${drillPlan.realBeginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime"/>
					<fmt:formatDate  value="${drillPlan.realEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
					<td class="tdulleft" style="width:20%;">实际演练时间：</td>
					<td class="tdulright">
						<c:out value="${formatRealBeginTime}"></c:out> - <c:out value="${formatRealEndTime}"></c:out>
						<input type="hidden" value="${formatRealBeginTime }" id="realBeginTime">
						<input type="hidden" value="${formatRealEndTime }" id="realEndTime">
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">总结提交时限：</td>
					<td class="tdulright">
						<bean:write name="drillPlan" property="deadline" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">计划演练地点：</td>
					<td class="tdulright">
						<c:out value="${drillTask.locale}"></c:out>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">实际演练地点：</td>
					<td class="tdulright">
						<c:out value="${drillPlan.address}"></c:out>
					</td>
				</tr>
				<c:if test="${not empty list}">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">演练方案变更：</td>
						<td class="tdulright">
							<a style="color: blue;cursor: pointer;" onclick="toViewDrillPlanModify('${drillPlan.id}')">演练方案变更信息</a>
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">计划投入人数：</td>
					<td class="tdulright">
						<c:out value="${drillPlan.personNumber}"></c:out> 人
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">实际投入人数：</td>
					<td class="tdulright">
						<input type="text" name="personNumber" id="personNumber" class="inputtext" style="width:200" /> 人
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">计划投入车辆：</td>
					<td class="tdulright">
						<c:out value="${drillPlan.carNumber}"></c:out> 辆
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">实际投入车辆：</td>
					<td class="tdulright">
						<input type="text" name="carNumber" id="carNumber" class="inputtext" style="width:200" /> 辆
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">计划投入设备数：</td>
					<td class="tdulright">
						<c:out value="${drillPlan.equipmentNumber}"></c:out> 套
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">实际投入设备数：</td>
					<td class="tdulright">
						<input type="text" name="equipmentNumber" id="equipmentNumber" class="inputtext" style="width:200" /> 套
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">场景设置：</td>
					<td class="tdulright">
						<c:out value="${drillPlan.scenario}"></c:out>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">备注：</td>
					<td class="tdulright">
						<c:out value="${drillPlan.remark}"></c:out>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">方案附件：</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="${drillPlan.id}" entityType="LP_DRILLPLAN" state="look"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">演练总结：</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" name="summary" id="summary"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">总结附件：</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="" entityType="LP_DRILLSUMMARY" state="add"/>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="审核人" inputName="approveId,mobiles"
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
				<input name="flag" type="hidden" id="flag" value="0"/>
				<html:button property="action" styleClass="button" onclick="checkForm(0)">提交</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">暂存</html:button>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</html:form>
	</body>
</html>
