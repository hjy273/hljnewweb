<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<br>
<%
  String flagGivenDate = (String)session.getAttribute("flagGivenDate");
%>
<div align="center" width="100%">
  <font size="3"><strong><%=flagGivenDate%>任务执行情况表</strong></font>
</div>

<body >
  <display:table name="sessionScope.smhistoryinfogivenday"   id="currentRowObject"  pagesize="18">
     <logic:equal value="11" name="LOGIN_USER" property="type">
	     <logic:equal value="11" name="SMRangeID">
	        <display:column property="rangename" title="区域" sortable="true"/>
	     </logic:equal>
	     <logic:notEqual  value="11" name="SMRangeID">
			<display:column property="rangename" title="代维公司" sortable="true"/>
	     </logic:notEqual>
     </logic:equal>
     <logic:equal value="12" name="LOGIN_USER" property="type">
          <logic:equal value="12" name="SMRangeID">
             <display:column property="rangename" title="代维公司" sortable="true"/>
          </logic:equal>
          <logic:notEqual  value="12" name="SMRangeID">
             <logic:equal value="group" name="PMType">
		       <display:column property="rangename" title="巡检维护组" sortable="true"/>
		     </logic:equal>
		     <logic:notEqual value="group" name="PMType">
		       <display:column property="rangename" title="巡检员" sortable="true"/>
		     </logic:notEqual>
             <display:column property="simid" title="SIM卡号" sortable="true"/>
             <display:column property="avgsendtime" title="间隔时间" sortable="true" />
	 		 <display:column property="avgsenddistance" title="间隔距离" sortable="true" />
          </logic:notEqual>
     </logic:equal>
     <logic:equal value="22" name="LOGIN_USER" property="type">
     	 <logic:equal value="group" name="PMType">
		    <display:column property="rangename" title="巡检维护组" sortable="true"/>
		 </logic:equal>
         <logic:notEqual value="group" name="PMType">
            <display:column property="rangename" title="巡检员" sortable="true"/>
         </logic:notEqual>
        <display:column property="simid" title="SIM卡号" sortable="true"/>
        <display:column property="avgsendtime" title="间隔时间" sortable="true" />
        <display:column property="avgsenddistance" title="间隔距离" sortable="true" />
     </logic:equal>
     <display:column property="dailykm" title="巡检里程(KM)" sortable="true"/>
	 <display:column property="totalnum" title="总数" sortable="true" />
	 <display:column property="patrolnum" title="巡检" sortable="true"/>
	 <display:column property="watchnum" title="盯防" sortable="true"/>
	 <display:column media="html" title="采集"  sortable="true">
	     <%
	        BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	        if(object != null)
	        out.println(object.get("collectnum"));
	     %>
     </display:column>
	 <display:column media="html" title="隐患"  sortable="true">
	     <%
	        BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	        if(object != null)
	        out.println(object.get("troublenum"));
	     %>
     </display:column>
	 <display:column media="html" title="其它"  sortable="true">
	     <%
	        BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	        if(object != null)
	        out.println(object.get("othernum"));
	     %>
     </display:column>         
  </display:table>
</body>






