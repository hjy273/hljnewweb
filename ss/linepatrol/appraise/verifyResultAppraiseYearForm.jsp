<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>代维确认结果查询</title>
		<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	</head>
	<script type="text/javascript">
		function view(id){
			window.location.href="${ctx}/appraiseYearAction.do?method=viewAppraiseYear&id="+id;
		}	
function eidt(id) {
		window.location.href="${ctx}/appraiseYearAction.do?method=viewAppraiseYear&flag=edit&id="+id;
	}
	</script>
	<body>
		<template:titile value="代维确认结果查询" />
		<html:form action="/appraiseYearAction.do?method=queryVerifyResultAppraise" styleId="form">
			<template:formTable>
				<template:formTr name="考核年份">
					<html:text property="appraiseTime" style="Wdate;width:140px;" styleId="appraiseTime" styleClass="inputtext required" onfocus="WdatePicker({dateFmt:'yyyy'})"></html:text><font color="red">*</font>
				</template:formTr>
				<template:formTr name="确认状态">
					&nbsp;<html:radio property="confirmResult" value="1">待确认</html:radio>
					&nbsp;<html:radio property="confirmResult" value="2">无异议</html:radio>&nbsp;
					<html:radio property="confirmResult" value="3">有异议</html:radio>
				</template:formTr>
				</template:formTable>
			<div align="center"><input type="submit" value="查询" class="button"/></div>
		</html:form>
		<c:if test="${appraiseYearResults != null}">
			<display:table name="sessionScope.appraiseYearResults" id="appraiseYearResults" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list"	>
				<logic:notEmpty name="appraiseYearResults">
				<bean:define id="id" name="appraiseYearResults" property="id"></bean:define>
				<display:column media="html" title="代维公司" sortable="true" >
					 	<c:out value="${contractor[appraiseYearResults.contractorId]}"></c:out>
					 </display:column>
				<display:column property="contractNO" title="标底包"
					 maxLength="9" sortable="true" />
				<display:column property="result" sortable="true" title="考核结果"
					 maxLength="18" />
				<display:column media="html" sortable="true" property="year"
					title="考核年份" >
					</display:column>
				<display:column property="appraiser" title="考核人"
					 sortable="true" />
				<display:column media="html" title="确认结果" sortable="true" maxLength="36" style="width:15%;">
				<c:if test="${appraiseYearResults.confirmResult=='1'}">
					待确认
				</c:if>
				<c:if test="${appraiseYearResults.confirmResult=='2'}">
					无异议
				</c:if>
				<c:if test="${appraiseYearResults.confirmResult=='3'}">
					有异议（${appConfirmResultMap[appraiseYearResults.id].result }）
				</c:if>
			</display:column>
			<display:column media="html" title="操作">
			
				<c:if test="${appraiseYearResults.confirmResult=='2'||appraiseYearResults.confirmResult=='1'}">
					<a href="javascript:view('${appraiseYearResults.id}');">查看</a>
				</c:if>
				<c:if test="${appraiseYearResults.confirmResult=='3'}">
					<a href="javascript:eidt('${appraiseYearResults.id}');">编辑</a>
				</c:if>
			</display:column>
				</logic:notEmpty>
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="返回" onclick="history.back();"
							type="button" />
					</td>
				</tr>
			</table>
		</c:if>
	</body>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
