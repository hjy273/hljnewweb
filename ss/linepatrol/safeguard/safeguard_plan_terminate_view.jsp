<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>查看保障方案</title>
		<style type="text/css">
			.tablelist{
				border-style:solid;
				border-color:#9A9A9A;
				border-width:1px 0 0 0;
				width:100%;
				border-collapse:collapse; 
				border-spacing:0; 
			}
			.tablelisttd{
				padding: 0; 
				border-style : solid;
				border-width: 0 0 1px 0;
				border-color : #9A9A9A;
			}
		</style>
		<script type="text/javascript">
			function showSpecPlan(spplanId){
				var url = "${ctx}/specialEndPlanAction.do?method=specialEndPlanForward&id="+spplanId+"&type=view"+"&isApply=false&flag=safeguard";
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
			function endSpecPlan(spid){
				window.location.href="${ctx}/specialEndPlanAction.do?method=specialEndPlanForward&id="+spid+"&type=add";
			}
			function viewPlanStat(id){
				window.location.href = '${ctx}/linepatrol/specialplan/stat/stat.jsp?type=002&id='+id;
			}
			function viewAllPlanStat(id){
				window.location.href = '${ctx}/linepatrol/specialplan/stat/allStat.jsp?id='+id;
			}
			toViewFinishSafeguard=function(conId){
            	var url = "${ctx}/process_history.do?method=showProcessHistoryList&&object_type=safeguard&&is_close=0&&object_id="+conId;
            	processWin = new Ext.Window({
				layout : 'fit',
				width:750,height:400, 
				resizable:true,
				closeAction : 'close', 
				modal:true,
				autoScroll:true,
				autoLoad:{url: url,scripts:true}, 
				plain: true,
				title:"查看保障流程处理信息" 
			});
			processWin.show(Ext.getBody());
			}
			closeProcessWin=function(){
				processWin.close();
			}
		</script>
	</head>
	<body>
		<template:titile value="查看保障方案"/>
		<jsp:include page="safeguard_task_base.jsp"></jsp:include>
		<table align="center" cellpadding="1" style="border-bottom:0px;border-top: 0px;" cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">涉及到的中继段：</td>
				<td class="tdulright" colspan="3">
					<c:if test="${safeguardPlan.isAllNet==0 || safeguardPlan.isAllNet==null}">
						<apptag:trunk id="trunk" state="view" value="${sublineIds}" />
					</c:if>
					<c:if test="${safeguardPlan.isAllNet==1}">
						全网保障
					</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划出动人员：</td>
				<td class="tdulright"  style="width:30%;">
					<c:out value="${safeguardPlan.planResponder}"/> 人
				</td>
				<td class="tdulleft" style="width:20%;">计划出动车辆：</td>
				<td class="tdulright" style="width:30%;">
					<c:out value="${safeguardPlan.planRespondingUnit}"/> 辆
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">计划投入设备数：</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardPlan.equipmentNumber}"/> 套
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">备注：</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardPlan.remark}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">方案附件：</td>
				<td class="tdulright" colspan="3">
					<apptag:upload cssClass="" entityId="${safeguardPlan.id}" entityType="LP_SAFEGUARD_PLAN" state="look"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft">
					特巡计划：
				</td>
				<td class="tdulright" colspan="3">
					<c:if test="${not empty safeguardSps}">
						<table class="tablelist">
							<c:forEach items="${specialPlans}" var="specialPlan">
								<tr class="trcolor">
									<c:forEach items="${specialPlan}" var="sp">
										<td class="tablelisttd">${sp['plan_name']}&nbsp;&nbsp;</td>
										<td class="tablelisttd">${sp['start_date']} -- ${sp['end_date']}&nbsp;&nbsp;</td>
										<td class="tablelisttd">
											<a onclick=showSpecPlan("${sp['id']}") style="color: blue;cursor: pointer;">查看</a>&nbsp;&nbsp;
										</td>
										<td class="tablelisttd">
											<c:if test="${sp['patrol_stat_state']=='1'}">
												<a onclick=viewPlanStat("${sp['id']}") style="color: blue;cursor: pointer;">查看统计结果</a>&nbsp;&nbsp;
											</c:if>
											<c:if test="${sp['patrol_stat_state']!='1'}">
												
											</c:if>
										</td>
										<td class="tablelisttd">
											<c:if test="${sp['flag']=='no'}">
												<a onclick=endSpecPlan("${sp['id']}") style="color: blue;cursor: pointer;">终止计划</a>&nbsp;&nbsp;
											</c:if>
											<c:if test="${sp['flag']=='yes'}">
												计划执行中
											</c:if>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${safeguardPlan.id}" objectType="LP_SAFEGUARD_PLAN" colSpan="4" />
			</tr>
		</table>
		<br>
		<div align="center" style="height:40px">
			<html:button property="button" styleClass="lbutton" onclick="toViewFinishSafeguard('${conId}')">查看流程历史</html:button>&nbsp;&nbsp;
			<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
		</div>
	</body>
</html>
