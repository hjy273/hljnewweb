<%@include file="/common/header.jsp"%>
<html>
<head>
<title>
正在执行中的计划信息
</title>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script type="" language="javascript">
	// 查看计划的已巡检线段信息
	function toSublinePatrol(planid) {
 		var url ="${ctx}/StatisticsCurrentPlanAction.do?method=getSublinePatrolInfo&planid=" + planid;
    	window.location.href=url;
 	}

	// 查看计划的漏检线段信息
	function toSublineLeak(planid) {
 		var url ="${ctx}/StatisticsCurrentPlanAction.do?method=getSublineLeakInfo&planid=" + planid;
    	window.location.href=url;
 	}

	// 查看线段的已巡检的巡检点信息
	function toPointPatrol(sublineid) {
		var planid = document.getElementById("upPagePlanId").value;
 		var url ="${ctx}/StatisticsCurrentPlanAction.do?method=getPointPatrolInfo&sublineid=" + sublineid + "&planid=" + planid;
    	window.location.href=url;
 	}

	// 查看线段的漏检的巡检点信息
	function toPointLeak(subline) {
		var planid = document.getElementById("upPagePlanId").value;
 		var url ="${ctx}/StatisticsCurrentPlanAction.do?method=getPointLeakInfo&sublineid=" + subline + "&planid=" + planid;;
    	window.location.href=url;
 	}

	// 页面上的"返回"按钮
	function oneInfoGoBack(type) {
		//history.back();

		var url;
		if(type == '11') {
			// 从计划的已巡检的线段信息返回到查看正在执行的计划信息页面
			url = "${ctx}/StatisticsCurrentPlanAction.do?method=getCurrentPlanInfo";
		} else if(type == '12') {
			// 从计划的已巡检的巡检点信息返回到计划的已巡检的线段信息页面
			var id = document.getElementById("curPlanId").value;
			url ="${ctx}/StatisticsCurrentPlanAction.do?method=getSublinePatrolInfo&planid=" + id;
		} else if(type == '21') {
			// 从计划的漏检的线段信息返回到查看正在执行的计划信息页面
			url = "${ctx}/StatisticsCurrentPlanAction.do?method=getCurrentPlanInfo";
		} else if(type == '22') {
			// 从计划的漏检的巡检点信息返回到计划的漏检的线段信息页面
			var id = document.getElementById("curPlanId").value;
			url ="${ctx}/StatisticsCurrentPlanAction.do?method=getSublineLeakInfo&planid=" + id;
		} 	
    	window.location.href=url;
	}
</script>
</head>
<body>
<logic:equal value="1" name="type" scope="session">
 <!--显示正在执行的计划信息-->
 <template:titile value="正在执行中的计划信息"/>
        <display:table name="sessionScope.planinfo" requestURI="${ctx}/StatisticsCurrentPlanAction.do?method=getCurrentPlanInfo" id="currentRowObject" pagesize="20">
        	<%
		    	BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
		    	String planId = "";
		    	String patrolTimes = "";
		    	String leaktimes = "";
		    	if(object != null) {
		    		planId = object.get("id").toString();
		    		patrolTimes = object.get("patroltimes").toString();
		    		leaktimes = object.get("leaktimes").toString();
		    	}
			%>
        	<display:column property="planname" title="计划名称"  sortable="true" maxLength="18"  style="width:220px"/>  
        	<display:column property="begindate" title="开始时间"  sortable="true" maxLength="10"  style="width:80px"/>  
        	<display:column property="enddate" title="结束时间"  sortable="true" maxLength="10"  style="width:80px"/>  
        	<display:column property="patrolname" title="巡检维护组"  sortable="true" maxLength="10"  style="width:120px"/>  
        	<display:column property="plantimes" title="计划巡检点次"  sortable="true" maxLength="6"  style="width:90px"/>  
			<display:column media="html" title="实际巡检点次" sortable="true" style="width:90px" >		
				<%if(!"0".equals(patrolTimes)) { %>	
					<a href="javascript:toSublinePatrol('<%=planId%>')" ><%=patrolTimes%></a>
				<% } else { %> 
					<%=patrolTimes %>
				<% } %>
			</display:column>
			<display:column media="html" title="漏检点次" sortable="true" style="width:80px">		
				<%if(!"0".equals(leaktimes)) { %>		
					<a href="javascript:toSublineLeak('<%=planId%>')" ><%=leaktimes%></a>
				<% } else { %> 
					<%=leaktimes %>
				<% } %>
			</display:column>        	
		</display:table>
		<html:link action="/StatisticsCurrentPlanAction.do?method=exportPlanCurrentResult">导出为Excel文件</html:link>
</logic:equal>

