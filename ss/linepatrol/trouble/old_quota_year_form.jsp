<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>生成故障指标</title>
		<script type="text/javascript">
			function checkAddInfo() {
				var year = document.getElementById("year").value;
				if(year==""){
					alert("统计年份不能为空!");
					return false;
				}
				return true;
			}
			
			exportList=function(){
			var guideType = document.getElementById("guideTypehidd").value;
			var year = document.getElementById("yearhidd").value;
			var url="${ctx}/troubleQuotaAction.do?method=exportYearTroubleQuotaList&guideType="+guideType+"&year="+year;
			self.location.replace(url);
		}
		</script>
	</head>

	<body>
		<template:titile value="统计年故障指标" />
		<html:form action="/troubleQuotaAction.do?method=statYearQuota"
			styleId="statTroubleInfo" onsubmit="return checkAddInfo()">
			<table width="50%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			      <tr  class=trwhite>
				    <td class="tdulleft" width="20%">统计指标：</td>
				    <td class="tdulright">
				     <select name="guideType" id="guideType" style="width:155px">
				     	<option value="1">一干故障指标</option>
				     	<option value="0">城域网故障指标</option>
				     </select>
				    </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" width="20%">统计年份：</td>
				    <td class="tdulright"><input name="year" id="year" class="Wdate" style="width:150px"
					onfocus="WdatePicker({dateFmt:'yyyy',maxDate:'%y'})" readonly/>
					</td>
				  </tr>
			     <tr class=trwhite>
					<td colspan="6" style="text-align:center;">
						<input name="action" class="button" value="生成报表"	 type="submit" />
					</td>
				 </tr>
			</table>
		</html:form>
		<c:if test="${not empty yearquota}">
			<table width="1950px" border="1" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#000" style="border-collapse: collapse"> 
				<input type="hidden" name="guideTypehidd" id="guideTypehidd" value="${guide.guideType}"/>
				<input type="hidden" name="yearhidd" id="yearhidd" value="${year}"/>
				<tr>
					<td colspan="14" align="center">
						<font size="2px" style="font-weight: bold">
							${year}年
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
				    <td rowspan="2"><font size="2px" style="font-weight: bold">全年千公里阻断次数模拟值(次)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">全年千公里阻断时长模拟值(小时)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">千公里阻断次数(次)</font></td>
				    <td colspan="2" ><font size="2px" style="font-weight: bold">千公里阻断时长(小时)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">光缆每次故障平均历时(小时)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">1月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">2月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">3月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">4月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">5月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">6月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">7月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">8月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">9月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">10月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">11月维护长度(公里)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">12月维护长度(公里)</font></td>
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
				<c:forEach items="${yearquota}" var="quota" varStatus="loop">
					<tr>
					    <td align="center">${loop.index+1}</td>
					    <td align="center"><bean:write name="quota" property="contractorname"/></td>
					    <td align="center"><bean:write name="quota" property="whole_year_standard_times"/></td>
					    <td align="center"><bean:write name="quota" property="whole_year_standard_time"/></td>
					    <td align="center"><bean:write name="quota" property="standard_times"/></td>
					    <td align="center"><bean:write name="quota" property="trouble_times"/></td>
					    <td align="center"><bean:write name="quota" property="standard_time"/></td>
					    <td align="center"><bean:write name="quota" property="interdiction_time"/></td>
					    <td align="center">${guide.rtrTimeNormValue}</td>
					    <td align="center">
					    	<bean:define id="time" name="quota" property="interdiction_time"></bean:define>
					    	<bean:define id="times" name="quota" property="trouble_times"></bean:define>
					    	<c:if test="${times!=0}">
					    		${time/times}
					    	</c:if>
					    	<c:if test="${times==0}">
					    		0
					    	</c:if>
					    </td>
					    <td align="center"><bean:write name="quota" property="jan"/></td>
					    <td align="center"><bean:write name="quota" property="feb"/></td>
					    <td align="center"><bean:write name="quota" property="mar"/></td>
					    <td align="center"><bean:write name="quota" property="apr"/></td>
					    <td align="center"><bean:write name="quota" property="may"/></td>
					    <td align="center"><bean:write name="quota" property="june"/></td>
					    <td align="center"><bean:write name="quota" property="july"/></td>
					    <td align="center"><bean:write name="quota" property="aug"/></td>
					    <td align="center"><bean:write name="quota" property="sep"/></td>
					    <td align="center"><bean:write name="quota" property="oct"/></td>
					    <td align="center"><bean:write name="quota" property="nov"/></td>
					    <td align="center"><bean:write name="quota" property="dece"/></td>
					    <td align="center"><bean:write name="quota" property="standard_time"/></td>
					    <td align="center"><bean:write name="quota" property="standard_times"/></td>
					    <c:if test="${guide.guideType=='0'}">
					    <td align="center"><bean:write name="quota" property="city_area_out_standard_number"/></td>
					    </c:if>
				  </tr>
			  </c:forEach>
			  <tr><td colspan="23">	<a href="javascript:exportList()">导出为Excel文件</a></td></tr>
		 </table>
	 </c:if>
	</body>
</html>
