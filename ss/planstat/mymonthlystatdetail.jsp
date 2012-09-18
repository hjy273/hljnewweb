<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />

<br>
<template:titile value="本月计划详细列表"/>
<br>
<display:table name="sessionScope.monthlystatdetail"   id="currentRowObject"  pagesize="18">
  <display:column property="planname" title="计划名称" maxLength="21" />
  <display:column property="startdate" title="开始时间"/>
  <display:column property="enddate" title="结束时间"/>
  <display:column property="planpoint" title="应巡检点次"/>
  <display:column property="factpoint" title="实际巡检点次"/>
  <display:column property="patrolp" title="巡检率"/>
  <display:column media="html" title="考核结果">
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

