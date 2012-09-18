<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>ͳ�Ʋ�ѯ</title>
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet"
			href="${ctx}/js/WdatePicker/skin/WdatePicker.css">

	</head>

	<body>
		<br>
		<template:titile value="ͳ�Ʋ�ѯ" />
		<html:form action="/troubleQueryStatAction.do?method=statTroubleInfos"
			styleId="statTroubleInfo">
			<table width="70%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdulleft" width="20%">
						���Ϸ���ʱ�䣺
					</td>
					<td colspan="5">
						<html:text property="startTimeBegin" name="troubleQueryStatBean" styleId="startTimeBegin" styleClass="Wdate" style="width: 105"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate: '%y-%M-%d-%H-%m-%s'})"
							readonly="true" />
						--
						<html:text property="startTimeEnd" name="troubleQueryStatBean" styleId="startTimeEnd" styleClass="Wdate" style="width: 105"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'startTimeBegin\')}',maxDate: '%y-%M-%d-%H-%m-%s'})"
							readonly="true" />
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						���ϸ����ά��
					</td>
					<td class="tdulright">
						<html:select property="contractorid" styleClass="inputtext" style="width:140px">
							<html:option value="">ȫ��</html:option>
						<html:options collection="cons" property="contractorid" labelProperty="contractorname"></html:options>
						</html:select>
					</td>
					<td class="tdulleft">
						�豸�����أ�
					</td>
					<td class="tdulright" colspan="3">
						<apptag:quickLoadList cssClass="checkbox" id="termiAddr"
							name="termiAddr" listName="terminal_address" keyValue="${otherCon.termiAddr}" type="checkbox"></apptag:quickLoadList>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdulleft">
						�������ͣ�
					</td>
					<td class="tdulright" colspan="2">
						<apptag:quickLoadList cssClass="checkbox" id="troubleType"
							name="troubleType" listName="lp_trouble_type" keyValue="${otherCon.troubleType}" type="checkbox"></apptag:quickLoadList>
					</td>
					<td class="tdulleft">
						�Ƿ�Ϊ�ش���ϣ�
					</td>
					<td class="tdulright" colspan="2">
						<html:multibox property="isGreatTrouble" value="1" />��&nbsp;&nbsp;
						<html:multibox property="isGreatTrouble" value="0" />��
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						����ԭ��
					</td>
					<td class="tdulright" colspan="5">
						<apptag:quickLoadList cssClass="checkbox" id="troublReason"
							name="troublReason" listName="trouble_reason_id" keyValue="${otherCon.troublReason}" type="checkbox"></apptag:quickLoadList>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdulleft">
						�м̶Σ�
					</td>
					<td class="tdulright" colspan="5">
						<apptag:trunk id="trunk" value="${otherCon.trunks}" state="edit"/>
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						eoms���ţ�
					</td>
					<td class="tdulright" colspan="5">
						<html:text property="eomscode" styleClass="inputtext" name="troubleQueryStatBean" styleId="eomscode" style="width: 225px" />
					</td>
			</table>
			<div align="center">
				<input type="hidden" name="isQuery" value="true" />
				<input name="action" class="button" value="��ѯ" type="submit" />
			</div>
		</html:form>
		<template:displayHide styleId="statTroubleInfo"></template:displayHide>

		<logic:notEmpty name="stattroubles">
			<script type="text/javascript" language="javascript">
	toViewForm = function(idValue) {
		window.location.href = "${ctx}/troubleQueryStatAction.do?method=viewModelByID&id="
				+ idValue;
	}

	exportList = function() {
		var url = "${ctx}/troubleQueryStatAction.do?method=exportTroubleStatList";
		self.location.replace(url);
	}

	function goBack() {
		var url = "${ctx}/troubleQueryStatAction.do?method=statTroubleForm";
		self.location.replace(url);
	}
</script>
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<style type="text/css">
.subject {
	text-align: center;
}
</style>
			<display:table name="sessionScope.stattroubles" id="currentRowObject"
				pagesize="15">
				<display:column property="trouble_id" title="���ϵ����"
					headerClass="subject" sortable="true" />
				<display:column property="trouble_name" sortable="true" title="��������"
					headerClass="subject" />
				<display:column property="contractorname" title="���ϴ���λ"
					headerClass="subject" sortable="true" />
				<display:column property="teraddr" title="�豸������"
					headerClass="subject" sortable="true" />
				<display:column property="trouble_type" title="��������"
					headerClass="subject" sortable="true" />
				<display:column property="replytime" title="������ʱ��Сʱ��"
					headerClass="subject" sortable="true" />
				<display:column property="etime" title="������ʱ��Сʱ��"
					headerClass="subject" sortable="true" />
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td>
						<logic:notEmpty name="stattroubles">
							<a href="javascript:exportList()">����ΪExcel�ļ�</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="����" onclick="goBack();"
							type="button" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>
