<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<html>
	<head>
		<title>eXtremeTest</title>
		<script language="javascript" type="text/javascript">
    function showpanel(){
         var strshowpanelvar = <%=session.getAttribute("isshowpanel")%>
         if (strshowpanelvar == 0){
           parent.document.getElementById("paneltable").style.display="none";
	      
         }else{
	       parent.document.getElementById("paneltable").style.display="";
         }  
       }
</script>
	</head>
	<body style="margin: 10px; background-color: EAE9E4; width: 95%"
		onload="showpanel()">
		<display:table name="sessionScope.progressinfo" id="progress"
			requestURI="${pageContext.request.contextPath}/planstat/showplanprogresstext.jsp"
			pagesize="10">
			<display:column property="planname" title="�ƻ�����" />
			<display:column property="begindate" title="��ʼ����" />
			<display:column property="enddate" title="��������" />
			<display:column media="html" title="���˽��">
				<%
				    Map list = (HashMap) pageContext.findAttribute("progress");
				    String examineresult = (String) list.get("examineresult");
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
			<display:column property="planpointtimes" title="�ƻ�Ѳ����" />
			<display:column property="actualpointtimes" title="ʵ��Ѳ����" />
			<display:column property="percentage" title="Ѳ����(%)" />
			<logic:equal value="group" name="PMType">
				<display:column property="patrolname" title="Ѳ��ά����" />
			</logic:equal>
			<logic:notEqual value="group" name="PMType">
				<display:column property="patrolname" title="Ѳ��Ա" />
			</logic:notEqual>
			<display:column media="html" title="����">
				<a
					href="${ctx}/PlanProgressAction.do?method=showSublineDetailInfo&planid=${progress.planid}"
					target="_parent"><font size=2>�鿴</font> </a>
			</display:column>
		</display:table>
		<html:link
			action="/planExportAction.do?method=exportPlanprogresstextResult">����ΪExcel�ļ�</html:link>
	</body>
</html>


<!--
     function seemore(){
       var url = "${ctx}/PlanProgressAction.do?method=showSublineDetailInfo&planid=00000000006320071560"
       var taskPop = window.open(url,'_parent','width=436,height=500,scrollbars=yes');
       taskPop.focus();
     }
-->