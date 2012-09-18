<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>查看测试计划详细信息</title>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/xtheme-gray.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
		  var win;
			function viewPalnDetail(){
			  var planid = document.getElementById("planid").value;
			  url="${ctx}/testPlanAction.do?method=viewPalnDetail&planId="+planid;
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.9 , 
              height:420 , 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+url+' />',
			  plain: true
			 });
			  win.show(Ext.getBody());
			}
			 function close(){
			  	win.close();
			 }
			 
			 //历史流程
			function viewHistoryDetail(){
			 var planid = document.getElementById('planid').value;
			 var u="${ctx}/process_history.do?method=showProcessHistoryList&object_type=maintenance&is_close=0&object_id="+planid;
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.55 , 
			  height:330, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  autoLoad:{url:u,scripts:true}, 
			 // html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+' />',
			  plain: true,
			  title:"流程历史" 
			 });
			  win.show(Ext.getBody());
			}
			  
			 function closeProcessWin(){
			  	win.close();
			 }
	</script>
	</head>

	<body>
		<template:titile value="查看测试计划详细信息" />
		<html:form action="/testPlanAction.do?method=readPlan">
			<table width="85%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout" style="border-bottom: 0px;">
				<input type="hidden" name="planid" id="planid" value="${plan.id}" />
				<input type="hidden" name="dataid" id="dataid" value="${dataid}" />
				<tr class=trcolor>
					<td class="tdulleft">
						代维单位：
					</td>
					<td class="tdulright">
						<c:out value="${c.contractorName}"></c:out>
					</td>
					<td class="tdulleft">
						计划制定人：
					</td>
					<td class="tdulright">
						<c:out value="${user.userName}"></c:out>
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						计划名称：
					</td>
					<td class="tdulright">
						<c:out value="${plan.testPlanName}"></c:out>
					</td>
					<td colspan="2">
						<a href="javascript:viewPalnDetail();">查看计划详细信息</a>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdulleft">
						计划类型：
					</td>
					<td class="tdulright" colspan="3">
						<c:if test="${plan.testPlanType=='1'}">备纤测试</c:if>
						<c:if test="${plan.testPlanType=='2'}">接地电阻测试</c:if>
						<c:if test="${plan.testPlanType=='3'}"> 金属护套绝缘电阻测试</c:if>
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						计划测试日期：
					</td>
					<td class="tdulright" colspan="3">
						<bean:write name="plan" property="testBeginDate"
							format="yyyy-MM-dd" />
						至
						<bean:write name="plan" property="testEndDate" format="yyyy-MM-dd" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdulleft">
						测试计划备注：
					</td>
					<td class="tdulright" colspan="3"
						style="word-break: break-all; width: 500px;">
						<c:out value="${plan.testPlanRemark}"></c:out>
					</td>
				</tr>
				<logic:notEmpty property="cancelUserId" name="plan">
					<tr class=trcolor>
						<td class="tdr">
							取消人：
						</td>
						<td class="tdl">
							<bean:write property="cancelUserName" name="plan" />
						</td>
						<td class="tdr">
							取消时间：
						</td>
						<td class="tdl">
							<bean:write property="cancelTimeDis" name="plan" />
						</td>
					</tr>
					<tr class=trcolor>
						<td class="tdr">
							取消原因：
						</td>
						<td class="tdl" colspan="3">
							<bean:write property="cancelReason" name="plan" />
						</td>
					</tr>
				</logic:notEmpty>
				<logic:notEmpty name="planApproves">
					<tr class=trcolor>
						<td class="tdulleft">
							测试计划审核次数：
						</td>
						<td class="tdulright" colspan="3">
							<c:out value="${plan.approveTimes}"></c:out>
						</td>
					</tr>
					<tr class=trwhite align="center">
						<td colspan="4" align="left">
							&nbsp;&nbsp;计划审核详细信息
						</td>
					</tr>
					<tr class=trcolor>
						<td width="15%">
							&nbsp;&nbsp;审核人
						</td>
						<td width="20%">
							审核时间
						</td>
						<td width="10%">
							审核结果
						</td>
						<td width="55%">
							审核意见
						</td>
					</tr>
					<c:forEach items="${planApproves}" var="approve" varStatus="loop">
						<c:if test="${loop.count%2==0 }">
							<tr class=trwhite>
						</c:if>
						<c:if test="${loop.count%2==1 }">
							<tr class=trcolor>
						</c:if>
						<td>
							&nbsp;&nbsp;
							<bean:write name="approve" property="username" />
						</td>
						<td>
							<bean:write name="approve" property="approve_time" />
						</td>
						<td>
							<bean:write name="approve" property="approve_result" />
						</td>
						<td>
							<bean:write name="approve" property="approve_remark" />
						</td>
						</tr>
					</c:forEach>
				</logic:notEmpty>
				</table>
				<apptag:appraiseDailyDaily businessId="${plan.id}"
					contractorId="${plan.contractorId}" businessModule="maintence"
					displayType="view" tableClass="tabout" tableStyle="width:85%;border-bottom: 0px;"></apptag:appraiseDailyDaily>
					<apptag:appraiseDailySpecial businessId="${plan.id}"
					contractorId="${plan.contractorId}" businessModule="maintence"
					displayType="view" tableClass="tabout" tableStyle="width:85%;border-bottom: 0px;"></apptag:appraiseDailySpecial>
					<table width="85%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor align="center">
					<td colspan="5">
						<c:if test="${query=='no' && isread=='true'}">
							<html:submit value="已阅读" styleClass="button"></html:submit>
						</c:if>
						<html:button property="action" styleClass="button"
							onclick="javascript:viewHistoryDetail();">查看流程历史</html:button>
						<input type="button" class="button" value="返回"
							onclick="javascript:history.go(-1);" />
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
