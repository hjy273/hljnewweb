<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />

<script language="javascript" type="text/javascript">
function addGoBack()
{
  window.history.go(-1);
}
function ghbq(td)
{
  var tr = td.parentNode.cells;
  var ob = document.getElementById("obody").rows;
  if (tr[td.cellIndex]==document.getElementById("ExecuteResultPanel")){
	document.getElementById("ExecuteResult").src ="${ctx}/PlanMonthlyStatAction.do?method=showExceuteResult";
  }
  if (tr[td.cellIndex]==document.getElementById("layingMethodExecuteResultPanel")){
	document.getElementById("layingMethodExecuteResult").src ="${ctx}/PlanMonthlyStatAction.do?method=showMonthLayingMethodExecuteResultInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("SubLinePatrolPanel")){
	document.getElementById("SubLinePatrol").src ="${ctx}/planstat/showSubLinePatrol.jsp";
  }
  if (tr[td.cellIndex]==document.getElementById("MonthPlanPanel")){
	document.getElementById("MonthPlan").src ="${ctx}/PlanMonthlyStatAction.do?method=showMonthPlan";
  }
  //xunjianzu
  if (tr[td.cellIndex]==document.getElementById("PatrolManPanel")){
	document.getElementById("PatrolMan").src ="${ctx}/PlanMonthlyStatAction.do?method=showPatrolMan";
  }
  //图表分析ChartAnalysePanel
   if (tr[td.cellIndex]==document.getElementById("ChartAnalysePanel")){
	document.getElementById("ChartAnalyse").src ="${ctx}/planstat/showChartAnalyse.jsp";
  }
  for(var i=0; i<tr.length-2; i++)
  {	
    tr[i].className = (td.cellIndex==i)?"ooihj":"ooihs";
    ob[i].style.display = (td.cellIndex==i)?"":"none";
  }
}
</script>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<table class="ooib" id="obody" border="0" cellspacing="0"
	cellpadding="0" width="100%" height="400px">
	<tr style="display: ">
		<td style="border: 0px;">
			<iframe id="MonthPlan" marginWidth="0" marginHeight="0"
				src="${ctx}/PlanMonthlyStatAction.do?method=showMonthPlan"
				frameBorder=0 width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>
	<tr style="display: none">
		<td style="border: 0px;">
			<iframe id="MonthPlanDetail" marginWidth="0" marginHeight="0"
				src="${ctx}/planstat/conMonthResultDetail.jsp" frameBorder=0
				width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>
	<tr style="display: none">
		<td style="border: 0px;">
			<iframe id="ExecuteResult" marginWidth="0" marginHeight="0" src=""
				frameBorder=0 width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>
	<tr style="display: none">
		<td style="border: 0px;">
			<iframe id="layingMethodExecuteResult" marginWidth="0"
				marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto
				height="480px">
			</iframe>
		</td>
	</tr>
	<tr style="display: none">
		<td style="border: 0px;">
			<iframe id="PatrolMan" marginWidth="0" marginHeight="0" src=""
				frameBorder=0 width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>
	<tr style="display: none">
		<td style="border: 0px;">
			<iframe id="SubLinePatrol" marginWidth="0" marginHeight="0" src=""
				frameBorder=0 width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>

	<tr width="100%" style="display: none">
		<td style="border: 0px;">
			<iframe id="ChartAnalyse" marginWidth="0" marginHeight="0" src=""
				frameBorder=0 width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>
</table>
<div style="width:98%; margin:0px auto; position:absolute; bottom:5px; right:1%;">
	<table class="ooih" border="0" cellspacing="0" cellpadding="0"
		width="400px" height="19px">
		<tr>
			<td class="ooihj" id="MonthPlanPanel" nowrap onclick="ghbq(this)">
				计划信息
			</td>
			<td class="ooihs" nowrap onclick="ghbq(this)">
				总体信息
			</td>
			<td class="ooihs" id="ExecuteResultPanel" nowrap onclick="ghbq(this)">
				执行结果
			</td>
			<td class="ooihs" id="layingMethodExecuteResultPanel" nowrap
				onclick="ghbq(this)">
				分级别执行结果信息
			</td>
			<td class="ooihs" id="PatrolManPanel" nowrap onclick="ghbq(this)">
				巡检组信息
			</td>
			<td class="ooihs" id="SubLinePatrolPanel" nowrap onclick="ghbq(this)">
				巡检线段
			</td>
			<td class="ooihs" id="ChartAnalysePanel" nowrap onclick="ghbq(this)">
				图表分析
			</td>
			<td class="ooihs" nowrap onclick="addGoBack()">
				返回查询页面
			</td>
			<td width="100%">
				&nbsp;
			</td>
		</tr>
	</table>
</div>
