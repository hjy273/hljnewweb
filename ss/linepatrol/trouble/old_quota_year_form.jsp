<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���ɹ���ָ��</title>
		<script type="text/javascript">
			function checkAddInfo() {
				var year = document.getElementById("year").value;
				if(year==""){
					alert("ͳ����ݲ���Ϊ��!");
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
		<template:titile value="ͳ�������ָ��" />
		<html:form action="/troubleQuotaAction.do?method=statYearQuota"
			styleId="statTroubleInfo" onsubmit="return checkAddInfo()">
			<table width="50%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			      <tr  class=trwhite>
				    <td class="tdulleft" width="20%">ͳ��ָ�꣺</td>
				    <td class="tdulright">
				     <select name="guideType" id="guideType" style="width:155px">
				     	<option value="1">һ�ɹ���ָ��</option>
				     	<option value="0">����������ָ��</option>
				     </select>
				    </td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" width="20%">ͳ����ݣ�</td>
				    <td class="tdulright"><input name="year" id="year" class="Wdate" style="width:150px"
					onfocus="WdatePicker({dateFmt:'yyyy',maxDate:'%y'})" readonly/>
					</td>
				  </tr>
			     <tr class=trwhite>
					<td colspan="6" style="text-align:center;">
						<input name="action" class="button" value="���ɱ���"	 type="submit" />
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
							${year}��
							<c:if test="${guide.guideType=='1'}">
								һ�ɹ���ָ��
							</c:if>
							<c:if test="${guide.guideType=='0'}">
								����������ָ��
							</c:if>
						</font>
					</td>
					<td colspan="8">&nbsp;</td>
			   </tr>
				<tr  bgcolor="#cccccc" height="30px">
				    <td rowspan="2"><font size="2px" style="font-weight: bold">���</font></td>
				    <td rowspan="2" width="80px" align="center"><font size="2px" style="font-weight: bold">��ά</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">ȫ��ǧ������ϴ���ģ��ֵ(��)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">ȫ��ǧ�������ʱ��ģ��ֵ(Сʱ)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">ǧ������ϴ���(��)</font></td>
				    <td colspan="2" ><font size="2px" style="font-weight: bold">ǧ�������ʱ��(Сʱ)</font></td>
				    <td colspan="2"><font size="2px" style="font-weight: bold">����ÿ�ι���ƽ����ʱ(Сʱ)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">1��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">2��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">3��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">4��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">5��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">6��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">7��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">8��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">9��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">10��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">11��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">12��ά������(����)</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${guide.interdictionTimeNormValue}Сʱ/ǧ����</font></td>
				    <td rowspan="2"><font size="2px" style="font-weight: bold">${guide.interdictionTimesNormValue}��/ǧ����</font></td>
				    <c:if test="${guide.guideType=='0'}">
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
			  <tr><td colspan="23">	<a href="javascript:exportList()">����ΪExcel�ļ�</a></td></tr>
		 </table>
	 </c:if>
	</body>
</html>
