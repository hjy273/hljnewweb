<%@ include file="/common/header.jsp"%>
<%@ page import = "com.cabletech.baseinfo.beans.*" %>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%
	UseTerminalBean useTerminal = (UseTerminalBean)session.getAttribute("query");
 %>
<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=useTerminal.getYear() %>��<%=useTerminal.getMonth() %>�·��豸ʹ�����</div><hr width='100%' size='1'>
<br>
<display:table name="sessionScope.useterminal"   id="currentRowObject"  pagesize="18">
     <display:column property="simid" title="sim��" sortable="true"/>
     <display:column property="regionname" title="����" />
	 <display:column property="contractorname" maxLength="8" title="��ά��˾" />
	  <display:column property="patrolname" maxLength="4" title="Ѳ����" />
	  <display:column property="totalnum" title="��������" />
	  <display:column property="monthlykm" title="Ѳ�����KM"/> 
	 <display:column property="onlinedays" title="��������"/> 
	 <display:column property="patrolnum" title="Ѳ��" />
	 <display:column property="watchnum" title="����"  />
	 <display:column property="collectnum" title="�ɼ�" /> 
	 <display:column property="troublenum" title="����"  /> 
	 <display:column property="othernum" title="����"/> 
</display:table>
<%
	String section = (String)session.getAttribute("section");
	if(section != null && !"".equals(section)){
%>
	<div align="center"><INPUT type="button" value="����" name="����" onclick="javascript:self.location.replace('${ctx}/baseinfo/useTerminalChart.jsp');"/></div>
<%
	}
%>