<%@ page contentType="text/html; charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.cabletech.commons.hb.*" %>
<%@page import="org.apache.commons.beanutils.BasicDynaBean" %>
<%
	List list = (List)session.getAttribute("planresult");
	int size  = list.size();
	StringBuffer myout = new StringBuffer();
	for(int i=0;i<size;i++){
		BasicDynaBean bean = (BasicDynaBean)list.get(i);
		String taskid = bean.get("taskid").toString();
		String sql = "select sublinename,sublineid from sublineinfo where sublineid in (  select pt.sublineid  from subtaskinfo tp,pointinfo pt,taskinfo st where st.id=tp.taskid and tp.objectid=pt.pointid  and st.id='"+taskid+"' group by pt.sublineid )";
		QueryUtil query = null;
		List sublinelist = null;
		try {
			query = new QueryUtil();
			sublinelist = query.queryBeans(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		myout.append("<br>子任务："+taskid+"<br>");
		myout.append("    线段：");
		UpdateUtil up = new UpdateUtil();
		for(int j =0 ;j<sublinelist.size();j++){
			String sublineid = ((BasicDynaBean)sublinelist.get(j)).get("sublineid").toString();
			myout.append(sublineid+";");
			up.setAutoCommitFalse();
			up.executeUpdate("insert into plantasksubline (tsblid,sublineid,taskid) values(SEQ_PLANTASKSUBLINE_ID.nextval,'"+sublineid+"','"+taskid+"')");
			up.commit();
		}
	}
	//chax xianduan
	//select sublinename,sublineid from sublineinfo where sublineid in (  select pt.sublineid  from subtaskinfo tp,pointinfo pt,taskinfo st where st.id=tp.taskid and tp.objectid=pt.pointid  and st.id='00000000000000051062' group by pt.sublineid )
	//执行插入
	out.println(myout.toString());
%>