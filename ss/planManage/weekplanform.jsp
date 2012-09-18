<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.beans.*"%>
<%@page import="com.cabletech.commons.config.*"%>
<%@page import="com.cabletech.planinfo.domainobjects.*" %>
<%@page import="java.util.*" %>
<%
  PlanBean planbean = (PlanBean) request.getSession().getAttribute("planBean");
  Vector taskVct = (Vector) request.getSession().getAttribute("tasklist");
  int taskRowSpanL = taskVct.size();
  GisConInfo gisip = GisConInfo.newInstance();
System.out.println("IP :"+gisip.getServerip());
%>
<script language="javascript" type="text/javascript">
<!--
function loadEditPlan(idValue){
        var url = "${ctx}/PlanAction.do?method=loadPlan&id=" + idValue;
        self.location.replace(url);

}

function toExpotr(){
	self.location.replace("${ctx}/PlanAction.do?method=ExportWeekPlanform");
}
function goBack(){
		location.href = "${ctx}/PlanAction.do?method=queryPlan";
}
function open_map(PLANID,TASKID,FORMAT)
{
 URL1="http://218.12.174.248:7003/WEBGIS/gisextend/igis.jsp?actiontype=008&planid="+PLANID+"&taskid="+TASKID;
 URL="http://<%=gisip.getServerip()%>:<%=gisip.getServerport()%>/WEBGIS/gisextend/igis.jsp?actiontype=008&planid="+PLANID+"&taskid="+TASKID;
 myleft=(screen.availWidth-650)/2;
 mytop=100
 mywidth=650;
 myheight=500;
 if(FORMAT=="1")
 {
    myleft=0;
    mytop=0
    mywidth=screen.availWidth-10;
    myheight=screen.availHeight-40;
 }
 window.open(URL,"","height="+myheight+",width="+mywidth+",status=1,toolbar=no,resizable=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft);
}
//-->
</script>
<style type="text/css">
<!--
.hiInput {
    width: 100%;
    background-color:transparent;
    border-bottom-width: 1px;
    border-top-style: none;
    border-right-style: none;
    border-bottom-style: solid;
    border-left-style: none;
    border-bottom-color: #000000;
}

.commonHiInput {
    background-color:transparent;
    border-bottom-width: 1px;
    border-top-style: none;
    border-right-style: none;
    border-bottom-style: solid;
    border-left-style: none;
    border-bottom-color: #000000;
}
-->
</style>

<br>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="15%">报表名称 ：</td>
    <td width="85%"><input type="text" class="hiInput" value="<%=planbean.getPlanname()%>" readonly></td>
  </tr>
  <tr>
    <td>报表类型 ：</td>
    <td><input type="text" class="hiInput" value="巡检计划信息报表" readonly></td>
  </tr>
  <tr>
    <td>报表编号 ：</td>
    <td><input type="text" class="hiInput" value="<%=planbean.getId()%>" readonly></td>
  </tr>
  <tr>
    <td>填报日期 ：</td>
    <td><input type="text" class="hiInput" value="<%=planbean.getApplyformdate()%>" readonly></td>
  </tr>
</table>
<br>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

	  <tr>
	    <td height="24" align="center" class="title2">制定巡检计划表
          </td>
      </tr>

</table>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- 计划信息 -->
  <tr bgcolor="#FFFFFF">
    <td width="11%" class=trcolor rowspan="<%=7+taskRowSpanL%>" align="center">计划信息</td>
    <td class=trcolor width="14%">计划名称</td>
    <td><%=planbean.getPlanname()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">巡检负责人</td>
    <td bgcolor="#FFFFFF"><%=planbean.getExecutorid()%>    </td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>计划时间</td>
    <td bgcolor="#FFFFFF"><%=planbean.getBegindate().substring(0,planbean.getBegindate().indexOf(" "))%>      &nbsp;&nbsp;
      -
      &nbsp;&nbsp;
<%=planbean.getEnddate().substring(0,planbean.getEnddate().indexOf(" "))%>    </td>
  </tr>
<%
  for (int i = 0; i < taskRowSpanL; i++) {
    TaskBean taskB = (TaskBean) taskVct.get(i);
%>
  <tr class=trwhite>
  <%if (i == 0) {  %>
    <td class=trcolor rowspan="<%=taskRowSpanL%>" bgcolor="#FFFFFF">任务</td>
  <%}  %>
    <td bgcolor="#FFFFFF"><%=taskB.getLineleveltext()%>      &nbsp;&nbsp;&nbsp;<%=taskB.getDescribtion()%>      &nbsp;&nbsp;&nbsp;
<%=taskB.getExcutetimes()%>次&nbsp;&nbsp;&nbsp;估算<%=taskB.getEvaluatepoint() %>点&nbsp;&nbsp;&nbsp;(<%=taskB.getTaskop().substring(0,taskB.getTaskop().length()) %>)<br>
巡检线段：<a href="javascript:open_map('<%=planbean.getId()%>','<%=taskB.getId() %>','0');">
<%
//<%=taskB.getSubline().substring(0,taskB.getSubline().length()-1)-
List list = taskB.getTaskSubline();
for(int k=0;k<list.size();k++){
	PlanTaskSubline subline = (PlanTaskSubline)list.get(k);
	out.print(subline.getName()+" ");	
}
 %>
</a>
&nbsp;&nbsp;&nbsp;计划点数 共:<%=(taskB.getTaskpoint().equals(""))?"":taskB.getTaskpoint()+"点"%> 实际点数 共:<%=taskB.getRealPoint()+"点"%>    所占比率：<%=taskB.getRatio()+"点"%>

</td>
  </tr>
<%}%>
  <tr class=trwhite>
	<td class=trcolor bgcolor="#FFFFFF">巡检方式</td>		      
	<td bgcolor="#FFFFFF">
	<%out.print(planbean.getPatrolmode().equals("01")?"手动":"自动"); %>
  </td>			  				
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">计划制定人</td>
    <td bgcolor="#FFFFFF"><%=planbean.getCreator()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">计划制定日期</td>
    <td bgcolor="#FFFFFF"><%=planbean.getCreatedate()%>    </td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>代维单位审批情况</td>
    <td bgcolor="#FFFFFF"><%=planbean.getIfinnercheck()%>    </td>
  </tr>

  <tr bgcolor="#FFFFFF" class=trwhite>
    <td rowspan="2" align="center" class=trcolor>巡检员/段长<br>确认</td>
    <td colspan="2" align="right">&nbsp;巡检员确认：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  </tr>
  <tr bgcolor="#FFFFFF" class=trwhite>
    <td colspan="2" align="right">&nbsp;段长确认：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  </tr>

  <tr bgcolor="#FFFFFF" class=trwhite>
    <td rowspan="2" align="center" class=trcolor>代维单位<br>领导意见</td>
    <% if(planbean.getApprovecontent()!= null){%>
<td height="63" colspan="2" bgcolor="#FFFFFF"><%=planbean.getApprovecontent()%></td>
<%}else{
%>
<td height="63" colspan="2" bgcolor="#FFFFFF"> </td>

<% }%>
  </tr>
  <tr bgcolor="#FFFFFF" class=trwhite>
    <td colspan="2">&nbsp;</td>
  </tr>

</table>
<br>

<br>
<template:formSubmit>
    <td width="85px">
        <html:button property="action" styleClass="button" onclick="toExpotr()">Excel报表 </html:button>
    </td>
    <td width="85px">
    <input class="button" type="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
        
    </td>
</template:formSubmit>

<br>
<br>
