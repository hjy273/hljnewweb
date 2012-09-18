<%@include file="/common/header.jsp"%>
<%@page
	import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant"%>
<html>
	<head>
		<script type="" language="javascript">
		addGoBack=function(){
			var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorage";
			self.location.replace(url);
		}
		toAddForm=function(){
			var url="${ctx}/SparepartApplyAction.do?method=addApplyForm&storage_id=<%=(String) request.getAttribute("storage_id")%>";
			self.location.replace(url);
		}
        toDeletForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	var url = "${ctx}/SparepartApplyAction.do?method=delApply&apply_id="+idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		toUpForm=function(idValue){
		 	var url = "${ctx}/SparepartApplyAction.do?method=editApplyForm&apply_id="+idValue;
		    self.location.replace(url);
		}
		toAuditForm=function(idValue){
		 	var url = "${ctx}/SparepartAuditingAction.do?method=auditingApplyForm&apply_id="+idValue;
		    self.location.replace(url);
		}
        toGetForm=function(idValue){
        	var url = "${ctx}/SparepartStorageAction.do?method=showOneRestoreInfo&storage_id="+idValue;
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
		BasicDynaBean object = null;
		String id = "";
		%>
		<template:titile value="�������������Ϣһ����" />
		<display:table name="sessionScope.STORAGE_LIST" id="currentRowObject" pagesize="18">
			<display:column property="product_factory" sortable="true" title="��������" headerClass="subject" maxLength="10" />
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
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="����ΪExcel"
						 onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
	