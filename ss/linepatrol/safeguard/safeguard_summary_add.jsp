<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>�ƶ������ܽ�</title>
		<script type="text/javascript">
			function checkForm(state){
				if(state=="1"){
					if(valCharLength($("summary").value)>2048){
						alert("�����ܽ᲻�ܳ���2048���ַ���");
					    return;
					}
					$('flag').value="1";
					addSafeguardSummary.submit();
					return;
				}
				if(getTrimValue("factResponder").length==0){
					alert("ʵ�ʳ�����Ա������Ϊ�գ�");
					getElement("factResponder").focus();
					return;
				}
				if(valiD("factResponder")==false){
					alert("ʵ�ʳ�����Ա������Ϊ������");
					getElement("factResponder").focus();
					return;
				}
				if(getTrimValue("factRespondingUnit").length==0){
					alert("ʵ�ʳ�������������Ϊ�գ�");
					getElement("factRespondingUnit").focus();
					return;
				}
				if(valiD("factRespondingUnit")==false){
					alert("ʵ�ʳ�������������Ϊ������");
					getElement("factRespondingUnit").focus();
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
				if(getTrimValue("realStartTime").length==0){
					alert("ʵ�ʱ��Ͽ�ʼʱ�䲻��Ϊ�գ�");
					getElement("realStartTime").focus();
					return;
				}
				if(getTrimValue("realEndTime").length==0){
					alert("ʵ�ʱ��Ͻ���ʱ�䲻��Ϊ�գ�");
					getElement("realEndTime").focus();
					return;
				}
				if(getTrimValue("summary").length==0){
					alert("�����ܽ᲻��Ϊ�գ�");
					getElement("summary").focus();
					return;
				}
				if(valCharLength($("summary").value)>2048){
					alert("�����ܽ᲻�ܳ���2048���ַ���");
				    return;
				}
				if(getTrimValue("approveId").length==0){
					alert("����ѡ��һ������ˣ�");
					return;
				}
				
				addSafeguardSummary.submit();
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
		<template:titile value="�ƶ������ܽ�"/>
		<jsp:include page="safeguard_task_base.jsp"/>
		<jsp:include page="safeguard_plan_base.jsp"/>
		<html:form action="/safeguardSummaryAction.do?method=addSafeguardSummary" enctype="multipart/form-data" styleId="addSafeguardSummary">
			<input type="hidden" name="taskId" value="${safeguardTask.id }"/>
			<input type="hidden" name="planId" value="${safeguardPlan.id }"/>
			<table align="center" cellpadding="1" style="border-top:0px;" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ�ʳ�����Ա��</td>
					<td class="tdulright">
						<input type="text" name="factResponder" id="factResponder" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ�ʳ���������</td>
					<td class="tdulright">
						<input type="text" name="factRespondingUnit" id="factRespondingUnit" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ��Ͷ���豸����</td>
					<td class="tdulright">
						<input type="text" name="equipmentNumber" id="equipmentNumber" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ�ʱ���ʱ�䣺</td>
					<td class="tdulright">
						<input name="realStartTime" class="Wdate " id="realStartTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',maxDate:'%y-%M-%d'})" readonly/>
						�D
						<input name="realEndTime" class="Wdate" id="realEndTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'realStartTime\')}',maxDate:'%y-%M-%d'})" readonly/>
						<font color="red">*</font>
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
						<apptag:upload cssClass="" entityId="" entityType="LP_SAFEGUARD_SUMMARY" state="add"/>
					</td>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect inputName="approveId,mobiles" colSpan="2" inputType="radio" 
							label="�����" notAllowName="reader"
							approverType="approver" objectId="${safeguardPlan.id }" objectType="LP_SAFEGUARD_PLAN" />
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="������" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="2"
							approverType="reader" objectId="${safeguardPlan.id }" objectType="LP_SAFEGUARD_PLAN" />
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" name="flag" id="flag" value="0">
				<html:button property="action" styleClass="button" onclick="checkForm(0)">�ύ</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">�ݴ�</html:button>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
