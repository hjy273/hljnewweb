<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />

<script language="JavaScript" type="">
function addGoQuery()
{
  var url="${ctx}/city_mobile_year_stat.do?method=queryYearStatForm";
  self.location.replace(url);

}

function ghbq(td)
{
  var tr = td.parentNode.cells;
  var ob = document.getElementById("obody").rows;
  if (tr[td.cellIndex]==document.getElementById("generalPanel")){
	document.getElementById("frameGeneralInfo").src="${ctx}/city_mobile_year_stat.do?method=showYearGeneralInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("executeResultPanel")){
	document.getElementById("frameExecuteResultInfo").src="${ctx}/city_mobile_year_stat.do?method=showYearExeuteResultInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("lineLevelExecuteResultPanel")){
	document.getElementById("frameLineLevelExecuteResultInfo").src ="${ctx}/city_mobile_year_stat.do?method=showYearLineLevelExecuteResultInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("layingMethodecuteResultPanel")){
	document.getElementById("frameLayingMethodExecuteResultInfo").src ="${ctx}/city_mobile_year_stat.do?method=showYearLayingMethodExecuteResultInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("analyseChartPanel")){
	document.getElementById("frameAnalyseChart").src="${ctx}/planstat/show_city_mobile_year_analyse_chart.jsp";
  }
  for(var ii=0; ii<tr.length-2; ii++)
  {
    tr[ii].className = (td.cellIndex==ii)?"ooihj":"ooihs";
    ob[ii].style.display = (td.cellIndex==ii)?"":"none";
  }
}  
</script>
<body>
	<table class="ooib" id="obody" border="0" cellspacing="0"
		cellpadding="0" width="100%" height="400px">
		<tr style="display: ">
			<td style="border: 0px;">
				<iframe name="frameGeneralInfo" id="frameGeneralInfo"
					marginWidth="0" marginHeight="0"
					src="${ctx}/city_mobile_year_stat.do?method=showYearGeneralInfo"
					frameBorder=0 width="100%" scrolling="auto" height="480px"></iframe>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="frameExecuteResultInfo" id="frameExecuteResultInfo"
					marginWidth="0" marginHeight="0" frameBorder=0 width="100%"
					scrolling="auto" height="480px">
				</iframe>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe id="frameLineLevelExecuteResultInfo" marginWidth="0"
					marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto
					height="480px">
				</iframe>
			</td>
		</tr>
		<!-- 
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="frameLayingMethodExecuteResultInfo"
					id="frameLayingMethodExecuteResultInfo" marginWidth="0"
					marginHeight="0" frameBorder="0" width="100%" scrolling="auto"
					height="480px">
				</iframe>
			</td>
		</tr>
		 -->
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="frameAnalyseChart" id="frameAnalyseChart" border="0"
					marginWidth="0" marginHeight="0" frameBorder="0" width="100%"
					scrolling="auto" height="480px">
				</iframe>
			</td>
		</tr>
	</table>
	<div
		style="width: 98%; margin: 0px auto; position: absolute; bottom: 5px; right: 1%;">
		<table class="ooih" border="0" cellspacing="0" cellpadding="0"
			width="400px" height="19px">
			<tr>
				<td id="generalPanel" class="ooihj" nowrap onclick="ghbq(this)">
					计划信息
				</td>
				<td id="executeResultPanel" class="ooihs" nowrap
					onclick="ghbq(this)">
					总体信息
				</td>
				<td class="ooihs" id="lineLevelExecuteResultPanel" nowrap
					onclick="ghbq(this)">
					分级别执行结果信息
				</td>
				<!-- 
			<td class="ooihs" id="layingMethodecuteResultPanel" nowrap
				onclick="ghbq(this)">
				分级别执行结果信息
			</td>
			 -->
				<td id="analyseChartPanel" class="ooihs" nowrap onclick="ghbq(this)">
					对比分析
				</td>
				<td class="ooihs" nowrap onclick="addGoQuery()">
					查询页面
				</td>
				<td width="100%">
					&nbsp;
				</td>
			</tr>
		</table>
	</div>
</body>






