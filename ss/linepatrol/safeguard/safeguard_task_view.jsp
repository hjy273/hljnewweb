<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>保障任务派单</title>
		<script type="text/javascript">
			//查看申请
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
		<template:titile value="保障任务派单"/>
		<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障任务名称：</td>
				<td class="tdulright">
					<c:out value="${safeguardTask.safeguardName}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障时间：</td>
				<td class="tdulright">
					<fmt:formatDate  value="${safeguardTask.startDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatStartDate"/>
					<fmt:formatDate  value="${safeguardTask.endDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndDate"/>
					<c:out value="${formatStartDate}"/> - <c:out value="${formatEndDate}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">方案提交时限：</td>
				<td class="tdulright">
					<bean:write name="safeguardTask" property="deadline" format="yyyy/MM/dd HH:mm:ss"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障级别：</td>
				<td class="tdulright">
					<c:if test="${safeguardTask.level=='4'}">特级</c:if>
					<c:if test="${safeguardTask.level=='1'}">一级</c:if>
					<c:if test="${safeguardTask.level=='2'}">二级</c:if>
					<c:if test="${safeguardTask.level=='3'}">三级</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障地点：</td>
				<td class="tdulright">
					<c:out value="${safeguardTask.region}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障要求：</td>
				<td class="tdulright">
					<c:out value="${safeguardTask.requirement}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障备注：</td>
				<td class="tdulright">
					<c:out value="${safeguardTask.remark }"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">任务附件：</td>
				<td class="tdulright">
					<apptag:upload cssClass="" entityId="${safeguardTask.id}" entityType="LP_SAFEGUARD_TASK" state="look"/>
				</td>
			</tr>
			<c:if test="${safeguardTask.cancelUserId!=null&&safeguardTask.cancelUserId!=''}">
				<tr class=trcolor>
					<td class="tdr">
						取消人：
					</td>
					<td class="tdl">
						${safeguardTask.cancelUserName}
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消时间：
					</td>
					<td class="tdl">
						${safeguardTask.cancelTimeDis}
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消原因：
					</td>
					<td class="tdl">
						${safeguardTask.cancelReason}
					</td>
				</tr>
			</c:if>
		</table>
		<br/>
		<div align="center" style="height:40px">
			<html:button property="button" styleClass="lbutton" onclick="toViewFinishSafeguard('${conId}')">查看流程历史</html:button>&nbsp;&nbsp;
			<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
		</div>
	</body>
</html>
