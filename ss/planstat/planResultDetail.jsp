<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<%@page import="com.cabletech.planstat.beans.*"%>
<%@page import="com.cabletech.planstat.domainobjects.PlanStat"%>
<%@page import="com.cabletech.commons.config.*"%>
	<%
	    PlanStat data = (PlanStat) session.getAttribute("PlanStatBeanSession");
	    String executorname = (String) session.getAttribute("executornamesession");
	%>
<body style="width: 95%">
	<table width="100%" border="0" align="center" cellpadding="0"
		cellspacing="0" style="border: 0px;">
		<tr>
			<td height="24" align="center" class="title2" style="border: 0px;"><%="�ƻ�ִ����ϸ��Ϣ��ʾ"%>
			</td>
		</tr>
	</table>
	<table width="90%" border="0" cellspacing="0" cellpadding="0"
		align="center" style="border: 0px;">
		<tr>
			<td align="center" style="border: 0px;">
				<input type="text" class="hiInput"
					value="��������:<bean:write name="PlanStatBeanSession" property="planname"/>"
					readonly>
			</td>
		</tr>
	</table>
	<table width="95%" border="0" align="center" cellpadding="3"
		cellspacing="1" bgcolor="#999999">
		<!-- �ƻ���Ϣ -->
		<tr bgcolor="#FFFFFF">
			<td width="16%" class=trcolor>
				�ƻ�����
			</td>
			<td width="41%" colspan="3">
				<bean:write name="PlanStatBeanSession" property="planname" />
			</td>

		</tr>
		<tr class=trcolor>
			<logic:equal value="group" name="PMType">
				<td bgcolor="#FFFFFF" class=trcolor>
					Ѳ��ά����
				</td>
			</logic:equal>
			<logic:notEqual value="group" name="PMType">
				<td bgcolor="#FFFFFF" class=trcolor>
					Ѳ��Ա
				</td>
			</logic:notEqual>
			<td bgcolor="#FFFFFF" colspan="3"><%=executorname%></td>
		</tr>
		<tr class=trwhite>
			<td class=trcolor bgcolor="#FFFFFF">
				��ʼ����
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="startdate" />
			</td>
			<td class=trcolor bgcolor="#FFFFFF">
				��������
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="enddate" />
			</td>
		</tr>
		<tr class=trcolor>
			<td bgcolor="#FFFFFF" class=trcolor>
				�ƻ�Ѳ���
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="planpointn" />
			</td>
			<td bgcolor="#FFFFFF" class=trcolor>
				ʵ��Ѳ���
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="factpointn" />
			</td>
		</tr>
		<tr class=trcolor>
			<td bgcolor="#FFFFFF" class=trcolor>
				�ƻ�Ѳ����
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="planpoint" />
			</td>
			<td bgcolor="#FFFFFF" class=trcolor>
				ʵ��Ѳ����
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="factpoint" />
			</td>
		</tr>
		<tr class=trcolor>
			<td bgcolor="#FFFFFF" class=trcolor>
				©���
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="leakpointn" />
			</td>
			<td bgcolor="#FFFFFF" class=trcolor>
				Ѳ����
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="patrolp" />
				%
			</td>
		</tr>
		<tr class=trcolor>
			<td bgcolor="#FFFFFF" class=trcolor>
				�ؼ�����
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="keypoint" />
			</td>
			<td bgcolor="#FFFFFF" class=trcolor>
				©��ؼ�����
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="leakkeypoint" />
			</td>
		</tr>
		<tr class=trcolor>
			<td bgcolor="#FFFFFF" class=trcolor>
				Ѳ�������(����)
			</td>
			<td bgcolor="#FFFFFF">
				<bean:write name="PlanStatBeanSession" property="patrolkm" />
			</td>
			<td bgcolor="#FFFFFF" class=trcolor>
				Ѳ�췽ʽ
			</td>
			<td bgcolor="#FFFFFF">
				<bean:define id="temp" name="PlanStatBeanSession" property="patrolmode"></bean:define>
				<%
				    out.print(temp.equals("01") ? "�ֶ�" : "�Զ�");
				%>
			</td>
		</tr>
		<tr class=trcolor>
			<td bgcolor="#FFFFFF" class=trcolor>
				���˽��
			</td>
			<td bgcolor="#FFFFFF" colspan="3">
				<%
				    int i = Integer.parseInt(data.getExamineresult());
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
			</td>
		</tr>
		<%
		    if (true) {
		%>

		<tr class=trcolor>
			<td class=trcolor>
				δ���ԭ��������ɼƻ�������д"�����"��
			</td>
			<td bgcolor="#FFFFFF" colspan="3">
				<bean:write name="PlanStatBeanSession" property="nofinishreason" />
			</td>
		</tr>
		<%
		    }
		%>
	</table>
	&nbsp;&nbsp;
	<html:link action="/planExportAction.do?method=exportPlanstateResult">����ΪExcel�ļ�</html:link>
	<%
	    if (true) {
	%>
	<logic:present name="writeflg">
		<logic:notEmpty name="writeflg">
			<center>
				<input type="button" class="button2"
					onclick='toInputNoFinishReson("<%=data.getPlanid()%>","<%=executorname%>")'
					value="��дδ���ԭ��">
			</center>
		</logic:notEmpty>
	</logic:present>
	<%
	    }
	%>
</body>

