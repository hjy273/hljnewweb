<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<script type="text/javascript" language="javascript">
		
		toViewForm=function(idValue){
		 	window.location.href = "${ctx}/remedyTypeAction.do?method=viewRemedyTypeForm&id="+idValue;
		}    
		
        toDeletForm=function(idValue){
       	 	if(confirm("你确定要执行此次删除操作吗?")){
            	window.location.href = "${ctx}/remedyTypeAction.do?method=deleteRemedyType&id="+idValue;
            }           
		}
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/remedyTypeAction.do?method=editRemedyTypeForm&id="+idValue;
		}       
		
		exportList=function(){
			var url="${ctx}/remedyTypeAction.do?method=exportTypeList";
			self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/remedyTypeAction.do?method=queryRemedyTypeForm";
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
		BasicDynaBean object=null;
		Object id=null;
		 %>
		<template:titile value="修缮类别信息一览表" />
		<display:table name="sessionScope.types" id="currentRowObject" pagesize="18">
			<display:column property="typename" sortable="true" title="规格名称" headerClass="subject" maxLength="10"/>
			<display:column property="itemname" sortable="true" title="所属项目" headerClass="subject" maxLength="10"/>
			<display:column property="price" sortable="true" title="单价" headerClass="subject" maxLength="10"/>
			<display:column property="unit" sortable="true" title="单位" headerClass="subject" maxLength="10"/>
			 <display:column media="html" title="操作" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	      	 		id = object.get("id");
				} %>
				<a href="javascript:toViewForm('<%=id%>')">查看</a>
				|
	            		<a href="javascript:toEditForm('<%=id%>')">修改</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>')">删除</a>
	            		
	             
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">
		    <tr>
		    	<td>
		    	      <logic:notEmpty name="types">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    		
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="返回"
						onclick="goBack();" type="button" />
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
