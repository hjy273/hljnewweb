<%@include file="/common/header.jsp"%>
<html>
<head>
<title>
����ִ���еļƻ���Ϣ
</title>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script type="" language="javascript">
	// �鿴�ƻ�����Ѳ���߶���Ϣ
	function toSublinePatrol(planid) {
 		var url ="${ctx}/StatisticsCurrentPlanAction.do?method=getSublinePatrolInfo&planid=" + planid;
    	window.location.href=url;
 	}

	// �鿴�ƻ���©���߶���Ϣ
	function toSublineLeak(planid) {
 		var url ="${ctx}/StatisticsCurrentPlanAction.do?method=getSublineLeakInfo&planid=" + planid;
    	window.location.href=url;
 	}

	// �鿴�߶ε���Ѳ���Ѳ�����Ϣ
	function toPointPatrol(sublineid) {
		var planid = document.getElementById("upPagePlanId").value;
 		var url ="${ctx}/StatisticsCurrentPlanAction.do?method=getPointPatrolInfo&sublineid=" + sublineid + "&planid=" + planid;
    	window.location.href=url;
 	}

	// �鿴�߶ε�©���Ѳ�����Ϣ
	function toPointLeak(subline) {
		var planid = document.getElementById("upPagePlanId").value;
 		var url ="${ctx}/StatisticsCurrentPlanAction.do?method=getPointLeakInfo&sublineid=" + subline + "&planid=" + planid;;
    	window.location.href=url;
 	}

	// ҳ���ϵ�"����"��ť
	function oneInfoGoBack(type) {
		//history.back();

		var url;
		if(type == '11') {
			// �Ӽƻ�����Ѳ����߶���Ϣ���ص��鿴����ִ�еļƻ���Ϣҳ��
			url = "${ctx}/StatisticsCurrentPlanAction.do?method=getCurrentPlanInfo";
		} else if(type == '12') {
			// �Ӽƻ�����Ѳ���Ѳ�����Ϣ���ص��ƻ�����Ѳ����߶���Ϣҳ��
			var id = document.getElementById("curPlanId").value;
			url ="${ctx}/StatisticsCurrentPlanAction.do?method=getSublinePatrolInfo&planid=" + id;
		} else if(type == '21') {
			// �Ӽƻ���©����߶���Ϣ���ص��鿴����ִ�еļƻ���Ϣҳ��
			url = "${ctx}/StatisticsCurrentPlanAction.do?method=getCurrentPlanInfo";
		} else if(type == '22') {
			// �Ӽƻ���©���Ѳ�����Ϣ���ص��ƻ���©����߶���Ϣҳ��
			var id = document.getElementById("curPlanId").value;
			url ="${ctx}/StatisticsCurrentPlanAction.do?method=getSublineLeakInfo&planid=" + id;
		} 	
    	window.location.href=url;
	}
</script>
</head>
<body>
<logic:equal value="1" name="type" scope="session">
 <!--��ʾ����ִ�еļƻ���Ϣ-->
 <template:titile value="����ִ���еļƻ���Ϣ"/>
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
        	<display:column property="planname" title="�ƻ�����"  sortable="true" maxLength="18"  style="width:220px"/>  
        	<display:column property="begindate" title="��ʼʱ��"  sortable="true" maxLength="10"  style="width:80px"/>  
        	<display:column property="enddate" title="����ʱ��"  sortable="true" maxLength="10"  style="width:80px"/>  
        	<display:column property="patrolname" title="Ѳ��ά����"  sortable="true" maxLength="10"  style="width:120px"/>  
        	<display:column property="plantimes" title="�ƻ�Ѳ����"  sortable="true" maxLength="6"  style="width:90px"/>  
			<display:column media="html" title="ʵ��Ѳ����" sortable="true" style="width:90px" >		
				<%if(!"0".equals(patrolTimes)) { %>	
					<a href="javascript:toSublinePatrol('<%=planId%>')" ><%=patrolTimes%></a>
				<% } else { %> 
					<%=patrolTimes %>
				<% } %>
			</display:column>
			<display:column media="html" title="©����" sortable="true" style="width:80px">		
				<%if(!"0".equals(leaktimes)) { %>		
					<a href="javascript:toSublineLeak('<%=planId%>')" ><%=leaktimes%></a>
				<% } else { %> 
					<%=leaktimes %>
				<% } %>
			</display:column>        	
		</display:table>
		<html:link action="/StatisticsCurrentPlanAction.do?method=exportPlanCurrentResult">����ΪExcel�ļ�</html:link>
