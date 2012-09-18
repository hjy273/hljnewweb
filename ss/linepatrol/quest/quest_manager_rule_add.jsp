<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>���������</title>
		<script type="text/javascript">
			function checkForm(){
				var sortId = $('sortId').value;
				var itemName = $('itemName').value;
				var remark = $('remark').value;
				if(itemName==""){
					alert('ָ�����Ϊ�գ�');
					return;
				}
				if(remark==""){
					alert('��ע����Ϊ�գ�');
					return;
				}
				var params = "";
  				var url = "${ctx}/questAction.do?method=judgeItemExists&sortId="+sortId+"&itemName="+itemName;
  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:false}); 
			}
			function callback(originalRequest) {
				var rst = originalRequest.responseText;
				if(rst=="exists"){
					alert("��ָ�����Ѵ��ڣ�");
				}else{
					editItem.submit();
				}
			}
			function addRuleForm(){
				var ruleName = $('ruleName').value;
				var mark = $('mark').value;
				if(ruleName==""){
					alert('�������Ϊ�գ�');
					return;
				}
				if(mark==""){
					alert('��ֵ����Ϊ�գ�');
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
		<template:titile value="���������"/>
		<html:form action="/questAction.do?method=addRule" styleId="addRule">
			<input type="hidden" id="itemId" name="itemId" value="${itemId }" />
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�����</td>
					<td>
						<input type="text" name="ruleName" id="ruleName">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ֵ��</td>
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
										<td align="center">���</td>
										<td align="center">������</td>
										<td align="center">��ֵ</td>
										<td align="center">����</td>
									</tr>
									<%int i = 1; %>
									<c:forEach items="${rules}" var="rule">
										<tr class="trcolor">
											<td align="center"><%=i %></td>
											<td>${rule.gradeExplain }</td>
											<td>${rule.mark }</td>
											<td align="center">�༭ | ɾ��</td>
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
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>
			<div align="center" style="height:40px">
				<html:button property="action" styleClass="button" onclick="addRuleForm()">���</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="submitRulesForm()">�ύ</html:button> &nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
