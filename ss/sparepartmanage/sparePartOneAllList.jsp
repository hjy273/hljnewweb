<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant" %>
<html>
	<head>
		<script type="" language="javascript">
        toDeletForm=function(idValue){
       	 	if(confirm("你确定要执行此次删除操作吗?")){
            	var url = "${ctx}/SparepartStorageAction.do?method=deleteSavedStorage&storage_id="+idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		toUpForm=function(idValue){
		 	var url = "${ctx}/SparepartStorageAction.do?method=updateSavedStorageForm&storage_id="+idValue;
		    self.location.replace(url);
		}
        toGetForm=function(idValue){
			var flag = 3;
        	var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForOne&spare_part_id="+idValue+"&flag="+flag;
        	self.location.replace(url);
		}
		toStorageOpForm=function(method,param){
		 	var url = "${ctx}/SparepartStorageAction.do?method="+method+"&"+param;
		    self.location.replace(url);
		}
		toApplyForm=function(url,param){
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
		addGoBack=function(){
			var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorage";
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
		String id="";
		 %>
		<template:titile value="备件信息一览表" />
		<display:table name="sessionScope.STORAGE_LIST" id="currentRowObject" pagesize="18">
			<display:column property="product_factory" sortable="true" title="生产厂商" headerClass="subject" maxLength="10"/>
			<display:column property="spare_part_name" sortable="true" title="备件名称" headerClass="subject" maxLength="10"/>
			<display:column property="spare_part_model" sortable="true" title="备件型号" headerClass="subject" maxLength="10"/>
			<display:column property="serial_number" sortable="true" title="备件序列号" headerClass="subject" maxLength="15"/>
			<display:column property="storage_position" title="保存位置" headerClass="subject" maxLength="10"/>
			<display:column property="storage_number" title="库存数量" sortable="true" headerClass="subject" maxLength="10" class="subject"/>
			<display:column media="html" title="备件状态" sortable="true" class="subject" headerClass="subject">
				<%
				    object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
				    String state = (String) object.get("spare_part_state");
				    String path =(String)application.getAttribute("ctx");
				    if(SparepartConstant.MOBILE_WAIT_USE.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"移动备用\">");
				    }
				    if(SparepartConstant.CONTRACTOR_WAIT_USE.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"代维备用\">");
				    }
				    if(SparepartConstant.IS_RUNNING.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"运行\">");
				    }
				    if(SparepartConstant.IS_MENDING.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"送修\">");
				    }
				    if(SparepartConstant.IS_DISCARDED.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"报废\">");
				    }
				 %>
			</display:column>
			<display:column media="html" title="操作" headerClass="subject">
				<apptag:checkpower thirdmould="90704">
					<%
				    	object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    	id = (String) object.get("tid");
				    	String bl = (String)request.getSession().getAttribute("bl");
				    	if(bl.equals("true")) {
				  	%>
					  	<logic:equal value="01" name="currentRowObject" property="spare_part_state">
							<a href="javascript:toUpForm('<%=id%>')">修改</a> 
							| <a href="javascript:toDeletForm('<%=id%>')">删除</a>
						</logic:equal>
					<%} %>
				</apptag:checkpower>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px" align="center">
			<tr>
				<td style="text-align:left;">说明：
					<img src="${ctx}/images/sparepartstate/01.gif">表示“移动备用”状态，
					<img src="${ctx}/images/sparepartstate/02.gif">表示“代维备用”状态，
					<img src="${ctx}/images/sparepartstate/03.gif">表示“运行”状态，
					<img src="${ctx}/images/sparepartstate/04.gif">表示“送修”状态，
					<img src="${ctx}/images/sparepartstate/05.gif">表示“报废”状态。
				</td>
			</tr>
			
			<tr>
				<td style="text-align: center;">
					<input name="action" class="button" value="返回"
						onclick="addGoBack()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
