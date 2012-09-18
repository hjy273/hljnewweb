<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>�ʾ���</title>
		<script type="text/javascript">
			var win;
			function checkForm(state){
				if(state=="2"){
					if($('issueName').value==""){
						alert("�ʾ����Ʋ���Ϊ�գ�");
						return;
					}
					var showItemDiv=$('showItemDiv');
					var elementA=showItemDiv.getElementsByTagName("a");
					if(elementA.length<1){
						alert("����Ӳ����");
						return;
					}
					if($('contractorId').value==""){
						alert("����ѡ��һ����ά��˾��");
						return;
					}
					if($('comIds').value==""){
						alert("����ѡ��һ����������");
						return;
					}
					if($('remark').value==""){
						alert("�ʾ�˵������Ϊ�գ�");
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
					title:'���ָ����'
				});
				win.show(Ext.getBody());
			}
			//ɾ����
			function deleteItem(rownum){
				if(confirm("�Ƿ�ɾ����ָ���")){
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
		<template:titile value="�ʾ���"/>
		<html:form action="/questAction.do?method=addQuest" styleId="addQuest">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
				<tr class="trcolor">
					<!-- input type="hidden" name="issueId" id="issueId" value="${issueId }" /> -->
					<td class="tdulleft" style="width:20%;">�ʾ����ƣ�</td>
					<td class="tdulright">
						<input type="text" name="issueName" id="issueName" class="inputtext" style="width:150px;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ʾ����ͣ�</td>
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
					<td class="tdulleft" style="width:20%;">�����</td>
					<td class="tdulright">
						<input type="button" onclick="addQuestIssueItem()" value="��Ӳ�����">
					</td>
				</tr>
				<apptag:processorselect inputName="contractorId,userId,mobileId,conUser" 
						spanId="userSpan" displayType="contractor" />
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��������</td>
					<td class="tdulright">
						<span id="companyInfo"></span>
						<input type="hidden" name="comIds" id="comIds" value="">
						<input type="button" value="��Ӳ�������" onclick="addCompanyInfo()">
					</td>
				</tr>
				<tr class="trcolor">
					<td colspan="2">
						<div id="showItemDiv"></div>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">�ʾ�˵����</td>
					<td class="tdulright">
						<textarea class="textarea" id="remark" rows="3" name="remark"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
			<div style="height:20px; margin-left: 5%; width: 95%; margin-top: 5px;">
				<font color="red">�������Ϊ���������������������д���ޡ�</font>
			</div>
			<div align="center" style="height:40px">
				<input type="hidden" id="saveflag" name="saveflag" value="2"/>
				<html:button property="action" styleClass="button" onclick="checkForm(2)">�ύ</html:button> &nbsp;&nbsp;
				<html:button property="action" styleClass="button" onclick="checkForm(1)">�ݴ�</html:button>
			</div>
		</html:form>
	</body>
</html>
