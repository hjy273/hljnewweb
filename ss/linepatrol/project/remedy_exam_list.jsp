<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>工程管理考核查询</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>
		<template:titile value="工程考核查询" />
		<html:form action="/remedyExamAction.do?method=examList"
			styleId="submitForm1">
			<table border="1" align="center" cellspacing="0" cellpadding="1"
				class="tabout" width="80%">
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						项目名称：
					</td>
					<td class="tdulright">
						<html:text property="projectName"></html:text>
					</td>
				</tr>
			</table>
			<div align="center">
				<html:submit property="action" styleClass="button">查询</html:submit>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">重写</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="submitForm1"></template:displayHide>
		<logic:notEmpty name="examList">
			<script type="text/javascript">
				toExamDispatch=function(id,contractorId){
	            	window.location.href = "${ctx}/remedyExamAction.do?method=examRemedyForm&apply_id="+id+"&contractorId="+contractorId;
				};
			</script>
			<%
				BasicDynaBean object = null;
				String id = "";
				String contractorId = "";
			%>
			<display:table name="sessionScope.examList" id="examBean" pagesize="18">
				<%
					object = (BasicDynaBean) pageContext.findAttribute("examBean");
					id = object.get("id") == null ? "" : (String)object.get("id");
					contractorId = object.get("contractorid") == null ? "" : (String)object.get("contractorid");
				%>
				<display:column property="projectname" title="项目名称"
					headerClass="subject" sortable="true" />
				<display:column property="contractorname" title="维护单位"
					headerClass="subject" sortable="true" />
				<display:column property="remedyaddress" title="发生地点"
					headerClass="subject" sortable="true" />
				<display:column property="remedydate" title="申请时间"
					headerClass="subject" sortable="true" />
				<display:column property="remedyreson" title="原因说明"
					headerClass="subject" sortable="true" />
				<display:column property="remedysolve" title="处理方案"
					headerClass="subject" sortable="true" />
				<display:column property="totalfee" title="工程费用"
					headerClass="subject" sortable="true" />
				<display:column media="html" title="操作" >
					<a href="javascript:toExamDispatch('<%=id %>','<%=contractorId %>')">考核</a>
				</display:column>
			</display:table>
			<div style="height: 50px; text-align: center;">
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</logic:notEmpty>
	</body>
</html>
