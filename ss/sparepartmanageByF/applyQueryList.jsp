<%@include file="/common/header.jsp"%>
<%@page
	import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant"%>
<html>
	<head>
		<script type="" language="javascript">
		
        toDeletForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	window.location.href = "${ctx}/SparepartApplyAction.do?method=delApply&apply_id="+idValue;
	        	//self.location.replace(url);
            }
            else
            	return ;
		}
		toUpForm=function(idValue,baseid){
		 	//var url = "${ctx}/SparepartApplyAction.do?method=editApplyForm&apply_id="+idValue;
		    //self.location.replace(url);
			window.location.href = "${ctx}/SparepartApplyAction.do?method=viewOneApplyInfoForApply&view_method=<%=(String)request.getAttribute("method")%>&apply_id="+idValue+"&act=update&baseid="+baseid;
		}
		toAuditForm=function(idValue){
		 	window.location.href = "${ctx}/SparepartAuditingAction.do?method=auditingApplyForm&apply_id="+idValue;
		   // self.location.replace(url);
		}
        toGetForm=function(idValue){
        	window.location.href = "${ctx}/SparepartApplyAction.do?method=viewOneApplyInfoForApply&view_method=<%=(String)request.getAttribute("method")%>&apply_id="+idValue;
        	//self.location.replace(url);
		}
		
		exportList=function(){
			window.location.href="${ctx}/SparepartApplyAction.do?method=exportApplyList";
			//self.location.replace(url);
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
		String sparepartId = "";
		%>
		<template:titile value="����������Ϣһ����" />
		<display:table name="sessionScope.APPLY_LIST" requestURI="${ctx}/SparepartApplyAction.do?method=doQueryForApply" id="currentRowObject"
			pagesize="18">
			<display:column property="product_factory" title="������������" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="spare_part_name" title="���뱸������" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="spare_part_model" title="�����ͺ�" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="serial_number" title="��������" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="username" title="������" headerClass="subject" maxLength="10" sortable="true"  />
			<display:column property="apply_date" title="��������" sortable="true" headerClass="subject" maxLength="20" />
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
				id = (String) object.get("appfid");
				%>
				<a href="javascript:toGetForm('<%=id%>')">��ϸ</a>
				<apptag:checkpower thirdmould="90707">
					<%
					object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					id = (String) object.get("appfid");
					sparepartId = (String) object.get("spare_part_id");
					%>
					<logic:empty name="currentRowObject" property="auditing_state">
						| <a href="javascript:toUpForm('<%=id%>','<%=sparepartId %>')">�޸�</a>
					</logic:empty>
					<logic:equal value="00" name="currentRowObject" property="auditing_state">
						| <a href="javascript:toUpForm('<%=id%>','<%=sparepartId %>')">�޸�</a>
					</logic:equal>
				</apptag:checkpower>
				<apptag:checkpower thirdmould="90707">
					<%
					object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					id = (String) object.get("appfid");
					%>
					<logic:empty name="currentRowObject" property="auditing_state">
						| <a href="javascript:toDeletForm('<%=id%>')">ɾ��</a>
					</logic:empty>
					<logic:equal value="00" name="currentRowObject" property="auditing_state">
						| <a href="javascript:toDeletForm('<%=id%>')">ɾ��</a>
					</logic:equal>
				</apptag:checkpower>
				<apptag:checkpower thirdmould="90708">
					<%
					object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					id = (String) object.get("appfid");
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
