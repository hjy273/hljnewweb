
<%@page import="org.springframework.web.context.request.SessionScope"%><%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<script type='text/javascript'
			src='${ctx}/linepatrol/js/change_style.js'></script>
		<script type="text/javascript">
		function check(){
			if( ($('begintime').value == '' && $('endtime').value != '') ||
				($('begintime').value != '' && $('endtime').value == '') ){
				alert("日期段必须同时有开始日期和结束日期");
				return false;
			}
			if( ($('begintime').value != '' && $('endtime').value != '')
					&& ($('begintime').value == $('endtime').value) ){
				alert('开始日期和结束日期不能相等');
				return false;
			}
			return true;
		}
		//编辑
		function toEditForm(applyId){
			window.location.href = "${ctx}/acceptancePlanQueryAction.do?method=forwardEditAcceptancePlan&applyId="+applyId;
		}
		//编辑管道列表
		function toEditPipeList(applyId){
			window.location.href = "${ctx}/acceptancePlanQueryAction.do?method=getCablePipeList&type=pipe&canDelete=can&applyId="+applyId;
		}
		//编辑光缆列表
		function toEditCableList(applyId){
			window.location.href = "${ctx}/acceptancePlanQueryAction.do?method=getCablePipeList&type=cable&canDelete=can&applyId="+applyId;
		}
	</script>
	<style type="text/css">
		.Content{ border:1px #000000 solid;}

		.table_1{  border:1px #000000 solid;}
		.table_1_tr1{ border-bottom:1px #000000 solid;}
		
		.table_2{  border:0px #000000 solid;}
		.table_2 td{ border-bottom:1px #000000 solid; border-right:1px #000000 solid;}
		.table_2_td{ border-top:1px #000000 solid; border-left:1px #000000 solid; }
		.table_2_left{border-left:1px #000000 solid; }
	</style>
	</head>
	<body onload="changeStyle()">
		<template:titile value="交维计划查询" />
		<!-- 查询页面 -->
		<html:form action="/acceptancePlanQueryAction.do?method=queryAcceptancePlanResult"
			styleId="form" onsubmit="return check()">
			<template:formTable namewidth="150" contentwidth="400">
				<template:formTr name="项目名称">
					<html:text property="name" styleClass="inputtext"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="是否核准" isOdd="false">
					<select name="ispassed" style="width:200px">
						<option value="">不限</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</template:formTr>
				<template:formTr name="验收时间">
					<html:text property="begintime" styleClass="Wdate"
						style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" /> 至
				<html:text property="endtime" styleClass="Wdate" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" />
				</template:formTr>
			</template:formTable>
			<div align="center">
				<input name="isQuery" value="true" type="hidden">
				<html:submit property="action" styleClass="button">查 询</html:submit>
			</div>
		</html:form>
		<template:displayHide styleId="form"></template:displayHide>
		
		<logic:notEmpty name="list">
			<display:table name="sessionScope.list" id="row" pagesize="18" export="false">
				<%
					DynaBean bean = (DynaBean)pageContext.findAttribute("row");
					String applicant = bean.get("applicant") == null ? "" : (String)bean.get("applicant");
					String id = bean.get("id") == null ? "" : (String)bean.get("id");
				%>
				<display:column property="name" title="名称" />
				<display:column property="code" title="验收交维编号" />
				<display:column media="html" title="项目经理">
					<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="<%=applicant %>" />
				</display:column>
				<display:column media="html" title="申请时间">
					<bean:write name="row" property="apply_date" format="yyyy/MM/dd" />
				</display:column>
				<display:column media="html" title="验收资源类型">
					<logic:equal value="2" name="row" property="resource_type">
						管道
					</logic:equal>
					<logic:notEqual value="2" name="row" property="resource_type">
						光缆
					</logic:notEqual>
				</display:column>
				<display:column media="html" title="操作">
					<logic:equal value="can" name="row" property="can_modify">
						<a href="javascript:toEditForm('<%=id %>')">编辑</a> | 
						<logic:equal value="2" name="row" property="resource_type">
							<a href="javascript:toEditPipeList('<%=id %>')">编辑管道</a>
						</logic:equal>
						<logic:notEqual value="2" name="row" property="resource_type">
							<a href="javascript:toEditCableList('<%=id %>')">编辑光缆</a>
						</logic:notEqual>
					</logic:equal>
				</display:column>
			</display:table>
		</logic:notEmpty>
		<!-- 查询列表 -->
		<logic:notEmpty name="list">
			<!-- 查询结果 -->
			<table class="tdlist">
				<tr>
					<td class="tdlist">
						<input type="button" class="button" value="返回"
							onclick="history.back()" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>