<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>审核测试计划</title>
		<script type='text/javascript'>
		
	    </script>
	</head>

	<body>
		<template:titile value="查询测试计划" />
		<div style="text-align: center;">
			<iframe
				src="${ctx}/testPlanAction.do?method=viewTestPlanProcess&&forward=query_test_plan_state&&task_names=${task_names }"
				frameborder="0" id="processGraphFrame" height="70" scrolling="no"
				width="95%"></iframe>
		</div>
		<html:form action="/testPlanQueryStatAction.do?method=getTestPlans"
			styleId="queryForm">
			<table width="100%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<template:formTr name="计划测试开始日期">
					<html:text property="planBeginTimeStart" styleId="planBeginTimeStart" styleClass="Wdate" style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"></html:text>
					至
					<html:text property="planBeginTimeEnd" styleId="planBeginTimeEnd" styleClass="Wdate" style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate: '#F{$dp.$D(\'planBeginTimeStart\')}'})"></html:text>
				</template:formTr>
				<template:formTr name="计划测试结束日期">
					<html:text property="planEndTimeStart" styleId="planEndTimeStart" styleClass="Wdate" style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					至
					<html:text property="planEndTimeEnd" styleId="planEndTimeEnd" styleClass="Wdate" style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate: '#F{$dp.$D(\'planEndTimeStart\')}'})"></html:text>
				</template:formTr>
				<tr class=trcolor>
					<td class="tdulleft">
						计划名称：
					</td>
					<td class="tdulright">
						<html:text property="planName" styleId="planName" style="width:205" styleClass="inputtext"></html:text>
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						制定人：
					</td>
					<td class="tdulright">
						<html:text property="createMan" styleId="createMan" style="width:205" styleClass="inputtext"></html:text>
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						代维公司：
					</td>
					<td class="tdulright">
						<c:if test="${deptype=='1'}">
							<html:select property="contractorId" styleId="contractorId" styleClass="inputtext" style="width:140px">
								<html:option value="">全部</html:option>
								<html:options collection="list" property="contractorID" labelProperty="contractorName"></html:options>
							</html:select>
						</c:if>
						<c:if test="${deptype=='2'}">
							<input type="hidden" value="${sessionScope.conId }" name="contractorId" />
							<c:out value="${sessionScope.conName}"></c:out>
						</c:if>
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						是否取消:
					</td>
					<td class="tdulright">
						<html:select property="testState" styleClass="inputtext" style="width:140px">
							<html:option value="">不限</html:option>
							<html:option value="999">是</html:option>
							<html:option value="0">否</html:option>
						</html:select>
					</td>
				</tr>
				<template:formTr name="测试类型">
					<html:multibox property="planType" value="'1'" />备纤测试
					<html:multibox property="planType" value="'2'" />接地电阻测试
				</template:formTr>
				<td colspan="2" style="text-align: center;">
					<input type="hidden" name="isQuery" value="true" />
					<input name="action" class="button" value="查询" type="submit" />
				</td>
			</table>
			<logic:present name="queryCondition" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondition"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		<logic:notEmpty name="plans">
			<script type="text/javascript">
		 toViewForm=function(idValue){
            window.location.href = "${ctx}/testPlanAction.do?method=viewPaln&planId="+idValue;
		}
		
		exportList=function(){
			//var url="${ctx}/troubleReplyAction.do?method=exportWaitReplyList";
			//self.location.replace(url);
		}
		//取消流程
		toCancelForm=function(planId){
			var url;
			if(confirm("确定要取消该技术维护流程吗？")){
				url="${ctx}/testPlanAction.do?method=cancelTestPlanForm";
				var queryString="test_plan_id="+planId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
       	function  goBack(){
			var url="${ctx}/testPlanQueryStatAction.do?method=queryTestPlanForm";
			self.location.replace(url);
		}
		</script>
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<style type="text/css">
.subject {
	text-align: center;
}
</style>
			<display:table name="sessionScope.plans" id="currentRowObject"
				pagesize="15">
				<logic:notEmpty name="currentRowObject">
					<bean:define id="pid" name="currentRowObject" property="id"></bean:define>
					<bean:define id="testState" name="currentRowObject" property="planstate"></bean:define>
					<display:column property="contractorname" sortable="true"
						title="代维单位" headerClass="subject" />
					<display:column media="html" title="测试计划名称">
						<a href="javascript:toViewForm('<%=pid %>')"> <bean:write
								name="currentRowObject" property="test_plan_name" />
						</a>
					</display:column>
					<display:column property="plantime" sortable="true" title="测试计划时间"
						headerClass="subject" />
					<display:column property="plantype" sortable="true" title="测试类型"
						headerClass="subject" />
					<display:column property="plannum" sortable="true" title="计划测试数量"
						headerClass="subject" />
					<display:column property="testnum" sortable="true" title="实际测试数量"
						headerClass="subject" />
					<display:column property="state" sortable="true" title="状态"
						headerClass="subject" />
					<display:column media="html" title="操作">
						<a href="javascript:toViewForm('<%=pid %>')">查看</a>
						<c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&testState!='999'&&testState!='50'}">
					| <a href="javascript:toCancelForm('<%=pid%>')">取消</a>
				</c:if>
					</display:column>
				</logic:notEmpty>
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td>
						<logic:notEmpty name="waitPlans1">
							<a href="javascript:exportList()">导出为Excel文件</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="返回" onclick="history.back();"
							type="button" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>
