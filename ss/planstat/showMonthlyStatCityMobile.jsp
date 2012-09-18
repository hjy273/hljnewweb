<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />

<script language="JavaScript" type="">
function addGoFirst()
{
  window.history.back();

}  

function addGoQuery()
{
  var url="${ctx}/planstat/queryMonthlyStatCityMobile.jsp";
  self.location.replace(url);

}

function ghbq(td)
{
  var tr = td.parentNode.cells;
  var ob = document.getElementById("obody").rows;
  if (tr[td.cellIndex]==document.getElementById("planPanel")){
	document.getElementById("FrmPlanInfo").src="${ctx}/planstat/showPlanForCMMonthlyStat.jsp";
  }
  //if (tr[td.cellIndex]==document.getElementById("generalPanel")){
	//document.getElementById("FrmGeneralInfo").src="${ctx}/MonthlyStatCityMobileAction.do?method=showGeneralInfo";
  //}
  if (tr[td.cellIndex]==document.getElementById("conExePanel")){
	document.getElementById("FrmConExe").src="${ctx}/MonthlyStatCityMobileAction.do?method=showContractorExeInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("layingMethodecuteResultPanel")){
	document.getElementById("frameLayingMethodExecuteResultInfo").src="${ctx}/MonthlyStatCityMobileAction.do?method=showMonthLayingMethodExecuteResultInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("planExePanel")){
	document.getElementById("FrmPlanExe").src="${ctx}/MonthlyStatCityMobileAction.do?method=showPlanExeInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("sublinePanel")){
	//document.getElementById("FrmSubline").src="${ctx}/MonthlyStatCityMobileAction.do?method=showCompDataAllTypeSubline";
	document.getElementById("FrmSubline").src="${ctx}/MonthlyStatCityMobileAction.do?method=showCompDataAllTypeSubline";
  }
  if (tr[td.cellIndex]==document.getElementById("compAnalysisPanel")){
	//document.getElementById("FrmCompAnalysis").src="${ctx}/MonthlyStatCityMobileAction.do?method=showCompAnalysisInfo";
	document.getElementById("FrmCompAnalysis").src="${ctx}/planstat/showVCompMonthlyStatCM.jsp";
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
				<iframe name="FrmPlanInfo" id="FrmPlanInfo" marginWidth="0"
					marginHeight="0" src="${ctx}/planstat/showPlanForCMMonthlyStat.jsp"
					frameBorder="0" width="100%" scrolling="auto" height="480px"></iframe>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="FrmConExe" id="FrmConExe" marginWidth="0"
					marginHeight="0" frameBorder="0" width="100%" scrolling="auto"
					height="480px">
				</iframe>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="frameLayingMethodExecuteResultInfo"
					id="frameLayingMethodExecuteResultInfo" marginWidth="0"
					marginHeight="0" frameBorder="0" width="100%" scrolling="auto"
					height="480px">
				</iframe>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="FrmSubline" id="FrmSubline" border="0" marginWidth="0"
					marginHeight="0" frameBorder="0" width="100%" scrolling="auto"
					height="480px">
				</iframe>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px;">
				<iframe name="FrmCompAnalysis" id="FrmCompAnalysis" border="0"
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
				<td id="planPanel" class="ooihj" nowrap onclick="ghbq(this)">
					计划信息
				</td>
				<td id="conExePanel" class="ooihs" nowrap onclick="ghbq(this)">
					总体信息
				</td>
				<td id="layingMethodecuteResultPanel" class="ooihs" nowrap
					onclick="ghbq(this)">
					分级别执行结果信息
				</td>
				<td id="sublinePanel" class="ooihs" nowrap onclick="ghbq(this)">
					巡检线段
				</td>
				<td id="compAnalysisPanel" class="ooihs" nowrap onclick="ghbq(this)">
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






