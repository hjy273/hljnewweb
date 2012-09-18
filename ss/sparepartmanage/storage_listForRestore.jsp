<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant" %>
<html>
	<head>
		<script type="" language="javascript">
        toDeletForm=function(idValue){
       	 	if(confirm("你确定要执行此次删除操作吗?")){
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
			var flag = 1;
        	var url = "${ctx}/SparepartStorageAction.do?method=viewSavedStorage&storage_id="+idValue+"&flag="+flag;
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
		<template:titile value="备件重新入库信息一览表" />
		<display:table name="sessionScope.STORAGE_LIST" id="currentRowObject" pagesize="18">
			<display:column property="product_factory" sortable="true" title="生产厂商" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_name" sortable="true" title="备件名称" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_model" sortable="true" title="备件型号" headerClass="subject" maxLength="10" />
			<display:column property="storage_position" title="保存位置" headerClass="subject" maxLength="10" />
			<display:column property="storage_number" title="库存数量" sortable="true" headerClass="subject" maxLength="10" />
			
			<display:column media="html" title="操作" headerClass="subject">
				<%
				    object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
				    id = (String) object.get("tid");
				  %>
				<a href="javascript:toGetForm('<%=id%>')">详细</a>
				<apptag:checkpower thirdmould="90710">
					<%
				    	object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    	id = (String) object.get("tid");
				  	%>
						| <a href="javascript:toStorageOpForm('restoreToStorageForm','storage_id=<%=id%>')">重新入库备件</a>
				</apptag:checkpower>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="导出为Excel"
						onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
