<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>添加评分项</title>
		<script type="text/javascript">
			function checkForm(){
				var sortId = $('sortId').value;
				var itemName = $('itemName').value;
				var remark = $('remark').value;
				if(itemName==""){
					alert('指标项不能为空！');
					return;
				}
				if(remark==""){
					alert('备注不能为空！');
					return;
				}
				var params = "";
  				var url = "${ctx}/questAction.do?method=judgeItemExists&sortId="+sortId+"&itemName="+itemName;
  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:false}); 
			}
			function callback(originalRequest) {
				var rst = originalRequest.responseText;
				if(rst=="exists"){
					alert("该指标项已存在！");
				}else{
					editItem.submit();
				}
			}
			function addRuleForm(){
				var ruleName = $('ruleName').value;
				var mark = $('mark').value;
				if(ruleName==""){
					alert('评分项不能为空！');
					return;
				}
				if(mark==""){
					alert('分值不能为空！');
					return;
				}
				addRule.submit();
			}
			submitRulesForm=function(){
				var itemId = $('itemId').value;
            	window.location.href = "${ctx}/questAction.do?method=editItemForm&flag=add&itemId="+itemId;
			}
		</script>
	</head>
	<body>
		<template:titile value="添加评分项"/>
		<html:form action="/questAction.do?method=addRule" styleId="addRule">
			<input type="hidden" id="itemId" name="itemId" value="${itemId }" />
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">评分项：</td>
					<td>
						<input type="text" name="ruleName" id="ruleName">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">分值：</td>
					<td>
						<input type="text" name="mark" id="mark">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<div id="ruleInfo">
							<c:if test="${not empty rules}">
								<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
									<tr class="trcolor">
										<td align="center">序号</td>
										<td align="center">评分项</td>
										<td align="center">分值</td>
										<td align="center">操作</td>
									</tr>
									<%int i = 1; %>
									<c:forEach items="${rules}" var="rule">
										<tr class="trcolor">
											<td align="center"><%=i %></td>
											<td>${rule.gradeExplain }</td>
											<td>${rule.mark }</td>
											<td align="center">编辑 | 删除</td>
										</tr>
										<%i++; %>
									</c:forEach>
								</table>
							</c:if>
						</div>
					</td>
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">所有项均为必填项，若无内容描述请填写“无”</font>
			</div>
			<div align="center" style="height:40px">
				<html:button property="action" styleClass="button" onclick="addRuleForm()">添加</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="submitRulesForm()">提交</html:button> &nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</html:form>
	</body>
</html>
