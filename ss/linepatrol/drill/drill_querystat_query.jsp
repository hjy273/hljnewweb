<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>演练查询</title>
		<script type="text/javascript">
			function checkForm(){
				return true;
			}
		</script>
	</head>
	<body>
		<template:titile value="演练查询" />
		<div style="text-align: center;">
			<iframe
				src="${ctx}/drillTaskAction.do?method=viewDrillTaskProcess&&forward=query_drill_task_state&&task_names=${task_names }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>
		<html:form action="/queryStatAction.do?method=queryStat"
			onsubmit="return checkForm();" styleId="queryForm">
			<template:formTable>
				<template:formTr name="代维公司">
					<c:if test="${deptype=='1'}">
						<html:select property="conId" styleId="conId" styleClass="inputtext" style="width:140px">
							<html:option value="">全部</html:option>
						<html:options collection="list" property="contractorID" labelProperty="contractorName"></html:options>
						</html:select>
					</c:if>
					<c:if test="${deptype=='2'}">
						<input type="hidden" value="${conId }" name="conId" />
						<c:out value="${conName}"></c:out>
					</c:if>
				</template:formTr>
				<template:formTr name="是否取消">
					<html:select property="drillState" styleClass="inputtext" style="width:140px">
						<html:option value="">不限</html:option>
						<html:option value="999">是</html:option>
						<html:option value="0">否</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="任务名称">
					<html:text property="name" styleId="name" styleClass="inputtext" style="width:140px"/>
				</template:formTr>
				<template:formTr name="演练时间">
					<html:text property="beginTime" styleClass="Wdate" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
					 -- <html:text property="endTime" styleClass="Wdate validate-date-after-startTime" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="true"/>
				</template:formTr>
				<template:formTr name="演练级别">
				<html:multibox property="levels" value="2" />一般演练
				<html:multibox property="levels" value="1" />重点演练
				<html:multibox property="levels" value="0" />自定义演练
				</template:formTr>
				<template:formTr name="状态">
					<html:multibox property="state" value="'1'" />任务派单
					<html:multibox property="state" value="'2','3','4','9','10'" />演练方案
					<html:multibox property="state" value="'5','6'" />演练总结
					<html:multibox property="state" value="'7'" />评分
					<html:multibox property="state" value="'8'" />完成
				</template:formTr>
			</template:formTable>
			<logic:present name="queryCondition" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondition"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
			<div align="center" style="height: 40px">
				<input name="isQuery" value="true" type="hidden" />
				<html:submit property="action" styleClass="button">查询</html:submit>
				&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">重写</html:reset>
				&nbsp;&nbsp;
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>

		<logic:notEmpty name="result">
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<link rel='stylesheet' type='text/css'
				href='${ctx}/js/extjs/resources/css/ext-all.css' />
			<script type='text/javascript'
				src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
			<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
			<script type="text/javascript">
			toViewDrill=function(taskId,planId,summaryId,conId){
            	window.location.href = "${ctx}/queryStatAction.do?method=viewDrill&taskId="+taskId+"&planId="+planId+"&conId="+conId+"&summaryId="+summaryId;
			}
			//取消流程
		toCancelForm=function(drillTaskId){
			var url;
			if(confirm("确定要取消该演练流程吗？")){
				url="${ctx}/drillTaskAction.do?method=cancelDrillTaskForm";
				var queryString="drill_task_id="+drillTaskId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				//window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
				window.showModalDialog(url+"&"+queryString+"&rnd="+Math.random(),"","dialogWidth:500px;dialogHeight:350px;center:yes;");
				document.forms[0].submit();
			}
		}
		</script>
			<%
				BasicDynaBean object = null;
					Object taskId = null;
					Object planId = null;
					Object summaryId = null;
					Object conId = null;
					Object taskName = null;
			%>
			<display:table name="sessionScope.result" id="drill" pagesize="18">
			<logic:notEmpty name="result">
				<bean:define id="applyState" name="drill" property="state"></bean:define>
				<bean:define id="createUser" name="drill" property="creator"></bean:define>
				
				<display:column title="演练名称" media="html" sortable="true">
					<%
						object = (BasicDynaBean) pageContext.findAttribute("drill");
									if (object != null) {
										taskId = object.get("task_id");
										taskName = object.get("task_name");
										planId = object.get("plan_id");
										summaryId = object.get("summary_id");
										conId=object.get("taskcon_id");
									}
					%>
					<a
						href="javascript:toViewDrill('<%=taskId%>','<%=planId%>','<%=summaryId%>','<%=conId%>')"><%=taskName%></a>
				</display:column>
				<display:column property="contractorname" title="代维单位"
					headerClass="subject" sortable="true" />
				<display:column property="drill_level" title="演练级别"
					headerClass="subject" sortable="true" />
				<display:column property="task_begintime" title="计划开始时间"
					headerClass="subject" sortable="true" />
				<display:column property="task_endtime" title="计划结束时间"
					headerClass="subject" sortable="true" />
				<display:column property="plan_person_number" title="计划投入人数"
					headerClass="subject" sortable="true" />
				<display:column property="plan_car_number" title="计划投入车辆数"
					headerClass="subject" sortable="true" />
				<display:column property="plan_equipment_number" title="计划投入设备数"
					headerClass="subject" sortable="true" />
				<display:column property="summary_person_number" title="实际投入人数"
					headerClass="subject" sortable="true" />
				<display:column property="summary_car_number" title="实际投入车辆数"
					headerClass="subject" sortable="true" />
				<display:column property="sum_equipment_number" title="实际投入设备数"
					headerClass="subject" sortable="true" />
				<display:column property="createtime" title="派发任务时间"
					headerClass="subject" sortable="true" />
				<display:column media="html" title="操作">
					<a href="javascript:toViewDrill('<%=taskId%>','<%=planId%>','<%=summaryId%>','<%=conId%>')">查看</a>
					
					<c:if test="${sessionScope.LOGIN_USER.userID == createUser }">
						<c:if test="${applyState!='999'}">
						| <a href="javascript:toCancelForm('<%=taskId%>')">取消</a>
						</c:if>
					</c:if>
					
					
				</display:column>
				</logic:notEmpty>
			</display:table>
			<p>
				<html:link action="/queryStatAction.do?method=exportDrillList">导出为Excel文件</html:link>
			</p>
			<div style="height: 50px; text-align: center;">
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</logic:notEmpty>
	</body>
</html>
