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
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'><%=conname%>��˾
		<bean:write name="queryCon" property="endYear" />
		��
		<bean:write name="queryCon" property="endMonth" />
		�·�ͳ����ϸ��Ϣ
	</div>
	<hr width='100%' size='1'>
	<br>
	<table width="80%" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				�д�ά��˾���ƣ�
			</td>
			<td class="tdulright" style="width:85%" colspan="3"><%=conname%></td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				���·ݣ�
			</td>
			<td class="tdulright" style="width:85%" colspan="3"><%=MonthlyConStatDynaBean.get("statdate")%></td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				�ƻ���Σ�
			</td>
			<td class="tdulright" style="width: 35%"><%=MonthlyConStatDynaBean.get("planpoint")%></td>
			<td class="tdulleft" style="width: 15%">
				Ѳ���Σ�
			</td>
			<td class="tdulright" style="width: 35%"><%=MonthlyConStatDynaBean.get("factpoint")%></td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				©���Σ�
			</td>
			<td class="tdulright" style="width:35%"><%=MonthlyConStatDynaBean.get("leakpoint")%></td>
			<td class="tdulleft" style="width:15%">
				Ѳ���ʣ�
			</td>
			<td class="tdulright" style="width:35%"><%=MonthlyConStatDynaBean.get("patrolp")%>%
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				�ؼ��㣺
			</td>
			<td class="tdulright" style="width:35%"><%=MonthlyConStatDynaBean.get("keypoint")%></td>
			<td class="tdulleft" style="width:15%">
				©��ؼ��㣺
			</td>
			<td class="tdulright" style="width:35%"><%=MonthlyConStatDynaBean.get("keypoint")%></td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				�ƻ�·�ɳ��ȣ�
			</td>
			<td class="tdulright" style="width:35%"><%=MonthlyConStatDynaBean.get("sublinekm")%>����</td>
			<td class="tdulleft" style="width:15%">
				Ѳ��·�ɳ��ȣ�
			</td>
			<td class="tdulright" style="width: 35%"><%=MonthlyConStatDynaBean.get("patrolkm")%>����</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:15%">
				���˽����
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

