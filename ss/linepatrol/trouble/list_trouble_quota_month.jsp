<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>生成故障指标</title>
		<script type="text/javascript">
			exportList=function(){
			var guideType = document.getElementById("guideTypehidd").value;
			//var month = document.getElementById("monthhidd").value;
			var url="${ctx}/troubleQuotaAction.do?method=exportTimeAreaTroubleQuotaList&guideType="+guideType+"&beginTime=${beginTime}&endTime=${endTime}";
			self.location.replace(url);
		}
		</script>
	</head>

	<body>
		<template:titile value="故障指标列表" />
		<c:if test="${not empty MONTH_TROUBLE_QUOTA_RESULT}">
			<table width="1950px" border="1" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#000" style="border-collapse: collapse"> 
				<input type="hidden" name="guideTypehidd" id="guideTypehidd" value="${guide.guideType}"/>
				<input type="hidden" name="yearhidd" id="yearhidd" value="${year}"/>
				<tr>
					<td colspan="14" align="center">
						<font size="2px" style="font-weight: bold">
							${beginTime}月到${endTime }月
							<c:if test="${guide.guideType=='1'}">
								一干故障指标
							</c:if>
							<c:if test="${guide.guideType=='0'}">
								城域网故障指标
							</c:if>
						</font>
					</td>
					<td colspan="8">&nbsp;</td>
			   </tr>
				<tr  bgcolor="#cccccc" height="30px">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">序号</font></td>
				    <td rowspan="2" width="80px" align="center"><font size="2px" style="font-weight: bold">代维</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">千公里阻断次数(次)</font></td>
				    <td colspan="2" ><font size="2px" style="font-weight: bold">千公里阻断时长(小时)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">光缆每次故障平均历时(小时)</font></td>
				    <c:forEach items="${MONTH_TROUBLE_QUOTA_RESULT.monthList}" var="oneMonthStr" varStatus="loop">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${oneMonthStr }月维护长度(公里)</font></td>
				    </c:forEach>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${guide.interdictionTimeNormValue}小时/千公里</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${guide.interdictionTimesNormValue}次/千公里</font></td>
				    <c:if test="${guide.guideType=='0'}">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">城域网单次抢修历时超出指标值的故障次数</font></td>
				    </c:if>
			   </tr>
			   <tr bgcolor="#cccccc" height="30px">
				    <td align="center" ><font size="2px" style="font-weight: bold">指标</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">故障次数</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">指标</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">故障总时长</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">指标</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">故障平均时长</font></td>
			  </tr>
				<c:forEach items="${MONTH_TROUBLE_QUOTA_RESULT.resultMap}" var="quota" varStatus="loop">
					<bean:define id="quotaData" name="quota" property="value"></bean:define>
					<bean:define id="quotaMonthData" name="quotaData" property="monthMap"></bean:define>
					<tr>
					    <td align="center">${loop.index+1}</td>
					    <td align="center"><bean:write name="quota" property="key"/></td>
					    <td align="center"><bean:write name="quotaData" property="standard_times"/></td>
					    <td align="center"><bean:write name="quotaData" property="trouble_times"/></td>
					    <td align="center"><bean:write name="quotaData" property="standard_time"/></td>
					    <td align="center"><bean:write name="quotaData" property="trouble_time"/></td>
					    <td align="center">${guide.rtrTimeNormValue}</td>
					    <td align="center">
					    	<bean:define id="time" name="quotaData" property="trouble_time"></bean:define>
					    	<bean:define id="times" name="quotaData" property="trouble_times"></bean:define>
					    	<c:if test="${not empty times}">
					    	<c:if test="${times!=0}">
									<fmt:formatNumber value="${time/times}" pattern="#0.00"></fmt:formatNumber>
							</c:if>
					    	<c:if test="${times==0}">
					    		0
					    	</c:if>
					    	</c:if>
					    	<c:if test="${empty times}">
					    	0
					    	</c:if>
					    </td>
					    <c:forEach items="${quotaMonthData}" var="monthData" varStatus="loop">
					    	<logic:empty name="monthData" property="value">
					    	<td align="center">0</td>
					    	</logic:empty>
					    	<logic:notEmpty name="monthData" property="value">
					    	<bean:define id="oneMonthData" name="monthData" property="value"></bean:define>
					    	<td align="center"><bean:write name="oneMonthData" property="maintenance_length"/></td>
					    	</logic:notEmpty>
					    </c:forEach>
					    <td align="center"><bean:write name="quotaData" property="standard_time"/></td>
					    <td align="center"><bean:write name="quotaData" property="standard_times"/></td>
					    <c:if test="${guide.guideType=='0'}">
					    <td align="center"><bean:write name="quotaData" property="city_area_out_standard_number"/></td>
					    </c:if>
				  </tr>
			  </c:forEach>
			  <tr><td colspan="23">	<a href="javascript:exportList()">导出为Excel文件</a></td></tr>
		 </table>
		 <div style="weight:100%" align="center"><input name="goback" class="button" value="返回"	onClick="javascript:history.go(-1)" type="button" /></div>
		 
	 </c:if>
	</body>
</html>
