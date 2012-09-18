<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>
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
					processBar(submitForm1);
					return true;
				}
				processBar(submitForm1);
				return true;
			}
			function getTrimValue(value){
				return document.getElementById(value).value.trim();
			}
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g,"");
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
		<html:form action="/drillSummaryAction.do?method=approveDrillSummary" onsubmit="return checkForm()" styleId="submitForm1" enctype="multipart/form-data">
			<input type="hidden" name="id" value="<c:out value='${drillSummary.id }' />"/>
			<input type="hidden" name="creator" value="<c:out value='${drillSummary.creator }' />"/>
			<input type="hidden" name="operator" id="operator" value="<c:out value='${operator }'/>"/>
			<jsp:include page="drill_summary_base.jsp"></jsp:include>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout">
				<c:if test="${operator=='approve'}">
					<tr class="trcolor">
						<td class="tdulright" colspan="4">
							<c:if test="${drillSummary.createTime>drillPlan.deadline}">
								<font color="red">�����ܽ��ύ��ʱ����ʱ<bean:write name="days" format="#.##"/>Сʱ</font>
							</c:if>
							<c:if test="${drillSummary.createTime<=drillPlan.deadline}">
								<font color="blue">�����ܽ��ύ��ǰ����ǰ<bean:write name="days" format="#.##"/>Сʱ</font>
							</c:if>
						</td>
					</tr>
					<tr class="trcolor">
						<apptag:approve labelClass="tdulleft" valueClass="tdulright" colSpan="4" areaClass="textarea max-length-1024" />
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center" class="tdc">
							<html:submit property="action" styleClass="button">�ύ</html:submit> &nbsp;&nbsp;
							<html:reset property="action" styleClass="button">��д</html:reset>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
						</td>
					</tr>
				</c:if>
				<c:if test="${operator=='transfer'}">
					<tr class="trcolor">
						<apptag:approverselect label="ת����" colSpan="4" inputName="approvers,mobiles" inputType="radio" spanId="approverSpan"
						approverType="transfer" objectId="${drillPlan.id }" objectType="LP_DRILLPLAN" />
					</tr>
					<tr class="trcolor">
						<td height="25" style="text-align: right;" class="tdulleft" style="width:20%;">
							ת��˵����<input type="hidden" name="approveResult" value="2" />
						</td>
						<td colspan="3" style="text-align: left;" class="tdulright">
							<textarea name="approveRemark" rows="6" style="width: 500px;"></textarea>
						</td>
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center" class="tdc">
							<html:submit property="action" styleClass="button">ת��</html:submit>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
						</td>
					</tr>
				</c:if>
			</table>
		</html:form>
	</body>
</html>
