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
	<%=bean.getRegionName()%>�ƶ���˾<%=bean.getEndYear()%>��<%=bean.getEndMonth()%>�·�ͳ��������Ϣ
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
	<template:formTr name="��������">
		<%=MonthlyCMStatDynaBean.get("trouble")%>
	</template:formTr>
	<template:formTr name="�ؼ���">
		<%=MonthlyCMStatDynaBean.get("keypoint")%>
	</template:formTr>
	<template:formTr name="©��ؼ���">
		<%=MonthlyCMStatDynaBean.get("leakkeypoint")%>
	</template:formTr>
	<template:formTr name="©����">
		<%=MonthlyCMStatDynaBean.get("leakpoint")%>
	</template:formTr>
	<template:formTr name="Ѳ�������">
		<%=MonthlyCMStatDynaBean.get("patrolkm")%>
	</template:formTr>
	<template:formTr name="Ѳ����">
		<%=MonthlyCMStatDynaBean.get("overallpatrolp")%>%
	</template:formTr>
	<template:formTr name="���˽��">
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
