<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�޸�ָ�����</title>
		<script type="text/javascript">
			function checkForm(){
				var typeId = $('typeId').value;
				var className = $('className').value;
				var remark = $('remark').value;
				var classId = $('id').value;
				if(className==""){
					alert('ָ�����Ͳ���Ϊ�գ�');
					return;
				}
				if(remark==""){
					alert('��ע����Ϊ�գ�');
					return;
				}
				var params = "";
  				var url = "${ctx}/questAction.do?method=judgeClassExists&typeId="+typeId+"&className="+className+"&classId="+classId;
  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:false}); 
			}
			function callback(originalRequest) {
				var rst = originalRequest.responseText;
				if(rst=="exists"){
					alert("��ָ������Ѵ��ڣ�");
				}else{
					processBar(editClass);
					editClass.submit();
				}
			}
			function setDefalutValue(){
				var typeIdBack = $('typeIdBack').value;
				var typeId=$('typeId');
				for(var i=0;i<typeId.length;i++){
					if(typeId[i].value==typeIdBack){
						typeId[i].selected='selected';
					}
				}
			}
		</script>
	</head>
	<body onload="setDefalutValue()">
		<template:titile value="�޸�ָ�����"/>
		<html:form action="/questAction.do?method=editClass" styleId="editClass">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<input type="hidden" name="id" id="id" value="${classInfo.id }" />
				<input type="hidden" name="typeIdBack" id="typeIdBack" value="${classInfo.typeId }" />
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ʾ����ͣ�</td>
					<td>
						<select style="width: 150px;" id="typeId" name="typeId">
							<c:forEach items="${types}" var="type">
								<option value="${type.id }">${type.type }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ָ�����</td>
					<td>
						<input type="text" name="className" id="className" value="${classInfo.className }">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ע��</td>
					<td>
						<textarea class="textarea" rows="3" name="remark" id="remark"><c:out value="${classInfo.remark}"/></textarea>
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
