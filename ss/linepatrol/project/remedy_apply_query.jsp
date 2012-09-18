<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>查询修缮申请</title>
		<script type="text/javascript" src="./js/prototype.js"></script>
		<script type="text/javascript" src="./js/json2.js"></script>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" defer="defer"
			src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
	getSelectDateThis = function(strID) {
		document.all.item(strID).value = getPopDate(document.all.item(strID).value);
		return true;
	}
</script>
		<script type="text/javascript" language="javascript">
	toViewForm = function(idValue) {
		var url = "${pageContext.request.contextPath}/project/remedy_apply.do?method=view&&apply_id="
				+ idValue;
		self.location.replace(url);
	}
	toCancelForm=function(applyId){
			var url;
			if(confirm("确定要取消该工程申请流程吗？")){
				url="${ctx}/project/remedy_apply.do?method=cancelRemedyForm";
				var queryString="apply_id="+applyId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
</script>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
.subject {
	text-align: center;
}
</style>
	</head>
	<body>
		<br>
		<template:titile value="工程查询" />
		<div style="text-align:center;">
			<iframe
				src="${ctx}/project/remedy_apply.do?method=viewRemedyProcess&&forward=query_remedy_state&&task_name=${task_names }"
				frameborder="0" id="processGraphFrame" height="70" scrolling="no"
				width="95%"></iframe>
		</div>
		<!-- 查询页面 -->
		<html:form action="/project/remedy_apply.do?method=queryList"
			styleId="queryForm" method="post">
			<template:formTable>
				<html:hidden property="reset_query" value="1" />
				<html:hidden property="power" value="52004" />
				<template:formTr name="编号">
					<html:text property="remedyCode" styleClass="inputtext"
						style="width:140px;" maxlength="20" />
				</template:formTr>
				<template:formTr name="项目名称">
					<html:text property="projectName" styleClass="inputtext"
						style="width:140px;" maxlength="20" />
				</template:formTr>
				<template:formTr name="代维单位">
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
				</template:formTr>
				<template:formTr name="是否取消">
					<html:select property="state" styleClass="inputtext" style="width:140px">
						<html:option value="">不限</html:option>
						<html:option value="999">是</html:option>
						<html:option value="0">否</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="工程费用合计（元）">
					介于
					<html:text property="totalFeeMin" styleClass="inputtext validate-integer-float-double"
						style="width:65;" maxlength="20" />
					和
					<html:text property="totalFeeMax" styleClass="inputtext validate-integer-float-double great-than-totalFeeMin"
						style="width:65;" maxlength="20" />
					之间
				</template:formTr>
				<template:formTr name="申请时间">
					介于
					<html:text property="remedyDateMin" styleClass="Wdate"
						style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					和
					<html:text property="remedyDataMax" styleClass="Wdate"
						style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:$('remedyDateMin')});" />
					之间
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
			<div align="center">
				<input name="isQuery" value="true" type="hidden" />
				<html:submit styleClass="button">查询</html:submit>
				<html:reset styleClass="button">重置</html:reset>
			</div>
		</html:form>
		<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}
		var valid = new Validation('queryForm', {immediate : true, onFormValidate : formCallback});
	</script>
		<template:displayHide styleId="queryForm"></template:displayHide>

		<!-- 查询结果 -->
		<logic:notEmpty name="APPLY_LIST">
			<%
				BasicDynaBean object = null;
					Object id = null;
					Object state = null;
			%>
			<display:table name="sessionScope.APPLY_LIST" id="currentRowObject"
				pagesize="18" defaultsort="3" defaultorder="descending" sort="list">
				<display:setProperty name="sort.amount" value="list" />
				<%
					object = (BasicDynaBean) pageContext
									.findAttribute("currentRowObject");
							if (object != null) {
								id = object.get("id");
								state = object.get("state");
							}
							request.setAttribute("applyState", (String)state);
				%>
				<display:column property="remedycode" sortable="true" title="编号"
					headerClass="subject" />
				<display:column media="html" sortable="true" title="项目名称"
					headerClass="subject" maxLength="15">
					<a href="javascript:toViewForm('<%=id%>')"><%=object.get("projectname")%></a>
				</display:column>
				<display:column property="remedydate" sortable="true" title="申请时间"
					headerClass="subject" />
				<display:column property="totalfee" title="工程费用合计金额" sortable="true" />
				<display:column media="html" title="操作">
					<a href="javascript:toViewForm('<%=id%>')">查看</a>
					<c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&applyState!='999'}">
					| <a href="javascript:toCancelForm('<%=id%>')">取消</a>
				</c:if>
				</display:column>
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td>
						<a href="#"></a>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;">
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>
