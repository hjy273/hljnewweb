<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>���ָ�����</title>
		<script type="text/javascript">
			function checkForm(){
				var classId = $('classId').value;
				var sortName = $('sortName').value;
				var remark = $('remark').value;
				if(sortName==""){
					alert('ָ����಻��Ϊ�գ�');
					return;
				}
				if(remark==""){
					alert('��ע����Ϊ�գ�');
					return;
				}
				var params = "";
  				var url = "${ctx}/questAction.do?method=judgeSortExists&operator=add&classId="+classId+"&sortName="+sortName;
  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:false}); 
			}
			function callback(originalRequest) {
				var rst = originalRequest.responseText;
				if(rst=="exists"){
					alert("��ָ������Ѵ��ڣ�");
				}else{
					processBar(editSort);
					editSort.submit();
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="���ָ�����"/>
		<html:form action="/questAction.do?method=editSort&operator=add" styleId="editSort">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ָ�����</td>
					<td>
						<select style="width: 150px;" id="classId" name="classId">
							<c:forEach items="${qclasses}" var="qclass">
								<option value="${qclass.id }">${qclass.className }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ָ����ࣺ</td>
					<td>
						<input type="text" name="sortName" id="sortName">
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
