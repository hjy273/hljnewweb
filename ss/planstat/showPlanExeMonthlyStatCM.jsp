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
		<%=bean.getRegionName()%><%=bean.getEndYear()%>��<%=bean.getEndMonth()%>�����мƻ�ִ�����
	</div>
	<hr width='100%' size='1'>
	<display:table name="sessionScope.cmmonthlystatplanexe"
		id="currentRowObject" pagesize="18">
		<display:column property="planname" title="�ƻ�����" />
		<display:column property="startdate" title="��ʼ����" />
		<display:column property="enddate" title="��������" />
		<display:column property="patrolp" title="Ѳ����(%)" />
		<display:column media="html" title="���˽��">
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
