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
      <input type="text" name="tasktimes" style="border:0;background-color:transparent;" value="<%=taskB.getExcutetimes()%>" readonly />次
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
      <td>因为没有该年度计划，因此无法自动分配任务。请手动分配。</td>
    <%} else {    %>
      <td>因为没有该月度计划或者该维护人员所负责线段中没有与该月计划指定任务线路级别相对应的线段，因此无法自动分配任务。请手动分配。</td>
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
