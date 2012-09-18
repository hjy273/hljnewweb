<%@ include file="/common/header.jsp"%>
<%

	String pid = request.getParameter("sPID");
	//盯防的结束点
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

	}else if(type.equals("POINTS")){ //修改巡检点信息

      url = "/WEBGIS/editPointForGisWatch.jsp";

	}else if(type.equals("SUBLINES")){

		url = "${ctx}/sublineAction.do";
		method = "loadSubline4Gis";

	}else if(type.equals("accidentsubline")){//盯防设定

		url = "${ctx}/watchAction.do";
		method = "toAddWatch4Gis";

	}else if(type.equals("ACCIDENTSUBLINES")){//盯防信息

		url = "${ctx}/watchAction.do";
		method = "loadWatch4GIS";

	}else if(type.equals("CROSSPOINTS")){//更新标示点

		url = "${ctx}/GISCrossPointAction.do";
		method = "loadGISCrossPoint";

	}else if(type.equals("ADDCROSSPOINTS")){//增加标示点

		url = "${ctx}/realtime/addGISCrossPoint.jsp";

	}else if(type.equals("sendsms")){//发送短信

		url = "${ctx}/baseinfo/getSelectForGISPatrol.jsp";

	}else if(type.equals("link2subline")){//连接两个线段

		url = "/WEBGIS/editPointForConn.jsp";

	}else if(type.equals("moveToSubline")){//移动巡检点

		url = "${ctx}/realtime/moveToAnotherSubline.jsp";

	}else if(type.equals("TEMPWATCHPOINTS")){//临时盯防 〉〉盯防

		url = "${ctx}/watchAction.do";
		method = "getTempWatchInfo";

	}else if(type.equals("ACCIDENTPOINT")){//事故点信息及统计

		url = "${ctx}/GisAccidentAction.do";
		method = "getAccidentInfo";

	}else if(type.equals("HISTORYROUTE")){//巡检执行信息

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
