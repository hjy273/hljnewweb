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
					addsafeguardtask.submit();
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
				if(getTrimValue("deadline").length==0){
					alert("�����ύʱ�޲� ��Ϊ�գ�");
					getElement("deadline").focus();
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
				addsafeguardtask.submit();
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
		</script>
	</head>
	<body>
		<template:titile value="���������ɵ�"/>
		<html:form action="/safeguardTaskAction.do?method=addSafeguardTask" styleId="addsafeguardtask" enctype="multipart/form-data">
			<input type="hidden" name="safeguardCode" value="${safeguardCode }"/>
			<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�������ƣ�</td>
					<td class="tdulright">
						<input type="text" name="safeguardName" id="safeguardName" class="inputtext" style="width:150px;">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">����ʱ�䣺</td>
					<td class="tdulright">
						<input name="startDate" class="Wdate" id="startDate" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
						�D
						<input name="endDate" class="Wdate" id="endDate" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}'})" readonly/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�����ύʱ�ޣ�</td>
					<td class="tdulright">
						<input name="deadline" class="Wdate" id="deadline" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
						<font color="red">*</font>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ϼ���</td>
					<td class="tdulright">
						<input type="radio" name="level" value="4" checked="checked">�ؼ�
						<input type="radio" name="level" value="1">һ��
						<input type="radio" name="level" value="2">����
						<input type="radio" name="level" value="3">����
					</td>
				</tr>
				<apptag:processorselect inputName="contractorId,userId,mobileId,conUser" 
						spanId="userSpan" displayType="contractor" />
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ϵص㣺</td>
					<td class="tdulright">
						<input type="text" name="region" class="inputtext" id="region" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">����Ҫ��</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="requirement" name="requirement"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ϱ�ע��</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="remark" name="remark"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���񸽼���</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="" entityType="LP_SAFEGUARD_TASK" state="add"/>
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
				<html:reset property="action" styleClass="button">��д</html:reset>
			</div>
		</html:form>
	</body>
</html>
