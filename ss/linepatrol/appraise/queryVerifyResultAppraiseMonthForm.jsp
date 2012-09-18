<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>确认结果查询</title>
<script type="text/javascript" 	src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
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
	<template:titile value="确认结果查询" />
	<html:form action="/appraiseMonthAction.do?method=queryVerifyResult" styleId="form" >
			<template:formTable>
				<template:formTr name="考核月份">
					<html:text property="appraiseTime" style="Wdate;width:140px;" styleId="appraiseTime" styleClass="inputtext" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"></html:text>
				</template:formTr>
				<html:hidden property="confirmResult"/>
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
			<display:column media="html" title="确认结果" sortable="true" >
					<c:if test="${appraiseResults.confirmResult eq '2'}">确认通过</c:if>
					<c:if test="${appraiseResults.confirmResult eq '3'}">确认不通过</c:if>
			</display:column>
			<display:column media="html" title="操作">
					<a href="javascript:view('${appraiseResults.id}');">查看</a>
			</display:column>
		</display:table>
	</logic:notEmpty>
</body>
</html>
