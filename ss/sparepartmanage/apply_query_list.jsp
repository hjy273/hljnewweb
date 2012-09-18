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
        	var url = "${ctx}/SparepartApplyAction.do?method=viewOneApplyInfoForApply&view_method=<%=(String)request.getAttribute("method")%>&apply_id="+idValue;
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
			var url="${ctx}/SparepartApplyAction.do?method=exportApplyList";
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
		<template:titile value="����������Ϣһ����" />
		<display:table name="sessionScope.APPLY_LIST" id="currentRowObject"
			pagesize="18">
			<display:column property="product_factory" title="������������" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="using_part_name" title="���뱸������" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="serial_number" title="���뱸�����к�" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="use_number" title="��������" sortable="true"  headerClass="subject" maxLength="10" />
			<display:column property="apply_person" title="������" headerClass="subject" maxLength="10" sortable="true"  />
			<display:column property="apply_date" title="��������" sortable="true" headerClass="subject" maxLength="10" />
			<display:column media="html" title="��˽��" sortable="true" headerClass="subject" sortable="true" >
				<%
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				String auditingResult=(String)object.get("auditing_state");
				if(SparepartConstant.AUDITING_PASSED.equals(auditingResult)){
					out.print("���ͨ��");
				}else if(SparepartConstant.AUDITING_NOTPASSED.equals(auditingResult)){
					out.print("<font color=\"#FF0000\">��˲�ͨ��</font>");
				}else{
					out.print("�����");
				}
				 %>
			</display:column>
			<display:column media="html" title="����" headerClass="subject">
				<%
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				id = (String) object.get("tid");
				%>
				<a href="javascript:toGetForm('<%=id%>')">��ϸ</a>
				<apptag:checkpower thirdmould="90707">
					<%
					object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					id = (String) object.get("tid");
					%>
					<logic:empty name="currentRowObject" property="auditing_state">
						| <a href="javascript:toUpForm('<%=id%>')">�޸�</a>
					</logic:empty>
					<logic:equal value="02" name="currentRowObject" property="auditing_state">
						| <a href="javascript:toUpForm('<%=id%>')">�޸�</a>
					</logic:equal>
				</apptag:checkpower>
				<apptag:checkpower thirdmould="90707">
					<%
					object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					id = (String) object.get("tid");
					%>
					<logic:empty name="currentRowObject" property="auditing_state">
						| <a href="javascript:toDeletForm('<%=id%>')">ɾ��</a>
					</logic:empty>
					<logic:equal value="02" name="currentRowObject" property="auditing_state">
						| <a href="javascript:toDeletForm('<%=id%>')">ɾ��</a>
					</logic:equal>
				</apptag:checkpower>
				<apptag:checkpower thirdmould="90708">
					<%
					object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					id = (String) object.get("tid");
					%>
					<logic:empty name="currentRowObject" property="auditing_state">
						| <a href="javascript:toAuditForm('<%=id%>')">���</a>
					</logic:empty>
				</apptag:checkpower>
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