<logic:equal value="2" name="type" scope="session">
 <!--已巡检的线段信息-->
 <template:titile value="已巡检的线段信息"/>
        <input id="upPagePlanId" type="hidden" value='<bean:write name="planid"/>'>
        <display:table name="sessionScope.sublineinfo" id="currentRowObject" pagesize="20">
        	<%
		    	BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");		    	
		    	String sublineid = "";
		    	String patroltimes = "";
		    	if(object != null) {
		    		sublineid = object.get("sublineid").toString();
		    		patroltimes = object.get("patroltimes").toString();
		    	}
			%>
        	<display:column property="sublinename" title="线段名"  sortable="true" maxLength="22"  style="width:260px"/>  
        	<display:column property="linename" title="所属线路"  sortable="true" maxLength="10"  style="width:120px"/> 
        	<display:column property="codename" title="所属层次"  sortable="true" maxLength="10"  style="width:120px"/>  
			<display:column media="html" title="已巡检点次" sortable="true" style="width:120px" >		
				<%if(!"0".equals(patroltimes)) { %>	
					<a href="javascript:toPointPatrol('<%=sublineid%>')" ><%=patroltimes%></a>
				<% } else { %> 
					<%=patroltimes %>
				<% } %>
			</display:column>			
		</display:table>
		<html:link action="/StatisticsCurrentPlanAction.do?method=exportPlanPatrolSublineResult">导出为Excel文件</html:link>
		 <p align="center">
		 <input type="buuton" value="返回" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
		</p>
</logic:equal>

<logic:equal value="3" name="type" scope="session">
 <!--已巡检的巡检点信息-->
 <template:titile value="已巡检的巡检点信息"/>
		 <input id="curPlanId" type="hidden" value='<bean:write name="planid"/>'>
        <display:table name="sessionScope.pointinfo" id="currentRowObject" pagesize="20">
        	<display:column property="pointname" title="巡检点名称"  sortable="true" maxLength="15"  style="width:180px"/>  
        	<display:column property="simid" title="SIM卡"  sortable="true" maxLength="13"  style="width:120px"/>   
        	<display:column property="patroldate" title="巡检时间"  sortable="true" maxLength="20"  style="width:120px"/>   
		</display:table>
		<html:link action="/StatisticsCurrentPlanAction.do?method=exportPlanPatrolPointResult">导出为Excel文件</html:link>
		<p align="center"><input type="buuton" value="返回" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
</logic:equal>

<logic:equal value="4" name="type" scope="session">
 <!--漏检的线段信息-->
 <template:titile value="漏检的线段信息"/>
 		 <input id="upPagePlanId" type="hidden" value='<bean:write name="planid"/>'>
        <display:table name="sessionScope.sublineList" id="currentRowObject" pagesize="20">
        	<%
		    	BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");		    	
		    	String sublineid = "";
		    	String leaktimes = "";
		    	if(object != null) {
		    		sublineid = object.get("sublineid").toString();
		    		leaktimes = object.get("leaktimes").toString();
		    	}
			%>
        	<display:column property="sublinename" title="线段名"  sortable="true" maxLength="22"  style="width:260px"/>  
        	<display:column property="linename" title="所属线路"  sortable="true" maxLength="10"  style="width:120px"/> 
        	<display:column property="codename" title="所属层次"  sortable="true" maxLength="10"  style="width:120px"/>   
			<display:column media="html" title="漏检点次" sortable="true" style="width:120px" >		
				<%if(!"0".equals(leaktimes)) { %>	
					<a href="javascript:toPointLeak('<%=sublineid%>')" ><%=leaktimes%></a>
				<% } else { %> 
					<%=leaktimes %>
				<% } %>
			</display:column>			
		</display:table>
		<html:link action="/StatisticsCurrentPlanAction.do?method=exportPlanLeakSublineResult">导出为Excel文件</html:link>
		<p align="center"><input type="buuton" value="返回" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
</logic:equal>

<logic:equal value="5" name="type" scope="session">
 <!--漏检的巡检点信息-->
 <template:titile value="漏检的巡检点信息"/>
 		 <input id="curPlanId" type="hidden" value='<bean:write name="planid"/>'>
        <display:table name="sessionScope.pointinfo" id="currentRowObject" pagesize="20">
        	<display:column property="pointname" title="巡检点名称"  sortable="true" maxLength="15"  style="width:180px"/>  
        	<display:column property="plantimes" title="计划巡检点次"  sortable="true" maxLength="13"  style="width:120px"/>   
        	<display:column property="patroltimes" title="已巡检点次"  sortable="true" maxLength="13"  style="width:120px"/>   
        	<display:column property="leaktimes" title="漏检点次"  sortable="true" maxLength="10"  style="width:120px"/>   
		</display:table>
		<html:link action="/StatisticsCurrentPlanAction.do?method=exportPlanLeakPointResult">导出为Excel文件</html:link>
		<p align="center"><input type="buuton" value="返回" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
</logic:equal>
</body>
</html>
