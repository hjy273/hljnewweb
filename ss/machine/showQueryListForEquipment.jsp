<%@include file="/common/header.jsp"%>

<html>
	<head>
		<script type="" language="javascript">
		addGoBack=function(){
			var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorage";
			self.location.replace(url);
		}
        toDelForm=function(idValue){
       	 	if(confirm("你确定要执行此次删除操作吗?")){
            	var url = "${ctx}/EquipmentInfoAction.do?method=deleteForEqu&eid="+idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		toUpForm=function(idValue){
			var url = "${ctx}/EquipmentInfoAction.do?method=showUpdateForEqu&eid="+idValue;
			self.location.replace(url);
		}
        toGetForm=function(idValue){
        	var url = "${ctx}/SparepartApplyAction.do?method=viewOneApplyInfoForApply&view_method=<%=(String)request.getAttribute("method")%>&apply_id="+idValue;
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
		<template:titile value="设备管理一览表" />
		<display:table name="sessionScope.equipmentList" id="currentRowObject"
			pagesize="18">
			<display:column property="equipment_name" title="设备名称" sortable="true" 
				 headerClass="subject" />
			<display:column property="contractorname" title="代维公司" sortable="true" 
				 headerClass="subject" />
			<display:column media="html" title="所在层" sortable="true" 
				 headerClass="subject" >
				<%
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				int layer = Integer.parseInt((String)object.get("layer"));
				if(1 == layer){
					out.print("核心层");
				}else if(2 == layer){
					out.print("接入层SDH");
				}else if(3 == layer){
					out.print("接入层微波");
				}else if(4 == layer){
					out.print("接入层FSO");
				}else if(5 == layer){
					out.print("光交维护");
				}
				 %>
			</display:column>
			<display:column media="html" title="操作" headerClass="subject">
				
					<%
					object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					id = (String) object.get("eid");
					%>
					 <a href="javascript:toUpForm('<%=id%>')">修改</a>
					| <a href="javascript:toDelForm('<%=id%>')">删除</a>
			</display:column>
			
		</display:table>
	</body>
</html>
