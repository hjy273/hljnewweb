<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<logic:notEmpty name="guide" >
<logic:equal value="1" name="guideType">
<!-- del stop by xueyh 20110415 for 客户新需求：只需显示千公里阻断时长(小时)指标、故障总时长(小时)、维护长度（公里）；样式为表格

-->
<table border="0" width="99%" cellspacing="0" cellpadding="0" class="tab" > 
	<tr ><td colspan="2">维护长度（公里）</td>
		<td>${trouble_quota_result["sumResultMap"]["maintenance_length"] }</td>
	</tr>
	<tr>
		<td rowspan="3"><p>千公里阻断时长</p>
		<p>(小时)
		(月<img src="${ctx}/images/tubiao.png" height="12px" width="20px" style="cursor: pointer;" onclick="openwinYG();">) 
		(年<img src="${ctx}/images/tubiao.png" height="12px" width="20px" style="cursor: pointer;" onclick="openwinYiganFiveYear();">)</p></td>
		<td>指标基准值</td>
		<td>${year_trouble_quota_result["sumResultMap"]["standard_time"] }</td>
	</tr>
	<tr><td>指标挑战值</td>
		<td>${year_trouble_quota_result["sumResultMap"]["dare_time"] }</td>
	</tr>
	   <!-- 故障总时长(小时) -->
	<tr ><td>指标完成值</td>
		<td>${year_trouble_quota_result["sumResultMap"]["trouble_time"] }</td>
	</tr>
	
</table>
</logic:equal>
<logic:equal value="0" name="guideType">
<!-- 
千公里阻断次数(次)指标基准值：${trouble_quota_result["sumResultMap"]["standard_times"] }
千公里阻断次数(次)指标挑战值：${trouble_quota_result["sumResultMap"]["dare_times"] }
千公里阻断时长(小时)指标基准值：${trouble_quota_result["sumResultMap"]["standard_time"] } 
千公里阻断时长(小时)指标挑战值：${trouble_quota_result["sumResultMap"]["dare_time"] } 
光缆每次故障平均历时(小时)指标基准值：${trouble_quota_result["sumResultMap"]["rtrTimeNormValue"] } 
光缆每次故障平均历时(小时)指标挑战值：${trouble_quota_result["sumResultMap"]["rtrTimeDareValue"] } 
故障总时长(小时)：${trouble_quota_result["sumResultMap"]["trouble_time"] } 
故障次数：${trouble_quota_result["sumResultMap"]["trouble_times"] } 
故障平均抢修时长(小时)：${trouble_quota_result["sumResultMap"]["avg_time"] } 
故障抢修超时次数：${trouble_quota_result["sumResultMap"]["city_area_out_standard_number"] } 
维护长度（公里）：${trouble_quota_result["sumResultMap"]["maintenance_length"] } 
-->
<table border="0" width="99%" cellspacing="0" cellpadding="0" class="tab"> 
	<tr>
		<td rowspan="3">
		<p>千公里阻断次数</p>
		<p>(次)
		(月<img src="${ctx}/images/tubiao.png" height="12px" width="20px" style="cursor: pointer;" onclick="openwinCity('21');">)
		(年<img src="${ctx}/images/tubiao.png" height="12px" width="20px" style="cursor: pointer;" onclick="openwinCityYear('21');">)</p>
		</td>
		<td>指标基准值</td>
		<td>${year_trouble_quota_result["sumResultMap"]["standard_times"] } </td>
	</tr>
	<tr><td>指标挑战值</td>
		<td>${year_trouble_quota_result["sumResultMap"]["dare_times"] } </td>
	</tr>
	<tr ><td >指标完成值</td><!-- 故障次数 -->
		<td>${year_trouble_quota_result["sumResultMap"]["trouble_times"] } </td>
	</tr>
	<tr>
		<td rowspan="3"><p>千公里阻断时长</p><p>(小时)
		(月<img src="${ctx}/images/tubiao.png" height="12px" width="20px" style="cursor: pointer;" onclick="openwinCity('22');">)
		(年<img src="${ctx}/images/tubiao.png" height="12px" width="20px" style="cursor: pointer;" onclick="openwinCityYear('22');">)</p>
		</p>
		</td>
		<td>指标基准值</td>
		<td>${year_trouble_quota_result["sumResultMap"]["standard_time"] }</td>
	</tr>
	<tr><td>指标挑战值</td>
		<td>${year_trouble_quota_result["sumResultMap"]["dare_time"] } </td>
	</tr>
	<!-- <p>故障总时长</p><p>(小时)</p> -->	   
	<tr ><td >指标完成值</td>
		<td>${year_trouble_quota_result["sumResultMap"]["trouble_time"] } </td>
	</tr>
	<tr>
		<td rowspan="2"><p>光缆抢修平均历时</p><p>(小时)
		(月<img src="${ctx}/images/tubiao.png" height="12px" width="20px" style="cursor: pointer;" onclick="openwinCity('23');">)
		(年<img src="${ctx}/images/tubiao.png" height="12px" width="20px" style="cursor: pointer;" onclick="openwinCityYear('23');">)</p>
		</p>
		</td>
		<td>指标基准值</td>
		<td>${guide.rtrTimeNormValue}</td>
	</tr>
	

	<!-- 故障平均抢修时长(小时) -->
	<tr ><td colspan="1">指标完成值</td>
		<td>${year_trouble_quota_result["sumResultMap"]["avg_time"] }</td>
	</tr>

	<tr ><td colspan="2">维护长度（公里）</td>
		<td>${trouble_quota_result["sumResultMap"]["maintenance_length"] }</td>
	</tr>
</table>
</logic:equal>
</logic:notEmpty>

