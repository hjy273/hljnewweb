<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>添加指标项</title>
		<script type="text/javascript">
			function checkForm(){
				var sortId = $('sortId').value;
				var itemName = $('itemName').value;
				var remark = $('remark').value;
				var options = $('options').value;
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
					processBar(addItem);
					addItem.submit();
				}
			}
			function showAddItemRule(){
				var options = $('options').value;
				var ruleShowDiv = $('ruleShowDiv');
				var ruleAddDiv = $('ruleAddDiv');
				if(options=="2"||options=='3'){
					ruleShowDiv.style.display='block';
					ruleAddDiv.style.display='block';
				}else{
					ruleShowDiv.style.display='none';
					ruleAddDiv.style.display='none';
				}
			}
			var i=1;
			addRuleForm=function(){
				//var i = ruleInfoTable.rows.length;
				//alert(i);
				var ruleName=$('ruleName').value;
				if(ruleName==""){
					alert("请填写评分项！");
					return;
				}
				var table = $('ruleInfoTable');
				var tr=table.insertRow();
				tr.id="rowId"+i;
				var td1=tr.insertCell();
				td1.innerHTML=i;
				var td2=tr.insertCell();
				td2.innerHTML="<input type='checkbox' style='display:none;' checked='checked' value='"+ruleName+"' name='ruleAddName'/>"+ruleName;
				//alert(td2.innerHTML);
				var td3=tr.insertCell();
				td3.innerHTML="<a style='color:blue;cursor:pointer;' id='browId"+i+"' onclick=deleteRuleRow('rowId"+i+"')>删除</a>";
				i++;
				$('ruleName').value="";
			}
			function deleteRuleRow(rowId){
				if(confirm("确认要删除吗？")){
					for(i =0; i<ruleInfoTable.rows.length;i++){
						if(ruleInfoTable.rows[i].id == rowId){
							ruleInfoTable.deleteRow(ruleInfoTable.rows[i].rowIndex);
						}
					}
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="添加指标项"/>
		<html:form action="/questAction.do?method=addManagerItem&operator=add" styleId="addItem">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">指标分类：</td>
					<td>
						<select style="width: 150px;" id="sortId" name="sortId">
							<c:forEach items="${sorts}" var="sortId">
								<option value="${sortId.id }">${sortId.sort }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">选项设置：</td>
					<td>
						<select style="width: 150px;" id="options" name="options" onchange="showAddItemRule()">
							<option value="2">单选</option>
							<option value="3">多选</option>
							<option value="1">数值</option>
							<option value="4">文本</option>
						</select>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<div id="ruleAddDiv">
							<table width="100%">
								<tr>
									<td class="tdulleft" style="width:20%;">评分项：</td>
									<td>
										<textarea class="textarea" rows="3" name="ruleName" id="ruleName"></textarea>
										<input type="button" onclick="addRuleForm()" value="添加">
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">指标项：</td>
					<td>
						<textarea class="textarea" rows="3" name="itemName" id="itemName"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">备注：</td>
					<td>
						<textarea class="textarea" rows="3" name="remark" id="remark"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<div id="ruleShowDiv">
							<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%" id="ruleInfoTable">
								<tr>
									<td>序号</td>
									<td>评分细项</td>
									<td>操作</td>
								</tr>
							</table>
						</div>
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
