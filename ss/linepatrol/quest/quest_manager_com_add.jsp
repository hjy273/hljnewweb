<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��Ӳ�������</title>
		<script type="text/javascript">
			function checkForm(){
				var comName = $('comName').value;
				if(comName==""){
					alert('����������Ϊ�գ�');
					return;
				}
				var params = "";
  				var url = "${ctx}/questAction.do?method=judgeComExists&comName="+comName;
  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:false}); 
			}
			function callback(originalRequest) {
				var rst = originalRequest.responseText;
				if(rst=="exists"){
					alert("�ò��������Ѵ��ڣ�");
				}else{
					processBar(editCom);
					editCom.submit();
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="��Ӳ�������"/>
		<html:form action="/questAction.do?method=editCom&operator=add" styleId="editCom">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��������</td>
					<td>
						<input type="text" name="comName" id="comName">
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
