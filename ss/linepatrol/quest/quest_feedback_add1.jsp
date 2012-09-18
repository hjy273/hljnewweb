<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />

<html>
	<head>
		<title>调查问卷</title>
		<script type="text/javascript">
			function checkForm(){
				if($('comIds').value==""){
					alert("至少选择一个参评对象！");
					return;
				}
				addFeedbackIssueForm.submit();
			}
			function addCompanyInfo(){
				var companyId=$('comIds').value;
				var url = "${ctx}/questAction.do?method=addCompany&companyId="+companyId;
				win = new Ext.Window({
					layout : 'fit',
					width:650,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
		</script>
		
	</head>
	<body>
		<template:titile value="调查问卷"/>
		<html:form action="/questAction.do?method=addFeedbackIssueForm" enctype="multipart/form-data" styleId="addFeedbackIssueForm">
			<input type="hidden" name="issueId" value="${issue.id }"/>
			<table style="width: 80%;" align="center" cellpadding="1" cellspacing="0" class="tabout">
				<tr class="trcolor" style="height: 30px;">
					<td colspan="2" style="text-align: center; line-height: 30px; font-weight: bolder;">${issue.name }</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">问卷说明：</td>
					<td class="tdulright">${issue.remark }</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">参评对象：</td>
					<td class="tdulright">
						<span id="companyInfo"><c:out value="${comNames }"/></span>
						<input type="hidden" name=comIds id="comIds" value="${comIds }">
						<input type="button" value="添加参评对象" onclick="addCompanyInfo()">
					</td>
				</tr>
			</table>
			<br/>
			<div align="center" style="height:40px">
				<html:button property="action" styleClass="button" onclick="checkForm()">下一步</html:button> &nbsp;&nbsp;
			</div>
		</html:form>
	</body>
</html>
