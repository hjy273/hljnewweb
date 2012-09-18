<%@include file="/common/header.jsp"%>
<%@ page import= "com.cabletech.planstat.beans.MonthlyStatCityMobileConBean" %>
<%@ page import= "org.apache.commons.beanutils.DynaBean" %>
<%
	DynaBean MonthlyCMStatDynaBean = (DynaBean) request.getAttribute("month_general_info");
	MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) session.getAttribute("CMMonthlyStatConBean");
%>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>
	全网<%=bean.getEndYear()%>年<%=bean.getEndMonth()%>月份统计总体信息
</div>
<hr width='100%' size='1'>
<br>
<table width="80%" border="0" align="center" cellpadding="3"
	cellspacing="0" class="tabout">
	<tr>
		<th class="thlist" align="center" width="200">
			项目
		</th>
		<th class="thlist" align="center" width="200">
			内容
		</th>
	</tr>
	<template:formTr name="计划点次">
		<%=MonthlyCMStatDynaBean.get("planpoint")%>
	</template:formTr>
	<template:formTr name="实际点次">
		<%=MonthlyCMStatDynaBean.get("factpoint")%>
	</template:formTr>
	<template:formTr name="计划路由长度">
		<%=MonthlyCMStatDynaBean.get("sublinekm")%>
	</template:formTr>
	<template:formTr name="巡回路由长度">
		<%=MonthlyCMStatDynaBean.get("patrolkm")%>
	</template:formTr>
	<template:formTr name="巡检率">
		<%=MonthlyCMStatDynaBean.get("patrolp")%>%
	</template:formTr>
</table>
