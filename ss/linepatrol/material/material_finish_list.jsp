<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		
		 toViewForm=function(idValue){
            	window.location.href = "${ctx}/mtUsedAssessAction.do?method=displayFinishMtUsed&id="+idValue;
		}
		
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/mtUsedAction.do?method=getMtUsedApplyForm&mtUsedId="+idValue;
		}   
		
		toAginEditForm=function(idValue){
		 	window.location.href = "${ctx}/mtUsedAssessAction.do?method=goMtUsedEditForm&id="+idValue;
		}      
		</script>
		<title>材料类型</title>
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
		Object id=null;		
			String state = null;
		 %>
		<template:titile value="材料盘点完成一览表" />
		<display:table name="sessionScope.finishList" id="currentRowObject" pagesize="18">
			<display:column property="createtime" title="申请时间" headerClass="subject"  sortable="true"/>	
      		<display:column property="contractorname" sortable="true" title="代维公司" headerClass="subject" maxLength="10"/>
			<display:column property="username" sortable="true" title="申请人" headerClass="subject" maxLength="10"/>
			<display:column property="remark" sortable="true" title="备注" headerClass="subject" maxLength="10"/>
			<display:column media="html" sortable="true" title="状态" headerClass="subject" maxLength="10">
			<% 
				object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				 if(object != null) {
				state = (String)object.get("state");
				if("4".equals(state)){
					out.print("移动审核通过");
				}else if("3".equals(state)){
					out.print("移动审核不通过");
				}
				}
			%>
			</display:column>
			 <display:column media="html" title="操作" >
				<% 
	            if(object != null) {
	      	 		id = object.get("id");
				} %>
				<a href="javascript:toViewForm('<%=id%>')">查看</a>
            </display:column>
		</display:table>
	</body>
</html>
