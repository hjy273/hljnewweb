<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>

<html>
	<head>
		<title>�����������</title>
		<script type="text/javascript">
			function checkForm(){
			if($("nextStartTime").value.length==0){
					alert("���������ʼʱ�䲻��Ϊ�գ�");
				    return false;
				}
				if($("nextEndTime").value.length==0){
					alert("�����������ʱ�䲻��Ϊ�գ�");
				    return false;
				}
				if($("modifyCase").value.length==0){
					alert("�������ԭ����Ϊ�գ�");
				    return false;
				}
				if(valCharLength($("modifyCase").value)>1024){
					alert("�������ԭ���ܳ���1024���ַ���");
				    return false;
				}
				processBar(addDrillPlanModify);
				return true;
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
		<template:titile value="�����������"/>
		<html:form action="/drillPlanModifyAction.do?method=addDrillPlanModify" styleId="addDrillPlanModify" onsubmit="return checkForm();" enctype="multipart/form-data">
			<jsp:include page="drill_plan_base.jsp"></jsp:include>
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ƻ�����ʱ�䣺</td>
					<td class="tdulright">
						<input type="hidden" name="planId" value="${drillPlan.id }">
						<input type="hidden" name="taskId" value="${drillPlan.taskId }" />
						<fmt:formatDate  value="${drillPlan.realBeginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime"/>
						<fmt:formatDate  value="${drillPlan.realEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
						<c:out value="${formatRealBeginTime}"></c:out> �D <c:out value="${formatRealEndTime}"></c:out>
						<input type="hidden" value="${formatRealBeginTime }" name="prevStartTime">
						<input type="hidden" value="${formatRealEndTime }" name="prevEndTime">
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�������ʱ�䣺</td>
					<td class="tdulright">
						<input name="nextStartTime" class="Wdate" id="nextStartTime" style="width:200"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})" readonly/>
						�D
						<input name="nextEndTime" class="Wdate" id="nextEndTime" style="width:200"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'nextStartTime\')}'})" readonly/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">���ԭ��</td>
					<td class="tdulright">
						<textarea class="textarea" rows="3" id="modifyCase" name="modifyCase"></textarea>
						<font color="red">*</font>
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
				<html:submit property="action" styleClass="button">�ύ</html:submit> &nbsp;&nbsp;
				<html:reset property="action" styleClass="button">��д</html:reset>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
