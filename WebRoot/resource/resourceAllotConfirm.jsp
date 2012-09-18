<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<head>
	<script language="javascript" type="text/javascript">
	function check(){
		if(!confirm("��ȷ��Ҫ������Щ��Դ��${sessionScope.NEW_CONTRACTOR_NAME }${sessionScope.PATROLMAN_NAME}��${sessionScope.NEW_PATROLMAN_NAME}��")){
			return false;
		}
		return true;
	}
	function goBack(){
		location="${ctx}/resourceAllotAction_input.jspx";
	}
</script>
</head>
<body>
	<template:titile value="������Դ����ȷ��" />
	<s:form action="resourceAllotAction_allot" name="resourceAllotForm"
		method="post" onsubmit="return check();">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					ԭ��ά��λ��
				</td>
				<td class="tdulright" style="width: 85%">
					<input name="parameter.oldMaintenceId" id="oldMaintenceId"
						value="${parameter.oldMaintenceId }" type="hidden" />
					<input name="parameter.oldPatrolmanId" id="oldMaintenceId"
						value="${parameter.oldPatrolmanId }" type="hidden" />
					<input name="parameter.newMaintenceId" id="newMaintenceId"
						value="${parameter.newMaintenceId }" type="hidden" />
					<input name="parameter.newPatrolmanId" id="newPatrolManId"
						value="${parameter.newPatrolmanId }" type="hidden" />
					<input name="parameter.resourceType" id="resourceType"
						value="${parameter.resourceType }" type="hidden" />
					${sessionScope.OLD_CONTRACTOR_NAME }
					${sessionScope.OLD_PATROLMAN_NAME }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					������ά����λ��
				</td>
				<td class="tdulright" style="width: 85%">
					${sessionScope.NEW_CONTRACTOR_NAME }
					${sessionScope.NEW_PATROLMAN_NAME }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��Դ���ͣ�
				</td>
				<td class="tdulright" style="width: 85%">
					${sessionScope.RESOURCE_TYPE_MAP[parameter.resourceType] }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%; border-right: 0px;">
					ѡ����Դ��
				</td>
				<td class="tdulright" style="width: 85%; border-left: 0px;">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="2"
					style="text-align: center; padding: 5px;">
					<display:table name="sessionScope.RESOURCE_LIST" id="row"
						export="fasle">
						<display:column title="��Դ����">
							${row.resource_name}
							<input name="parameter.newResources" value="${row.id}"
								type="hidden" />
						</display:column>
						<display:column property="resource_type" title="��Դ����" />
						<display:column property="resource_code" title="��Դ���" />
						<display:column property="contractorname" title="ԭά����λ" />
						<display:column property="patrolname" title="ԭά����" />
					</display:table>
				</td>
			</tr>
		</table>
		<table width="40%" border="0"
			style="margin-top: 6px; border: 0px solid #B2DCF6;" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td style="text-align: center; border: 0px;">
					<logic:notEmpty name="RESOURCE_LIST" scope="session">
						<input type="submit" class="button" value="ȷ ��">
					</logic:notEmpty>
					<input type="button" class="button" value="����ѡ��"
						onclick="goBack();">
				</td>
			</tr>
		</table>
	</s:form>
</body>
