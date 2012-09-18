<%@ include file="/common/header.jsp"%>
<script type="text/javascript">
	function ck(id) {
		if (id == "1") {
			//document.getElementById("monthcontrast").src ="${ctx}/ShowConMonthlyStat";
			//document.getElementById("monthcontrast").src ="${ctx}/MonthlyStatCityMobileAction.do?method=showSublinePatrolRateChart&&width=700&&height=250";
		}
		if (id == "2") {
			//alert("test");
			//document.getElementById("monthcontrast").src ="${ctx}/ShowRegionMonthlyStat";
			//document.getElementById("monthcontrast").src ="${ctx}/MonthlyStatCityMobileAction.do?method=showContractorPatrolRateChart&&width=700&&height=250";
		}
		if (id == "1") {
			//document.getElementById("monthcontrast").src ="${ctx}/city_mobile_year_stat.do?method=showYearContractorPatrolRateChart&&width=700&&height=250";
		}
		if (id == "2") {
			//document.getElementById("monthcontrast").src ="${ctx}/city_mobile_year_stat.do?method=showYearContractorLineLevelPatrolRateChart&&width=700&&height=250";
		}
	}
	//-->
</script>
<div class='title2' style='font-size: 14px; font-weight: 600; bottom: 1'
	align='center'>
	全网${CMMonthlyStatConBean.endYear }年 ${CMMonthlyStatConBean.endMonth}月各线路级别巡检率对比图
</div>
<hr width='100%' size='1'>
<table width='100%'>
	<tr>
		<td valign="top" align="center" width="20%">
		</td>
	</tr>
	<tr>
		<td>
			<iframe style="color: #EAE9E4" marginWidth="0" marginHeight="0"
				id="monthcontrast"
				src="${ctx}/whole_net_stat.do?method=showLineLevelMonthPatrolRateChart&&width=700&&height=250"
				frameBorder=0 width="100%" scrolling=auto height="250px">
			</iframe>
		</td>
	</tr>
</table>
