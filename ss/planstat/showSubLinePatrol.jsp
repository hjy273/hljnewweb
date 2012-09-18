<%@include file="/common/header.jsp"%>
<div class='title2' style='font-size: 14px; font-weight: 600; bottom: 1'
	align='center'>
	<bean:write name="queryCon" property="conName" />
	公司
	<bean:write name="queryCon" property="endYear" />
	年
	<bean:write name="queryCon" property="endMonth" />
	月份巡检线段考核结果
</div>
<hr width='100%' size='1'>
<script type="text/javascript">
<!--
function ck(id){
  if (id=="1"){
	document.getElementById("monthsubline").src ="${ctx}/PlanMonthlyStatAction.do?method=showSubLinePatrol&type=pass";
  }
  if (id=="2"){
	document.getElementById("monthsubline").src ="${ctx}/PlanMonthlyStatAction.do?method=showSubLinePatrol&type=nonpass";
  }
  if (id=="3"){
	document.getElementById("monthsubline").src ="${ctx}/PlanMonthlyStatAction.do?method=showSubLinePatrol&type=nonplan";
  }
}
//-->
</script>
<table width='100%'>
	<tr>
		<td width="20%"></td>
		<td width="20%">
			<input type="radio" name="subline" checked onclick="ck('1')" />
			<span style='font-size: 12px; font-weight: 600; bottom: 1'>合格巡检线段</span>
		</td>
		<td width="20%">
			<input type="radio" name="subline" onclick="ck('2')" />
			<span style='font-size: 12px; font-weight: 600; bottom: 1'>未合格巡检线段</span>
		</td>
		<td width="20%">
			<input type="radio" name="subline" onclick="ck('3')" />
			<span style='font-size: 12px; font-weight: 600; bottom: 1'>未计划巡检线段</span>
		</td>
		<td width="20%"></td>
	</tr>
</table>
<iframe id="monthsubline" style="color: #EAE9E4" marginWidth="0"
	marginHeight="0"
	src="${ctx}/PlanMonthlyStatAction.do?method=showSubLinePatrol&type=pass"
	frameBorder=0 width="100%" scrolling=auto height="450px">
</iframe>
