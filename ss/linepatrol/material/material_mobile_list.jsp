<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		
		 toGetForm=function(idValue){
            	window.location.href = "${ctx}/materialTypeAction.do?method=getTypesByItemID&id="+idValue;
		}
		
		 toViewForm=function(idValue){
            	window.location.href = "${ctx}/materialTypeAction.do?method=viewTypeByID&id="+idValue;
		}
		
        toDeletForm=function(idValue){
       	 	if(confirm("你确定要执行此次删除操作吗?")){
            	window.location.href = "${ctx}/materialTypeAction.do?method=deleteMeterialType&id="+idValue;
            }           
		}
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/mtUsedAssessAction.do?method=goMtUsedAppoverForm&mtUsedId="+idValue;
		}       
		
		exportList=function(){
			var url="${ctx}/materialTypeAction.do?method=exportTypeList";
			self.location.replace(url);
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
		 %>
		<template:titile value="（移动审核）材料盘点申请一览表" />
		<display:table name="sessionScope.approveList" id="currentRowObject" pagesize="18">
			<display:column property="createtime" title="申请时间" headerClass="subject"  sortable="true"/>	
      		<display:column property="contractorname" sortable="true" title="代维公司" headerClass="subject" maxLength="10"/>
			<display:column property="username" sortable="true" title="申请人" headerClass="subject" maxLength="10"/>
			<display:column property="remark" sortable="true" title="备注" headerClass="subject" maxLength="10"/>
			<display:column media="html" sortable="true" title="状态" headerClass="subject" maxLength="10">
			<% 
				object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				 if(object != null) {
				String state = (String)object.get("state");
				if("2".equals(state)){
					out.print("审核通过");
				}else if("1".equals(state)){
					out.print("审核不通过");
				}else{
					out.print("待审核");
				}
				}
			%>
			</display:column>
			 <display:column media="html" title="操作" >
				<% 
	            if(object != null) {
	      	 		id = object.get("id");
				} %>
	            		<a href="javascript:toEditForm('<%=id%>')">审核</a>
            </display:column>
		</display:table>
	</body>
</html>
