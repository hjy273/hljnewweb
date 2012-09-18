<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>�����ܽ����</title>
		<script type="text/javascript">
			function checkForm(){
				if(getTrimValue("operator")=="transfer"){
					if(getTrimValue("approvers").length==0){
						alert("ת���˲���Ϊ�գ�");
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
			function getElement(value){
				return document.getElementById(value);
			}
			//�ж��Ƿ�Ϊ����
			function valiD(id){
				var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
				var obj = document.getElementById(id);
				if(mysplit.test(obj.value)){
					//return true;
				}
				else{
					obj.focus();
					return false;
				}
			}
		</script>
	</head>
	<body>
		<c:if test="${operator=='approve'}">
			<template:titile value="�����ܽ����"/>
		</c:if>
		<c:if test="${operator=='transfer'}">
			<template:titile value="�����ܽ�ת��"/>
		</c:if>
		<jsp:include page="safeguard_task_base.jsp"></jsp:include>
		<jsp:include page="safeguard_plan_base.jsp"></jsp:include>
		<jsp:include page="safeguard_summary_base.jsp"></jsp:include>
		<html:form action="/safeguardSummaryAction.do?method=approveSafeguardSummary" onsubmit="return checkForm();" enctype="multipart/form-data">
			<input type="hidden" value="${operator }" name="operator"/>
			<input type="hidden" value="${safeguardTask.id }" name="taskId"/>
			<input type="hidden" value="${safeguardPlan.contractorId }" name="conId"/>
			<input type="hidden" value="${safeguardSummary.sumManId }" name="sumManId"/>
			<input type="hidden" value="${safeguardSummary.id }" name="id"/>
			<table align="center" cellpadding="1" cellspacing="0" style="border-top:0px;" class="tabout" width="90%">
				<c:if test="${operator=='approve'}">
					<tr class="trcolor">
						<td class="tdulright" colspan="2">
							<c:if test="${safeguardSummary.sumDate>safeguardPlan.deadline}">
								<font color="red">�����ܽ��ύ��ʱ����ʱ<bean:write name="days" format="#.##"/>Сʱ</font>
							</c:if>
							<c:if test="${safeguardSummary.sumDate<=safeguardPlan.deadline}">
								<font color="blue">�����ܽ��ύ��ǰ����ǰ<bean:write name="days" format="#.##"/>Сʱ</font>
							</c:if>
						</td>
					</tr>
					<tr class="trcolor">
						<apptag:approve labelClass="tdulleft" valueClass="tdulright" colSpan="3" areaClass="textarea max-length-1024" />
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center">
							<html:submit property="action" styleClass="button" onclick="checkForm()">�ύ</html:submit> &nbsp;&nbsp;
							<html:reset property="action" styleClass="button">��д</html:reset>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
						</td>
					</tr>
				</c:if>
				<c:if test="${operator=='transfer'}">
					<tr class="trcolor">
						<apptag:approverselect label="ת����" inputName="approvers,mobiles" 
								inputType="radio" colSpan="4" spanId="approverSpan" 
								approverType="transfer" objectId="${safeguardPlan.id }" objectType="LP_SAFEGUARD_PLAN" />
					</tr>
					<tr class="trcolor">
						<td height="25" class="tdulleft">
							ת��˵����<input type="hidden" name="approveResult" value="2" />
						</td>								   
						<td colspan="3" class="tdulright">
							<textarea name="approveRemark" rows="6" style="width: 500px;"></textarea>
						</td>
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center">
							<html:submit property="action" styleClass="button">ת��</html:submit>&nbsp;&nbsp;
							<html:reset property="action" styleClass="button">����</html:reset>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
						</td>
					</tr>
				</c:if>
			</table>
		</html:form>
	</body>
</html>
