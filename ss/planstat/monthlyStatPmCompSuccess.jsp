<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<br>
<display:table name="sessionScope.sublineInfoList"   id="currentRowObject"  pagesize="16">
     <display:column property="sublinename" title="巡检线段名称" sortable="true"/>
     <display:column property="linetype" title="所属线路类型" sortable="true"/>
	 <display:column property="executetimes" title="计划执行次数" sortable="true"/>
     <display:column property="planpoint" title="计划执行点次" sortable="true"/>
     <display:column property="factpoint" title="实际执行点次" sortable="true"/>
     <display:column property="patrolp" title="巡检率" sortable="true"/>
   <display:column media="html" title="考核结果" sortable="true">
   <%
  		BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  		String examineresult = object.get("examineresult").toString();
  		int i = Integer.parseInt(examineresult);
  		switch(i) {
  			case 1 : out.print("<img src=\""+request.getContextPath()+"/images/1.jpg\" height=\"10\" width=\"50\" />"); break; 
  			case 2 : out.print("<img src=\""+request.getContextPath()+"/images/2.jpg\" height=\"10\" width=\"50\" />"); break;
  			case 3 : out.print("<img src=\""+request.getContextPath()+"/images/3.jpg\" height=\"10\" width=\"50\" />"); break;
  			case 4 : out.print("<img src=\""+request.getContextPath()+"/images/4.jpg\" height=\"10\" width=\"50\" />"); break;
  			case 5 : out.print("<img src=\""+request.getContextPath()+"/images/5.jpg\" height=\"10\" width=\"50\" />"); break;
  			default: out.print("<img src=\""+request.getContextPath()+"/images/0.jpg\" height=\"10\" width=\"50\" />");
		}
  	 %>
  </display:column>
</display:table>