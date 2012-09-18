<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>保障查询</title>
		<script type="text/javascript">
		setAll=function(all){
			var chk = document.safeguardTaskBean.levels;
			if(all.checked==true){
				for (i = 0; i < chk.length; i++){
					chk[i].checked = true ;
				}
			}else{
				for (i = 0; i < chk.length; i++){
					chk[i].checked = false ;
				}
			}
		};
		
		//取消流程
		toCancelForm=function(safeguardTaskId){
			var url;
			if(confirm("确定要取消该保障流程吗？如果取消将结束所有的分发的流程。")){
				url="${ctx}/safeguardTaskAction.do?method=cancelSafeguardTaskForm";
				var queryString="safeguard_task_id="+safeguardTaskId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<%
		Map map = (Map)request.getSession().getAttribute("map");
		String deptype = (String)map.get("deptype");
		List list = (List)map.get("list");
		String conId = (String)map.get("conId");
		String conName = (String)map.get("conName");
		request.setAttribute("deptype",deptype);
		request.setAttribute("list",list);
		request.setAttribute("conId",conId);
		request.setAttribute("conName",conName);
	%>
	<body>
		<c:if test="${sessionScope.operator=='query'}">
			<template:titile value="保障查询"/>
		</c:if>
		<c:if test="${sessionScope.operator=='stat'}">
			<template:titile value="保障统计"/>
		</c:if>
		<div style="text-align: center;">
			<iframe
				src="${ctx}/safeguardTaskAction.do?method=viewSafeGuardProcess&&forward=query_safe_guard_state&&task_names=${task_names }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>
		<html:form action="/safeguardQueryStatAction.do?method=queryStat" enctype="multipart/form-data" styleId="queryForm">
			<input type="hidden" name="operator" value="${operator }"/>
			<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="100%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">保障名称：</td>
					<td class="tdulright">
						<html:text property="safeguardName" styleId="safeguardName" style="width:150px;" styleClass="inputtext"></html:text>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">代维公司：</td>
					<td class="tdulright">
						<c:if test="${deptype=='1'}">
							<html:select property="conId" styleId="conId" styleClass="inputtext" style="width:150px;">
								<html:option value="">全部</html:option>
								<html:options property="contractorID" labelProperty="contractorName" collection="list"/>
							</html:select>
						</c:if>
						<c:if test="${deptype=='2'}">
							<input type="hidden" value="${conId }" name="conId"/>
							<c:out value="${conName}"></c:out>
						</c:if>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">保障时间：</td>
					<td class="tdulright">
						<html:text property="startDate" styleId="startDate" style="width:150px;" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true" />
						―<html:text property="endDate" styleId="startDate" style="width:150px;" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'startDate\')}'})" readonly="true"></html:text>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">保障级别：</td>
					<td class="tdulright">
					<input type="checkbox" name="all" onclick="setAll(this)" value="0">全部
					<html:multibox property="levels" value="4" />特级
					<html:multibox property="levels" value="1"/>一级
					<html:multibox property="levels" value="2"/>二级
					<html:multibox property="levels" value="3"/>三级
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">是否取消：</td>
					<td class="tdulright">
						<html:select property="safeguardState"  styleClass="inputtext" style="width:150px;">
							<html:option value="">不限</html:option>
							<html:option value="999">是</html:option>
							<html:option value="0">否</html:option>
						</html:select>
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
			<div align="center" style="height:40px">
				<input type="hidden" name="isQuery" value="true" />
				<html:submit property="action" styleClass="button">查询</html:submit> &nbsp;&nbsp;&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">重写</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		<!-- 查询结果 -->
		<logic:notEmpty name="Query_List">
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
			<c:if test="${sessionScope.operator=='query'}">
		<script type="text/javascript">
			toViewSafeguard=function(taskId,planId,summaryId,conId){
            	window.location.href = "${ctx}/safeguardQueryStatAction.do?method=viewSafeguard&taskId="+taskId+"&planId="+planId+"&summaryId="+summaryId+"&conId="+conId;

			}
		</script>
		<%
		BasicDynaBean object=null;
		Object taskId=null;
		Object planId=null;
		Object summaryId=null;
		Object safeguardName= null;
		Object conId1 = null;
	%>
	<display:table name="sessionScope.Query_List" id="safeguard" pagesize="18">
		<% object = (BasicDynaBean ) pageContext.findAttribute("safeguard");
	   		if(object != null) {
	  			taskId = object.get("task_id");
				safeguardName = object.get("safeguard_name");
	    		planId = object.get("plan_id");
	    		summaryId = object.get("summary_id");
				conId1 = object.get("taskcon_id");
		} %>
			<display:column title="保障名称" media="html"  sortable="true" headerClass="subject">
      			<a href="javascript:toViewSafeguard('<%=taskId%>','<%=planId%>','<%=summaryId%>','<%=conId1%>')"><%=safeguardName%></a> 
			</display:column>
			<display:column property="contractorname" title="代维公司" headerClass="subject"  sortable="true"/>
			<display:column property="start_date" title="开始时间" headerClass="subject"  sortable="true"/>
			<display:column property="end_date" title="结束时间" headerClass="subject"  sortable="true"/>
			<display:column property="safeguard_level" title="保障级别" headerClass="subject"  sortable="true"/>
			<display:column property="transact_state" title="保障状态" headerClass="subject"  sortable="true"/>
			<display:column property="send_date" title="任务派发时间" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<bean:define id="applyState" name="safeguard" property="safeguard_state"></bean:define>
				<a href="javascript:toViewSafeguard('<%=taskId%>','<%=planId%>','<%=summaryId%>','<%=conId1%>')">查看</a>  
				<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
					<c:if test="${applyState=='1'}">
						<a href="javascript:toCancelForm('<bean:write name="safeguard" property="task_id"/>')">取消</a>
					</c:if>
					<c:if test="${applyState=='2'}">
						<a href="javascript:toCancelForm('<bean:write name="safeguard" property="task_id"/>')">取消</a>
					</c:if>
					<c:if test="${applyState=='3'}">
						 <a href="javascript:toCancelForm('<bean:write name="safeguard" property="task_id"/>">取消</a>
					</c:if>
				</c:if>
		 </display:column>	
		</display:table>
			<p><html:link action="/safeguardQueryStatAction.do?method=exportSafeguardList">导出为Excel文件</html:link></p>
		<div style="width=100%;height:30px;text-align:center;">
			<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
		</div>
		</c:if>
		<c:if test="${sessionScope.operator=='stat'}">
		<display:table name="sessionScope.Query_List" id="drill" pagesize="18">
			<display:column property="contractorname" title="代维单位" headerClass="subject"  sortable="true"/>
			<display:column property="safeguard_number" title="保障个数" headerClass="subject"  sortable="true"/>
			<display:column property="total_time" title="保障用时(小时)" headerClass="subject"  sortable="true"/>
			<display:column property="total_person_number" title="实际投入人数(人)" headerClass="subject"  sortable="true"/>
			<display:column property="total_car_number" title="实际投入车辆数(辆)" headerClass="subject"  sortable="true"/>
			<display:column property="total_equipment_number" title="实际投入设备数(套)" headerClass="subject"  sortable="true"/>
		</display:table>
		<br/>
		<div style="width=100%;height:50px;text-align:center;">
			<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
		</div>
		</c:if>
		</logic:notEmpty>
	</body>
</html>
