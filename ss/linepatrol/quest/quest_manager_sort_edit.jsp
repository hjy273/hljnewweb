<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>修改指标分类</title>
		<script type="text/javascript">
			function checkForm(){
				var classId = $('classId').value;
				var sortName = $('sortName').value;
				var remark = $('remark').value;
				var sortId = $('id').value;
				if(sortName==""){
					alert('指标分类不能为空！');
					return;
				}
				if(remark==""){
					alert('备注不能为空！');
					return;
				}
				var params = "";
  				var url = "${ctx}/questAction.do?method=judgeSortExists&classId="+classId+"&sortName="+sortName+"&sortId="+sortId;
  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:false}); 
			}
			function callback(originalRequest) {
				var rst = originalRequest.responseText;
				if(rst=="exists"){
					alert("该指标分类已存在！");
				}else{
					editSort.submit();
				}
			}
			function setDefalutValue(){
				var classIdBack = $('classIdBack').value;
				var classId=$('classId');
				for(var i=0;i<classId.length;i++){
					if(classId[i].value==classIdBack){
						classId[i].selected='selected';
					}
				}
			}
		</script>
	</head>
	<body onload="setDefalutValue()">
		<template:titile value="修改指标分类"/>
		<html:form action="/questAction.do?method=editSort" styleId="editSort">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<input type="hidden" name="id" id="id" value="${sortInfo.id }" />
				<input type="hidden" name="classIdBack" id="classIdBack" value="${sortInfo.classId }" />
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">指标类别：</td>
					<td>
						<select style="width: 150px;" id="classId" name="classId">
							<c:forEach items="${qclasses}" var="qclass">
								<option value="${qclass.id }">${qclass.className }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">指标分类：</td>
					<td>
						<input type="text" name="sortName" id="sortName" value="${sortInfo.sort }">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">备注：</td>
					<td>
						<textarea class="textarea" rows="3" name="remark"><c:out value="${sortInfo.remark}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">所有项均为必填项，若无内容描述请填写“无”</font>
			</div>
			<div align="center" style="height:40px">
				<html:button property="action" styleClass="button" onclick="checkForm()">提交</html:button> &nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</html:form>
	</body>
</html>
