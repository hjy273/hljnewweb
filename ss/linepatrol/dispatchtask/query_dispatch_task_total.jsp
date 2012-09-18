<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>查询统计</title>
		<script type="text/javascript">
			/*function showQuery(queryForm){
				if(queryForm.style.display=="none"){
					queryForm.style.display="";
					showSpan.innerText="隐 藏";
				}else{
					queryForm.style.display="none";
					showSpan.innerText="显 示";
				}
			}*/
			//显示一个派单详细信息
		 	toViewForm=function (dispatchId){
		 		var url = "${ctx}/dispatchtask/dispatch_task.do?method=viewDispatchTask&dispatch_id="+dispatchId+"&rnd="+Math.random();
		    	location=url;
		 	}
		 	
		</script>
	</head>
	<body>
		<!--条件查询显示-->
		<template:titile value="任务派单查询" />
		<div style="text-align: center;">
			<iframe
				src="${ctx}/dispatchtask/dispatch_task.do?method=viewDispatchTaskProcess&&forward=query_dispatch_task_state&&task_names=${task_names }"
				frameborder="0" id="processGraphFrame" height="70" scrolling="no"
				width="95%"></iframe>
		</div>
		<html:form
			action="/dispatchtask/query_dispatch_task.do?method=queryDispatchTaskTotal"
			styleId="queryForm" style="display:">
			<template:formTable>
				<template:formTr name="任务主题">
					<html:text property="sendtopic" styleClass="inputtext"
						style="width:200px;" value="${queryCondi.sendtopic}" />
				</template:formTr>
				<template:formTr name="工作单类别">
					<html:select property="sendtype" styleId="sendtype" style="width: 200px" styleClass="inputtext">
						<html:option value="">不限</html:option>
						<html:options property="code" labelProperty="lable" collection="dispatch_task_type_list"/>
					</html:select>
				</template:formTr>
				<template:formTr name="派单部门">
					<html:select property="senddeptid" styleId="deptid" style="width:200px" styleClass="inputtext">
						<html:option value="">不限</html:option>
						<html:options property="deptid" labelProperty="deptname" collection="deptinfo"/>
					</html:select>
				</template:formTr>
				<template:formTr name="是否取消">
					<html:select property="workstate" styleClass="inputtext" style="width:200px">
						<html:option value="">不限</html:option>
						<html:option value="888">是</html:option>
						<html:option value="0">否</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="派 单 人">
					<html:text property="sendusername" styleClass="inputtext"
						style="width:200px" value="${queryCondi.sendusername}" />
				</template:formTr>
				<template:formTr name="受理部门">
					<html:select property="acceptdeptid" styleId="acceptdeptid" style="width:200px" styleClass="inputtext">
						<html:option value="">不限</html:option>
						<html:options property="deptid" labelProperty="deptname" collection="deptinfo"/>
					</html:select>
				</template:formTr>
				<template:formTr name="统计开始时间">
					<html:text property="begintime" styleId="begintime" style="width:150px" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'#F{$dp.$D(\'endtime\')||\'%y-%M-%d\'}'})" readonly="true"></html:text>
					--
					<html:text property="endtime" styleId="endtime" style="width:150px" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begintime\')}',maxDate:'%y-%M-%d'})" readonly="true"></html:text>
				</template:formTr>
			</template:formTable>
			<logic:present name="queryCondi" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondi"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
			<div align="center">
				<input name="isQuery" value="true" type="hidden" />
				<html:submit property="action" styleClass="button">查询</html:submit>
				<html:reset property="action" styleClass="button">重置</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		<!-- %@include file="/common/listhander.jsp"%-->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<logic:notEmpty name="TOTAL_LIST">
			<display:table name="sessionScope.TOTAL_LIST"
				requestURI="${ctx}/dispatchtask/query_dispatch_task.do?method=queryDispatchTaskTotal"
				id="currentRowObject" pagesize="20" style="width:100%">
				<bean:define id="dispatchId" name="currentRowObject" property="id" />
				<bean:define id="serialNumber" name="currentRowObject"
					property="serialnumber" />
				<bean:define id="sendTopic" name="currentRowObject"
					property="sendtopic" />
				<display:column media="html" title="流水号" sortable="true"
					style="width:120px">
					<a href="javascript:toViewForm('${dispatchId }')">${serialNumber}</a>
				</display:column>
				<display:column media="html" title="主题" sortable="true" style="padding-left:5px;">
					<a href="javascript:toViewForm('${dispatchId }')">${sendTopic }</a>
				</display:column>
				<display:column property="sendtypelabel" title="类型"
					style="width:80px" sortable="true" />
				<display:column property="departname" title="派单部门"
					style="width:150px" sortable="true" />
				<display:column property="username" title="派单人" style="width:60px"
					sortable="true" maxWords="8"/>
				<display:column property="processterm" title="处理期限"
					style="width:140px" sortable="true" />
			</display:table>
			<html:link
				action="/dispatchtask/query_dispatch_task.do?method=exportTotalResult">导出为Excel文件</html:link>
		</logic:notEmpty>
	</body>
	<script type="text/javascript">
		jQuery(document).ready(function() {
		    jQuery("#sendtype option[value='${queryCondi.sendtype}']").attr('selected', 'selected');
		    jQuery("#deptid option[value='${queryCondi.senddeptid}']").attr('selected', 'selected');
		    jQuery("#acceptdeptid option[value='${queryCondi.acceptdeptid}']").attr('selected', 'selected');
		});
	</script>
</html>
