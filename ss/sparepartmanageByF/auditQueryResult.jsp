<%@include file="/common/header.jsp"%>
<%@page
	import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant"%>
<html>
	<head>
		<script type="" language="javascript">
	
		
        toGetForm=function(idValue){
        	window.location.href = "${ctx}/SparepartApplyAction.do?method=viewOneApplyInfoForApply&view_method=<%=(String) request.getAttribute("method")%>&apply_id="+idValue;
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
.subject {
	text-align: center;
}
</style>
	</head>
	<body>
		<br />
		<%
			BasicDynaBean object = null;
			String id = "";
		%>
		<template:titile value="����������Ϣһ����" />
		<display:table name="sessionScope.APPLY_LIST" requestURI="${ctx}/SparepartApplyAction.do?method=doQueryForAu" id="currentRowObject"
			pagesize="18">
			<display:column property="product_factory" title="������������"
				maxLength="10" headerClass="subject" />
			<display:column property="spare_part_name" title="���뱸������"
				maxLength="10" headerClass="subject" />
			<display:column property="serial_number" title="���뱸�����к�"
				maxLength="10" headerClass="subject" />
			<display:column property="apply_person" title="������"
				headerClass="subject" maxLength="10" />
			<display:column property="apply_date" title="��������" sortable="true"
				headerClass="subject" maxLength="10" />
			<display:column media="html" title="��˽��" sortable="true"
				headerClass="subject">
				<%
					object = (BasicDynaBean) pageContext
										.findAttribute("currentRowObject");
								String auditingResult = (String) object
										.get("auditing_state");
								if (SparepartConstant.AUDITING_PASSED
										.equals(auditingResult)) {
									out.print("���ͨ��");
								} else if (SparepartConstant.AUDITING_NOTPASSED
										.equals(auditingResult)) {
									out.print("<font color=\"#FF0000\">��˲�ͨ��</font>");
								} else {
									out.print("�����");
								}
				%>
			</display:column>
			<display:column media="html" title="����" headerClass="subject">
				<%
					object = (BasicDynaBean) pageContext
										.findAttribute("currentRowObject");
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
