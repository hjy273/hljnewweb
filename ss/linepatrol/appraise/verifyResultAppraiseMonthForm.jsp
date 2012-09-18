<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>代维确认结果查询</title>
<script type="text/javascript" 	src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	function view(id) {
		window.location.href="${ctx}/appraiseMonthAction.do?method=viewAppraiseMonth&flag=view&resultId="+id;
	}
	function viewMark(mark){
		var actionUrl="/WebApp/linepatrol/appraise/mark.jsp?mark="+mark;
		viewMarkWin = new Ext.Window({
			layout : 'fit',
			width:450,height:200, 
			resizable:true,
			closeAction : 'close', 
			modal:false,
			autoScroll:true,
			autoLoad:{url: actionUrl,scripts:true}, 
			plain: false,
			title:"问题反馈备注" 
	});
	viewMarkWin.show(Ext.getBody());
}
function closeMark(){
	viewMarkWin.close();
}
function eidt(id) {
		window.location.href="${ctx}/appraiseMonthAction.do?method=editAppraise&flag=edit&resultId="+id;
	}
</script>
<body>
	<template:titile value="代维确认结果查询" />
	<html:form action="/appraiseMonthAction.do?method=queryVerifyResultAppraise" styleId="form" >
			<template:formTable>
				<template:formTr name="考核月份">
					<html:text property="appraiseTime" style="Wdate;width:140px;" styleId="appraiseTime" styleClass="inputtext required" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"></html:text><font color="red">*</font>
				</template:formTr>
				<template:formTr name="确认状态">
					&nbsp;<html:radio property="confirmResult" value="1">待确认</html:radio>
					&nbsp;<html:radio property="confirmResult" value="2">无异议</html:radio>&nbsp;
					<html:radio property="confirmResult" value="3">有异议</html:radio>
				</template:formTr>
				</template:formTable>
			<div align="center"><input type="submit" value="查询" class="button"/></div>
		</html:form>
	<logic:notEmpty name="appraiseResults">
		<display:table name="sessionScope.appraiseResults"
			id="appraiseResults" pagesize="18" export="false" defaultsort="1"
			defaultorder="descending" sort="list">
			<input type="hidden" name="appraiseTime" value="${appraiseResultBean.appraiseTime}"/>
			<display:column property="appraiseTime" title="考核月份" format="{0,date,yyyy-MM}"
				sortable="true" />
			<display:column media="html" title="代维公司" sortable="true">
				<c:out value="${contractor[appraiseResults.contractorId]}"></c:out>
			</display:column>
			<display:column property="contractNO" title="标底包" maxLength="9"
				sortable="true" />
				<display:column property="result" sortable="true" title="考核结果" />
			<display:column property="appraiseDate" sortable="true"
				format="{0,date,yyyy-MM-dd}" title="考核时间" />
			<display:column property="appraiser" title="考核人" sortable="true" ></display:column>
			<display:column media="html" title="确认结果" sortable="true" maxLength="30" style="width:15%;">
				<c:if test="${appraiseResults.confirmResult=='1'}">
					待确认
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='2'}">
					无异议
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='3'}">
					有异议（${appConfirmResultMap[appraiseResults.id].result }）
				</c:if>
			</display:column>
			<display:column media="html" title="操作">
				<c:if test="${appraiseResults.confirmResult=='1'||appraiseResults.confirmResult=='2'}">
					<a href="javascript:view('${appraiseResults.id}');">查看</a>
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='3'}">
					<a href="javascript:eidt('${appraiseResults.id}');">编辑</a>
				</c:if>
			</display:column>
		</display:table>
	</logic:notEmpty>
</body>
<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
