<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant" %>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		gotoformformobile=function(idValue,bl) {
			var flag = 3;
        	var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForOneByMobile&spare_part_id="+idValue+"&flag="+flag+"&bl="+bl;
        	self.location.replace(url);
		}
		
		gotoformforcon=function(idValue) {
			var flag = 3;
        	var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForOneByCon&spare_part_id="+idValue+"&flag="+flag;
        	self.location.replace(url);
		}
		
		gotoformforrun=function(idValue) {
			var flag = 3;
        	var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForOneByRun&spare_part_id="+idValue+"&flag="+flag;
        	self.location.replace(url);
		}
		
		gotoformforrepair=function(idValue) {
			var flag = 3;
        	var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForOneByRepair&spare_part_id="+idValue+"&flag="+flag;
        	self.location.replace(url);
		}
		
		gotoformforscrap=function(idValue) {
			var flag = 3;
        	var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForOneByScrap&spare_part_id="+idValue+"&flag="+flag;
        	self.location.replace(url);
		}
		

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
			var url = "${ctx}/SparepartStorageAction.do?method=querySparepartStorageForm";
			self.location.replace(url);
		}
		exportList=function(){
			var url="${ctx}/SparepartStorageAction.do?method=exportStorageList1";
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
			<display:column media="html" title="移动备用" headerClass="subject" class="subject">
				<%
					object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
					String mobile = String.valueOf(object.get("mobilenum"));
					id = (String) object.get("tid");
					String total = String.valueOf(object.get("totalnum"));
					int totaln = Integer.parseInt(total);
					int mobilen = Integer.parseInt(mobile);
					String bl = "false";
					if(totaln == mobilen) {
						bl = "true";
					}
					if("0".equals(mobile)) { %>
						<%=mobile %>
				<%	} else {%>
				 <a href="javascript:gotoformformobile('<%=id %>','<%=bl %>')"> <%=mobile %> </a>
				<%  }%> 
			</display:column>
			<display:column  media="html" title="代维备用" headerClass="subject" class="subject">
				<%
					object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
					String connum = String.valueOf(object.get("connum"));
					id = (String) object.get("tid");
					if("0".equals(connum)) { %>
						<%=connum %>
				<%	} else {%>
				 <a href="javascript:gotoformforcon('<%=id %>')"> <%=connum %> </a>
				<%  }%> 
			</display:column>
			<display:column  media="html" title="正在运行" headerClass="subject" class="subject">
				<%
					object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
					String runningnum = String.valueOf(object.get("runningnum"));
					id = (String) object.get("tid");
					if("0".equals(runningnum)) { %>
						<%=runningnum %>
				<%	} else {%>
				 <a href="javascript:gotoformforrun('<%=id %>')"> <%=runningnum %> </a>
				<%  }%> 
			</display:column>
			<display:column media="html"  title="送修" headerClass="subject"   class="subject">
				<%
					object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
					String repairnum = String.valueOf(object.get("repairnum"));
					id = (String) object.get("tid");
					if("0".equals(repairnum)) { %>
						<%=repairnum %>
				<%	} else {%>
				 <a href="javascript:gotoformforrepair('<%=id %>')"> <%=repairnum %> </a>
				<%  }%> 
			</display:column>
			<display:column media="html"  title="报废" headerClass="subject" class="subject">
				<%
					object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
					String scrapnum = String.valueOf(object.get("scrapnum"));
					id = (String) object.get("tid");
					if("0".equals(scrapnum)) { %>
						<%=scrapnum %>
				<%	} else {%>
				 <a href="javascript:gotoformforscrap('<%=id %>')"> <%=scrapnum %> </a>
				<%  }%> 
			</display:column>
			<display:column property="totalnum"  title="总量" headerClass="subject" maxLength="10" class="subject">
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="导出为Excel"
						onclick="exportList()" type="button" />
				</td>
			</tr>
			
			
		
			<%--
			<tr>
				<td style="text-align:left;">说明：
					<img src="${ctx}/images/sparepartstate/01.gif">表示“移动备用”状态，
					<img src="${ctx}/images/sparepartstate/02.gif">表示“代维备用”状态，
					<img src="${ctx}/images/sparepartstate/03.gif">表示“运行”状态，
					<img src="${ctx}/images/sparepartstate/04.gif">表示“送修”状态，
					<img src="${ctx}/images/sparepartstate/05.gif">表示“报废”状态。
				</td>
			</tr>
			--%>
			
		</table>
	</body>
</html>
