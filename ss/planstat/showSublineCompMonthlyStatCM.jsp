<%@ include file="/common/header.jsp"%>
<script type="text/javascript">
<!--
function ck(id){
	document.getElementById("monthcontrast").src ="${ctx}/MonthlyStatCityMobileAction.do?method=showSublinePatrolRateChart&&type="+id+"&&width=3700&&height=250";
}
//-->
</script>
<body style="width: 95%">
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		${CMMonthlyStatConBean.endYear }年${CMMonthlyStatConBean.endMonth
		}月${CMMonthlyStatConBean.regionName }多家代维公司三类线段对比分析图
	</div>
	<hr width='100%' size='1'>
	<table width='100%'>
		<tr>
			<td valign="top" align="center" width="20%">
				<input type="radio" name="stat" onclick="ck('1')" checked />
				各代维公司合格巡检线段比较
				<input type="radio" name="stat" onclick="ck('2')" />
				各代维公司未合格巡检线段比较
				<input type="radio" name="stat" onclick="ck('3')" />
				各代维公司未计划巡检线段比较
			</td>
		</tr>
		<tr>
			<td>
				<iframe style="color: #EAE9E4" marginWidth="0" marginHeight="0"
					id="monthcontrast"
					src="${ctx}/MonthlyStatCityMobileAction.do?method=showSublinePatrolRateChart&&type=1&&width=3700&&height=250"
					frameBorder=0 width="100%" scrolling=auto height="350px">
				</iframe>
			</td>
		</tr>
	</table>
</body>
