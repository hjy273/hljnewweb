<%@include file="/common/header.jsp"%>
<%@ page import= "com.cabletech.planstat.beans.MonthlyStatCityMobileConBean" %>
<%@ page import= "org.apache.commons.beanutils.DynaBean" %>
<%
	DynaBean MonthlyCMStatDynaBean = (DynaBean) request.getAttribute("month_general_info");
	MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) session.getAttribute("CMMonthlyStatConBean");
%>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>
	ȫ��<%=bean.getEndYear()%>��<%=bean.getEndMonth()%>�·�ͳ��������Ϣ
</div>
<hr width='100%' size='1'>
<br>
<table width="80%" border="0" align="center" cellpadding="3"
	cellspacing="0" class="tabout">
	<tr>
		<th class="thlist" align="center" width="200">
			��Ŀ
		</th>
		<th class="thlist" align="center" width="200">
			����
		</th>
	</tr>
	<template:formTr name="�ƻ����">
		<%=MonthlyCMStatDynaBean.get("planpoint")%>
	</template:formTr>
	<template:formTr name="ʵ�ʵ��">
		<%=MonthlyCMStatDynaBean.get("factpoint")%>
	</template:formTr>
	<template:formTr name="�ƻ�·�ɳ���">
		<%=MonthlyCMStatDynaBean.get("sublinekm")%>
	</template:formTr>
	<template:formTr name="Ѳ��·�ɳ���">
		<%=MonthlyCMStatDynaBean.get("patrolkm")%>
	</template:formTr>
	<template:formTr name="Ѳ����">
		<%=MonthlyCMStatDynaBean.get("patrolp")%>%
	</template:formTr>
</table>
