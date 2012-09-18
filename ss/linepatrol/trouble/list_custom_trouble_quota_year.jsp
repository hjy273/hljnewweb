<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>自定义故障年指标</title>
		<script type="text/javascript">
		addYearQuota = function (){
			window.location.href="${ctx}/customTroubleQuotaActon.do?method=customYearTroubleQuotaForm";
		};
		editYearQuota = function(id){
			window.location.href="${ctx}/customTroubleQuotaActon.do?method=customYearTroubleQuotaForm&id="+id;
		};
		</script>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
	</head>
	<body>
	<template:titile value="自定义故障年指标" />
	<display:table name="sessionScope.yearQuotaList" id="yearquota" pagesize="15">
			<display:column media="html"  title="年份" >
				<a href="javascript:editYearQuota('${yearquota.id}')">${yearquota.year}</a>
			</display:column>
			<display:column media="html"  title="指标类型" >
				<c:if test="${yearquota.guideType==1}">
				一干故障指标
				</c:if>
				<c:if test="${yearquota.guideType==0}">
				城域网故障指标
				</c:if>
			</display:column>
			<display:column property="maintenanceLength" sortable="true" title="维护公里数" headerClass="subject"/>
			<display:column property="interdictionNormTimes" sortable="true" title="千公里阻断次数基准" headerClass="subject" />
			<display:column property="troubleTimes" sortable="true" title="阻断次数完成值" headerClass="subject" />
			<display:column property="interdictionNormTime" sortable="true" title="千公里阻断时长基准值" headerClass="subject" />
			<display:column property="interdictionTime" sortable="true" title="阻断时长完成值" headerClass="subject" />
			<display:column property="rtrTimeNormValue" sortable="true" title="光缆抢修平均时长基准值" headerClass="subject" />
			<display:column property="rtrTimeFinishValue" sortable="true" title="光缆抢修平均时长完成值" headerClass="subject" />
			
	</display:table>
	<div align="right">
	<input type="button" class="button" onclick="addYearQuota()" value="添加"/>
	</div>
	</body>
</html>