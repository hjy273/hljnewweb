<%@ include file="/common/header.jsp"%>
<script type="text/javascript">
<!--
function ck(id){
  if (id=="1"){
	//document.getElementById("monthcontrast").src ="${ctx}/ShowConMonthlyStat";
	document.getElementById("monthcontrast").src ="${ctx}/PlanMonthlyStatAction.do?method=showPatrolManPatrolRateChart&&width=700&&height=250";
  }
  if (id=="2"){
  	//alert("test");
	//document.getElementById("monthcontrast").src ="${ctx}/ShowRegionMonthlyStat";
	document.getElementById("monthcontrast").src ="${ctx}/PlanMonthlyStatAction.do?method=showLineLevelPatrolRateChart&&width=700&&height=250";
  }
}
//-->
</script>
<body style="width: 95%">
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		<bean:write name="queryCon" property="conName" />
		公司
		<bean:write name="queryCon" property="endYear" />
		年
		<bean:write name="queryCon" property="endMonth" />
		月巡检情况统计分析
	</div>
	<hr width='100%' size='1'>
	<table width='100%' >
		<tr>
			<td valign="top" align="center" width="20%">
				<input type="radio" name="stat" checked onclick="ck('1')" />
				各巡检组巡检率比较
				<input type="radio" name="stat" onclick="ck('2')" />
				各线路级别巡检率比较
			</td>
		</tr>
		<tr>
			<td width="80%" height="402">
				<iframe id="monthcontrast" style="color: #EAE9E4" marginWidth="0"
					marginHeight="0"
					src="${ctx}/PlanMonthlyStatAction.do?method=showPatrolManPatrolRateChart&&width=700&&height=250"
					frameBorder=0 width="100%" scrolling=auto height="100%">
				</iframe>
			</td>
		</tr>
	</table>
</body>