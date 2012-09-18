<%@include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<%@page import="com.cabletech.commons.config.*"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<%
    String strplanid = (String) request.getSession().getAttribute("theplanid");
    String patroldate = "";
    String patroldateinitial = "";
    int day = 1;
    GisConInfo gisip = GisConInfo.newInstance();
    System.out.println("IP :" + gisip.getServerip());
%>
<script language="javascript">
function toLookText(dateValue,idValue){
        var url = "${ctx}/PlanExeResultAction.do?method=showWorkdayText&patroldate=" + dateValue + "&planid=" + idValue;
        self.location.replace(url);
}
function toLookGIS(dateValue,idValue){
        var URL = "Http://<%=gisip.getServerip()%>:<%=gisip.getServerport()%>/WEBGIS/gisextend/igis.jsp?actiontype=005&planid=" + idValue + "&executedate=" + dateValue + "&token=12234";
        myleft=(screen.availWidth-650)/2;
		mytop=100
		mywidth=650;
		myheight=500;
		window.open(URL,"","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
}

</script>
<body style="width: 95%">
	<template:titile value="�鿴�ƻ�������ÿ��������������" />
	<display:table name="sessionScope.workdayList" id="currentRowObject"
		pagesize="18">
		<display:column media="html" title="���������">
  		 ��<%=day++%>��
  		</display:column>
		<display:column property="patroldate" title="Ѳ������" sortable="true" />
		<display:column property="pointnum" title="Ѳ�����" sortable="true" />
		<apptag:checkpower thirdmould="21401" ishead="0">
			<display:column media="html" title="���ֻط�">
				<%
				    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    if (object != null)
				        patroldateinitial = (String) object.get("patroldate");
				    patroldate = patroldateinitial.replaceAll("-", "");
				%>
				<a
					href="javascript:toLookText('<%=patroldateinitial%>','<%=strplanid%>')">�鿴</a>
			</display:column>
		</apptag:checkpower>
		<apptag:checkpower thirdmould="21401" ishead="0">
			<display:column media="html" title="��ͼ�ط�">
				<a href="javascript:toLookGIS('<%=patroldate%>','<%=strplanid%>')">�鿴</a>
			</display:column>
		</apptag:checkpower>
	</display:table>
</body>