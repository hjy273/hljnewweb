<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>问卷发布</title>
		<script type="text/javascript">
			var win;
			function checkForm(state){
				if(state=="2"){
					if($('issueName').value==""){
						alert("问卷名称不能为空！");
						return;
					}
					var showItemDiv=$('showItemDiv');
					var elementA=showItemDiv.getElementsByTagName("a");
					if(elementA.length<1){
						alert("请添加参评项！");
						return;
					}
					if($('contractorId').value==""){
						alert("至少选择一个代维公司！");
						return;
					}
					if($('comIds').value==""){
						alert("至少选择一个参评对象！");
						return;
					}
					if($('remark').value==""){
						alert("问卷说明不能为空！");
						return;
					}
				}else{
					$('saveflag').value="1";
				}
				processBar(addQuest);
				addQuest.submit();
			}
			function addCompanyInfo(){
				var companyId=$('comIds').value;
				var url = "/WebApp/questAction.do?method=addCompany&companyId="+companyId;
				win = new Ext.Window({
					layout : 'fit',
					width:450,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
			
			function addQuestIssueItem(){
				var itemIds="";
				var queryType = $('queryType').value;
				var itemId = document.forms[0].elements["itemId"];
				if(typeof(itemId)!="undefined"){
					if(typeof(itemId.length)!="undefined"){
						for(var i=0;i<itemId.length;i++){
							itemIds+=itemId[i].value + ",";
						}
					}else{
						itemIds+=itemId.value + ",";
					}
				}
				var url = "/WebApp/questAction.do?method=loadQuestIssueItem&queryType="+queryType+"&itemIds="+itemIds;
				win = new Ext.Window({
					layout : 'fit',
					width:450,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true,
					title:'添加指标项'
				});
				win.show(Ext.getBody());
			}
			//删除行
			function deleteItem(rownum){
				if(confirm("是否删除该指标项！")){
					var showItemTable = $('showItemTable');
					for(var i = 0; i < showItemTable.rows.length; i++){
						if(showItemTable.rows[i].id==rownum){
							showItemTable.deleteRow(showItemTable.rows[i].rowIndex);
						}
					}
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="问卷发布"/>
		<html:form action="/questAction.do?method=addQuest" styleId="addQuest">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<!-- input type="hidden" name="issueId" id="issueId" value="${issueId }" /> -->
					<td class="tdulleft" style="width:20%;">问卷名称：</td>
					<td class="tdulright">
						<input type="text" name="issueName" id="issueName" class="inputtext" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">问卷类型：</td>
					<td class="tdulright">
						<select id="queryType" name="queryType" style="width:150px;" class="inputtext">
							<c:forEach items="${questTypes}" var="questType">
								<option value="${questType.id }">${questType.type }</option>
							</c:forEach>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">参评项：</td>
					<td class="tdulright">
						<input type="button" onclick="addQuestIssueItem()" value="添加参评项">
					</td>
				</tr>
				<apptag:processorselect inputName="contractorId,userId,mobileId,conUser" 
						spanId="userSpan" displayType="contractor" />
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">参评对象：</td>
					<td class="tdulright">
						<span id="companyInfo"></span>
						<input type="hidden" name="comIds" id="comIds" value="">
						<input type="button" value="添加参评对象" onclick="addCompanyInfo()">
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<div id="showItemDiv"></div>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">问卷说明：</td>
					<td class="tdulright">
						<textarea class="textarea" id="remark" rows="3" name="remark"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">所有项均为必填项，若无内容描述请填写“无”</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" id="saveflag" name="saveflag" value="2"/>
				<html:button property="action" styleClass="button" onclick="checkForm(2)">提交</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">暂存</html:button>
			</div>
		</html:form>
	</body>
</html>
