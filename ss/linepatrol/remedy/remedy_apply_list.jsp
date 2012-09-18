<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<script type="text/javascript" language="javascript">
        toDeleteForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	var url = "<%=request.getContextPath()%>/remedy_apply.do?method=deleteApply&&apply_id="+idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		toViewForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/remedy_apply.do?method=view&&power=52004&&to_page=view_remedy_apply&&apply_id="+idValue;
		    self.location.replace(url);
		} 
		toEditForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/remedy_apply.do?method=view&&power=52002&&to_page=update_remedy_apply&&apply_id="+idValue;
		    self.location.replace(url);
		} 
		toApproveForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/remedy_apply_approve.do?method=view&&power=52101&&to_page=approve_remedy_apply&&apply_id="+idValue;
		    self.location.replace(url);
		}       
		toCheckForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/remedy_apply_check.do?method=view&&power=52201&&to_page=check_remedy_apply&&apply_id="+idValue;
		    self.location.replace(url);
		}       
		toSquareForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/remedy_apply_square.do?method=view&&power=52301&&to_page=square_remedy_apply&&apply_id="+idValue;
		    self.location.replace(url);
		}  
		goBack=function(){
			var url = "<%=request.getContextPath()%>/remedy_apply.do?method=queryApplyForm";
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
			    object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
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
			<display:column property="totalfee" title="�ϼ�"
				headerClass="subject" />
			<display:column property="status_name" sortable="true" title="����״̬"
				headerClass="subject" />
			<display:column media="html" title="����">
				<logic:equal value="52004" name="POWER" scope="session">
					<logic:equal value="1" property="allow_edited"
						name="currentRowObject">
						<apptag:checkpower thirdmould="52002">
							<a href="javascript:toEditForm('<%=id%>')">�޸�</a>
						</apptag:checkpower>
					</logic:equal>
					<logic:equal value="1" property="allow_deleted"
						name="currentRowObject">
						<apptag:checkpower thirdmould="52003">
	            		| <a href="javascript:toDeleteForm('<%=id%>')">ɾ��</a>
						</apptag:checkpower>
					</logic:equal>
				</logic:equal>
				<logic:equal value="52101" name="POWER" scope="session">
					<logic:equal value="1" property="allow_approved"
						name="currentRowObject">
						<apptag:checkpower thirdmould="52101">
							<a href="javascript:toApproveForm('<%=id%>')">����</a>
						</apptag:checkpower>
					</logic:equal>
				</logic:equal>
				<logic:equal value="52201" name="POWER" scope="session">
					<logic:equal value="1" property="allow_checked"
						name="currentRowObject">
						<apptag:checkpower thirdmould="52201">
							<a href="javascript:toCheckForm('<%=id%>')">����</a>
						</apptag:checkpower>
					</logic:equal>
				</logic:equal>
				<logic:equal value="52301" name="POWER" scope="session">
					<logic:equal value="1" property="allow_squared"
						name="currentRowObject">
						<apptag:checkpower thirdmould="52301">
							<a href="javascript:toSquareForm('<%=id%>')">����</a>
						</apptag:checkpower>
					</logic:equal>
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
					<%
					    if ("52004".equals(session.getAttribute("POWER"))) {
					%>
					<input name="action" class="button" value="����" onclick="goBack();"
						type="button" />
					<%
					    }
					%>
				</td>
			</tr>
		</table>
	</body>
</html>
