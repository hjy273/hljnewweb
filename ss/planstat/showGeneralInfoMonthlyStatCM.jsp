<%@include file="/common/header.jsp"%>
<%@ page
	import="com.cabletech.planstat.beans.MonthlyStatCityMobileConBean"%>
<%@ page import="org.apache.commons.beanutils.BasicDynaBean"%>
<%
    BasicDynaBean MonthlyCMStatDynaBean = (BasicDynaBean) session
            .getAttribute("cmmonthlystatgeneralinfo");
    MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) session
            .getAttribute("CMMonthlyStatConBean");
%>
<div class='title2' style='font-size: 14px; font-weight: 600; bottom: 1'
	align='center'>
	<%=bean.getRegionName()%>移动公司<%=bean.getEndYear()%>年<%=bean.getEndMonth()%>月份统计总体信息
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
	<template:formTr name="隐患总数">
		<%=MonthlyCMStatDynaBean.get("trouble")%>
	</template:formTr>
	<template:formTr name="关键点">
		<%=MonthlyCMStatDynaBean.get("keypoint")%>
	</template:formTr>
	<template:formTr name="漏检关键点">
		<%=MonthlyCMStatDynaBean.get("leakkeypoint")%>
	</template:formTr>
	<template:formTr name="漏检点次">
		<%=MonthlyCMStatDynaBean.get("leakpoint")%>
	</template:formTr>
	<template:formTr name="巡检总里程">
		<%=MonthlyCMStatDynaBean.get("patrolkm")%>
	</template:formTr>
	<template:formTr name="巡检率">
		<%=MonthlyCMStatDynaBean.get("overallpatrolp")%>%
	</template:formTr>
	<template:formTr name="考核结果">
		<%
		    String examineresult = MonthlyCMStatDynaBean.get("examineresult").toString();
		    int i = Integer.parseInt(examineresult);
		    switch (i) {
		    case 1:
		        out.print("<img src=\"" + request.getContextPath()
		                + "/images/1.jpg\" height=\"10\" width=\"50\" />");
		        break;
		    case 2:
		        out.print("<img src=\"" + request.getContextPath()
		                + "/images/2.jpg\" height=\"10\" width=\"50\" />");
		        break;
		    case 3:
		        out.print("<img src=\"" + request.getContextPath()
		                + "/images/3.jpg\" height=\"10\" width=\"50\" />");
		        break;
		    case 4:
		        out.print("<img src=\"" + request.getContextPath()
		                + "/images/4.jpg\" height=\"10\" width=\"50\" />");
		        break;
		    case 5:
		        out.print("<img src=\"" + request.getContextPath()
		                + "/images/5.jpg\" height=\"10\" width=\"50\" />");
		        break;
		    default:
		        out.print("<img src=\"" + request.getContextPath()
		                + "/images/0.jpg\" height=\"10\" width=\"50\" />");
		    }
		%>
	</template:formTr>
</table>
