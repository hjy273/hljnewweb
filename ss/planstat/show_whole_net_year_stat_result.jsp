<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />

<script language="JavaScript" type="">
function addGoQuery()
{
  var url="${ctx}/whole_net_stat.do?method=queryYearStatForm";
  self.location.replace(url);

}

function ghbq(td)
{
  var tr = td.parentNode.cells;
  var ob = document.getElementById("obody").rows;
  if (tr[td.cellIndex]==document.getElementById("wholeYearGeneralInfoPanel")){
	document.getElementById("frameWholeYearGeneralInfo").src="${ctx}/whole_net_stat.do?method=showWholeYearGeneralInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("wholeYearLineTypeExecuteResultPanel")){
	document.getElementById("frameWholeYearLineTypeExecuteResult").src="${ctx}/whole_net_stat.do?method=showWholeYearLineTypeExecuteResult";
  }
  if (tr[td.cellIndex]==document.getElementById("wholeYearLayingMethodExecuteResultPanel")){
	document.getElementById("frameWholeYearLayingMethodExecuteResult").src="${ctx}/whole_net_stat.do?method=showYearLayingMethodExecuteResultInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("wholeYearLineTypeStatPanel")){
	document.getElementById("frameWholeYearLineTypeStat").src="${ctx}/planstat/show_whole_year_line_type_stat_chart.jsp";
  }
  if (tr[td.cellIndex]==document.getElementById("monthLineTypeStatPanel")){
	document.getElementById("frameMonthLineTypeStat").src="${ctx}//planstat/show_month_line_type_stat_chart.jsp";
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
				<iframe name="frameWholeYearGeneralInfo"
					id="frameWholeYearGeneralInfo" marginWidth="0" marginHeight="0"
					src="${ctx}/whole_net_stat.do?method=showWholeYearGeneralInfo"
					frameBorder="0" width="100%" scrolling=auto height="480px"></iframe>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="frameWholeYearLineTypeExecuteResult"
					id="frameWholeYearLineTypeExecuteResult" marginWidth="0"
					marginHeight="0" src="" frameBorder="0" width="100%"
					scrolling="auto" height="480px"></iframe>
			</td>
		</tr>
		<!-- 
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="frameWholeYearLayingMethodExecuteResult"
					id="frameWholeYearLayingMethodExecuteResult" marginWidth="0"
					marginHeight="0" src="" frameBorder="0" width="100%"
					scrolling="auto" height="480px"></iframe>
			</td>
		</tr>
		 -->
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="frameWholeYearLineTypeStat"
					id="frameWholeYearLineTypeStat" marginWidth="0" marginHeight="0"
					src="" frameBorder="0" width="100%" scrolling="auto" height="480px"></iframe>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="frameMonthLineTypeStat" id="frameMonthLineTypeStat"
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
				<td id="wholeYearGeneralInfoPanel" class="ooihj" nowrap
					onclick="ghbq(this)">
					总体信息
				</td>
				<td id="wholeYearLineTypeExecuteResultPanel" class="ooihs" nowrap
					onclick="ghbq(this)">
					分级别执行结果信息
				</td>
				<!-- 
			<td id="wholeYearLayingMethodExecuteResultPanel" class="ooihs" nowrap
				onclick="ghbq(this)">
				分级别执行结果信息
			</td>
			 -->
				<td id="wholeYearLineTypeStatPanel" class="ooihs" nowrap
					onclick="ghbq(this)">
					当年全网巡检率
				</td>
				<td id="monthLineTypeStatPanel" class="ooihs" nowrap
					onclick="ghbq(this)">
					各线路级别月巡检率对比分析
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






