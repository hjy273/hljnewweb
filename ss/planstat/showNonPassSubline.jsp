<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<body style="width: 95%">
	<display:table name="sessionScope.nonpasssubline" id="currentRowObject"
		pagesize="8">
		<display:column property="patrolname" title="Ѳ����" sortable="true"></display:column>
		<display:column property="linename" title="Ѳ����· " sortable="true"></display:column>
		<display:column property="sublinename" title="Ѳ���߶�" sortable="true"></display:column>
		<display:column property="linetype" title="��·����" sortable="true"></display:column>
		<display:column property="executetimes" title="�ƻ�Ѳ�����" sortable="true"></display:column>
		<display:column property="planpoint" title="�ƻ�Ѳ����" sortable="true"></display:column>
		<display:column property="factpoint" title="ʵ��Ѳ����" sortable="true" />
		<display:column property="patrolp" title="Ѳ����(%)" sortable="true"></display:column>
		<display:column media="html" title="���˽��">
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
