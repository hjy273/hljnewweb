<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.beans.*"%>
<script language="javascript" type="">
function preSetTaskList(){
	parent.tasklistspan.innerHTML = preGetSpan.innerHTML;
	parent.loadSpan.innerHTML = "";
	try{
      parent.setPlanName(weekSpan.innerHTML)
  	}
    catch(e){}
}
</script>
<body onload="preSetTaskList()">
<span id="preGetSpan">
<%
  //
  String weekNumber = "";
  if (request.getAttribute("weekNumber") != null) {
    weekNumber = (String) request.getAttribute("weekNumber");
  }
  Vector taskListVct = (Vector) request.getAttribute("taskListVct");
  if (taskListVct.size() > 0) {
    out.println("<table id=\"tasklisttable\" name=\"tasklisttable\" border=\"0\" cellspacing=\"0\">");
	out.println("<tr valign=\"middle\"><td></td></tr>");
    for (int i = 0; i < taskListVct.size(); i++) {
      TaskBean taskB = (TaskBean) taskListVct.get(i);
%>
  <tr valign="middle">
    <!-- td>
      <input type="checkbox" name="taskcheck" value="<%=taskB.getId()%>" checked>
    </td -->
	<td>
		<input type="text" name="linelevel" class="inputtext" style="border:0;background-color:transparent;" value="<%=taskB.getLineleveltext()%>" idValue="<%=taskB.getId()%>" index=<%=i%> readonly />
	</td>
    <td>
      <input type="text" name="taskname" class="inputtext" style="border:0;background-color:transparent;" value="<%=taskB.getDescribtion()%>" idValue="<%=taskB.getId()%>" index=<%=i%>  readonly />
    </td>
    <td>
      <input type="text" name="tasktimes" style="border:0;background-color:transparent;" value="<%=taskB.getExcutetimes()%>" readonly />��
    </td>
  </tr>
<%
  }
      out.println("</table>");
  } else {
%>
  <table id="errMsgTable" name="tasklisttable" border="0" cellspacing="0" style="display:">
    <tr valign="middle">
    <%if (weekNumber.equals("") || weekNumber.length() == 0) {    %>
      <td>��Ϊû�и���ȼƻ�������޷��Զ������������ֶ����䡣</td>
    <%} else {    %>
      <td>��Ϊû�и��¶ȼƻ����߸�ά����Ա�������߶���û������¼ƻ�ָ��������·�������Ӧ���߶Σ�����޷��Զ������������ֶ����䡣</td>
    <%}    %>
    </tr>
  </table>
  <table id="tasklisttable" name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
    <tr valign="middle">
      <td>      </td>
    </tr>
  </table>
<%}%>
</span>
<span id="weekSpan"><%=weekNumber%></span>
</body>
