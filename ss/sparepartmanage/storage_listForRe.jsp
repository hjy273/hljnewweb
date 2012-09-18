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
        	var url = "${ctx}/SparepartStorageAction.do?method=viewSavedStorage&storage_id="+idValue;
        	self.location.replace(url);
		}
		toStorageOpForm=function(method,param){
		 	var url = "${ctx}/SparepartStorageAction.do?method="+method+"&"+param;
		    self.location.replace(url);
		}
		toApplyForm=function(url,param){
			self.location.replace(url);
		}
		toApplyFormMod=function(url) {
			self.location.replace(url);
		}
		toStorageOpForm=function(method,param){
		 	var url = "${ctx}/SparepartStorageAction.do?method="+method+"&"+param;
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
			var url = "${ctx}/SparepartStorageAction.do?method=querySparepartStorageForm";
			self.location.replace(url);
		}
		exportList=function(){
			var url="${ctx}/SparepartStorageAction.do?method=exportStorageList";
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
		String num="";
		 %>
		<template:titile value="备件申请一览表" />
		<display:table name="sessionScope.STORAGE_LIST" id="currentRowObject" pagesize="18">
			<display:column property="product_factory" sortable="true" title="生产厂商" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_name" sortable="true" title="备件名称" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_model" sortable="true" title="备件型号" headerClass="subject" maxLength="10" />
			<display:column property="serial_number" sortable="true" title="备件序列号" headerClass="subject" maxLength="15" />
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
			<display:column property="storage_position" title="保存位置" headerClass="subject" maxLength="10" />
			<display:column property="storage_number" title="库存数量" sortable="true" headerClass="subject" maxLength="10" />
			<display:column media="html" title="操作" headerClass="subject">
				<apptag:checkpower thirdmould="90711">
					<%
				    	object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    	id = (String) object.get("tid");
				    	num = String.valueOf( object.get("storage_number"));
				  	%>
				  	<%--<logic:equal value="02" name="currentRowObject" property="spare_part_state">
						<a href="javascript:toApplyForm('${ctx}/SparepartApplyAction.do?method=listApply&storage_id=<%=id%>')">备件申请</a>
					--%>
						
						 
							<%if(!num.equals("0")) { %>
								<logic:equal value="02" name="currentRowObject" property="spare_part_state">
									<a href="javascript:toApplyFormMod('${ctx}/SparepartApplyAction.do?method=addApplyForm&storage_id=<%=id%>')" >添加申请</a> | 
								</logic:equal>
								<a href="javascript:toStorageOpForm('giveBackToStorageForm','from_storage_id=<%=id%>')">归还备件</a>
							<%} %>
						<%--<logic:equal value="03" name="currentRowObject" property="spare_part_state">
							 | <a href="javascript:toApplyFormMod('${ctx}/SparepartApplyAction.do?method=addApplyForm&storage_id=<%=id%>')" >更换使用</a>
						</logic:equal>
					--%><%--</logic:equal>--%>
				</apptag:checkpower>	
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">
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
				<td style="text-align:center;">
					<input name="action" class="button" value="导出为Excel"
						onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
