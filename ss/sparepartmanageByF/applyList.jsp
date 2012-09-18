<%@include file="/common/header.jsp"%>
<%@page
	import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant"%>
<html>
	<head>
		<script type="" language="javascript">
		toAddForm=function(){
			window.location.href="${ctx}/SparepartApplyAction.do?method=addApplyForm&storage_id=<%=(String) request.getAttribute("storage_id")%>";
			//self.location.replace(url);
		}
     
		toAuditForm=function(idValue){
		 	window.location.href = "${ctx}/SparepartAuditingAction.do?method=auditingApplyForm&apply_id="+idValue;
		    //self.location.replace(url);
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
		%>
		<template:titile value="备件申请信息一览表" />
		<display:table name="sessionScope.APPLY_LIST" requestURI="${ctx}/SparepartApplyAction.do?method=listWaitAuditingApplyForAu" id="currentRowObject"
			pagesize="18">
			<display:column property="product_factory" title="备件生产厂商" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="spare_part_name" title="备件名称" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="spare_part_model" title="备件型号" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="serial_number" title="备件数量" sortable="true" 
				maxLength="10" headerClass="subject" />
			<display:column property="username" title="申请人" headerClass="subject" maxLength="10" sortable="true"  />
			<display:column property="apply_date" title="申请日期" sortable="true" headerClass="subject" maxLength="25" />
			<display:column media="html" title="审核结果" sortable="true" headerClass="subject" sortable="true" >
				<%
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				String auditingResult=(String)object.get("auditing_state");
				if(SparepartConstant.AUDITING_PASSED.equals(auditingResult)){
					out.print("审核通过");
				}else if(SparepartConstant.AUDITING_NOTPASSED.equals(auditingResult)){
					out.print("<font color=\"#FF0000\">审核不通过</font>");
				}else{
					out.print("待审核");
				}
				 %>
			</display:column>
			<display:column media="html" title="操作" headerClass="subject">
				<%
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				id = (String) object.get("appfid");
				%>
				<a href="javascript:toGetForm('<%=id%>')">详细</a>			
							
					<%
					object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					id = (String) object.get("appfid");
					%>
					<logic:equal value="00" property="auditing_state" name="currentRowObject">
					 	|&nbsp;<a href="javascript:toAuditForm('<%=id%>')">审核</a>
					</logic:equal>
			
				</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td style="text-align:center;">
					<apptag:checkpower thirdmould="90707">
						<input name="action" class="button" value="添加申请"
						 	onclick="toAddForm()" type="button" />
					</apptag:checkpower>
					<input name="action" class="button" value="导出为Excel"
						 onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
