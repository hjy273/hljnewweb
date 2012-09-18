<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>���ָ����</title>
		<script type="text/javascript">
			function checkForm(){
				var sortId = $('sortId').value;
				var itemName = $('itemName').value;
				var remark = $('remark').value;
				var options = $('options').value;
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
					alert("����д�����");
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
				td3.innerHTML="<a style='color:blue;cursor:pointer;' id='browId"+i+"' onclick=deleteRuleRow('rowId"+i+"')>ɾ��</a>";
				i++;
				$('ruleName').value="";
			}
			function deleteRuleRow(rowId){
				if(confirm("ȷ��Ҫɾ����")){
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
		<template:titile value="���ָ����"/>
		<html:form action="/questAction.do?method=addManagerItem&operator=add" styleId="addItem">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ָ����ࣺ</td>
					<td>
						<select style="width: 150px;" id="sortId" name="sortId">
							<c:forEach items="${sorts}" var="sortId">
								<option value="${sortId.id }">${sortId.sort }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ѡ�����ã�</td>
					<td>
						<select style="width: 150px;" id="options" name="options" onchange="showAddItemRule()">
							<option value="2">��ѡ</option>
							<option value="3">��ѡ</option>
							<option value="1">��ֵ</option>
							<option value="4">�ı�</option>
						</select>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<div id="ruleAddDiv">
							<table width="100%">
								<tr>
									<td class="tdulleft" style="width:20%;">�����</td>
									<td>
										<textarea class="textarea" rows="3" name="ruleName" id="ruleName"></textarea>
										<input type="button" onclick="addRuleForm()" value="���">
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ָ���</td>
					<td>
						<textarea class="textarea" rows="3" name="itemName" id="itemName"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ע��</td>
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
									<td>���</td>
									<td>����ϸ��</td>
									<td>����</td>
								</tr>
							</table>
						</div>
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
