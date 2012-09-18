<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>生成故障指标</title>
		<script type="text/javascript">
			function checkAddInfo() {
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
		<template:titile value="年故障指标查询" />
		<html:form action="/troubleQuotaAction.do?method=listTroubleQuotaYear"
			styleId="statTroubleInfo" onsubmit="return checkAddInfo()">
			<table width="50%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdulleft" width="20%">
						代维公司：
					</td>
					<td class="tdulright">
						<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
							<select style="width:150px;" name="contractorId" class="inputtext">
								<option value="">全部</option>
								<c:forEach items="${contractorList}" var="contractor">
									<option value="<bean:write name="contractor" property="contractorid" /> "><bean:write name="contractor" property="contractorname" /></option>
								</c:forEach>
						</c:if>
						<c:if test="${sessionScope.LOGIN_USER.deptype=='2'}">
							<input type="hidden" value="${sessionScope.LOGIN_USER.deptID }" name="contractorId"/>
							<c:out value="${sessionScope.LOGIN_USER.deptName}"></c:out>
						</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdulleft" width="20%">
						统计指标：
					</td>
					<td class="tdulright">
						<select name="guideType" id="guideType" style="width: 155px">
							<option value="1">
								一干故障指标
							</option>
							<option value="0">
								城域网故障指标
							</option>
						</select>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdulleft" width="20%">
						查询时间：
					</td>
					<td class="tdulright">
        <input id="begin" type="text" name="beginTime" readonly="readonly" class="inputtext" style="width:75px" onfocus="WdatePicker({dateFmt:'yyyy'})"/>
        -
        <input id="end" type="text" name="endTime" readonly="readonly" class="inputtext" style="width:75px" onfocus="WdatePicker({dateFmt:'yyyy'})"/>
					</td>
				</tr>
				<tr class=trwhite>
					<td colspan="6" style="text-align: center;">
						<input name="queryType" value="month" type="hidden" /> 
						<input name="action" class="button" value="查询" type="submit" />
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
