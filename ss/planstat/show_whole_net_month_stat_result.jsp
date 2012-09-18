<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />

<script type="text/javascript">
function addGoBack()
{
  var url="${ctx}/whole_net_stat.do?method=queryMonthStatForm";
  self.location.replace(url);
}
function ghbq(td)
{
  var tr = td.parentNode.cells;
  var ob = document.getElementById("obody").rows;
  if (tr[td.cellIndex]==document.getElementById("generalPanel")){
	document.getElementById("frameGeneralInfo").src ="${ctx}/whole_net_stat.do?method=showMonthGeneralInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("executeResultPanel")){
	document.getElementById("frameExecuteResultInfo").src ="${ctx}/whole_net_stat.do?method=showMonthExecuteResultInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("layingMethodExecuteResultPanel")){
	document.getElementById("frameLayingMethodExecuteResultInfo").src ="${ctx}/whole_net_stat.do?method=showMonthLayingMethodExecuteResultInfo";
  }
  //图表分析ChartAnalysePanel
   if (tr[td.cellIndex]==document.getElementById("analyseChartPanel")){
	document.getElementById("frameAnalyseChart").src ="${ctx}/planstat/show_whole_net_month_analyse_chart.jsp";
  }
  for(var i=0; i<tr.length-2; i++)
  {	
    tr[i].className = (td.cellIndex==i)?"ooihj":"ooihs";
    ob[i].style.display = (td.cellIndex==i)?"":"none";
  }
}
</script>
<table class="ooib" id="obody" width='100%' height="400px">
	<tr style="display: ">
		<td style="border: 0px;">
			<iframe id="frameGeneralInfo" marginWidth="0" marginHeight="0"
				src="${ctx}/whole_net_stat.do?method=showMonthGeneralInfo"
				frameBorder=0 width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>
	<tr style="display: none">
		<td style="border: 0px;">
			<iframe id="frameExecuteResultInfo" marginWidth="0" marginHeight="0"
				frameBorder=0 width="100%" scrolling=auto height="480px">
			</iframe>
		</td>
	</tr>
	<!-- 
	<tr style="display: none">
		<td style="border: 0px;">
			<iframe id="frameLayingMethodExecuteResultInfo" marginWidth="0"
				marginHeight="0" frameBorder=0 width="100%" scrolling=auto
				height="480px">
			</iframe>
		</td>
	</tr>
	 -->
	<tr width="100%" style="display: none">
		<td style="border: 0px;">
			<iframe id="frameAnalyseChart" marginWidth="0" marginHeight="0"
				frameBorder=0 width="100%" scrolling=auto height="480px">
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
				总体信息
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
