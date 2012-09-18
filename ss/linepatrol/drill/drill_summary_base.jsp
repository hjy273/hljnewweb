<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>演练总结基本数据</title>
		<script type="text/javascript">
			toViewDrillPlanModify=function(planId){
            	//window.location.href = "${ctx}/drillPlanModifyAction.do?method=viewDrillPlanModify&planModifyId="+planModifyId;
				var url = "${ctx}/drillPlanModifyAction.do?method=viewDrillPlanModifyByPlanId&planId="+planId;
				win = new Ext.Window({
					layout : 'fit',
					width:650,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
		</script>
	</head>
	<body>
		<table align="center" style="border-bottom:0px;" cellpadding="1" cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">演练名称：</td>
				<td class="tdulright">
					<c:out value="${drillTask.name}"></c:out>
				</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatDate  value="${drillTask.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
				<fmt:formatDate  value="${drillTask.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
				<td class="tdulleft" style="width:20%;">建议演练时间：</td>
				<td class="tdulright">
					<c:out value="${formatBeginTime}"></c:out> - <c:out value="${formatEndTime}"></c:out>
					<input type="hidden" value="${formatBeginTime }" id="beginTime">
					<input type="hidden" value="${formatEndTime }" id="endTime">
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划提交时限：</td>
				<td class="tdulright">
					<bean:write name='drillTask' property='deadline' format='yyyy/MM/dd HH:mm:ss' />
				</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatDate  value="${drillPlan.realBeginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime"/>
				<fmt:formatDate  value="${drillPlan.realEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
				<td class="tdulleft" style="width:20%;">实际演练时间：</td>
				<td class="tdulright">
					<c:out value="${formatRealBeginTime}"></c:out> - <c:out value="${formatRealEndTime}"></c:out>
					<input type="hidden" value="${formatRealBeginTime }" id="realBeginTime">
					<input type="hidden" value="${formatRealEndTime }" id="realEndTime">
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">总结提交时限：</td>
				<td class="tdulright">
					<bean:write name='drillPlan' property='deadline' format='yyyy/MM/dd HH:mm:ss' />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划演练地点：</td>
				<td class="tdulright">
					<c:out value="${drillTask.locale}"></c:out>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">实际演练地点：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.address}"></c:out>
				</td>
			</tr>
			<c:if test="${not empty list}">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">演练方案变更：</td>
					<td class="tdulright">
						<a style="color: blue;cursor: pointer;" onclick="toViewDrillPlanModify('${drillPlan.id}')">演练方案变更信息</a>
					</td>
				</tr>
			</c:if>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划投入人数：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.personNumber}"></c:out> 人
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">实际投入人数：</td>
				<td class="tdulright">
					<c:out value="${drillSummary.personNumber }" /> 人
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划投入车辆：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.carNumber}"></c:out> 辆
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">实际投入车辆：</td>
				<td class="tdulright">
					<c:out value="${drillSummary.carNumber }" /> 辆
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划投入设备数：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.equipmentNumber}"></c:out> 套
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">实际投入设备数：</td>
				<td class="tdulright">
					<c:out value="${drillSummary.equipmentNumber }" /> 套
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">场景设置：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.scenario}"></c:out>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">备注：</td>
				<td class="tdulright">
					<c:out value="${drillPlan.remark}"></c:out>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">方案附件：</td>
				<td class="tdulright">
					<apptag:upload cssClass="" id="drillPlanAttch" entityId="${drillPlan.id }" entityType="LP_DRILLPLAN" state="look"/>
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
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">演练总结：</td>
				<td class="tdulright">
					<c:out value="${drillSummary.summary}" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">总结附件：</td>
				<td class="tdulright">
					<apptag:upload cssClass="" entityId="${drillSummary.id }" entityType="LP_DRILLSUMMARY" state="look"/>
				</td>
			</tr>
			<c:if test="${not empty planDays}">
				<tr class="trcolor">
					<td class="tdulright" colspan="2">
						<c:if test="${drillPlan.createTime>drillTask.deadline}">
							<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;演练方案提交超时，超时<bean:write name="planDays" format="#.##"/>小时</font>
						</c:if>
						<c:if test="${drillPlan.createTime<=drillTask.deadline}">
							<font color="blue">&nbsp;&nbsp;&nbsp;&nbsp;演练方案提交提前，提前<bean:write name="planDays" format="#.##"/>小时</font>
						</c:if>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty sumDays}">
				<tr class="trcolor">
					<td class="tdulright" colspan="4">
						<c:if test="${drillSummary.createTime>drillPlan.deadline}">
							<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;演练总结提交超时，超时<bean:write name="sumDays" format="#.##"/>小时</font>
						</c:if>
						<c:if test="${drillSummary.createTime<=drillPlan.deadline}">
							<font color="blue">&nbsp;&nbsp;&nbsp;&nbsp;演练总结提交提前，提前<bean:write name="sumDays" format="#.##"/>小时</font>
						</c:if>
					</td>
				</tr>
			</c:if>
			<tr class="trcolor">
				<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;方案审核信息：</td>
			</tr>
			<tr class="trcolor">
				<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${drillPlan.id}" objectType="LP_DRILLPLAN" colSpan="2" />
			</tr>
			<c:if test="${haveApproveInfo=='yes'}">
				<tr class="trcolor">
					<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;总结审核信息：</td>
				</tr>
				<tr class="trcolor">
					<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${drillSummary.id}" objectType="LP_DRILLSUMMARY" colSpan="2" />
				</tr>
			</c:if>
		</table>
	</body>
</html>
