<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>���������ɵ�</title>
		<script type="text/javascript">
			function checkForm(state){
				if(state=="1"){
					if(valCharLength($("safeguardName").value)>100){
						alert("�����ɵ����Ʋ��ܳ���100���ַ���");
					    return;
					}
					if(valCharLength($("region").value)>500){
						alert("���ϵص㲻�ܳ���500���ַ���");
					    return;
					}
					if(valCharLength($("requirement").value)>1024){
						alert("����Ҫ���ܳ���1024���ַ���");
					    return;
					}
					if(valCharLength($("remark").value)>1024){
						alert("���ϱ�ע���ܳ���1024���ַ���");
					    return;
					}
					$("saveflag").value="1";
					perfectsafeguardtask.submit();
					return;
				}
				if(getTrimValue("safeguardName").length==0){
					alert("�����ɵ����Ʋ���Ϊ�գ�");
					getElement("safeguardName").focus();
				    return;
				}
				if(valCharLength($("safeguardName").value)>100){
					alert("�����ɵ����Ʋ��ܳ���100���ַ���");
				    return;
				}
				if(getTrimValue("startDate").length==0){
					alert("��ʼʱ�䲻��Ϊ�գ�");
					getElement("startDate").focus();
				    return;
				}
				if(getTrimValue("endDate").length==0){
					alert("����ʱ�䲻��Ϊ�գ�");
					getElement("endDate").focus();
				    return;
				}
				if(getTrimValue("contractorId").length==0){
					alert("����ѡ��һ����ά��˾��");
				    return;
				}				
				if(getTrimValue("region").length==0){
					alert("���ϵص㲻��Ϊ�գ�");
					getElement("region").focus();
				    return;
				}
				if(valCharLength($("region").value)>500){
					alert("���ϵص㲻�ܳ���500���ַ���");
				    return;
				}
				if(getTrimValue("requirement").length==0){
					alert("����Ҫ����Ϊ�գ�");
					getElement("requirement").focus();
				    return;
				}
				if(valCharLength($("requirement").value)>1024){
					alert("����Ҫ���ܳ���1024���ַ���");
				    return;
				}
				if(getTrimValue("remark").length==0){
					alert("���ϱ�ע����Ϊ�գ�");
					getElement("remark").focus();
				    return;
				}
				if(valCharLength($("remark").value)>1024){
					alert("���ϱ�ע���ܳ���1024���ַ���");
				    return;
				}
				perfectsafeguardtask.submit();
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
			//���õ�ѡĬ��ֵ
			function setCheckedValue() {   
    			//if(!radioName) return;   
       			var radios = document.getElementsByName("level");  
       			var newValue = document.getElementById("level").value;    
       			for(var i=0; i<radios.length; i++) {   
	          		radios[i].checked = false;   
	          		if(radios[i].value == newValue) {   
	          			radios[i].checked = true;   
	       			}   
	        	} 
	        	setConId();  
			}  
			//���ô�ά��λ��ֵ
			function setConId(){
				var conId = $('conId').value;
				var contractorId = $('contractorId');
				contractorId.value = conId;
			}
		</script>
	</head>
	<body onload="setCheckedValue()">
		<template:titile value="���������ɵ�"/>
		<html:form action="/safeguardTaskAction.do?method=addSafeguardTask" styleId="perfectsafeguardtask" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${safeguardTask.id }"/>
			<input type="hidden" name="safeguardCode" value="${safeguardTask.safeguardCode }"/>
			<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�������ƣ�</td>
					<td class="tdulright">
						<input type="text" name="safeguardName" id="safeguardName" class="inputtext" style="width:150px;" value="${safeguardTask.safeguardName }">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">����ʱ�䣺</td>
					<td class="tdulright">
						<input name="startDate" class="Wdate" id="startDate" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly 
							value="<bean:write name='safeguardTask' property='startDate' format='yyyy/MM/dd HH:mm:ss'/>"/>
						�D
						<input name="endDate" class="Wdate" id="endDate" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}'})" readonly 
							value="<bean:write name='safeguardTask' property='endDate' format='yyyy/MM/dd HH:mm:ss'/>"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�����ύʱ�ޣ�</td>
					<td class="tdulright">
						<input name="deadline" class="Wdate" id="deadline" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly value="<bean:write name='safeguardTask' property='deadline' format='yyyy/MM/dd HH:mm:ss'/>"/>
						<font color="red">*</font>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ϼ���</td>
					<td class="tdulright">
						<input type="hidden" id="level" value="${safeguardTask.level }">
						<input type="radio" name="level" value="4" checked="checked">�ؼ�
						<input type="radio" name="level" value="1">һ��
						<input type="radio" name="level" value="2">����
						<input type="radio" name="level" value="3">����
					</td>
				</tr>
				<input type="hidden" id="conId" value="${userIds[3] }"/>
				<apptag:processorselect inputName="contractorId,userId,mobileId,conUser"
						spanId="userSpan" displayType="contractor" existValue="${userIds[0] }--${userIds[1] }" spanValue="${userIds[2] }" />
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ϵص㣺</td>
					<td class="tdulright">
						<input type="text" name="region" class="inputtext" id="region" style="width:150px;" value="${safeguardTask.region }" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">����Ҫ��</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="requirement" name="requirement"><c:out value="${safeguardTask.requirement}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ϱ�ע��</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="remark" name="remark"><c:out value="${safeguardTask.requirement}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���񸽼���</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="${safeguardTask.id}" entityType="LP_SAFEGUARD_TASK" state="edit"/>
					</td>
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" name="saveflag" id="saveflag" value="0">
				<html:button property="action" styleClass="button" onclick="checkForm(0)">�ύ</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">�ݴ�</html:button> &nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
