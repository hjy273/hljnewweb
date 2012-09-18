<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var jQuery=$;
</script>
<link href="${ctx}/js/jQuery/pikachoose/styles/bottom.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/js/jQuery/pikachoose/lib/jquery.pikachoose.js"></script>
<script type="text/javascript" src="${ctx }/bs/fault/js/fault_common.js"></script>
<script language="javascript" type="text/javascript">
	function check(){
		return true;
	}
</script>
<body>
	<template:titile value="���ϻص����" />
	<s:form action="faultAuditAction_audit" name="form" method="post"
		onsubmit="return check()">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<c:if test="${fault_alert.findType=='B16' }">
				<tr class="trwhite">
					<td class="tdulright" colspan="4" style="width: 100%">
						<b>����ͼƬ</b>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulright" colspan="4"
						style="width: 100%; padding: 5px; text-align: center;"
						id="photosTd">
					</td>
				</tr>
			</c:if>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>���ϵ���Ϣ</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���ϱ��⣺
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input name="faultAudit.workflowTaskId"
						value="${faultAudit.workflowTaskId}" type="hidden" />
					<input name="businessType" value="${businessType }" type="hidden" />
					<input name="faultAudit.taskId" value="${fault_dispatch.id }"
						type="hidden" />
					${fault_alert.troubleTitle }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���ַ�ʽ��
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel objType="dic" id="${fault_alert.findType}"
						dicType="FIND_TYPE"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					EMOS���ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_alert.eomsId }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���Ϸ���ʱ�䣺
				</td>
				<td class="tdulright" style="width: 35%">
					<fmt:formatDate value="${fault_alert.troubleTime }"
						pattern="yyyy-MM-dd HH:mm:ss" var="troubleTime" />
					${troubleTime }
				</td>
				<td class="tdulleft" style="width: 15%">
					�Ƿ�������ϣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${fault_alert.isInstancy==1 }">��</c:if>
					<c:if test="${fault_alert.isInstancy==2 }">��</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���ϼ���
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel objType="dic" id="${fault_alert.troubleLevel}"
						dicType="TROUBLE_LEVEL"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					�������ޣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<fmt:formatDate value="${fault_dispatch.deadline }"
						pattern="yyyy-MM-dd HH:mm:ss" var="deadline" />
					${deadline }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����������
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${fault_alert.troubleDesc }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����վ�㣺
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_alert.stationName }
				</td>
				<td class="tdulleft" style="width: 15%">
					���ϵص㣺
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_alert.address }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��ά��˾��
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_dispatch.maintenanceName }
				</td>
				<td class="tdulleft" style="width: 15%">
					ά���飺
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_dispatch.patrolGroupName }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�ɵ��ˣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_dispatch.createrName }
				</td>
				<td class="tdulleft" style="width: 15%">
					�ɵ�ʱ�䣺
				</td>
				<td class="tdulright" style="width: 35%">
					<fmt:formatDate value="${fault_dispatch.sendTime }"
						pattern="yyyy-MM-dd HH:mm:ss" var="sendTime" />
					${sendTime }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��ϵ�绰��
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${fault_dispatch.phone }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��ע��
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${fault_dispatch.remark }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>���ϴ������</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4"
					style="width: 100%; padding: 5px; text-align: center;"
					id="processHistoryTd">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>�����ֳ�ͼƬ</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4"
					style="width: 100%; padding: 5px; text-align: center;"
					id="processPhotosTd">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>���ϱ�����Ϣ</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�����豸��
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_reply.equipment }
				</td>
				<td class="tdulleft" style="width: 15%">
					�Ƿ���ҵ��
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${fault_reply.isGroupbusiness==1 }">��</c:if>
					<c:if test="${fault_reply.isGroupbusiness==2 }">��</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					ҵ��Ӱ�죺
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_reply.influenceBusiness }
				</td>
				<td class="tdulleft" style="width: 15%">
					������ࣺ
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_reply.networkType }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��������
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					${fault_reply.phenomena }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����ԭ��
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					${fault_reply.reason }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���Ͻ��������
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					${fault_reply.solution }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������⣺
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					${fault_reply.leaveBehindProblem }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>���ϻص������Ϣ</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4"
					style="width: 100%; padding: 5px; text-align: center;"
					id="auditHistoryTd">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��˽����
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					<input type="radio" name="faultAudit.isAuditing" value="pass"
						checked />
					ͨ��
					<input type="radio" name="faultAudit.isAuditing" value="reject" />
					��ͨ��
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��˱�ע��
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<textarea name="faultAudit.remark" class="inputtext"
						style="width: 780px; height: 80px;"></textarea>
				</td>
			</tr>
		</table>
		<table width="100%" style="border: 0px">
			<tr class="trwhite">
				<td colspan="4" style="text-align: center; border: 0px">
					<html:submit styleClass="button">���</html:submit>
					<input type="button" class="button" onclick="history.back()"
						value="����">
				</td>
			</tr>
		</table>
	</s:form>
	<script type="text/javascript">
	setContextPath("${ctx}");
	showFaultPhotos('${fault_alert.id }');
	showProcessHistory('${fault_dispatch.id }');
	showProcessPhotos('${fault_dispatch.id }');
	showAuditHistory('${fault_dispatch.id }');
	</script>
</body>