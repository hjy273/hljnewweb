<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<%@page import="com.cabletech.planstat.util.MyWrapper"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<%
    String theyear = (String) request.getSession().getAttribute("endYearStat");
    ;
    String themonth = (String) request.getSession().getAttribute("endMonthStat");
    String patrolname = (String) request.getSession().getAttribute("patrolname");
%>
<body style="width: 95%">
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'><%=patrolname%><%=theyear%>年<%=themonth%>月任务执行情况
	</div>
	<hr width='100%' size='1'>
	<display:table name="sessionScope.mWorkDaysInfoList"
		id="currentRowObject" pagesize="18"
		decorator="com.cabletech.planstat.util.MyWrapper">
		<display:column property="operatedate" title="日期" sortable="true">
			<%
			    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    String stroperatedate = "";
			    if (object != null) {
			        stroperatedate = (String) object.get("operatedate");
			        request.getSession().setAttribute("stroperatedate", stroperatedate);
			    }
			%>
		</display:column>
		<display:column property="simid" title="SIM卡号" sortable="true" />
		<display:column property="totalnum" title="总数" sortable="true" />
		<display:column property="patrolnum" title="巡检" sortable="true" />
		<display:column property="watchnum" title="盯防" sortable="true" />
		<display:column property="collectnum" title="采集" sortable="true" />
		<display:column property="troublenum" title="隐患" sortable="true" />
		<display:column property="othernum" title="其它" sortable="true" />
	</display:table>
</body>
