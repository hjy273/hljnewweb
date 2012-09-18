<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>

<html>
	<head>
		<title>����������������</title>
	</head>
	<body>
		<table align="center" cellpadding="1" cellspacing="0" style="border-bottom:0px;" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�������ƣ�</td>
				<td class="tdulright">
					<c:out value="${drillTask.name}"></c:out>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">��������</td>
				<td class="tdulright">
					<c:if test="${drillTask.level=='1'}">
						һ������
					</c:if>
					<c:if test="${drillTask.level=='2'}">
						�ص�����
					</c:if>
					<c:if test="${drillTask.level=='0'}">
						�Զ�������
					</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">��������ʱ�䣺</td>
				<td class="tdulright">
					<fmt:formatDate  value="${drillTask.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
					<fmt:formatDate  value="${drillTask.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
					<c:out value="${formatBeginTime}"/> - <c:out value="${formatEndTime}"/>
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
				<td class="tdulleft" style="width:20%;">�����ص㣺</td>
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
				<td class="tdulleft" style="width:20%;">��ע��</td>
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
			<c:if test="${drillTask.cancelUserId!=null&&drillTask.cancelUserId!=''}">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						${drillTask.cancelUserName}
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						${drillTask.cancelTimeDis}
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl">
						${drillTask.cancelReason}
					</td>
				</tr>
			</c:if>
		</table>
	</body>
</html>
