<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.statistics.domainobjects.*"%>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--
var enlargeFlag = 0;

function makeSize(){
	if(enlargeFlag == 0){
		parent.makeSize(0);
		enlargeIcon.src = "${ctx}/images/icon_arrow_down.gif";
		enlargeIcon.alt = "显示查询条件";
		enlargeFlag = 1;
	}else{
		parent.makeSize(1);
		enlargeIcon.src = "${ctx}/images/icon_arrow_up.gif";
		enlargeIcon.alt = "隐藏查询条件";
		enlargeFlag = 0;
	}
}

function toCloseThis(){
	try{
		parent.makeSize(1);
	}catch(e){}

	self.location.replace("${ctx}/common/blank.html");
}

function toExpotr(){
	self.location.replace("${ctx}/StatisticAction.do?method=ExportPlanFormWithSession");
}

//-->
</script>
<%
  Hashtable resultHt = (Hashtable) request.getAttribute("QueryResult");
  String ifhasrecord = (String)resultHt.get("ifhasrecord");

  if (ifhasrecord.equals("0")) {
%>
<br>
<br>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">没有计划信息可用，不能生成图表！</td>
  </tr>
</table>
<%} else {

	String year = 	(String)resultHt.get("year");
	String month = 	(String)resultHt.get("month");
	String patrol = 	(String)resultHt.get("patrol");

	String chartTitle = "巡检员 " + patrol + " " +  year + " 年" + month + " 月任务分配图";

	Vector oplist = (Vector)resultHt.get("");
%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2"><%=chartTitle%> &nbsp;&nbsp; <a href="javascript:makeSize()"><img id="enlargeIcon" src="${ctx}/images/icon_arrow_up.gif" width="14" height="16" border="0" alt="隐藏查询条件"></a></td>
  </tr>
</table>
<br>
<br>
<template:formSubmit>
    <td width="85">
        <html:button property="action" styleClass="button" onclick="toExpotr()">Excel报表 </html:button>
    </td>
    <td width="85">
        <html:button property="action" styleClass="button" onclick="toCloseThis()">关闭窗口  </html:button>
    </td>
</template:formSubmit>
<br>
<br>

<%
  }
      //System.out.println(flag+"!!!!!!!!!!!!!");
%>
