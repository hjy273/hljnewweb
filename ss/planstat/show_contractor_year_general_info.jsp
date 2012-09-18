<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.beans.*"%>
<%@page import="com.cabletech.planinfo.domainobjects.*"%>
<%@page import="com.cabletech.planstat.beans.*"%>
<%
    YearMonthPlanBean planbean = (YearMonthPlanBean) request.getSession().getAttribute(
            "YearMonthPlanBean");
    Vector taskVct = (Vector) request.getSession().getAttribute("tasklist");
    int taskRowSpanL = taskVct.size();
%>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td height="24" align="center" class="title2">
			<bean:write name="queryCon" property="conName" />
			��˾
			<bean:write name="queryCon" property="endYear" />
			��ƻ���Ϣ
		</td>
	</tr>
</table>
<hr width='100%' size='1'>
<table width="95%" border="0" align="center" cellpadding="3"
	cellspacing="1" bgcolor="#999999">
	<!-- �ƻ���Ϣ -->
	<tr bgcolor="#FFFFFF">
		<td width="11%" class=trcolor rowspan="<%=8 + taskRowSpanL%>"
			align="center">
			�ƻ���Ϣ
		</td>
		<td width="14%" class=trcolor>
			�ƻ�����
		</td>
		<td><%=planbean.getPlanname()%></td>
	</tr>
	<tr class=trcolor>
		<td bgcolor="#FFFFFF" class=trcolor>
			�ƻ�ʱ��
		</td>
		<td bgcolor="#FFFFFF">
			<%=planbean.getYear()%>
			��
		</td>
	</tr>
	<%
	    for (int i = 0; i < taskRowSpanL; i++) {
	        TaskBean task = (TaskBean) taskVct.get(i);
	%>
	<tr class=trwhite>
		<%
		    if (i == 0) {
		%>
		<td class=trcolor rowspan="<%=taskRowSpanL%>" bgcolor="#FFFFFF">
			����
		</td>
		<%
		    }
		%>
		<td bgcolor="#FFFFFF"><%=task.getLineleveltext()%>
			&nbsp;&nbsp;&nbsp;<%=task.getDescribtion()%>
			&nbsp;&nbsp;&nbsp;
			<%=task.getExcutetimes()%>
			��
		</td>
	</tr>
	<%
	    }
	%>
	<tr class=trwhite>
		<td class=trcolor bgcolor="#FFFFFF">
			�� �� ��
		</td>
		<td bgcolor="#FFFFFF"><%=planbean.getCreator()%></td>
	</tr>
	<tr class=trwhite>
		<td class=trcolor bgcolor="#FFFFFF">
			�ƶ�����
		</td>
		<td bgcolor="#FFFFFF">
			<%=planbean.getCreatedate().substring(0, planbean.getCreatedate().indexOf(" "))%>
		</td>
	</tr>
	<tr class=trwhite>
		<td class=trcolor bgcolor="#FFFFFF">
			�ƶ�����
		</td>
		<td bgcolor="#FFFFFF"><%=planbean.getStatus()%></td>
	</tr>
	<tr class=trwhite>
		<td bgcolor="#FFFFFF" class=trcolor>
			�� �� ��
		</td>
		<td bgcolor="#FFFFFF"><%=planbean.getApprover()%></td>
	</tr>
	<tr class=trwhite>
		<td bgcolor="#FFFFFF" class=trcolor>
			��������
		</td>
		<td bgcolor="#FFFFFF"><%=planbean.getApprovedate()%></td>
	</tr>

	<tr bgcolor="#FFFFFF" class=trwhite>
		<td rowspan="2" align="center" class=trcolor>
			�ƶ���˾
			<br>
			�쵼��ʾ
		</td>
		<%
		    if (planbean.getApprovecontent() != null) {
		%>
		<td height="63" colspan="2" bgcolor="#FFFFFF"><%=planbean.getApprovecontent()%></td>
		<%
		    } else {
		%>
		<td height="63" colspan="2" bgcolor="#FFFFFF">
		</td>

		<%
		    }
		%>
	</tr>

</table>
<br>
<br>
<br>
<br>
</body>
