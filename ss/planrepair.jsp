<%@include file="/common/header.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.cabletech.commons.hb.*" %>

<html>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<body>
	<div>
		<form method='post' action="${ctx}/planrepair.jsp">
			��ѯ<input type="text" name="day" size='2'>��ǰ��������ļƻ�������
			<input type="submit" value="��ѯ���������������">
		</form>
	</div>
	<%
		String day = request.getParameter("day");
		if(day != null && !"".equals(day)){
			List planresult = QueryBug(day);
			session.setAttribute("planresult",planresult);
		}
		if(null != session.getAttribute("planresult")){
	%>

	<display:table name="sessionScope.planresult" id="resultList">
  		<display:column property="planid"  title="�ƻ�ID"/>
  		<display:column property="planname" title="�ƻ�����"/>
  		<display:column property="begindate" title="��ʼ����"/> 
  		<display:column property="taskid" title="������ID"/>
  	</display:table>
  	<div>
  		<input type="button" onclick="repair()" value="ִ���޲�">
	</div>
	<%} %>
</body>
<script type="text/javascript">
	function repair(){
		var url = "${ctx}/executerepair.jsp";
        self.location.replace(url);
	}
</script>
</html>
<%!
	private List QueryBug(String day) throws SQLException {
		String querybug = "select p.id planid,p.planname planname,to_char(p.begindate,'yy-mm-dd') begindate,t.id taskid from plan p ,plantasklist pl,taskinfo t where p.id=pl.planid and pl.taskid =t.id and t.id not in (select ID from plantasksubline pt,taskinfo t where t.id = pt.taskid) and p.begindate > (sysdate-"
				+ day + ") order by begindate";
		QueryUtil query;
		try {
			query = new QueryUtil();
			List planresult = query.queryBeans(querybug);
			return planresult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

%>