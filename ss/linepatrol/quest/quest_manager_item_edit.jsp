<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�޸�ָ����</title>
		<script type="text/javascript">
			function checkForm(){
				var sortId = $('sortId').value;
				var itemName = $('itemName').value;
				var remark = $('remark').value;
				var itemId = $('id').value;
				if(itemName==""){
					alert('ָ�����Ϊ�գ�');
					return;
				}
				if(remark==""){
					alert('��ע����Ϊ�գ�');
					return;
				}
				var params = "";
  				var url = "${ctx}/questAction.do?method=judgeItemExists&sortId="+sortId+"&itemName="+itemName+"&itemId="+itemId;
  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:false}); 
			}
			function callback(originalRequest) {
				var rst = originalRequest.responseText;
				if(rst=="exists"){
					alert("��ָ�����Ѵ��ڣ�");
				}else{
					processBar(editItem);
					editItem.submit();
				}
			}
			function setDefalutValue(){
				var sortIdBack = $('sortIdBack').value;
				var sortId=$('sortId');
				for(var i=0;i<sortId.length;i++){
					if(sortId[i].value==sortIdBack){
						sortId[i].selected='selected';
					}
				}
				var optionsBack = $('optionsBack').value;
				var options=$('options');
				for(var i=0;i<options.length;i++){
					if(options[i].value==optionsBack){
						options[i].selected='selected';
					}
				}
				showAddItemRule();
				showRuleInfo();
			}
			function showAddItemRule(){
				var options = $('options').value;
				var ruleInfoDiv = $('ruleInfoDiv');
				var ruleAddDiv = $('ruleAddDiv');
				if(options=="2"||options=='3'){
					ruleInfoDiv.style.display='block';
					ruleAddDiv.style.display='block';
				}else{
					ruleInfoDiv.style.display='none';
					ruleAddDiv.style.display='none';
				}
			}
			function showRuleInfo(){ 
				var itemId = $('id').value;
				var urls = '${ctx}/questAction.do?method=queryRuleListByItemId&&itemId='+itemId;
				var pars = "";
				var myAjax = new Ajax.Request(urls, {method:"post", parameters:pars, onComplete:showRuleInfoResponse, asynchronous:true});
			}
			function showRuleInfoResponse(rs){
				var rst = rs.responseText;
	    		var queryRes = eval('('+rst+')');
	    		var ruleInfoDiv = $('ruleInfoDiv');
	    		ruleInfoDiv.innerHTML="";
	    		var tab = document.createElement("table");
	    		tab.setAttribute("width","100%");
	    		var row=tab.insertRow();
	    		var cell1=row.insertCell();
  				cell1.setAttribute("width","10%");
  				cell1.innerHTML="���";
  				var cell2=row.insertCell();
  				cell2.setAttribute("width","80%");
  				cell2.innerHTML="ָ�����";
  				var cell3=row.insertCell();
  				cell3.setAttribute("width","10%");
  				cell3.innerHTML="����";
	    		for(var i = 0 ; i < queryRes.length; i++) {
	    			var rownum = i + 1;
	  				var row=tab.insertRow();
	  				var cell1=row.insertCell();
	  				cell1.setAttribute("width","10%");
	  				cell1.innerHTML=rownum;
	  				var cell2=row.insertCell();
	  				cell2.setAttribute("width","80%");
	  				cell2.innerHTML=queryRes[i].gradeExplain;
	  				var cell3=row.insertCell();
	  				cell3.setAttribute("width","10%");
	  				cell3.innerHTML="<a style='color:blue;cursor:pointer;' onclick=deleteRuleRow('"+queryRes[i].id+"')>ɾ��</a>";
	  			}
	  			ruleInfoDiv.appendChild(tab);
			}
			
			function deleteRuleRow(ruleId){ 
				if(confirm("ȷ��Ҫɾ����")){
					var urls = '${ctx}/questAction.do?method=deleteManagerRule&&ruleId='+ruleId;
					var pars = "";
					var myAjax = new Ajax.Request(urls, {method:"post", parameters:pars, onComplete:showRuleInfo, asynchronous:true});
				}
			}
			function addRuleForm(){ 
				var itemId = $('id').value;
				var ruleName = $('ruleName').value;
				if(ruleName==""){
					alert("����д�����");
					return;
				}
				var urls = '${ctx}/questAction.do?method=addManagerRule&&itemId='+itemId+"&ruleName="+ruleName;
				var pars = "";
				$('ruleName').value="";
				var myAjax = new Ajax.Request(urls, {method:"post", parameters:pars, onComplete:showRuleInfo, asynchronous:true});
			}
		</script>
	</head>
	<body onload="setDefalutValue()">
		<template:titile value="�޸�ָ����"/>
		<html:form action="/questAction.do?method=editItem" styleId="editItem">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<input type="hidden" name="flag" id="flag" value="${flag }" />
				<input type="hidden" name="id" id="id" value="${itemInfo.id }" />
				<input type="hidden" name="sortIdBack" id="sortIdBack" value="${itemInfo.sortId }" />
				<input type="hidden" name="optionsBack" id="optionsBack" value="${itemInfo.options }" />
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
						<textarea class="textarea" rows="3" name="itemName" id="itemName"><c:out value="${itemInfo.item}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ע��</td>
					<td>
						<textarea class="textarea" rows="3" name="remark" id="remark"><c:out value="${itemInfo.remark}"/></textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<div id="ruleInfoDiv">
							
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
