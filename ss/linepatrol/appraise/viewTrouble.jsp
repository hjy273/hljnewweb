<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
			<table width="1950px" border="1" id="datatable" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#000" style="border-collapse: collapse"> 
				<input type="hidden" name="guideTypehidd" id="guideTypehidd" value="${normGuide.guideType}"/>
				<input type="hidden" name="yearhidd" id="yearhidd" value="${year}"/>
				<tr>
					<td colspan="12" align="center">
						<font size="2px" style="font-weight: bold">
							${year}年
							<c:if test="${normGuide.guideType=='1'}">
								一干故障指标
							</c:if>
							<c:if test="${normGuide.guideType=='0'}">
								城域网故障指标
							</c:if>
						</font>
					</td>
			   </tr>
				<tr  bgcolor="#cccccc" height="30px">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">序号</font></td>
				    <td rowspan="2" align="center"><font size="2px" style="font-weight: bold">代维</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">全年千公里阻断次数模拟值(次)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">全年千公里阻断时长模拟值(小时)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">千公里阻断次数(次)</font></td>
				    <td colspan="2" ><font size="2px" style="font-weight: bold">千公里阻断时长(小时)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">光缆每次故障平均历时(小时)</font></td>
				     <td rowspan="2"><font size="2px" style="font-weight: bold">${normGuide.interdictionTimeNormValue}小时/千公里</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${normGuide.interdictionTimesNormValue}次/千公里</font></td>
				    <c:if test="${normGuide.guideType=='0'}">
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
			  <c:forEach items="${yearquotaNorm.resultMap}" var="quota" varStatus="loop">
					<bean:define id="quotaData" name="quota" property="value"></bean:define>
					<bean:define id="quotaMonthData" name="quotaData" property="monthMap"></bean:define>
					<c:if test="${quota.key==contractorName}">
					<tr>
					    <td align="center">${loop.index+1}</td>
					    <td align="center"><bean:write name="quota" property="key"/></td>
					    <td align="center"><bean:write name="quotaData" property="whole_year_standard_times"/></td>
					    <td align="center"><bean:write name="quotaData" property="whole_year_standard_time"/></td>
					    <td align="center"><bean:write name="quotaData" property="standard_times"/></td>
					    <td align="center"><bean:write name="quotaData" property="trouble_times"/></td>
					    <td align="center"><bean:write name="quotaData" property="standard_time"/></td>
					    <td align="center"><bean:write name="quotaData" property="trouble_time"/></td>
					    <td align="center">${normGuide.rtrTimeNormValue}</td>
					    <td align="center">
					    	<bean:define id="time" name="quotaData" property="trouble_time"></bean:define>
					    	<bean:define id="times" name="quotaData" property="trouble_times"></bean:define>
					    	<c:if test="${not empty times}">
					    	<c:if test="${times!=0}">
					    		<fmt:formatNumber value="${time/times}" pattern="#0.00"/>
					    		
					    	</c:if>
					    	<c:if test="${times==0}">
					    		0
					    	</c:if>
					    	</c:if>
					    	<c:if test="${empty times}">
					    	0
					    	</c:if>
					    </td>
					    <td align="center"><bean:write name="quotaData" property="standard_time"/></td>
					    <td align="center"><bean:write name="quotaData" property="standard_times"/></td>
					    <c:if test="${normGuide.guideType=='0'}">
					    <td align="center"><bean:write name="quotaData" property="city_area_out_standard_number"/></td>
					    </c:if>
					    </tr>
					    </c:if>
					    </c:forEach>
				<tr  bgcolor="#cccccc" height="30px">    
				    <td rowspan="1"><font size="2px" style="font-weight: bold">1月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">2月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">3月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">4月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">5月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">6月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">7月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">8月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">9月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">10月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">11月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">12月维护长度(公里)</font></td>
				   
			   </tr>
			   
				<c:forEach items="${yearquotaNorm.resultMap}" var="quota" varStatus="loop">
					<bean:define id="quotaData" name="quota" property="value"></bean:define>
					<bean:define id="quotaMonthData" name="quotaData" property="monthMap"></bean:define>
					<c:if test="${quota.key==contractorName}">
					<tr>
					    <c:forEach items="${quotaMonthData}" var="monthData" varStatus="loop">
					    	<logic:empty name="monthData" property="value">
					    	<td align="center">0</td>
					    	</logic:empty>
					    	<logic:notEmpty name="monthData" property="value">
					    	<bean:define id="oneMonthData" name="monthData" property="value"></bean:define>
					    	<td align="center"><bean:write name="oneMonthData" property="maintenance_length"/></td>
					    	</logic:notEmpty>
					    </c:forEach>
				  </tr>
				  </c:if>
			  </c:forEach>
		 </table>
		 <table width="1950px" border="1" id="datatable" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#000" style="border-collapse: collapse"> 
				<input type="hidden" name="guideTypehidd" id="guideTypehidd" value="${mainGuide.guideType}"/>
				<input type="hidden" name="yearhidd" id="yearhidd" value="${year}"/>
				<tr>
					<td colspan="13" align="center">
						<font size="2px" style="font-weight: bold">
							${year}年
							<c:if test="${mainGuide.guideType=='1'}">
								一干故障指标
							</c:if>
							<c:if test="${mainGuide.guideType=='0'}">
								城域网故障指标
							</c:if>
						</font>
					</td>
			   </tr>
				<tr  bgcolor="#cccccc" height="30px">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">序号</font></td>
				    <td rowspan="2" align="center"><font size="2px" style="font-weight: bold">代维</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">全年千公里阻断次数模拟值(次)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">全年千公里阻断时长模拟值(小时)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">千公里阻断次数(次)</font></td>
				    <td colspan="2" ><font size="2px" style="font-weight: bold">千公里阻断时长(小时)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">光缆每次故障平均历时(小时)</font></td>
				    
				    
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${mainGuide.interdictionTimeNormValue}小时/千公里</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${mainGuide.interdictionTimesNormValue}次/千公里</font></td>
				    <c:if test="${mainGuide.guideType=='0'}">
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
			  <c:forEach items="${yearquotaMain.resultMap}" var="quota" varStatus="loop">
					<bean:define id="quotaData" name="quota" property="value"></bean:define>
					<bean:define id="quotaMonthData" name="quotaData" property="monthMap"></bean:define>
					<c:if test="${quota.key==contractorName}">
					<tr>
					    <td align="center">${loop.index+1}</td>
					    <td align="center"><bean:write name="quota" property="key"/></td>
					    <td align="center"><bean:write name="quotaData" property="whole_year_standard_times"/></td>
					    <td align="center"><bean:write name="quotaData" property="whole_year_standard_time"/></td>
					    <td align="center"><bean:write name="quotaData" property="standard_times"/></td>
					    <td align="center"><bean:write name="quotaData" property="trouble_times"/></td>
					    <td align="center"><bean:write name="quotaData" property="standard_time"/></td>
					    <td align="center"><bean:write name="quotaData" property="trouble_time"/></td>
					    <td align="center">${mainGuide.rtrTimeNormValue}</td>
					    <td align="center">
					    	<bean:define id="time" name="quotaData" property="trouble_time"></bean:define>
					    	<bean:define id="times" name="quotaData" property="trouble_times"></bean:define>
					    	<c:if test="${not empty times}">
					    	<c:if test="${times!=0}">
					    		<fmt:formatNumber value="${time/times}" pattern="#0.00"/>
					    		
					    	</c:if>
					    	<c:if test="${times==0}">
					    		0
					    	</c:if>
					    	</c:if>
					    	<c:if test="${empty times}">
					    	0
					    	</c:if>
					    </td>
					    <td align="center"><bean:write name="quotaData" property="standard_time"/></td>
					    <td align="center"><bean:write name="quotaData" property="standard_times"/></td>
					    <c:if test="${mainGuide.guideType=='0'}">
					    <td align="center"><bean:write name="quotaData" property="city_area_out_standard_number"/></td>
					    </c:if>
					    </tr>
					    </c:if>
					    </c:forEach>
			   <tr bgcolor="#cccccc" height="30px">
				    <td rowspan="1"><font size="2px" style="font-weight: bold">1月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">2月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">3月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">4月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">5月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">6月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">7月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">8月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">9月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">10月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">11月维护长度(公里)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">12月维护长度(公里)</font></td>
				    
			   </tr>
				<c:forEach items="${yearquotaMain.resultMap}" var="quota" varStatus="loop">
					<bean:define id="quotaData" name="quota" property="value"></bean:define>
					<bean:define id="quotaMonthData" name="quotaData" property="monthMap"></bean:define>
					<c:if test="${quota.key==contractorName}">
					<tr>
					    					    <c:forEach items="${quotaMonthData}" var="monthData" varStatus="loop">
					    	<logic:empty name="monthData" property="value">
					    	<td align="center">0</td>
					    	</logic:empty>
					    	<logic:notEmpty name="monthData" property="value">
					    	<bean:define id="oneMonthData" name="monthData" property="value"></bean:define>
					    	<td align="center"><bean:write name="oneMonthData" property="maintenance_length"/></td>
					    	</logic:notEmpty>
					    </c:forEach>
				  </tr>
				  </c:if>
			  </c:forEach>
		 </table>
	</body>
</html>
