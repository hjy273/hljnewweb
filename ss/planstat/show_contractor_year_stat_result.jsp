<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />

<script language="javascript" type="text/javascript">
function addGoBack()
{
  var url="${ctx}/contractor_year_stat.do?method=queryYearStatForm";
  self.location.replace(url);
}
function ghbq(td)
{
  var tr = td.parentNode.cells;
  var ob = document.getElementById("obody").rows;
  if (tr[td.cellIndex]==document.getElementById("generalPanel")){
	document.getElementById("frameGeneralInfo").src ="${ctx}/contractor_year_stat.do?method=showYearGeneralInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("layingMethodExecuteResultPanel")){
	document.getElementById("frameLayingMethodExecuteResult").src ="${ctx}/contractor_year_stat.do?method=showYearLayingMethodExecuteResultInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("executeResultPanel")){
	document.getElementById("frameExecuteResultInfo").src ="${ctx}/contractor_year_stat.do?method=showYearExecuteResultInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("lineLevelExecuteResultPanel")){
	document.getElementById("frameLineLevelExecuteResultInfo").src ="${ctx}/contractor_year_stat.do?method=showYearLineLevelExecuteResultInfo";
  }
  //图表分析ChartAnalysePanel
   if (tr[td.cellIndex]==document.getElementById("analyseChartPanel")){
	document.getElementById("frameAnalyseChart").src ="${ctx}/planstat/show_contractor_year_analyse_chart.jsp";
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
			<iframe id="frameGeneralInfo" marginWidth="0" marginHeight="0"
				src="${ctx}/contractor_year_stat.do?method=showYearGeneralInfo"
				frameBorder=0 width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>
	<tr style="display: none">
		<td style="border: 0px;">
			<iframe id="frameExecuteResultInfo" marginWidth="0" marginHeight="0"
				src="" frameBorder=0 width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>
	<!-- 
	<tr style="display: none">
		<td style="border: 0px;">
			<iframe id="frameLayingMethodExecuteResult" marginWidth="0"
				marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto
				height="480px">
			</iframe>
		</td>
	</tr>
	 -->
	<tr style="display: none">
		<td style="border: 0px;">
			<iframe id="frameLineLevelExecuteResultInfo" marginWidth="0"
				marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto
				height="480px">
			</iframe>
		</td>
	</tr>
	<tr width="100%" style="display: none">
		<td style="border: 0px;">
			<iframe id="frameAnalyseChart" marginWidth="0" marginHeight="0"
				src="" frameBorder=0 width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>
</table>
<div
	style="width: 98%; margin: 0px auto; position: absolute; bottom: 5px; right: 1%;">
	<table class="ooih" border="0" cellspacing="0" cellpadding="0"
		width="400px" height="19px">
		<tr>
			<td class="ooihj" id="generalPanel" nowrap onclick="ghbq(this)">
				计划信息
			</td>
			<td class="ooihs" id="executeResultPanel" nowrap onclick="ghbq(this)">
				执行结果信息
			</td>
			<!-- 
		<td class="ooihs" id="layingMethodExecuteResultPanel" nowrap
			onclick="ghbq(this)">
			分级别执行结果信息
		</td>
		 -->
			<td class="ooihs" id="lineLevelExecuteResultPanel" nowrap
				onclick="ghbq(this)">
				分级别执行结果信息
			</td>
			<td class="ooihs" id="analyseChartPanel" nowrap onclick="ghbq(this)">
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






