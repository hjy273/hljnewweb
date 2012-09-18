<%@ include file="/common/header.jsp"%>
<%

	String pid = request.getParameter("sPID");
	//�����Ľ�����
	String tpid = "";
	if(request.getParameter("tPID") != null){
		tpid = request.getParameter("tPID");
	}

	String type = request.getParameter("sType");
	String funid = request.getParameter("sFunID");

	String method = "";
	String url = "";

	if(type.equals("TEMPPOINTS")){

		url = "${ctx}/GisPointAction.do";
		method = "getTempPointInfo";

	}else if(type.equals("PEOPLE")){

		url = "${ctx}/GisPmAction.do";
		method = "getPatrolmanInfo";

	}else if(type.equals("POINTS")){ //�޸�Ѳ�����Ϣ

      url = "/WEBGIS/editPointForGisWatch.jsp";

	}else if(type.equals("SUBLINES")){

		url = "${ctx}/sublineAction.do";
		method = "loadSubline4Gis";

	}else if(type.equals("accidentsubline")){//�����趨

		url = "${ctx}/watchAction.do";
		method = "toAddWatch4Gis";

	}else if(type.equals("ACCIDENTSUBLINES")){//������Ϣ

		url = "${ctx}/watchAction.do";
		method = "loadWatch4GIS";

	}else if(type.equals("CROSSPOINTS")){//���±�ʾ��

		url = "${ctx}/GISCrossPointAction.do";
		method = "loadGISCrossPoint";

	}else if(type.equals("ADDCROSSPOINTS")){//���ӱ�ʾ��

		url = "${ctx}/realtime/addGISCrossPoint.jsp";

	}else if(type.equals("sendsms")){//���Ͷ���

		url = "${ctx}/baseinfo/getSelectForGISPatrol.jsp";

	}else if(type.equals("link2subline")){//���������߶�

		url = "/WEBGIS/editPointForConn.jsp";

	}else if(type.equals("moveToSubline")){//�ƶ�Ѳ���

		url = "${ctx}/realtime/moveToAnotherSubline.jsp";

	}else if(type.equals("TEMPWATCHPOINTS")){//��ʱ���� ��������

		url = "${ctx}/watchAction.do";
		method = "getTempWatchInfo";

	}else if(type.equals("ACCIDENTPOINT")){//�¹ʵ���Ϣ��ͳ��

		url = "${ctx}/GisAccidentAction.do";
		method = "getAccidentInfo";

	}else if(type.equals("HISTORYROUTE")){//Ѳ��ִ����Ϣ

		url = "${ctx}/PlanExeAction.do";
		method = "getHistoryRouteInfo";

	}else {

		url = "${ctx}/baseinfo/commonError.jsp";

	}

%>
<form id="gotoAction" name="gotoAction" action="<%=url%>" method="POST" style="display:none">
    <input type="text"  name="sPID" value="<%=pid%>" />
	<input type="text"  name="tPID" value="<%=tpid%>" />
    <input type="text"  name="sType" value="<%=type%>" />
    <input type="text"  name="sFunID" value="<%=funid%>" />
    <input type="text"  name="method" value="<%=method%>" />
</form>
<script language="javascript">

<%if(type.equals("TEMPWATCHPOINTS") || type.equals("ACCIDENTSUBLINES") || type.equals("accidentsubline")){%>

	var watchPop = window.open("",'watchPop','width=436,height=600,scrollbars=yes');
	watchPop.focus();
	gotoAction.target = "watchPop";
<%}%>
	gotoAction.submit();
</script>
