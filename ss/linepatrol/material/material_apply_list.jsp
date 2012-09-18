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
            	window.location.href = "${ctx}/mtUsedAction.do?method=goMtUsedDisForm&id="+idValue;
		}
		
        toDeletForm=function(idValue){
       	 	if(confirm("你确定要执行此次删除操作吗?")){
            	window.location.href = "${ctx}/mtUsedAction.do?method=doMtUsedDeleteForm&id="+idValue;
            }           
		}
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/mtUsedAction.do?method=goMtUsedEditForm&id="+idValue;
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
		<template:titile value="材料申请一览表" />
		<display:table name="sessionScope.applyList" id="currentRowObject" pagesize="18">
			<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	      	 		id = object.get("id");
				} %>
			<display:column property="createtime" title="申请时间" headerClass="subject"  sortable="true"/>	
			<display:column property="contractorname" sortable="true" title="代维单位" headerClass="subject" maxLength="10"/>
			<display:column property="remark" sortable="true" title="备注" headerClass="subject" maxLength="30"/>
			<display:column media="html" sortable="true" title="状态">
				<%
					if(object != null) {
	      	 			String state = (String)object.get("state");
	      	 			if("1".equals(state)){
	      	 				out.println("审核不通过");
	      	 			}else if("0".equals(state)){
	      	 				out.println("待审核");
	      	 			}else{
	      	 				out.println("&nbsp;");
	      	 			}
				} 
				 %>
			</display:column>
			 <display:column media="html" title="操作" >
				
				<a href="javascript:toViewForm('<%=id%>')">查看</a>
				|
	            		<a href="javascript:toEditForm('<%=id%>')">修改</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>')">删除</a>
	            		
	             
            </display:column>
		</display:table>
	</body>
</html>
