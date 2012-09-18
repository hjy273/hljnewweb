<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;" %>

<html>
  <head>
    <title>queryResulte</title>
    <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
    <script type="text/javascript">
    	function toGetForm(id) {
    		var url = "SeparepartBaseInfoAction.do?method=getOneSeparepart&id=" + id;
    		window.location.href=url;
    	}
    	
    	function toEditForm(id) {
    		var url = "SeparepartBaseInfoAction.do?method=ModOneSeparepart&id=" + id;
    		window.location.href=url;
    	}
    	
    	function toDelForm(id) {
    		if(!confirm("你确定要执行此次删除操作吗?")) {
    			return;
    		} else {
    			var url= "SeparepartBaseInfoAction.do?method=delSeparepart&id=" + id;
    			window.location.href = url;
    		}
    	}
    	
    	
    	function checkOrCancel() {
			var all = document.getElementById('sel');
			if(all.checked) {
				checkAll();
			} else {
				cancelCheck();
			}
		}

		function checkAll() {
			var allChecks = document.getElementsByName('ifCheck');
			var length = allChecks.length;
			for(var i = 0; i < length; i++) {
				allChecks[i].checked = true;
			}
		}
		
		function cancelCheck() {
			var allChecks = document.getElementsByName('ifCheck');
			var length = allChecks.length;
			for(var i = 0; i < length; i++) {
				allChecks[i].checked = false;
			}
		}
		
		function delSel() {
			var idStr = null;
			var delChecks = document.getElementsByName('ifCheck');
			var delLength = delChecks.length;
			var length = 0;
			for(var i = 0; i < delLength; i++) {
				if(delChecks[i].checked == true) {
					length++;
					if(idStr == null) {
						idStr = "'" + delChecks[i].value + "'";
					} else {
						idStr += ",'" + delChecks[i].value + "'";
					}
				}
			}
			if(length == 0) {
				alert("请选择你想删除的备件!");
				return;
			}
			if(!confirm("你确定要执行此次删除操作吗?")) {
    			return;
    		} else {
				var url= "SeparepartBaseInfoAction.do?method=delMore&idStr=" + idStr;
    			window.location.href = url;
    		}
		}
    </script>
  </head>
  
  <body>
      	<br />
      	<%
      		String spare_part_name = "";
      		BasicDynaBean  object = null;
      		String deptType = "";
      		String tid = "";
      		String state = "";
      	 %>
      	<template:titile value="备件信息一览表"/>
      	 <display:table name="sessionScope.resList" requestURI="${ctx}/SeparepartBaseInfoAction.do?method=showQueryResult" id="currentRowObject" pagesize="18">
      	 	<%
      	 		deptType = (String)request.getSession().getAttribute("deptType");
      	 		object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
      	 		if(object != null) {
	      	 		tid = (String) object.get("tid");
					state = (String) object.get("part_state");
					spare_part_name = (String) object.get("spare_part_name");
				}
      	 		if(deptType.equals("1")) { %>
      	 			<display:column title="选择" media="html">
		      	 		 <%if("0".equals(state)) {%>
						   		<input type="checkBox" name="ifCheck" value="<%=tid %>" >
						 <% } else {} %>
		      	 	</display:column>
      	 	<% 	}%>
      	 	
      		<display:column media="html" title="备件名称"  sortable="true">
      			<a href="javascript:toGetForm('<%=tid%>')"><%=spare_part_name %></a> 
      		</display:column>
            <display:column property="spare_part_model" title="备件型号"  maxLength="10"  sortable="true"/>
            <display:column property="software_version" title="软件版本"  maxLength="10" sortable="true"/>
            <display:column property="product_factory" title="生产厂家"  maxLength="10" sortable="true"/>
            <display:column media="html" title="操作" >
				   <%
	            	String depType = (String)request.getSession().getAttribute("deptType");
	            	if("1".equals(depType)) { %>
	            		<a href="javascript:toEditForm('<%=tid%>')">修改</a>
	            		<%if("0".equals(state)) {  %>
	            			| <a href="javascript:toDelForm('<%=tid%>')">删除</a>
	            		<%} %>
	             <%} %>
            </display:column>
      	 </display:table>
      	 <%if("1".equals(deptType)) { %>
	      	 <div style="margin-top: 5px; line-height: 22px; height: 22px;">
	      	 	<input type="checkbox" onclick="checkOrCancel()" id="sel">全选/全不选
	      	 	<input type="button" class="button" onclick="delSel()" value="删除选中"/>
	      	 </div>
	     <%} %>
  </body>
</html>
