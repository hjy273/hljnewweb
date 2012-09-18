<%@ include file="/common/header.jsp"%>
<script type="text/javascript">
<!--
function ck(id){
  document.getElementById("endMonth").style.display="none";
  document.getElementById("timeSpan").style.display="none";
  if (id=="1"){
	document.getElementById("monthcontrast").src ="${ctx}/whole_net_stat.do?method=showLineLevelDifferentMonthPatrolRateChart&&width=700&&height=250";
  }
  if (id=="2"){
  	document.getElementById("endMonth").style.display="";
  	var endMonth=document.getElementById("endMonth").value;
	document.getElementById("monthcontrast").src ="${ctx}/whole_net_stat.do?method=showLineLevelFiveYearPatrolRateChart&&endMonth="+endMonth+"&&width=700&&height=250";
  }
  if (id=="3"){
  	document.getElementById("timeSpan").style.display="";
  }
}
function generate(){
	var beginTime=document.getElementById("begin").value;
	var endTime=document.getElementById("end").value;
	if(beginTime!=""&&endTime!=""){
		document.getElementById("monthcontrast").src ="${ctx}/whole_net_stat.do?method=showLineLevelInputTimeAreaPatrolRateChart&&beginTime="+beginTime+"&&endTime="+endTime+"&&width=700&&height=250";
  	}
}
//-->
</script>
<div class='title2' style='font-size: 14px; font-weight: 600; bottom: 1'
	align='center'>
	<bean:write name="CMMonthlyStatConBean" property="endYear" />
	年巡检情况统计分析
</div>
<hr width='100%' size='1'>
<table width='100%'>
	<tr>
		<td valign="top" align="center" width="20%">
			<input type="radio" name="stat" checked onclick="ck('1')" />
			当年各线路级别巡检率统计分析
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
			各线路级别巡检率对比
			<input type="radio" name="stat" onclick="ck('3')" />
			按时间段各线路级别巡检率对比
			<span id="timeSpan" style="display: none;"> <input id="begin"
					type="text" readonly="readonly" name="beginTime" class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy/MM',minDate:'2000/1'})"
					style="width: 50px" /> <input id="end" type="text" name="endTime"
					readonly="readonly" class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy/MM',minDate:'2000/1'})"
					style="width: 50px" /> <input name="btn" value="对比" type="button"
					onclick="generate();" /> </span>
			<br>
		</td>
	</tr>
	<tr>
		<td width="80%" height="402">
			<iframe id="monthcontrast" style="color: #EAE9E4" marginWidth="0"
				marginHeight="0"
				src="${ctx}/whole_net_stat.do?method=showLineLevelDifferentMonthPatrolRateChart&&width=700&&height=250"
				frameBorder=0 width="100%" scrolling=auto height="100%">
			</iframe>
		</td>
	</tr>
</table>