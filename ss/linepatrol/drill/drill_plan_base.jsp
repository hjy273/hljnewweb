<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>

<html>
	<head>
		<title>演练方案基本数据</title>
	</head>
	<body>
		<table align="center" cellpadding="1" cellspacing="0" style="border-bottom:0px;" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">演练名称：</td>
				<td class="tdulright">
					<c:out value="${drillTask.name }"/>
				</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatDate  value="${drillTask.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
				<fmt:formatDate  value="${drillTask.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
				<td class="tdulleft" style="width:20%;">建议演练时间：</td>
				<td class="tdulright">
					<c:out value="${formatBeginTime }"/> - <c:out value="${formatEndTime }"/>
					<input type="hidden" value="<c:out value='${formatBeginTime }'/>" id="beginTime">
					<input type="hidden" value="<c:out value='${formatEndTime }'/>" id="endTime">
				</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatDate  value="${drillPlan.realBeginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime"/>
				<fmt:formatDate  value="${drillPlan.realEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
				<td class="tdulleft" style="width:20%;">计划演练时间：</td>
				<td class="tdulright">
					<c:out value="${formatRealBeginTime }"/> - <c:out value="${formatRealEndTime }"/>
					<input type="hidden" value="<c:out value='${formatRealBeginTime }'/>" id="realBeginTime">
					<input type="hidden" value="<c:out value='${formatRealEndTime }'/>" id="realEndTime">
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划演练地点：</td>
				<td class="tdulright">
					<c:out value="${drillTask.locale }" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">实际演练地点：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.address }" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划投入人数：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.personNumber }"/> 人
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划投入车辆：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.carNumber }"/> 辆
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划投入设备数：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.equipmentNumber }"/> 套
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">场景设置：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.scenario}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">备注：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.remark}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">方案附件：</td>
				<td class="tdulright">
					<apptag:upload cssClass="" entityId="${drillPlan.id }" entityType="LP_DRILLPLAN" state="look"/>
				</td>
			</tr>
			<c:if test="${drillTask.cancelUserId!=null&&drillTask.cancelUserId!=''}">
				<tr class=trcolor>
					<td class="tdr">
						取消人：
					</td>
					<td class="tdl">
						${drillTask.cancelUserName}
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消时间：
					</td>
					<td class="tdl">
						${drillTask.cancelTimeDis}
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消原因：
					</td>
					<td class="tdl">
						${drillTask.cancelReason}
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty drillPlan.deadline }">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">总结提交时限：</td>
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
