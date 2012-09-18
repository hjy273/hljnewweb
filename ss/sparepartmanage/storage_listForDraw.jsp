<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant" %>
<html>
	<head>
		<script type="" language="javascript">
        toDeletForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	var url = "${ctx}/SparepartStorageAction.do?method=deleteSavedStorage&storage_id="+idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		toUpForm=function(idValue){
		 	var url = "${ctx}/SparepartStorageAction.do?method=updateSavedStorageForm&storage_id="+idValue;
		    self.location.replace(url);
		}
        toGetForm=function(idValue){
			var flag = 2;
        	var url = "${ctx}/SparepartStorageAction.do?method=viewSavedStorage&storage_id="+idValue+"&flag=" + flag;
        	self.location.replace(url);
		}
		toStorageOpForm=function(method,param){
		 	var url = "${ctx}/SparepartStorageAction.do?method="+method+"&"+param;
		    self.location.replace(url);
		}
		toApplyForm=function(url,param){
			self.location.replace(url);
		}
		valCharLength=function(Value){
			var j=0;
			var s=Value;
			for(var i=0;i<s.length;i++){
				if(s.substr(i,1).charCodeAt(0)>255){
					j=j+2;
				}else{
					j++;
				}
			}
			return j;
		}
		addGoBack=function(){
			var url = "${ctx}/SparepartStorageAction.do?method=querySparepartStorageForm";
			self.location.replace(url);
		}
		exportList=function(){
			var url="${ctx}/SparepartStorageAction.do?method=exportStorageList";
			self.location.replace(url);
		}

		function checkOrCancel() {
			var all = document.getElementById('sel');
			if(all.checked) {
				checkAll();
			} else {
				cancelCheck();
			}
		}

		function checkAll() {
			var allChecks = document.getElementsByName('ifCheck');
			var length = allChecks.length;
			for(var i = 0; i < length; i++) {
				allChecks[i].checked = true;
			}
		}
		
		function cancelCheck() {
			var allChecks = document.getElementsByName('ifCheck');
			var length = allChecks.length;
			for(var i = 0; i < length; i++) {
				allChecks[i].checked = false;
			}
		}

		function drawMore() {
			var idStr = null;
			var subCheck = document.getElementsByName('ifCheck');
			var subLength = subCheck.length;
			var length = 0;
			for(var i = 0; i < subLength; i++ ) {
				if(subCheck[i].checked == true) {
					length ++;
					if(idStr == null) {
						idStr = "'" + subCheck[i].value + "'";
					} else {
						idStr += ",'" + subCheck[i].value + "'";
					}
				}	
			}
			if(length == 0) {
				alert("��ѡ���������õı���!");
				return;
			}
			var url= "SparepartStorageAction.do?method=drawMore&idStr=" + idStr;
			
		}
		</script>
		<title>partBaseInfo</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />
		<%
		BasicDynaBean object=null;
		String id="";
		 %>
		<template:titile value="��������һ����" />
		<display:table name="sessionScope.STORAGE_LIST" id="currentRowObject" pagesize="18">
			<%--<display:column media="html" title="����" headerClass="subject">
				<%
				    object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
				    id = (String) object.get("tid");
				  %>
				<input type="checkbox" name="ifcheck" value="<%=id %>">
			</display:column>
			--%><display:column property="product_factory" sortable="true" title="��������" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_name" sortable="true" title="��������" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_model" sortable="true" title="�����ͺ�" headerClass="subject" maxLength="10" />
			<display:column property="storage_position" title="����λ��" headerClass="subject" maxLength="10" />
			
			<display:column property="storage_number" title="�������" sortable="true" headerClass="subject" maxLength="10" />
			<display:column media="html" title="����" headerClass="subject">
				<%
				    object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
				    id = (String) object.get("tid");
				  %>
				<a href="javascript:toGetForm('<%=id%>')">��ϸ</a>
				<apptag:checkpower thirdmould="90706">
					<%
				    	object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    	id = (String) object.get("tid");
				  	%>
				  	<logic:notEqual value="0" name="currentRowObject" property="storage_number">
					  	<logic:equal value="01" name="currentRowObject" property="spare_part_state">
							| <a href="javascript:toStorageOpForm('takeOutFromStorageForm','mobile_storage_id=<%=id%>')">���ñ���</a>
						</logic:equal>
					</logic:notEqual>
				</apptag:checkpower>	
			</display:column>
		</display:table>
		<div style="width: 100%; text-align: center;">
			<%--<div style="text-align: left;float: left;">
				<input type="checkbox" id="sel" onclick="checkOrCancel()">ȫѡ/ȫ��ѡ&nbsp;
				<input type="button" onclick="drawMore()" class="button2" value="����ѡ�б���">
			</div>
			--%>
				<input name="action" class="button" value="����ΪExcel" onclick="exportList()" type="button" />
		</div>
	</body>
</html>
