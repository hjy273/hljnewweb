<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>����ʾ�����</title>
		<script type="text/javascript">
			function checkForm(){
				var typeName = $('typeName').value;
				var remark = $('remark').value;
				if(typeName==""){
					alert('�ʾ����Ͳ���Ϊ�գ�');
					return;
				}
				if(remark==""){
					alert('��ע����Ϊ�գ�');
					return;
				}
				var params = "";
  				var url = "${ctx}/questAction.do?method=judgeTypeExists&typeName="+typeName;
  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:false}); 
			}
			function callback(originalRequest) {
				var rst = originalRequest.responseText;
				if(rst=="exists"){
					alert("���ʾ������Ѵ��ڣ�");
				}else{
					processBar(editType);
					editType.submit();
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="����ʾ�����"/>
		<html:form action="/questAction.do?method=editType&operator=add" styleId="editType">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ʾ����ͣ�</td>
					<td>
						<input type="text" name="typeName" id="typeName">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ע��</td>
					<td>
						<textarea class="textarea" rows="3" id="remark" name="remark"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>
			<div align="center" style="height:40px">
				<html:button property="action" styleClass="button" onclick="checkForm()">�ύ</html:button> &nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
