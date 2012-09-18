<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.beans.*"%>
<%@page import="com.cabletech.planinfo.domainobjects.*"%>
<%@page import="com.cabletech.planstat.beans.*"%>
<%
  YearMonthPlanBean planbean = (YearMonthPlanBean) request.getSession().getAttribute("YearMonthPlanBean");
  Vector taskVct = (Vector) request.getSession().getAttribute("tasklist");
  int taskRowSpanL = taskVct.size();

  String fID = (String) request.getAttribute("fID");
  String titleName = "年度巡检计划表";
  if(fID.equals("2")){
	  titleName = "月度巡检计划表";
  }
%>

<script language="javascript" type="">
<!--
function loadEditPlan(idValue,fID){


    var url="/";
  	if(fID=="1")
         url = "${ctx}/YearMonthPlanAction.do?method=queryYMPlan&fID=1&year=";
     else
     	 url = "${ctx}/YearMonthPlanAction.do?method=queryYMPlan&fID=2&year=";
    self.location.replace(url);

}

function toExpotr(){
	self.location.replace("${ctx}/YearMonthPlanAction.do?method=ExportYMPlanform");
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
<body>
<%
	String looktype = (String)request.getAttribute("looktype");
	//System.out.println("=="+looktype);
	if(looktype == null || !looktype.equals("stat")){
 %>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="15%">报表名称 ：</td>
    <td width="85%"><input type="text" class="hiInput" value="<%=planbean.getPlanname() %>" readonly></td>
  </tr>
  <tr>
    <td>报表类型 ：</td>
    <td><input type="text" class="hiInput" value="代维单位年度/月度巡检计划信息报表" readonly></td>
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
    <td height="24" align="center" class="title2"><%=titleName%>
    </td>
  </tr>
</table>
<br>
<%}else{ %>
	<%PatrolStatConditionBean pbean = (PatrolStatConditionBean)session.getAttribute("queryCon"); %>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2"><bean:write name="queryCon" property="conName"/>公司<bean:write name="queryCon" property="endYear"/>年<%=pbean.getEndMonth() %>月份计划信息
    </td>
  </tr>
</table>
<hr width='100%' size='1'>
<%} %>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- 计划信息 -->
  <tr bgcolor="#FFFFFF">
    <td width="11%" class=trcolor rowspan="<%=8+taskRowSpanL%>" align="center">计划信息</td>
    <td width="14%" class=trcolor>计划名称</td>
    <td><%=planbean.getPlanname() %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>计划时间</td>
    <td bgcolor="#FFFFFF">      <%=planbean.getYear() %> 年 <%=planbean.getMonth()%>
</td>
  </tr>
<%
  for (int i = 0; i < taskRowSpanL; i++) {
    TaskBean task = (TaskBean) taskVct.get(i);
%>
  <tr class=trwhite>
  <%if (i == 0) {  %>
    <td class=trcolor rowspan="<%=taskRowSpanL%>" bgcolor="#FFFFFF">任务</td>
  <%}  %>
    <td bgcolor="#FFFFFF"><%=task.getLineleveltext()%>      &nbsp;&nbsp;&nbsp;<%=task.getDescribtion()%>      &nbsp;&nbsp;&nbsp;
<%=task.getExcutetimes()%>      次
</td>
  </tr>
<%}%>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">制 定 人</td>
    <td bgcolor="#FFFFFF"><%=planbean.getCreator()%></td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">制定日期</td>
    <td bgcolor="#FFFFFF">
    ${ctime}
    </td>
  </tr>
  <!--
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">代维单位审批情况</td>
    <td bgcolor="#FFFFFF"><%=planbean.getIfinnercheck() %></td>
  </tr>
  -->
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">移动审批</td>
    <td bgcolor="#FFFFFF"><%=planbean.getStatus() %></td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>审 批 人</td>
    <td bgcolor="#FFFFFF"><%=planbean.getApprover() %></td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>审批日期</td>
    <td bgcolor="#FFFFFF"><%=planbean.getApprovedate() %></td>
  </tr>
  <!--
  <tr bgcolor="#FFFFFF" class=trwhite>
    <td rowspan="2" align="center" class=trcolor>代维单位<br>领导确认</td>
    <td height="63" colspan="2">&nbsp;</td>
  </tr>-->

    <tr bgcolor="#FFFFFF" class=trwhite>
    <td rowspan="2" align="center" class=trcolor>移动公司<br>领导批示</td>
<% if(planbean.getApprovecontent()!= null){%>
<td height="63" colspan="2" bgcolor="#FFFFFF"><%=planbean.getApprovecontent()%></td>
<%}else{
%>
<td height="63" colspan="2" bgcolor="#FFFFFF"> </td>

<% }%>
  </tr>

</table>
<%
	if(looktype == null || !looktype.equals("stat")){
 %>
<br>
<br>
<template:formSubmit>
    <td width="85">
        <html:button property="action" styleClass="button" onclick="toExpotr()">Excel报表 </html:button>
    </td>
    <td>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
      </td>
</template:formSubmit>
<br>
<br>
<%} %>
</body>
