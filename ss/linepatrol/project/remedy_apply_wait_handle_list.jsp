<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<script type="text/javascript" language="javascript">
        toDeleteForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	var url = "${pageContext.request.contextPath}/project/remedy_apply.do?method=deleteApply&&apply_id="+idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		toViewForm=function(idValue){
		 	var url = "${pageContext.request.contextPath}/project/remedy_apply.do?method=view&&apply_id="+idValue;
		    self.location.replace(url);
		} 
		toEditForm=function(idValue){
		 	var url = "${pageContext.request.contextPath}/project/remedy_apply.do?method=updateApplyForm&&apply_id="+idValue;
		    self.location.replace(url);
		} 
		toApproveForm=function(idValue){
		 	var url = "${pageContext.request.contextPath}/project/remedy_apply_approve.do?method=approveApplyForm&&apply_id="
				+ idValue;
			self.location.replace(url);
		}
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
.subject {
	text-align: center;
}
</style>
	</head>
	<body>
		<br />
		<%
			BasicDynaBean object = null;
			Object id = null;
		%>
		<template:titile value="���������б�" />
		<display:table name="sessionScope.APPLY_LIST" id="currentRowObject"
			pagesize="18">
			<%
				object = (BasicDynaBean) pageContext
							.findAttribute("currentRowObject");
					if (object != null) {
						id = object.get("id");
					}
			%>
			<display:column property="remedycode" sortable="true" title="���"
				headerClass="subject" />
			<display:column media="html" sortable="true" title="��Ŀ����"
				headerClass="subject" maxLength="15">
				<a href="javascript:toViewForm('<%=id%>')"><%=object.get("projectname")%></a>
			</display:column>
			<display:column property="remedydate" sortable="true" title="����ʱ��"
				headerClass="subject" />
			<display:column property="totalfee" title="�ϼ�" headerClass="subject" />
			<display:column media="html" title="����">
				<logic:equal value="remedy_apply_task" property="flow_state"
					name="currentRowObject">
					<a href="javascript:toEditForm('<%=id%>')">�޸�</a>
				</logic:equal>
				<logic:equal value="remedy_apply_task" property="flow_state"
					name="currentRowObject">
					<a href="javascript:toDeleteForm('<%=id%>')">ɾ��</a>
				</logic:equal>
				</logic:equal>
				<logic:equal value="remedy_approve_task" property="flow_state"
					name="currentRowObject">
					<a href="javascript:toApproveForm('<%=id%>')">����</a>
				</logic:equal>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border: 0px">
			<tr>
				<td>
					<a href="#"></a>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
				</td>
			</tr>
		</table>
	</body>
</html>
