<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<%@page import="com.cabletech.planstat.beans.*"%>
<%@page import="com.cabletech.planstat.domainobjects.PlanStat"%>
<%@page import="com.cabletech.commons.config.*"%>
<body style="width: 95%">
	<%
	    BasicDynaBean MonthlyConStatDynaBean = (BasicDynaBean) session
	            .getAttribute("monthlyconstatDynaBeanSession");
	    String conname = (String) session.getAttribute("connameSession");
	%>
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'><%=conname%>公司
		<bean:write name="queryCon" property="endYear" />
		年
		<bean:write name="queryCon" property="endMonth" />
		月份统计详细信息
	</div>
	<hr width='100%' size='1'>
	<br>
	<table width="80%" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				市代维公司名称：
			</td>
			<td class="tdulright" style="width:85%" colspan="3"><%=conname%></td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				年月份：
			</td>
			<td class="tdulright" style="width:85%" colspan="3"><%=MonthlyConStatDynaBean.get("statdate")%></td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				计划点次：
			</td>
			<td class="tdulright" style="width: 35%"><%=MonthlyConStatDynaBean.get("planpoint")%></td>
			<td class="tdulleft" style="width: 15%">
				巡检点次：
			</td>
			<td class="tdulright" style="width: 35%"><%=MonthlyConStatDynaBean.get("factpoint")%></td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				漏检点次：
			</td>
			<td class="tdulright" style="width:35%"><%=MonthlyConStatDynaBean.get("leakpoint")%></td>
			<td class="tdulleft" style="width:15%">
				巡检率：
			</td>
			<td class="tdulright" style="width:35%"><%=MonthlyConStatDynaBean.get("patrolp")%>%
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				关键点：
			</td>
			<td class="tdulright" style="width:35%"><%=MonthlyConStatDynaBean.get("keypoint")%></td>
			<td class="tdulleft" style="width:15%">
				漏检关键点：
			</td>
			<td class="tdulright" style="width:35%"><%=MonthlyConStatDynaBean.get("keypoint")%></td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				计划路由长度：
			</td>
			<td class="tdulright" style="width:35%"><%=MonthlyConStatDynaBean.get("sublinekm")%>公里</td>
			<td class="tdulleft" style="width:15%">
				巡回路由长度：
			</td>
			<td class="tdulright" style="width: 35%"><%=MonthlyConStatDynaBean.get("patrolkm")%>公里</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				考核结果：
			</td>
			<td class="tdulright" style="width:85%" colspan="3">
				<%
				    String examineresult = (String) MonthlyConStatDynaBean.get("examineresult");
				    int i = Integer.parseInt(examineresult);

				    String path = (String) request.getSession().getServletContext().getAttribute("ctx");
				    switch (i) {
				    case 1:
				        out.print("<img src=\"" + path + "/images/1.jpg\"/>");
				        break;
				    case 2:
				        out.print("<img src=\"" + path + "/images/2.jpg\"/>");
				        break;
				    case 3:
				        out.print("<img src=\"" + path + "/images/3.jpg\"/>");
				        break;
				    case 4:
				        out.print("<img src=\"" + path + "/images/4.jpg\"/>");
				        break;
				    case 5:
				        out.print("<img src=\"" + path + "/images/5.jpg\"/>");
				        break;
				    default:
				        out.print("<img src=\"" + path + "/images/0.jpg\"/>");
				    }
				%>
			</td>
		</tr>
	</table>
</body>

