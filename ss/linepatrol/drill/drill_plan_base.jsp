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
					<c:out value="${drillTask.name }"/>
				</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatDate  value="${drillTask.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
				<fmt:formatDate  value="${drillTask.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
				<td class="tdulleft" style="width:20%;">��������ʱ�䣺</td>
				<td class="tdulright">
					<c:out value="${formatBeginTime }"/> - <c:out value="${formatEndTime }"/>
					<input type="hidden" value="<c:out value='${formatBeginTime }'/>" id="beginTime">
					<input type="hidden" value="<c:out value='${formatEndTime }'/>" id="endTime">
				</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatDate  value="${drillPlan.realBeginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime"/>
				<fmt:formatDate  value="${drillPlan.realEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
				<td class="tdulleft" style="width:20%;">�ƻ�����ʱ�䣺</td>
				<td class="tdulright">
					<c:out value="${formatRealBeginTime }"/> - <c:out value="${formatRealEndTime }"/>
					<input type="hidden" value="<c:out value='${formatRealBeginTime }'/>" id="realBeginTime">
					<input type="hidden" value="<c:out value='${formatRealEndTime }'/>" id="realEndTime">
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�ƻ������ص㣺</td>
				<td class="tdulright">
					<c:out value="${drillTask.locale }" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">ʵ�������ص㣺</td>
				<td class="tdulright">
					<c:out value="${drillPlan.address }" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�ƻ�Ͷ��������</td>
				<td class="tdulright">
					<c:out value="${drillPlan.personNumber }"/> ��
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�ƻ�Ͷ�복����</td>
				<td class="tdulright">
					<c:out value="${drillPlan.carNumber }"/> ��
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�ƻ�Ͷ���豸����</td>
				<td class="tdulright">
					<c:out value="${drillPlan.equipmentNumber }"/> ��
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�������ã�</td>
				<td class="tdulright">
					<c:out value="${drillPlan.scenario}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">��ע��</td>
				<td class="tdulright">
					<c:out value="${drillPlan.remark}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">����������</td>
				<td class="tdulright">
					<apptag:upload cssClass="" entityId="${drillPlan.id }" entityType="LP_DRILLPLAN" state="look"/>
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
			<c:if test="${not empty drillPlan.deadline }">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ܽ��ύʱ�ޣ�</td>
					<td class="tdulright">
						<bean:write name="drillPlan" property="deadline" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
				</tr>
			</c:if>
			<tr class="trcolor">
				<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${drillPlan.id}" objectType="LP_DRILLPLAN" colSpan="2" />
			</tr>
		</table>
	</body>
</html>
