<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<br>
<%
  String flagGivenDate = (String)session.getAttribute("flagGivenDate");
%>
<div align="center" width="100%">
  <font size="3"><strong><%=flagGivenDate%>����ִ�������</strong></font>
</div>

<body >
  <display:table name="sessionScope.smhistoryinfogivenday"   id="currentRowObject"  pagesize="18">
     <logic:equal value="11" name="LOGIN_USER" property="type">
	     <logic:equal value="11" name="SMRangeID">
	        <display:column property="rangename" title="����" sortable="true"/>
	     </logic:equal>
	     <logic:notEqual  value="11" name="SMRangeID">
			<display:column property="rangename" title="��ά��˾" sortable="true"/>
	     </logic:notEqual>
     </logic:equal>
     <logic:equal value="12" name="LOGIN_USER" property="type">
          <logic:equal value="12" name="SMRangeID">
             <display:column property="rangename" title="��ά��˾" sortable="true"/>
          </logic:equal>
          <logic:notEqual  value="12" name="SMRangeID">
             <logic:equal value="group" name="PMType">
		       <display:column property="rangename" title="Ѳ��ά����" sortable="true"/>
		     </logic:equal>
		     <logic:notEqual value="group" name="PMType">
		       <display:column property="rangename" title="Ѳ��Ա" sortable="true"/>
		     </logic:notEqual>
             <display:column property="simid" title="SIM����" sortable="true"/>
             <display:column property="avgsendtime" title="���ʱ��" sortable="true" />
	 		 <display:column property="avgsenddistance" title="�������" sortable="true" />
          </logic:notEqual>
     </logic:equal>
     <logic:equal value="22" name="LOGIN_USER" property="type">
     	 <logic:equal value="group" name="PMType">
		    <display:column property="rangename" title="Ѳ��ά����" sortable="true"/>
		 </logic:equal>
         <logic:notEqual value="group" name="PMType">
            <display:column property="rangename" title="Ѳ��Ա" sortable="true"/>
         </logic:notEqual>
        <display:column property="simid" title="SIM����" sortable="true"/>
        <display:column property="avgsendtime" title="���ʱ��" sortable="true" />
        <display:column property="avgsenddistance" title="�������" sortable="true" />
     </logic:equal>
     <display:column property="dailykm" title="Ѳ�����(KM)" sortable="true"/>
	 <display:column property="totalnum" title="����" sortable="true" />
	 <display:column property="patrolnum" title="Ѳ��" sortable="true"/>
	 <display:column property="watchnum" title="����" sortable="true"/>
	 <display:column media="html" title="�ɼ�"  sortable="true">
	     <%
	        BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	        if(object != null)
	        out.println(object.get("collectnum"));
	     %>
     </display:column>
	 <display:column media="html" title="����"  sortable="true">
	     <%
	        BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	        if(object != null)
	        out.println(object.get("troublenum"));
	     %>
     </display:column>
	 <display:column media="html" title="����"  sortable="true">
	     <%
	        BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	        if(object != null)
	        out.println(object.get("othernum"));
	     %>
     </display:column>         
  </display:table>
</body>






