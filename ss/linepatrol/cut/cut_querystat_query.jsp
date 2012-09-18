<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>割接申请查询</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
		<script type="text/javascript">
			function judgeWhetherChecked(value){
				for(var i=0; i<value.length; i++){
					if(value[i].checked==true){
						return true;
					}
				}
				return false;
			}
			function submitForm(){
				var flag = true;
				/*var cutLevels=document.getElementsByName("cutLevels");
				var cableLevels=document.getElementsByName("cabletype");
				var states=document.getElementsByName("states");
				if(!judgeWhetherChecked(cutLevels)){
					alert("割接级别最少选择一项！");
					flag=false;
					return;
				}
				if(!judgeWhetherChecked(cableLevels)){
					alert("光缆级别最少选择一项！");
					flag=false;
					return;
				}
				if(!judgeWhetherChecked(states)){
					alert("割接状态最少选择一项！");
					flag=false;
					return;
				}
				if(document.getElementById("beginTime").value.length==0){
					alert("开始时间不能为空！");
					document.getElementById("beginTime").focus();
					flag=false;
					return;
				}
				if(document.getElementById("endTime").value.length==0){
					alert("结束时间不能为空！");
					document.getElementById("endTime").focus();
					flag=false;
					return;
				}*/
				if(flag==true){
					document.getElementById('submitForm1').submit();
				}
			}
		</script>
	</head>
	<body>
		<c:if test="${sessionScope.operator=='query'}">
			<template:titile value="割接申请查询" />
		</c:if>
		<c:if test="${sessionScope.operator=='stat'}">
			<template:titile value="割接统计查询" />
		</c:if>
		<div style="text-align: center;">
			<iframe
				src="${ctx}/cutAction.do?method=viewCutProcess&&forward=query_cut_state&&task_names=${task_names }"
				frameborder="0" id="processGraphFrame" height="75" scrolling="no"
				width="95%"></iframe>
		</div>
		<html:form action="/cutQueryStatAction.do?method=queryCutInfo"
			styleId="submitForm1">
			<table border="1" align="center" cellspacing="0" cellpadding="1"
				class="tabout" width="80%">
				<c:if test="${sessionScope.depttype=='1'}">
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						代维公司：
					</td>
					<td class="tdulright">
						<html:select property="contractorId" styleClass="inputtext" style="width:140px">
							<html:option value="">全部</html:option>
							<html:options property="contractorid" labelProperty="contractorname" collection="list"/>
						</html:select>
					</td>
				</tr>
				</c:if>
				<c:if test="${sessionScope.operator=='query'}">
					<tr class="trcolor">
						<td class="tdulleft" style="width: 20%;">
							是否取消：
						</td>
						<td class="tdulright">
							<select name="cancelState" class="inputtext" style="width:140px">
								<option value="">不限</option>
								<option value="999">是</option>
								<option value="0">否</option>
							</select>
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						割接级别：
					</td>
					<td class="tdulright">
						<html:multibox property="cutLevels" value="1" />一般割接
						<html:multibox property="cutLevels" value="2" />紧急割接
						<html:multibox property="cutLevels" value="3" />预割接
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						光缆级别：
					</td>
					<td class="tdulright">
						<apptag:quickLoadList cssClass="input" name="cabletype"
							listName="cabletype" keyValue="${cableLevels}" type="checkbox" />
					</td>
				</tr>
				
				<c:if test="${sessionScope.operator=='query'}">
					<tr class="trcolor">
						<td class="tdulleft" style="width: 20%;">
						<input type="hidden" value="${operator}" name="operator" />
							状态：
						</td>
						<td class="tdulright">
							<html:multibox property="states" value="1" />割接申请
							<html:multibox property="states" value="2" />申请审核未通过
							<html:multibox property="states" value="3" />待反馈
							<html:multibox property="states" value="4" />反馈待审核
							<br />
							<html:multibox property="states" value="5" />反馈审核未通过
							<html:multibox property="states" value="6" />待验收结算
							<html:multibox property="states" value="7" />验收待审核
							<br />
							<html:multibox property="states" value="8" />验收审核未通过
							<html:multibox property="states" value="9" />考核
							<html:multibox property="states" value="0" />完成
						</td>
					</tr>
				</c:if>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						是否中断业务：
					</td>
					<td class="tdulright">
						<html:radio property="isInterrupt" value="" />全部
						<html:radio property="isInterrupt" value="1" />是
						<html:radio property="isInterrupt" value="0" />否
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						是否超时：
					</td>
					<td class="tdulright">
						<html:radio property="isTimeOut" value="" />全部
						<html:radio property="isTimeOut" value="1" />是
						<html:radio property="isTimeOut" value="0" />否
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						选择时间段：
					</td>
					<td class="tdulright">
						<html:select property="timeType" styleId="timeType">
							<html:option value="1">申请时间</html:option>
							<html:option value="2">实际开始时间</html:option>
							<html:option value="3">审批时间</html:option>
						</html:select>
						<html:text property="beginTime" styleId="beginTime" styleClass="Wdate" style="width: 150" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})" readonly="true" />
						-
						<html:text property="endTime" styleId="endTime" styleClass="Wdate validate-date-after-startTime" style="width:150" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="true"/>
					</td>
				</tr>
			</table>
			<logic:present name="queryCondition" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondition"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
			<div align="center">
				<input type="hidden" name="isQuery" value="true" />
				<html:button property="action" styleClass="button"
					onclick="submitForm()">查询</html:button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">重写</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="submitForm1"></template:displayHide>
		<c:if test="${sessionScope.operator=='query'}">
			<logic:notEmpty name="result">
				<script type="text/javascript">
			exportList=function(){
				var url="${ctx}/cutQueryStatAction.do?method=exportCutList";
				self.location.replace(url);
			};
			toViewForm=function(cutId){
            	window.location.href = "${ctx}/cutQueryStatAction.do?method=viewQueryCut&cutId="+cutId;
			};
		</script>
				<%
					BasicDynaBean object = null;
							Object cutId = null;
							Object workorder_id = null;
				%>
				<display:table name="sessionScope.result" id="cut" pagesize="18">
					<display:column title="工单号" media="html" sortable="true">
						<%
							object = (BasicDynaBean) pageContext.findAttribute("cut");
											if (object != null) {
												cutId = object.get("id");
												workorder_id = object.get("workorder_id");
											}
						%>
						<a href="javascript:toViewForm('<%=cutId%>')"><%=workorder_id%></a>
					</display:column>
					<display:column property="cut_name" title="割接名称"
						headerClass="subject" sortable="true" />
					<display:column property="cut_level" title="割接级别"
						headerClass="subject" sortable="true" />
					<display:column property="contractorname" title="代维单位"
						headerClass="subject" sortable="true" />
					<display:column property="apply_date" title="申请时间"
						headerClass="subject" sortable="true" />
					<display:column property="feedback_hours" title="反馈性(小时)"
						headerClass="subject" sortable="true" />
					<display:column property="contractorname" title="申请人"
						headerClass="subject" sortable="true" />
					<display:column property="cut_state" title="割接状态"
						headerClass="subject" sortable="true" />
				</display:table>
				<p>
					<html:link action="/cutQueryStatAction.do?method=exportCutList">导出为Excel文件</html:link>
				</p>
				<div style="height: 50px; text-align: center;">
					<html:button property="button" styleClass="button"
						onclick="javascript:history.go(-1);">返回</html:button>
				</div>
			</logic:notEmpty>
		</c:if>
		<c:if test="${sessionScope.operator=='stat'}">
			<logic:notEmpty name="result">
				<display:table name="sessionScope.result" id="cut" pagesize="18">
					<display:column property="contractorname" title="代维单位"
						headerClass="subject" sortable="true" />
					<display:column property="cut_number" title="割接数量"
						headerClass="subject" sortable="true" />
					<display:column property="total_budget" title="割接费用(元)"
						headerClass="subject" sortable="true" />
					<display:column property="total_time" title="割接时长(小时)"
						headerClass="subject" sortable="true" />
					<display:column property="total_bs" title="影响基站数"
						headerClass="subject" sortable="true" />
				</display:table>
				<p>
					<html:link action="/cutQueryStatAction.do?method=exportCutStat">导出为Excel文件</html:link>
				</p>
				<br />
				<br />
				<div style="height: 50px; text-align: center;">
					<html:button property="button" styleClass="button"
						onclick="javascript:history.go(-1);">返回</html:button>
				</div>
			</logic:notEmpty>
		</c:if>
	</body>
</html>
