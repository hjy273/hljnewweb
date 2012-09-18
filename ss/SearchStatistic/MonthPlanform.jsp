<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.statistics.domainobjects.PatrolRate"%>
<%@page import="com.cabletech.planinfo.beans.*"%>

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
	self.location.replace("${ctx}/StatisticAction.do?method=ExportContractorPlanForm");
}

//-->
</script>


			<%
			  PatrolRate patrolrate = (PatrolRate) request.getSession().getAttribute("QueryResult");
			  String flag = patrolrate.getIfhasrecord();
              YearMonthPlanBean bean = (YearMonthPlanBean)request.getSession().getAttribute("YearMonthPlanBean");
			  if (flag.equals("0")) {
    				if(bean == null){
			%>
		            <br>
			      <br>
			      <br>
			      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			        <tr>
			          <td height="24" align="center" class="title2">该月计划还没有制定,不能生成报表！</td>
			        </tr>
			      </table>
            <%     }else{%>
						<br>
						<br>
						<br>
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						  <tr>
						    <td height="24" align="center" class="title2">该月没有制定周计划，不能生成报表！</td>
						  </tr>
						</table>
			<%  }
          }else {

				YearMonthPlanBean planbean = (YearMonthPlanBean) request.getSession().getAttribute("YearMonthPlanBean");
                if(bean == null){
              %>
              		<br>
			      <br>
			      <br>
			      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			        <tr>
			          <td height="24" align="center" class="title2">该月计划还没有制定,不能生成报表！!</td>
			        </tr>
			      </table>

              <%
                }else{
				Vector taskVct = (Vector) request.getSession().getAttribute("tasklist");
				int taskRowSpanL = taskVct.size();

				String fID = "2";
				String titleName = "月度巡检计划表";

			%>
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
			  <tr bgcolor="#FFFFFF">
			    <td width="116" class=trcolor rowspan="<%=8+taskRowSpanL%>" align="center">计划信息</td>
			    <td width="113" class=trcolor >计划名称1</td>
			    <td colspan="2"><%=planbean.getPlanname() %></td>
			  </tr>
			  <tr class=trcolor>
			    <td bgcolor="#FFFFFF" class=trcolor>计划时间</td>
			    <td bgcolor="#FFFFFF" colspan="2">      <%=planbean.getYear() %> 年 <%=planbean.getMonth()%>
			</td>
			  </tr>
			<%
			  for (int i = 0; i < taskRowSpanL; i++) {
			    TaskBean taskB = (TaskBean) taskVct.get(i);
			%>
			  <tr class=trwhite>
			  <%if (i == 0) {  %>
			    <td class=trcolor rowspan="<%=taskRowSpanL%>" bgcolor="#FFFFFF">任务</td>
			  <%}  %>
			    <td bgcolor="#FFFFFF" colspan="2"><%=taskB.getLineleveltext()%>      &nbsp;&nbsp;&nbsp;<%=taskB.getDescribtion()%>      &nbsp;&nbsp;&nbsp;
			<%=taskB.getExcutetimes()%>      次
			</td>
			  </tr>
			<%}%>
			  <tr class=trwhite>
			    <td class=trcolor bgcolor="#FFFFFF">计划制定人</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getCreator()%></td>
			  </tr>
			  <tr class=trwhite>
			    <td class=trcolor bgcolor="#FFFFFF">计划制定日期</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getCreatedate() %></td>
			  </tr>
			  <tr class=trwhite>
			    <td class=trcolor bgcolor="#FFFFFF">代维单位审批情况</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getIfinnercheck() %></td>
			  </tr>
			  <tr class=trwhite>
			    <td class=trcolor bgcolor="#FFFFFF">移动公司审批情况</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getStatus() %></td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>审批人</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getApprover() %></td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>审批日期</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getApprovedate() %></td>
			  </tr>
			  <!-- 计划信息 -->
			  <tr class=trwhite>
			    <td width="116" rowspan="7" bgcolor="#FFFFFF" class=trcolor  align="center">计划执行情况    </td>
			    <td width="113" bgcolor="#FFFFFF" class=trcolor><%=patrolrate.getStatype()%></td>
			    <td width="25%" bgcolor="#FFFFFF"><%=patrolrate.getStaobject() %>    </td>
			    <td width="300" rowspan="7"  bgcolor="#CCCCCC" >      <div><img src="${ctx}/images/1px.gif"><br>
			        <iframe name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/ShowChart?PRate=<%=patrolrate.getPatrolrate() %>&LRate=<%=patrolrate.getLossrate() %>" frameBorder=0 width="100%" scrolling=NO height="165">        </iframe>
			    </div></td>
			  </tr>
			  <tr class=trcolor>
			    <td bgcolor="#FFFFFF" class=trcolor>统计时间</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getFormtime() %>    </td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>计划工作量</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getPlancount() %>      点次
			</td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>实际工作量</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getRealcount() %>      点次
			</td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>漏检工作量</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getLosscount() %>      点次
			</td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>巡检率</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getPatrolrate() %>    </td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>漏检率</td>
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
			  }}
			%>
