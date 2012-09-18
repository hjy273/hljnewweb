<%@ include file="/common/header.jsp"%>
<script type="text/javascript">
<!--
function ck(id){
  document.getElementById("endMonth").style.display="none";
  if (id=="1"){
	document.getElementById("monthcontrast").src ="${ctx}/contractor_year_stat.do?method=showCurrentYearPatrolRateChart&&width=700&&height=250";
  }
  if (id=="2"){
  	document.getElementById("endMonth").style.display="";
  	var endMonth=document.getElementById("endMonth").value;
	document.getElementById("monthcontrast").src ="${ctx}/contractor_year_stat.do?method=showFiveYearPatrolRateChart&&endMonth="+endMonth+"&&width=700&&height=250";
  }
}
//-->
</script>
<div class='title2' style='font-size: 14px; font-weight: 600; bottom: 1'
	align='center'>
	<bean:write name="queryCon" property="conName" />
	公司
	<bean:write name="queryCon" property="endYear" />
	年巡检情况统计分析
</div>
<hr width='100%' size='1'>
<table width='100%'>
	<tr>
		<td valign="top" align="center" width="20%">
			<input type="radio" name="stat" checked onclick="ck('1')" />
			当年巡检率统计分析
			<input type="radio" name="stat" onclick="ck('2')" />
			最近五年
			<select name="endMonth" id="endMonth" style="display: none;"
				onchange="ck('2');">
				<option value="1" selected>
					1月
				</option>
				<c:forEach var="month" begin="2" end="12">
					<option value="${month }">
						${month }月
					</option>
				</c:forEach>
			</select>
			巡检率对比
			<br>
		</td>
	</tr>
	<tr>
		<td width="80%" height="402">
			<iframe id="monthcontrast" style="color: #EAE9E4" marginWidth="0"
				marginHeight="0"
				src="${ctx}/contractor_year_stat.do?method=showCurrentYearPatrolRateChart&&width=700&&height=250"
				frameBorder=0 width="100%" scrolling=auto height="100%">
			</iframe>
		</td>
	</tr>
</table>