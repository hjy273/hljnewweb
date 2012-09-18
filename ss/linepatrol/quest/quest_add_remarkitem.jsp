<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>��Ӳ�����</title>
		<script type="text/javascript">
			function checkForm(){
				var issueId = $('issueId').value;
				$('addIssueItem').action="${ctx}/questAction.do?method=addIssueItem&issueId="+issueId;
				addIssueItem.submit();
			}
			function changeType(){ 
				var classId = $("classId").value;
				var ops = $('sortId');
  				ops.options.length = 0;
  				ops.options.add(new Option("==��ѡ�����==", ""));
				var urls = '${ctx}/questAction.do?method=querySortList&&classId='+classId;
				var pars = "";
				var myAjax = new Ajax.Request(urls, {method:"post", parameters:pars, onComplete:showResponse, asynchronous:true});
			}
			function showResponse(rs){
				var rst = rs.responseText;
	    		var queryRes = eval('('+rst+')');
	    		var ops = $('sortId');
	    		for(var i = 0 ; i < queryRes.length; i++) {
	  				ops.options.add(new Option(queryRes[i].sort, queryRes[i].id));
	  			}
			}
			function changeSort(){ 
				var sortId = $("sortId").value;
				if(sortId==""){
					alert("��ѡ�����!");
					return;
				}
				var issueId = $('issueId').value;
				var urls = '${ctx}/questAction.do?method=queryItemList&&sortId='+sortId+'&issueId='+issueId;
				var pars = "";
				var myAjax = new Ajax.Request(urls, {method:"post", parameters:pars, onComplete:showSortResponse, asynchronous:true});
			}
			function showSortResponse(rs){
				var rst = rs.responseText;
	    		var queryRes = eval('('+rst+')');
	    		var itemDiv = $('itemDiv');
	    		itemDiv.innerHTML="";
	    		var tab = document.createElement("table");
	    		tab.setAttribute("width","100%");
	    		var row=tab.insertRow();
	    		var cell2=row.insertCell();
  				cell2.setAttribute("width","5%");
  				var cell2=row.insertCell();
  				cell2.setAttribute("width","15%");
  				cell2.innerHTML="ָ�����";
  				var cell3=row.insertCell();
  				cell3.setAttribute("width","15%");
  				cell3.innerHTML="ָ�����";
  				var cell4=row.insertCell();
  				cell4.setAttribute("width","40%");
  				cell4.innerHTML="ָ��ϸ��";
  				var cell5=row.insertCell();
  				cell5.setAttribute("width","5%");
  				cell5.innerHTML="ϸ��Ȩ��";
	    		for(var i = 0 ; i < queryRes.length; i++) {
	  				var row=tab.insertRow();
	  				var cell1=row.insertCell();
	  				cell1.setAttribute("width","5%");
	  				cell1.innerHTML="<input type='checkbox' name='items' value='"+queryRes[i].id+"'/>";
	  				var cell2=row.insertCell();
	  				cell2.setAttribute("width","15%");
	  				cell2.innerHTML=queryRes[i].qclass;
	  				var cell3=row.insertCell();
	  				cell3.setAttribute("width","15%");
	  				cell3.innerHTML=queryRes[i].sort;
	  				var cell4=row.insertCell();
	  				cell4.setAttribute("width","40%");
	  				cell4.innerHTML=queryRes[i].item;
	  				var cell5=row.insertCell();
	  				cell5.setAttribute("width","5%");
	  				cell5.innerHTML=queryRes[i].weight_value;
	  			}
	  			itemDiv.appendChild(tab);
			}
			function addItem(){
				var params = "";
				var items = document.getElementsByName("items");
				for(var i=0;i<items.length;i++){
					if(items[i].checked){
						params=params+items[i].value+",";
					}
				}
				var issueId = $('issueId').value;
				var urls = '${ctx}/questAction.do?method=addItem&&items='+params+"&issueId="+issueId;
				var para = "";
				var myAjax = new Ajax.Request(urls, {method:"post", parameters:para, onComplete:addItemResponse, asynchronous:true});
			}
			function addItemResponse(rs){
				var issueId = $('issueId').value;
				var urls = '${ctx}/questAction.do?method=showItem&&&issueId='+issueId;
				var para = "";
				var myAjax = new Ajax.Request(urls, {method:"post", parameters:para, onComplete:showItemResponse, asynchronous:true});
			}
			function showItemResponse(rs){
				var rst = rs.responseText;
	    		var queryRes = eval('('+rst+')');
	    		var showItemDiv = $('showItemDiv');
	    		showItemDiv.innerHTML="";
	    		var tab = document.createElement("table");
	    		tab.setAttribute("width","100%");
	    		var row=tab.insertRow();
  				var cell2=row.insertCell();
  				cell2.setAttribute("width","15%");
  				cell2.innerHTML="ָ�����";
  				var cell3=row.insertCell();
  				cell3.setAttribute("width","15%");
  				cell3.innerHTML="ָ�����";
  				var cell4=row.insertCell();
  				cell4.setAttribute("width","30%");
  				cell4.innerHTML="ָ��ϸ��";
  				var cell5=row.insertCell();
  				cell5.setAttribute("width","10%");
  				cell5.innerHTML="ϸ��Ȩ��";
  				var cell1=row.insertCell();
  				cell1.setAttribute("width","10%");
  				cell1.innerHTML="����";
	    		for(var i = 0 ; i < queryRes.length; i++) {
	  				var row=tab.insertRow();
	  				var cell2=row.insertCell();
	  				cell2.setAttribute("width","15%");
	  				cell2.innerHTML=queryRes[i].qclass;
	  				var cell3=row.insertCell();
	  				cell3.setAttribute("width","15%");
	  				cell3.innerHTML=queryRes[i].sort;
	  				var cell4=row.insertCell();
	  				cell4.setAttribute("width","30%");
	  				cell4.innerHTML=queryRes[i].item;
	  				var cell5=row.insertCell();
	  				cell5.setAttribute("width","10%");
	  				cell5.innerHTML=queryRes[i].weight_value;
	  				var cell1=row.insertCell();
	  				cell1.setAttribute("width","10%");
	  				cell1.innerHTML="<a onclick=deleteItem('"+queryRes[i].quest_id+"') style='color:blue;cursor:pointer;'>ɾ��</a>";
	  			}
	  			showItemDiv.appendChild(tab);
	  			//�Ƴ�DIV�ӽڵ�
	  			var itemDiv = document.getElementById("itemDiv");   
				var childs = itemDiv.childNodes;   
				for(var i = 0; i < childs.length; i++) {   
				    itemDiv.removeChild(childs[i]);   
				}  
			}
			function deleteItem(issueItemId){
				var issueId = $('issueId').value;
				var urls = '${ctx}/questAction.do?method=deleteItem&&issueItemId='+issueItemId+'&issueId='+issueId;
				var para = "";
				if(confirm("ȷ���Ƿ�ɾ����")){
					var myAjax = new Ajax.Request(urls, {method:"post", parameters:para, onComplete:showItemResponse, asynchronous:true});
				}
			}
			function showItem(){
				var issueId = $('issueId').value;
				var urls = '${ctx}/questAction.do?method=showItem&&&issueId='+issueId;
				var para = "";
				var myAjax = new Ajax.Request(urls, {method:"post", parameters:para, onComplete:showItemResponse, asynchronous:true});
			}
		</script>
	</head>
	<body onload="showItem()">
		<template:titile value="��Ӳ�����"/>
		<html:form enctype="multipart/form-data" styleId="addIssueItem">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<input type="hidden" name="issueId" id="issueId" value="${issueId }" />
					<td class="tdulleft" style="width:20%;">ָ�����</td>
					<td class="tdulright">
						<select name="classId" style="width:150px;" class="inputtext" onchange="changeType()">
							<option value="">==��ѡ�����==</option>
							<c:forEach items="${classes}" var="class">
								<option value="${class.id }">${class.className }</option>
							</c:forEach>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">ָ����ࣺ</td>
					<td class="tdulright">
						<select id="sortId" style="width:150px;" class="inputtext" onchange="changeSort()">
							<option value="">==��ѡ�����==</option>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<div id="itemDiv"></div>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�����</td>
					<td class="tdulright">
						<input type="button" onclick="addItem()" value="��Ӳ�����">
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<div id="showItemDiv"></div>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2" align="center">
						<input type="button" onclick="checkForm()" value="�ύ">
					</td>
			</table>
		</html:form>
	</body>
</html>
