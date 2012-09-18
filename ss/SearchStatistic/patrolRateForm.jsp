<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.statistics.domainobjects.*"%>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" type="">
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
	self.location.replace("${ctx}/StatisticAction.do?method=ReportPatrolRateWithSession");
}

//-->
</script><%
  PatrolRate patrolrate = (PatrolRate) request.getSession().getAttribute("QueryResult");
  String flag = patrolrate.getIfhasrecord();
  if (flag.equals("0")) {
%>
<br>
<br>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">没有计划信息可用，不能生成报表！</td>
  </tr>
</table>
<%} else {%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2"><%=patrolrate.getFormname()%>      &nbsp;&nbsp;
      <a href="javascript:makeSize()"><img id="enlargeIcon" src="${ctx}/images/icon_arrow_up.gif" width="14" height="16" border="0" alt="隐藏查询条件"></a>
    </td>
  </tr>
</table>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- 计划信息 -->
  <tr class=trwhite>
    <td width="15%" bgcolor="#FFFFFF" class=trcolor><%=patrolrate.getStatype()%>    </td>
    <td width="45%" bgcolor="#FFFFFF"><%=patrolrate.getStaobject() %>    </td>
    <td width="300" rowspan="7"  bgcolor="#CCCCCC" >      <div><img src="${ctx}/images/1px.gif"><br>
        <iframe name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/ShowChart?PRate=<%=patrolrate.getPatrolrate() %>&LRate=<%=patrolrate.getLossrate() %>" frameBorder=0 width="100%" scrolling=NO height="165">        </iframe>
      </div>
</td>
  </tr>
  <tr class=trcolor>
    <td class=trcolor bgcolor="#FFFFFF">统计时间</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getFormtime() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">计划工作量</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getPlancount() %>      点次
</td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">实际工作量</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getRealcount() %>      点次
</td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">漏检工作量</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getLosscount() %>      点次
</td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">巡检率</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getPatrolrate() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">漏检率</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getLossrate() %>    </td>
  </tr>
</table>
<br>
<br>
<template:formSubmit>
  <td width="85">
    <html:button property="action" styleClass="button" onclick="toExpotr()">Excel报表</html:button>
  </td>
  <td width="85">
    <html:button property="action" styleClass="button" onclick="toCloseThis()">关闭窗口</html:button>
  </td>
</template:formSubmit>
<br>
<br>
<%
  }
      //System.out.println(flag+"!!!!!!!!!!!!!");
%>
