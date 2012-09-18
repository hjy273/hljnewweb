<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="" language="javascript">

    //查看详细按钮动作
 	function toGetOneAuForm(idValue){
     	var url = "${ctx}/LineCutReAction.do?method=getOneQueryDetail&reid=" + idValue;
        window.location.href=url;
    }
     //查看审批详细返回动作
 	function  AuShowGoBack(){
     	var url = "${ctx}/LineCutReAction.do?method=showAllAu";
        window.location.href=url;
    }
</script>


		<title>lineCutQueryList</title>
	</head>
	<body>
	
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<BR>
			 <%
				DynaBean object2=null;
				String id2= null;
				String numerical = null;
				String name = null;
				String isarchive = null;
				String auditresult = null;
			%>
			<template:titile value="割接查询列表" />
			<display:table name="sessionScope.lineCutQueryList" requestURI="${ctx}/LineCutReAction.do?method=queryCutBeanStat" id="currentRowObject"
				pagesize="18">
				<display:column media="html" title="流水号" sortable="true" >
    				<%
						object2 = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object2 != null) {
							id2 = (String) object2.get("reid");
							numerical = (String)object2.get("numerical");
							numerical = numerical == null? "": numerical;
							name = (String) object2.get("name");
							auditresult = (String)object2.get("auditresult");
							if(name != null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
							isarchive = (String) object2.get("isarchive");
						}
					 %>
					 <a href="javascript:toGetOneAuForm('<%=id2%>')"><%=numerical %></a>
    			</display:column>
				
				<display:column media="html"  title="割接名称" sortable="true" >
					  <a href="javascript:toGetOneAuForm('<%=id2%>')"><%=name %></a>
            	</display:column>
            	
				<display:column property="sublinename" title="中继段" maxLength="15"
					style="align:center" sortable="true" />
					
					<display:column property="address" title="割接地点" maxLength="10"
					sortable="true" />
					
				<display:column property="prousetime" title="割接计划用时(小时)" maxLength="15"
					style="align:center" sortable="true" />
				
					<display:column property="usetime" title="割接实际用时(小时)" maxLength="15"
					style="align:center" sortable="true" />
					
				<display:column property="protime" title="割接计划时间" maxLength="18"
					style="align:center" sortable="true" />
					
				<display:column property="essetime" title="割接实际时间" maxLength="18"
					style="align:center" sortable="true" />
				<%if(("通过审批".equals(auditresult) || "未通过审批".equals(auditresult)) && "已经审批".equals(isarchive)) 
				{%>
					<display:column property="auditresult" title="当前状态" style="align:center" sortable="true" />
				<% }else{%>
					<display:column property="isarchive" title="当前状态" style="align:center" sortable="true" />
				<% }%>
					
			</display:table>
			<html:link action="/LineCutReAction.do?method=exportQueryStat">导出为Excel文件</html:link>


		<iframe id="hiddenIframe" style="display: none"></iframe>
	</body>

</html>
