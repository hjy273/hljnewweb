<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<display:table name="sessionScope.sublineUnfinished"
		id="currentRowObject" pagesize="10">
		<display:column property="sublinename" title="Ѳ���߶�����" sortable="true" />
		<display:column property="linetype" title="������·����" sortable="true" />
		<display:column property="planpointtimes" title="Ӧ��ִ�е��"
			sortable="true" />
		<display:column property="factpointtimes" title="�Ѿ�ִ�е��"
			sortable="true" />
		<display:column property="percentage" title="Ѳ����(%)" sortable="true" />
		<display:column media="html" title="���˽��" sortable="true">
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