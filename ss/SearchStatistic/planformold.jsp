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
	  PlanStatisticForm planform = (PlanStatisticForm) request.getSession().getAttribute("QueryResult");
	  String flag = planform.getIfhasrecord();
	  String cyctype = planform.getCyctype();
	  if (flag.equals("0")) {
	%>
				<br>
				<br>
				<br>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
				    <td height="24" align="center" class="title2">没有该时间段的计划，不能生成报表！</td>
				  </tr>
				</table>
<%} else {%>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
				    <td height="24" align="center" class="title2"><%=planform.getFormname()%>      &nbsp;&nbsp;
				      <a href="javascript:makeSize()">
				        <img id="enlargeIcon" src="${ctx}/images/icon_arrow_up.gif" width="14" height="16" border="0" alt="隐藏查询条件">
				      </a>
				    </td>
				  </tr>
				</table>
				<br>
<%
  int comPlanUnitL = 6;
  Vector planVct = planform.getPlanvct();
  //Vector dailyVct = planform.getDailypatrolvct();
%>
				<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- 计划信息 -->
<%
  for (int i = 0; i < planVct.size(); i++) {
    Plan plan = (Plan) planVct.get(i);
    Vector taskVct = plan.getTaskvct();
    int callPlanRSpan = comPlanUnitL + taskVct.size();
%>
					  <tr bgcolor="#FFFFFF">
					    <td width="11%" class=trcolor rowspan="<%=callPlanRSpan%>" align="center"><%=plan.getPlanname()%>
						</td>
					    <td class=trcolor width="14%">计划名称</td>
					    <td colspan="3"><%=plan.getPlanname()%>    </td>
					  </tr>
					  <tr class=trwhite>
					    <td class=trcolor bgcolor="#FFFFFF">巡检负责人</td>
					    <td colspan="3" bgcolor="#FFFFFF"><%=plan.getPatrolname()%>    </td>
					  </tr>
					  <tr class=trcolor>
					    <td class=trcolor bgcolor="#FFFFFF">计划时间</td>
					    <td colspan="3" bgcolor="#FFFFFF"><%=plan.getBegindateStr()%> &nbsp;- &nbsp;<%=plan.getEnddateStr()%>    </td>
					  </tr>
					  <tr class=trwhite>
					    <td class=trcolor bgcolor="#FFFFFF">工作量</td>
					    <td colspan="3" bgcolor="#FFFFFF"><%=plan.getPlancount()%>      点次
					</td>
					  </tr>
<%
  for (int k = 0; k < taskVct.size(); k++) {
    Task task = (Task) taskVct.get(k);
%>
  					<tr class=trwhite>
  <%if (k == 0) {  %>
    					<td class=trcolor rowspan="<%=taskVct.size()%>" bgcolor="#FFFFFF">任务</td>
  <%}  %>
    					<td colspan="3" bgcolor="#FFFFFF"><%=task.getTaskname() %>      &nbsp;&nbsp;&nbsp;<%=task.getTimes()%>      次
						</td>
    			    </tr>
<%}%>
					  <tr class=trwhite>
					    <td class=trcolor bgcolor="#FFFFFF">计划制定人</td>
					    <td colspan="3" bgcolor="#FFFFFF"><%=plan.getCreator()%>    </td>
					  </tr>
					  <tr class=trwhite>
					    <td class=trcolor bgcolor="#FFFFFF">计划制定日期</td>
					    <td colspan="3" bgcolor="#FFFFFF"><%=plan.getCreatedate()%>    </td>
					  </tr>
<%}%>


					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td class=trcolor rowspan="<%=dailyVct.size()+2%>" align="center">执行情况</td>
					    <td class=trcolor rowspan="2">日期</td>
					    <td class=trcolor colspan="3" align="center">执行情况</td>
					  </tr>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td class=trcolor align="center" width="33%">执行内容(杆号)</td>
					    <td class=trcolor align="center">巡检数量</td>
					    <td class=trcolor align="center">预防警报次数</td>
					  </tr>
<%
  for (int k = 0; k < dailyVct.size(); k++) {
    DailyPatrol daily = (DailyPatrol) dailyVct.get(k);
%>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td><%=daily.getDatestr()%>    </td>
					    <td><%=daily.getWorkcontent()%>    </td>
					    <td><%=daily.getPtimes()%>      &nbsp;&nbsp;&nbsp; 点次
					</td>
					    <td><%=daily.getAccnum()%>      次
					</td>
					  </tr>
<%}%>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td rowspan="5" class=trcolor align="center">计划统计</td>
					    <td class=trcolor>应检点次</td>
					    <td><%=planform.getPlancount()%>    </td>
					    <td rowspan="5" colspan="2" align="center" bgcolor="#CCCCCC" width="300">
					      <div>
					        <img src="${ctx}/images/1px.gif">
					        <br>
					        <iframe name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/ShowChart?PRate=<%=planform.getPatrolrate() %>&LRate=<%=planform.getLossrate() %>" frameBorder=0 width="100%" scrolling=NO height="165">        </iframe>
					      </div>
					    </td>
					  </tr>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td class=trcolor>实检点次</td>
					    <td><%=planform.getRealcount()%>    </td>
					  </tr>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td class=trcolor>漏检点次</td>
					    <td><%=planform.getLosscount() %>    </td>
					  </tr>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td class=trcolor>巡检率</td>
					    <td><%=planform.getPatrolrate() %>    </td>
					  </tr>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td class=trcolor>漏检率</td>
					    <td><%=planform.getLossrate() %>    </td>
					  </tr>
  <!--
    <tr bgcolor="#FFFFFF" class=trwhite>
    <td rowspan="2" align="center" class=trcolor>领导批示</td>
    <td height="63" colspan="3">&nbsp;</td>
    </tr>
    <tr bgcolor="#FFFFFF" class=trwhite>
    <td colspan="3">&nbsp;</td>
    </tr>
  -->
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
