<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>添加参评项</title>
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
  				ops.options.add(new Option("==请选择分类==", ""));
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
					alert("请选择分类!");
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
  				cell2.innerHTML="指标类别";
  				var cell3=row.insertCell();
  				cell3.setAttribute("width","15%");
  				cell3.innerHTML="指标分类";
  				var cell4=row.insertCell();
  				cell4.setAttribute("width","40%");
  				cell4.innerHTML="指标细项";
  				var cell5=row.insertCell();
  				cell5.setAttribute("width","5%");
  				cell5.innerHTML="细项权重";
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
  				cell2.innerHTML="指标类别";
  				var cell3=row.insertCell();
  				cell3.setAttribute("width","15%");
  				cell3.innerHTML="指标分类";
  				var cell4=row.insertCell();
  				cell4.setAttribute("width","30%");
  				cell4.innerHTML="指标细项";
  				var cell5=row.insertCell();
  				cell5.setAttribute("width","10%");
  				cell5.innerHTML="细项权重";
  				var cell1=row.insertCell();
  				cell1.setAttribute("width","10%");
  				cell1.innerHTML="操作";
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
	  				cell1.innerHTML="<a onclick=deleteItem('"+queryRes[i].quest_id+"') style='color:blue;cursor:pointer;'>删除</a>";
	  			}
	  			showItemDiv.appendChild(tab);
	  			//移除DIV子节点
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
				if(confirm("确认是否删除！")){
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
		<template:titile value="添加参评项"/>
		<html:form enctype="multipart/form-data" styleId="addIssueItem">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<input type="hidden" name="issueId" id="issueId" value="${issueId }" />
					<td class="tdulleft" style="width:20%;">指标类别：</td>
					<td class="tdulright">
						<select name="classId" style="width:150px;" class="inputtext" onchange="changeType()">
							<option value="">==请选择类别==</option>
							<c:forEach items="${classes}" var="class">
								<option value="${class.id }">${class.className }</option>
							</c:forEach>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">指标分类：</td>
					<td class="tdulright">
						<select id="sortId" style="width:150px;" class="inputtext" onchange="changeSort()">
							<option value="">==请选择分类==</option>
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
					<td class="tdulleft" style="width:20%;">参评项：</td>
					<td class="tdulright">
						<input type="button" onclick="addItem()" value="添加参评项">
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<div id="showItemDiv"></div>
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2" align="center">
						<input type="button" onclick="checkForm()" value="提交">
					</td>
			</table>
		</html:form>
	</body>
</html>
