<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant" %>
<html>
	<head>
		<script type="" language="javascript">
        toDeletForm=function(idValue){
       	 	if(confirm("你确定要执行此次删除操作吗?")){
            	window.location.href = "${ctx}/SparepartStorageAction.do?method=deleteSavedStorage&storage_id="+idValue;
	        	//self.location.replace(url);
            }
            else
            	return ;
		}
		toUpForm=function(idValue){
		 window.location.href= "${ctx}/SparepartStorageAction.do?method=updateSavedStorageForm&storage_id="+idValue;
		    //self.location.replace(url);
		}
     
		addGoBack=function(){
			window.location.href = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageByBack";
			//self.location.replace(url);
			//history.back();
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
		<template:titile value="备件基本信息一览表" />
		<display:table name="sessionScope.STORAGE_LIST" id="currentRowObject" pagesize="18">
			<display:column property="product_factory" sortable="true" title="生产厂商" headerClass="subject" maxLength="10"/>
			<display:column property="spare_part_name" sortable="true" title="备件名称" headerClass="subject" maxLength="10"/>
			<display:column property="spare_part_model" sortable="true" title="备件型号" headerClass="subject" maxLength="10"/>
			<display:column property="serial_number" sortable="true" title="备件序列号" headerClass="subject" />
			<%
				 object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
				String name =  (String) object.get("name");
				if(name != null && !name.trim().equals("")){%>
				<display:column property="name" title="保存位置" headerClass="subject" maxLength="20"/>
			<% 	}
				
			 %>
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
				    if(SparepartConstant.IS_APPLY.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"已被申请\">");
				    }
				    if(SparepartConstant.IS_REPLACE.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"被替换\">");
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
			<logic:equal value="01" name="currentRowObject" property="spare_part_state">
				<display:column media="html" title="操作" headerClass="subject" >
				<%
					 object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
					 id = (String) object.get("tid");
				 %>
					<apptag:checkpower thirdmould="90704">				
						<a href="javascript:toUpForm('<%=id%>')">修改</a> 
						| <a href="javascript:toDeletForm('<%=id%>')">删除</a>
					</apptag:checkpower>
				</display:column>
			</logic:equal>			
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px" align="center">
			<tr>
				<td style="text-align:left;">说明：
					<img src="${ctx}/images/sparepartstate/01.gif">表示“移动备用”状态，
					<img src="${ctx}/images/sparepartstate/02.gif">表示“代维备用”状态，
					<img src="${ctx}/images/sparepartstate/06.gif">表示“已被申请”状态，
					<img src="${ctx}/images/sparepartstate/07.gif">表示“被替换”状态，
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
