<%@ include file="/common/header.jsp"%>
<script type="text/javascript">
<!--
function ck(id){
  if (id=="1"){
	//document.getElementById("monthcontrast").src ="${ctx}/ShowConMonthlyStat";
	//document.getElementById("monthcontrast").src ="${ctx}/MonthlyStatCityMobileAction.do?method=showSublinePatrolRateChart&&width=700&&height=250";
  }
  if (id=="2"){
  	//alert("test");
	//document.getElementById("monthcontrast").src ="${ctx}/ShowRegionMonthlyStat";
	//document.getElementById("monthcontrast").src ="${ctx}/MonthlyStatCityMobileAction.do?method=showContractorPatrolRateChart&&width=700&&height=250";
  }
  if (id=="1"){
	document.getElementById("monthcontrast").src ="${ctx}/MonthlyStatCityMobileAction.do?method=showContractorPatrolRateChart&&width=3700&&height=250";
  }
  if (id=="2"){
	document.getElementById("monthcontrast").src ="${ctx}/MonthlyStatCityMobileAction.do?method=showLineLevelSublinePatrolRateChart&&width=3700&&height=250";
  }
}
//-->
</script>
<div class='title2' style='font-size: 14px; font-weight: 600; bottom: 1'
	align='center'>
	多个代维公司巡检率对比图
</div>
<hr width='100%' size='1'>
<table width='100%'>
	<tr>
		<td valign="top" align="center" width="20%">
			<input type="radio" name="stat" checked onclick="ck('1')" />
			巡检率总体统计分析
			<input type="radio" name="stat" onclick="ck('2')" />
			巡检率按线路级别统计分析
		</td>
	</tr>
	<tr>
		<td>
			<iframe style="color: #EAE9E4" marginWidth="0" marginHeight="0"
				id="monthcontrast"
				src="${ctx}/MonthlyStatCityMobileAction.do?method=showContractorPatrolRateChart&&width=3700&&height=250"
				frameBorder=0 width="100%" scrolling=auto height="350px">
			</iframe>
		</td>
	</tr>
</table>
