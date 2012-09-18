<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>

<html>
	<head>
		<title>�ƶ���������</title>
		<script type="text/javascript">
			function checkForm(state){
				if(state=="1"){
					if(valCharLength($("address").value)>500){
						alert("ʵ�������ص㲻�ܳ���500���ַ���");
					    return;
					}
					if(valCharLength($("scenario").value)>500){
						alert("�������ò��ܳ���500���ַ���");
					    return;
					}
					if(valCharLength($("remark").value)>500){
						alert("��ע���ܳ���500���ַ���");
					    return;
					}
					$('tempFlag').value="1";
					processBar(addDrillPlan);
					addDrillPlan.submit();
					return;
				}
				if(document.getElementById("drillTaskId").value==""){
					if(getTrimValue("name").length==0){
						alert("�������Ʋ���Ϊ�գ�");
						getElement("name").focus();
						return;
					}
				}
				if(getTrimValue("realBeginTime").length==0){
					alert("�ƻ�������ʼʱ�䲻��Ϊ�գ�");
					getElement("realBeginTime").focus();
					return;
				}
				if(getTrimValue("realEndTime").length==0){
					alert("�ƻ���������ʱ�䲻��Ϊ�գ�");
					getElement("realEndTime").focus();
					return;
				}
				if(getTrimValue("personNumber").length==0){
					alert("����Ͷ����������Ϊ�գ�");
					getElement("personNumber").focus();
					return;
				}
				if(valiD("personNumber")==false){
					alert("Ͷ����������Ϊ������");
					getElement("personNumber").focus();
					return;
				}
				if(getTrimValue("carNumber").length==0){
					alert("����Ͷ�복��������Ϊ�գ�");
					getElement("carNumber").focus();
					return;
				}
				if(valiD("carNumber")==false){
					alert("Ͷ�복��������Ϊ������");
					getElement("carNumber").focus();
					return;
				}
				if(getTrimValue("equipmentNumber").length==0){
					alert("�ƻ�Ͷ���豸��������Ϊ�գ�");
					getElement("equipmentNumber").focus();
					return;
				}
				if(valiD("equipmentNumber")==false){
					alert("�ƻ�Ͷ���豸��������Ϊ������");
					getElement("equipmentNumber").focus();
					return;
				}
				if(getTrimValue("address").length==0){
					alert("ʵ�������ص㲻��Ϊ�գ�");
					getElement("address").focus();
					return;
				}
				if(valCharLength($("address").value)>500){
					alert("ʵ�������ص㲻�ܳ���500���ַ���");
				    return;
				}
				if(getTrimValue("scenario").length==0){
					alert("�������ò���Ϊ�գ�");
					getElement("scenario").focus();
					return;
				}
				if(valCharLength($("scenario").value)>500){
					alert("�������ò��ܳ���500���ַ���");
				    return;
				}
				if(getTrimValue("remark").length==0){
					alert("��ע����Ϊ�գ�");
					getElement("remark").focus();
					return;
				}
				if(valCharLength($("remark").value)>500){
					alert("��ע���ܳ���500���ַ���");
				    return;
				}
				if(getTrimValue("approveId").length==0){
					alert("����ѡ������ˣ�");
					return;
				}
				processBar(addDrillPlan);
				addDrillPlan.submit();
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
	<%
		DrillTask drillTask = (DrillTask)request.getAttribute("drillTask");
		request.setAttribute("drillTask",drillTask);
	%>
	<body>
		<template:titile value="�ƶ���������"/>
		<input type="hidden" value="${drillTask.id }" name="drillTaskId" id="drillTaskId">
		<html:form action="/drillPlanAction.do?method=addDrillPlan" enctype="multipart/form-data" styleId="addDrillPlan">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<c:if test="${empty drillTask}">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">�������ƣ�</td>
						<td class="tdulright">
							<input type="text" name="name" id="name" class="inputtext" style="width:150px;" />
							<font color="red">*</font>
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty drillTask}">
					<input type="hidden" value="${drillTask.id}" name="taskId"/>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">�������ƣ�</td>
						<td class="tdulright">
							<c:out value="${drillTask.name}"></c:out>
							<input type="hidden" value="<c:out value='${drillTask.name }'/>" name="name">
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">��������ʱ�䣺</td>
						<td class="tdulright">
							<fmt:formatDate  value="${drillTask.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
							<fmt:formatDate  value="${drillTask.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
							<c:out value="${formatBeginTime}"></c:out> �D <c:out value="${formatEndTime}"></c:out>
							<input type="hidden" value="${formatBeginTime }" id="beginTime">
							<input type="hidden" value="${formatEndTime }" id="endTime">
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">�����ύʱ�ޣ�</td>
						<td class="tdulright">
							<fmt:formatDate  value="${drillTask.deadline}" pattern="yyyy/MM/dd HH:mm:ss" var="formatDeadline"/>
							<c:out value="${formatDeadline}"/>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">�ƻ������ص㣺</td>
						<td class="tdulright">
							<c:out value="${drillTask.locale}"></c:out>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">����Ҫ��</td>
						<td class="tdulright">
							<c:out value="${drillTask.demand}"></c:out>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">������ע��</td>
						<td class="tdulright">
							<c:out value="${drillTask.remark}"></c:out>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">���񸽼���</td>
						<td class="tdulright">
							<apptag:upload cssClass="" entityId="${drillTask.id}" entityType="LP_DRILLTASK" state="look"/>
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�����ʱ�䣺</td>
					<td class="tdulright">
						<input name="realBeginTime" class="Wdate" id="realBeginTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})" readonly/>
						�D
						<input name="realEndTime" class="Wdate" id="realEndTime" style="width:150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'realBeginTime\')}'})" readonly/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�Ͷ��������</td>
					<td class="tdulright">
						<input type="text" name="personNumber" id="personNumber" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�Ͷ�복����</td>
					<td class="tdulright">
						<input type="text" name="carNumber" id="carNumber" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�Ͷ���豸����</td>
					<td class="tdulright">
						<input type="text" name="equipmentNumber" id="equipmentNumber" class="inputtext" style="width:150px;" /> ��
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ʵ�������ص㣺</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="address" name="address"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�������ã�</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="scenario" name="scenario"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ע��</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="remark" name="remark"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">����������</td>
					<td class="tdulright">
						<apptag:upload cssClass="" entityId="" entityType="LP_DRILLPLAN" state="add"/>
					</td>
				</tr>
				<tr class="trcolor">
					<c:if test="${empty approveInfo}">
						<apptag:approverselect label="�����" inputName="approveId,mobile"
							spanId="approverSpan" inputType="radio" colSpan="2" notAllowName="reader"/>
					</c:if>
					<c:if test="${not empty approveInfo}">
						<apptag:approverselect label="�����" inputName="approveId,mobile"
								spanId="approverSpan" inputType="radio" colSpan="2" notAllowName="reader" existValue="${approveInfo[0] }--${approveInfo[1] }--${approveInfo[2] }" />
					</c:if>
				</tr>
				<tr class="trcolor">
					<apptag:approverselect label="������" inputName="reader,readerPhones"
							spanId="readerSpan" notAllowName="approveId" colSpan="2" />
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" name="tempFlag" id="tempFlag" value="0">
				<html:button property="action" styleClass="button" onclick="checkForm(0)">�ύ</html:button> &nbsp;&nbsp;
				<c:if test="${empty drillTask}">
					<html:reset property="action" styleClass="button">��д</html:reset>&nbsp;&nbsp;
				</c:if>
				<c:if test="${not empty drillTask}">
					<html:button property="action" styleClass="button" onclick="checkForm(1)">�ݴ�</html:button> &nbsp;&nbsp;
				</c:if>
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
