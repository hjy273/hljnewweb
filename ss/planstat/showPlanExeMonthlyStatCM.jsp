<%@ include file="/common/header.jsp"%>
<%@ page
	import="com.cabletech.planstat.beans.MonthlyStatCityMobileConBean"%>
<%@ page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<%
	    MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) session
	            .getAttribute("CMMonthlyStatConBean");
	%>
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		<%=bean.getRegionName()%><%=bean.getEndYear()%>年<%=bean.getEndMonth()%>月所有计划执行情况
	</div>
	<hr width='100%' size='1'>
	<display:table name="sessionScope.cmmonthlystatplanexe"
		id="currentRowObject" pagesize="18">
		<display:column property="planname" title="计划名称" />
		<display:column property="startdate" title="开始日期" />
		<display:column property="enddate" title="结束日期" />
		<display:column property="patrolp" title="巡检率(%)" />
		<display:column media="html" title="考核结果">
			<%
			    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    String examineresult = object.get("examineresult").toString();
			    int i = Integer.parseInt(examineresult);
			    switch (i) {
			    case 1:
			        out.print("<img src=\"" + request.getContextPath()
			                + "/images/1.jpg\" height=\"10\" width=\"50\" />");
			        break;
			    case 2:
			        out.print("<img src=\"" + request.getContextPath()
			                + "/images/2.jpg\" height=\"10\" width=\"50\" />");
			        break;
			    case 3:
			        out.print("<img src=\"" + request.getContextPath()
			                + "/images/3.jpg\" height=\"10\" width=\"50\" />");
			        break;
			    case 4:
			        out.print("<img src=\"" + request.getContextPath()
			                + "/images/4.jpg\" height=\"10\" width=\"50\" />");
			        break;
			    case 5:
			        out.print("<img src=\"" + request.getContextPath()
			                + "/images/5.jpg\" height=\"10\" width=\"50\" />");
			        break;
			    default:
			        out.print("<img src=\"" + request.getContextPath()
			                + "/images/0.jpg\" height=\"10\" width=\"50\" />");
			    }
			%>
		</display:column>
	</display:table>
</body>
