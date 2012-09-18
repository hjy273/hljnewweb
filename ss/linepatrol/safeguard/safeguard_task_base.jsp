<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>���������ɵ���������</title>
	</head>
	<body>
		<table align="center" style="border-bottom:0px;"  cellpadding="1" cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�������ƣ�</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardTask.safeguardName}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">����ʱ�䣺</td>
				<td class="tdulright" >
					<fmt:formatDate  value="${safeguardTask.startDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatStartDate"/>
					<fmt:formatDate  value="${safeguardTask.endDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndDate"/>
					<c:out value="${formatStartDate}"/> - <c:out value="${formatEndDate}"/>
				</td>
				<td class="tdulleft" style="width:20%;">�����ύʱ�ޣ�</td>
				<td class="tdulright" >
					<bean:write name="safeguardTask" property="deadline" format="yyyy/MM/dd HH:mm:ss"/>
				</td>
			</tr>
				
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">���ϼ���</td>
				<td class="tdulright" style="width:30%;">
					<c:if test="${safeguardTask.level=='4'}">�ؼ�</c:if>
					<c:if test="${safeguardTask.level=='1'}">һ��</c:if>
					<c:if test="${safeguardTask.level=='2'}">����</c:if>
					<c:if test="${safeguardTask.level=='3'}">����</c:if>
				</td>
				<td class="tdulleft" style="width:20%;">���ϵص㣺</td>
				<td class="tdulright" style="width:30%;">
					<c:out value="${safeguardTask.region}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">����Ҫ��</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardTask.requirement}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">���ϱ�ע��</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardTask.remark }"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">���񸽼���</td>
				<td class="tdulright" colspan="3">
					<apptag:upload cssClass="" entityId="${safeguardTask.id}" entityType="LP_SAFEGUARD_TASK" state="look"/>
				</td>
			</tr>
			<c:if test="${safeguardTask.cancelUserId!=null&&safeguardTask.cancelUserId!=''}">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						${safeguardTask.cancelUserName}
					</td>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						${safeguardTask.cancelTimeDis}
					</td>
				</tr>
					
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl" colspan="3">
						${safeguardTask.cancelReason}
					</td>
				</tr>
			</c:if>
		</table>
	</body>
</html>
