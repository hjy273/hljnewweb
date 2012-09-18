<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>审核测试计划</title>
		<script type='text/javascript'>
	
</script>
	</head>

	<body>
		<template:titile value="统计测试计划" />
		<html:form action="/testPlanQueryStatAction.do?method=statTestPlans"
			styleId="queryForm">
			<table width="80%" border="0" align="center" cellpadding="3"
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
				<logic:equal value="1" name="LOGIN_USER" property="deptype">
					<template:formTr name="测试单位">
						<html:select property="contractorId" styleId="contractorId" styleClass="inputtext" style="width:140px">
							<html:option value="">不限</html:option>
							<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
						</html:select>
					</template:formTr>
				</logic:equal>
				<template:formTr name="测试类型">
					<html:multibox property="planType" value="'1'" />备纤测试
					<html:multibox property="planType" value="'2'" />接地电阻测试
				</template:formTr>
				<td colspan="2" style="text-align: center;">
					<input type="hidden" name="isQuery" value="true" />
					<input name="action" class="button" value="查询" type="submit" />
				</td>
			</table>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		<logic:notEmpty name="statPlans">
			<script type="text/javascript" language="javascript">
	exportList = function() {
		var url = "${ctx}/testPlanQueryStatAction.do?method=exportTestPlansStatList";
		self.location.replace(url);
	}
	function goBack() {
		var url = "${ctx}/testPlanQueryStatAction.do?method=statTestPlanForm";
		self.location.replace(url);
	}
</script>
			<title>测试计划一览表</title>
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<style type="text/css">
.subject {
	text-align: center;
}
</style>

			<%
				BasicDynaBean object = null;
					int num = 0;
					int solvenum = 0;
			%>
			<display:table name="sessionScope.statPlans" id="currentRowObject"
				pagesize="15">
				<display:column property="contractorname" sortable="true"
					title="代维单位" headerClass="subject" />
				<display:column property="test_plan_name" sortable="true"
					title="测试计划名称" headerClass="subject" />
				<display:column property="plantime" sortable="true" title="测试计划时间"
					headerClass="subject" />
				<display:column property="plantype" sortable="true" title="测试类型"
					headerClass="subject" />
				<display:column property="plannum" sortable="true" title="计划测试数量"
					headerClass="subject" />
				<display:column property="testnum" sortable="true" title="实际测试数量"
					headerClass="subject" />
				<display:column property="num" sortable="true" title="发现问题数量"
					headerClass="subject" />
				<display:column property="solvenum" sortable="true" title="解决问题数量"
					headerClass="subject" />
				<%
					String rate = "100%";
							object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
							if (object != null) {
								num = Integer.parseInt(object.get("num").toString());
								solvenum = Integer.parseInt(object.get("solvenum").toString());
								if (num != 0) {
									double r = (double) solvenum / num;
									rate = r * 100 + "%";
								}
							}
				%>
				<display:column value="<%=rate%>" sortable="true" title="问题修复率"
					headerClass="subject" />
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td>
						<logic:notEmpty name="statPlans">
							<a href="javascript:exportList()">导出为Excel文件</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="返回" onclick="goBack();"
							type="button" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>
