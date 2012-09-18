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
							${year}��
							<c:if test="${normGuide.guideType=='1'}">
								һ�ɹ���ָ��
							</c:if>
							<c:if test="${normGuide.guideType=='0'}">
								����������ָ��
							</c:if>
						</font>
					</td>
			   </tr>
				<tr  bgcolor="#cccccc" height="30px">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">���</font></td>
				    <td rowspan="2" align="center"><font size="2px" style="font-weight: bold">��ά</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">ȫ��ǧ������ϴ���ģ��ֵ(��)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">ȫ��ǧ�������ʱ��ģ��ֵ(Сʱ)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">ǧ������ϴ���(��)</font></td>
				    <td colspan="2" ><font size="2px" style="font-weight: bold">ǧ�������ʱ��(Сʱ)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">����ÿ�ι���ƽ����ʱ(Сʱ)</font></td>
				     <td rowspan="2"><font size="2px" style="font-weight: bold">${normGuide.interdictionTimeNormValue}Сʱ/ǧ����</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${normGuide.interdictionTimesNormValue}��/ǧ����</font></td>
				    <c:if test="${normGuide.guideType=='0'}">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">����������������ʱ����ָ��ֵ�Ĺ��ϴ���</font></td>
				    </c:if>
				</tr>
				
			  <tr bgcolor="#cccccc" height="30px">
				    <td align="center" ><font size="2px" style="font-weight: bold">ָ��</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">���ϴ���</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">ָ��</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">������ʱ��</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">ָ��</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">����ƽ��ʱ��</font></td>
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
				    <td rowspan="1"><font size="2px" style="font-weight: bold">1��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">2��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">3��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">4��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">5��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">6��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">7��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">8��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">9��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">10��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">11��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">12��ά������(����)</font></td>
				   
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
							${year}��
							<c:if test="${mainGuide.guideType=='1'}">
								һ�ɹ���ָ��
							</c:if>
							<c:if test="${mainGuide.guideType=='0'}">
								����������ָ��
							</c:if>
						</font>
					</td>
			   </tr>
				<tr  bgcolor="#cccccc" height="30px">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">���</font></td>
				    <td rowspan="2" align="center"><font size="2px" style="font-weight: bold">��ά</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">ȫ��ǧ������ϴ���ģ��ֵ(��)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">ȫ��ǧ�������ʱ��ģ��ֵ(Сʱ)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">ǧ������ϴ���(��)</font></td>
				    <td colspan="2" ><font size="2px" style="font-weight: bold">ǧ�������ʱ��(Сʱ)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">����ÿ�ι���ƽ����ʱ(Сʱ)</font></td>
				    
				    
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${mainGuide.interdictionTimeNormValue}Сʱ/ǧ����</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${mainGuide.interdictionTimesNormValue}��/ǧ����</font></td>
				    <c:if test="${mainGuide.guideType=='0'}">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">����������������ʱ����ָ��ֵ�Ĺ��ϴ���</font></td>
				    </c:if>
			   </tr>
			    <tr bgcolor="#cccccc" height="30px">
				    <td align="center" ><font size="2px" style="font-weight: bold">ָ��</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">���ϴ���</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">ָ��</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">������ʱ��</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">ָ��</font></td>
				    <td align="center"><font size="2px" style="font-weight: bold">����ƽ��ʱ��</font></td>
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
				    <td rowspan="1"><font size="2px" style="font-weight: bold">1��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">2��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">3��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">4��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">5��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">6��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">7��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">8��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">9��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">10��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">11��ά������(����)</font></td>
				    <td rowspan="1"><font size="2px" style="font-weight: bold">12��ά������(����)</font></td>
				    
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
