<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.commons.config.*"%>
<%
	String apppath = SmsConInfo.getInstance().getWholePath();
  String cachePath = apppath + SmsConInfo.getInstance().getSmsCachePath();

  System.out.println(cachePath);
%>

<%
String commandStr = "";
if(request.getParameter("commandStr") != null){
	commandStr = request.getParameter("commandStr");
}

%>

<script language="javascript">
<!--
function reDirectURL(){
	
	//刷新SMS缓存
	top.bottomFrame.freshSmsCache();

	var cStr = commandInput.value;
	var newUrl = "${ctx}/baseinfo/queryTerminal.jsp";

	if(cStr == "1"){ //修改 巡检设备
		newUrl = "${ctx}/baseinfo/queryTerminal.jsp";
	}else if(cStr == "2"){ // 增加 巡检设备
		newUrl = "${ctx}/baseinfo/addTerminal.jsp";
	}else if(cStr == "3"){ //修改 巡检员
		newUrl = "${ctx}/baseinfo/queryPatrolMan.jsp";
	}else if(cStr == "4"){ // 增加 巡检员
		newUrl = "${ctx}/baseinfo/addPatrolMan.jsp";
	}else if(cStr == "5"){ // 增加 巡检故障
		newUrl = "${ctx}/baseinfo/addPatrolOp.jsp";
	}else if(cStr == "6"){ // 修改 巡检故障
		newUrl = "${ctx}/baseinfo/queryPatrolOp.jsp";
	}

	self.location.replace(newUrl);
}
//-->
</script>

<body onload="reDirectURL()">
<input type="hidden" name="commandInput" value="<%=commandStr%>">
</body>