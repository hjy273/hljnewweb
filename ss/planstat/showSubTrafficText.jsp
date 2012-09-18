<%@ include file="/common/header.jsp"%>
<%@ page import="com.cabletech.planstat.beans.LogPathBean" %>
<%@ page import="java.util.Map" %>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%
  //String selectedType =(String)session.getAttribute("SelectedType");
  LogPathBean logPathBean =(LogPathBean)session.getAttribute("theTrafficBean");
  //Map regionMap =(Map)application.getAttribute("regionMap");
  
  String mainMenuName = (String)request.getSession().getAttribute("mainMenuName");
  //System.out.println(" regionid "+logPathBean.getRegionID());
  //String regionname = (String) regionMap.get(logPathBean.getRegionID());
 %>

<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=mainMenuName%>下级模块访问情况显示</div><hr width='100%' size='1'>
<display:table name="sessionScope.subTrafficList"   id="currentRowObject"  pagesize="18">
	 <display:column property="lablename" title="模块名称" sortable="true"/>
	 <% 
	 if (!logPathBean.getQueryType().equals("0")){
	 %>
	    <display:column property="visittimes" title="访问次数" sortable="true"/> 
	 <%}else{%>
        <display:column property="mvisittimes" title="移动公司访问次数" sortable="true"/> 
        <display:column property="cvisittimes" title="代维公司访问次数" sortable="true"/> 
     <%}%>
</display:table>