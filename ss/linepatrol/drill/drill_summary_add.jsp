<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>�����ܽ�</title>
		<script type="text/javascript">
			function checkForm(state){
				if(valCharLength($("summary").value)>300){
					alert("�����ܽ᲻�ܳ���300���ַ���");
				    return;
				}
				if(state=="1"){
					$('flag').value="1";
					processBar(addDrillSummary);
					addDrillSummary.submit();
					return;
				}
				if(getTrimValue("personNumber").length==0){
					alert("ʵ��Ͷ����������Ϊ�գ�");
					getElement("personNumber").focus();
					return;
				}
				if(valiD("personNumber")==false){
					alert("ʵ��Ͷ����������Ϊ������");
					getElement("personNumber").focus();
					return;
				}
				if(getTrimValue("personNumber").length>3){
					alert("ʵ��Ͷ����������");
					getElement("personNumber").focus();
					return;
				}
				if(getTrimValue("carNumber").length==0){
					alert("ʵ��Ͷ�복��������Ϊ�գ�");
					getElement("carNumber").focus();
					return;
				}
				if(valiD("carNumber")==false){
					alert("ʵ��Ͷ�복��������Ϊ������");
					getElement("carNumber").focus();
					return;
				}
				if(getTrimValue("carNumber").length>3){
					alert("ʵ��Ͷ�복��������");
					getElement("carNumber").focus();
					return;
				}
				if(getTrimValue("equipmentNumber").length==0){
					alert("ʵ��Ͷ���豸������Ϊ�գ�");
					getElement("equipmentNumber").focus();
					return;
				}
				if(valiD("equipmentNumber")==false){
					alert("ʵ��Ͷ���豸������Ϊ������");
					getElement("equipmentNumber").focus();
					return;
				}
				if(getTrimValue("equipmentNumber").length>3){
					alert("ʵ��Ͷ���豸������");
					getElement("equipmentNumber").focus();
					return;
				}
				if(getTrimValue("summary").length==0){
					alert("�����ܽ᲻��Ϊ�գ�");
					getElement("summary").focus();
					return;
				}
				if(valCharLength($("summary").value)>300){
					alert("�����ܽ᲻�ܳ���300���ַ���");
				    return;
				}
				if(getTrimValue("approveId").length==0){
					alert("����ѡ������ˣ�");
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
			//�ж��Ƿ�Ϊ����
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
			//�ж��ַ�����
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
		<template:titile value="�����ܽ�"/>
		<html:form action="/drillSummaryAction.do?method=addDrillSummary" styleId="addDrillSummary" enctype="multipart/form-data">
			<table align="center" style="border-bottom:0px;" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�������ƣ�</td>
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
					<td class="tdulleft" style="width:20%;">��������ʱ�䣺</td>
					<td class="tdulright">
						<c:out value="${formatBeginTime}"></c:out> - <c:out value="${formatEndTime}"></c:out>
						<input type="hidden" value="${formatBeginTime }" id="beginTime">
						<input type="hidden" value="${formatEndTime }" id="endTime">
					</td>
				</tr>
				<tr class="trcolor">
					<fmt:formatDate  value="${drillPlan.realBeginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime"/>
					<fmt:formatDate  value="${drillPlan.realEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
					<td class="tdulleft" style="width:20%;">ʵ������ʱ�䣺</td>
					<td class="tdulright">
						<c:out value="${formatRealBeginTime}"></c:out> - <c:out value="${formatRealEndTime}"></c:out>
						<input type="hidden" value="${formatRealBeginTime }" id="realBeginTime">
						<input type="hidden" value="${formatRealEndTime }" id="realEndTime">
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ܽ��ύʱ�ޣ�</td>
					<td class="tdulright">
						<bean:write name="drillPlan" property="deadline" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ������ص㣺</td>
					<td class="tdulright">
						<c:out value="${drillTask.locale}"></c:out>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ�������ص㣺</td>
					<td class="tdulright">
						<c:out value="${drillPlan.address}"></c:out>
					</td>
				</tr>
				<c:if test="${not empty list}">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">�������������</td>
						<td class="tdulright">
							<a style="color: blue;cursor: pointer;" onclick="toViewDrillPlanModify('${drillPlan.id}')">�������������Ϣ</a>
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�Ͷ��������</td>
					<td class="tdulright">
						<c:out value="${drillPlan.personNumber}"></c:out> ��
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ��Ͷ��������</td>
					<td class="tdulright">
						<input type="text" name="personNumber" id="personNumber" class="inputtext" style="width:200" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�Ͷ�복����</td>
					<td class="tdulright">
						<c:out value="${drillPlan.carNumber}"></c:out> ��
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ��Ͷ�복����</td>
					<td class="tdulright">
						<input type="text" name="carNumber" id="carNumber" class="inputtext" style="width:200" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�Ͷ���豸����</td>
					<td class="tdulright">
						<c:out value="${drillPlan.equipmentNumber}"></c:out> ��
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ��Ͷ���豸����</td>
					<td class="tdulright">
						<input type="text" name="equipmentNumber" id="equipmentNumber" class="inputtext" style="width:200" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�������ã�</td>
					<td class="tdulright">
						<c:out value="${drillPlan.scenario}"></c:out>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ע��</td>
					<td class="tdulright">
						<c:out value="${drillPlan.remark}"></c:out>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">����������</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="${drillPlan.id}" entityType="LP_DRILLPLAN" state="look"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�����ܽ᣺</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" name="summary" id="summary"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ܽḽ����</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="" entityType="LP_DRILLSUMMARY" state="add"/>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="�����" inputName="approveId,mobiles"
							spanId="approverSpan" inputType="radio" colSpan="2" notAllowName="reader"
							approverType="approver" objectId="${drillPlan.id }" objectType="LP_DRILLPLAN" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="������" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="2"
							approverType="reader" objectId="${drillPlan.id }" objectType="LP_DRILLPLAN" />
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>
			<div align="center" style="height:40px">
				<input name="flag" type="hidden" id="flag" value="0"/>
				<html:button property="action" styleClass="button" onclick="checkForm(0)">�ύ</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">�ݴ�</html:button>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
