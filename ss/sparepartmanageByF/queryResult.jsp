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
    		if(!confirm("��ȷ��Ҫִ�д˴�ɾ��������?")) {
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
				alert("��ѡ������ɾ���ı���!");
				return;
			}
			if(!confirm("��ȷ��Ҫִ�д˴�ɾ��������?")) {
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
      	<template:titile value="������Ϣһ����"/>
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
      	 			<display:column title="ѡ��" media="html">
		      	 		 <%if("0".equals(state)) {%>
						   		<input type="checkBox" name="ifCheck" value="<%=tid %>" >
						 <% } else {} %>
		      	 	</display:column>
      	 	<% 	}%>
      	 	
      		<display:column media="html" title="��������"  sortable="true">
      			<a href="javascript:toGetForm('<%=tid%>')"><%=spare_part_name %></a> 
      		</display:column>
            <display:column property="spare_part_model" title="�����ͺ�"  maxLength="10"  sortable="true"/>
            <display:column property="software_version" title="����汾"  maxLength="10" sortable="true"/>
            <display:column property="product_factory" title="��������"  maxLength="10" sortable="true"/>
            <display:column media="html" title="����" >
				   <%
	            	String depType = (String)request.getSession().getAttribute("deptType");
	            	if("1".equals(depType)) { %>
	            		<a href="javascript:toEditForm('<%=tid%>')">�޸�</a>
	            		<%if("0".equals(state)) {  %>
	            			| <a href="javascript:toDelForm('<%=tid%>')">ɾ��</a>
	            		<%} %>
	             <%} %>
            </display:column>
      	 </display:table>
      	 <%if("1".equals(deptType)) { %>
	      	 <div style="margin-top: 5px; line-height: 22px; height: 22px;">
	      	 	<input type="checkbox" onclick="checkOrCancel()" id="sel">ȫѡ/ȫ��ѡ
	      	 	<input type="button" class="button" onclick="delSel()" value="ɾ��ѡ��"/>
	      	 </div>
	     <%} %>
  </body>
</html>
