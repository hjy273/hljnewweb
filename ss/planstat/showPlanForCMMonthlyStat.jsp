<%@ include file="/common/header.jsp"%>
<%@ page
	import="com.cabletech.planstat.beans.MonthlyStatCityMobileConBean"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<%
	    MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) session
	            .getAttribute("CMMonthlyStatConBean");
	%>
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		<%=bean.getRegionName()%>移动公司<%=bean.getEndYear()%>年<%=bean.getEndMonth()%>月计划信息
	</div>
	<hr width='100%' size='1'>
	<%
	    Map dictMap = (Map) session.getAttribute("lineTypeDictMap");
	%>
	<display:table name="sessionScope.planforcmmonthlystatinfo"
		id="currentRowObject" pagesize="18">
		<display:column property="conname" title="代维公司" />
		<%
		    Iterator itDict = dictMap.keySet().iterator();
		    while (itDict.hasNext()) {
		        String dictKey = itDict.next().toString();
		        String value = (String) dictMap.get(dictKey);
		%>
		<display:column property="<%=dictKey%>" title="<%=value%>" />
		<%
		    }
		%>
		<display:column property="total" title="总次数" />
	</display:table>
</body>
