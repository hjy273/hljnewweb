<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<body style="width: 95%">
	<display:table name="sessionScope.nonpasssubline" id="currentRowObject"
		pagesize="8">
		<display:column property="patrolname" title="巡检组" sortable="true"></display:column>
		<display:column property="linename" title="巡检线路 " sortable="true"></display:column>
		<display:column property="sublinename" title="巡检线段" sortable="true"></display:column>
		<display:column property="linetype" title="线路级别" sortable="true"></display:column>
		<display:column property="executetimes" title="计划巡检次数" sortable="true"></display:column>
		<display:column property="planpoint" title="计划巡检点次" sortable="true"></display:column>
		<display:column property="factpoint" title="实际巡检点次" sortable="true" />
		<display:column property="patrolp" title="巡检率(%)" sortable="true"></display:column>
		<display:column media="html" title="考核结果">
			<%
			    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    String examineresult = object.get("examineresult").toString();
			    int i = Integer.parseInt(examineresult);
			    String path = (String) application.getAttribute("ctx");
			    switch (i) {
			    case 1:
			        out.print("<img src=\"" + path + "/images/1.jpg\"/>");
			        break;
			    case 2:
			        out.print("<img src=\"" + path + "/images/2.jpg\"/>");
			        break;
			    case 3:
			        out.print("<img src=\"" + path + "/images/3.jpg\"/>");
			        break;
			    case 4:
			        out.print("<img src=\"" + path + "/images/4.jpg\"/>");
			        break;
			    case 5:
			        out.print("<img src=\"" + path + "/images/5.jpg\"/>");
			        break;
			    default:
			        out.print("<img src=\"" + path + "/images/0.jpg\"/>");
			    }
			%>
		</display:column>
	</display:table>
</body>