</logic:equal>

<logic:equal value="2" name="type" scope="session">
 <!--��Ѳ����߶���Ϣ-->
 <template:titile value="��Ѳ����߶���Ϣ"/>
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
        	<display:column property="sublinename" title="�߶���"  sortable="true" maxLength="22"  style="width:260px"/>  
        	<display:column property="linename" title="������·"  sortable="true" maxLength="10"  style="width:120px"/> 
        	<display:column property="codename" title="�������"  sortable="true" maxLength="10"  style="width:120px"/>  
			<display:column media="html" title="��Ѳ����" sortable="true" style="width:120px" >		
				<%if(!"0".equals(patroltimes)) { %>	
					<a href="javascript:toPointPatrol('<%=sublineid%>')" ><%=patroltimes%></a>
				<% } else { %> 
					<%=patroltimes %>
				<% } %>
			</display:column>			
		</display:table>
		<html:link action="/StatisticsCurrentPlanAction.do?method=exportPlanPatrolSublineResult">����ΪExcel�ļ�</html:link>
		 <p align="center">
		 <input type="buuton" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
		</p>
</logic:equal>

<logic:equal value="3" name="type" scope="session">
 <!--��Ѳ���Ѳ�����Ϣ-->
 <template:titile value="��Ѳ���Ѳ�����Ϣ"/>
		 <input id="curPlanId" type="hidden" value='<bean:write name="planid"/>'>
        <display:table name="sessionScope.pointinfo" id="currentRowObject" pagesize="20">
        	<display:column property="pointname" title="Ѳ�������"  sortable="true" maxLength="15"  style="width:180px"/>  
        	<display:column property="simid" title="SIM��"  sortable="true" maxLength="13"  style="width:120px"/>   
        	<display:column property="patroldate" title="Ѳ��ʱ��"  sortable="true" maxLength="20"  style="width:120px"/>   
		</display:table>
		<html:link action="/StatisticsCurrentPlanAction.do?method=exportPlanPatrolPointResult">����ΪExcel�ļ�</html:link>
		<p align="center"><input type="buuton" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
</logic:equal>

<logic:equal value="4" name="type" scope="session">
 <!--©����߶���Ϣ-->
 <template:titile value="©����߶���Ϣ"/>
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
        	<display:column property="sublinename" title="�߶���"  sortable="true" maxLength="22"  style="width:260px"/>  
        	<display:column property="linename" title="������·"  sortable="true" maxLength="10"  style="width:120px"/> 
        	<display:column property="codename" title="�������"  sortable="true" maxLength="10"  style="width:120px"/>   
			<display:column media="html" title="©����" sortable="true" style="width:120px" >		
				<%if(!"0".equals(leaktimes)) { %>	
					<a href="javascript:toPointLeak('<%=sublineid%>')" ><%=leaktimes%></a>
				<% } else { %> 
					<%=leaktimes %>
				<% } %>
			</display:column>			
		</display:table>
		<html:link action="/StatisticsCurrentPlanAction.do?method=exportPlanLeakSublineResult">����ΪExcel�ļ�</html:link>
		<p align="center"><input type="buuton" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
</logic:equal>

<logic:equal value="5" name="type" scope="session">
 <!--©���Ѳ�����Ϣ-->
 <template:titile value="©���Ѳ�����Ϣ"/>
 		 <input id="curPlanId" type="hidden" value='<bean:write name="planid"/>'>
        <display:table name="sessionScope.pointinfo" id="currentRowObject" pagesize="20">
        	<display:column property="pointname" title="Ѳ�������"  sortable="true" maxLength="15"  style="width:180px"/>  
        	<display:column property="plantimes" title="�ƻ�Ѳ����"  sortable="true" maxLength="13"  style="width:120px"/>   
        	<display:column property="patroltimes" title="��Ѳ����"  sortable="true" maxLength="13"  style="width:120px"/>   
        	<display:column property="leaktimes" title="©����"  sortable="true" maxLength="10"  style="width:120px"/>   
		</display:table>
		<html:link action="/StatisticsCurrentPlanAction.do?method=exportPlanLeakPointResult">����ΪExcel�ļ�</html:link>
		<p align="center"><input type="buuton" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
</logic:equal>
</body>
</html>
