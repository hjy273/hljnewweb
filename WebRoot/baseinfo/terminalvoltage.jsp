<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script type="text/javascript">
<!--

//-->
</script>
<div class='title2' style='font-size:12px; font-weight:600;bottom:1'>设备电压查询结果</div><hr width='100%' size='1'>
<display:table name="sessionScope.lowVoltage" pagesize="18"
	id="currentRowObject">
	<display:column property="deviceid" title="设备编号" />
	<display:column property="simnumber" title="SIM卡号" />
	<display:column property="queryman" title="巡检人" />
	<display:column property="querytime" title="查询时间" />
	<display:column property="respvoltage" title="设备电压" />
	<display:column property="resptime" title="返回时间" />
</display:table>