<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%
   String sublineName = (String)request.getSession().getAttribute("sublineNameRTRequest");
   String requestTime = (String)request.getSession().getAttribute("requestTimeRT");
 %>
<script language="javascript" type="">
function toGetBack(){
   var url = "${ctx}/SublineRealTimeAction.do?method=showRequestInfo";
   self.location.replace(url);
}
</script>
<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=sublineName %>��ֹ����<%=requestTime %>Ѳ������ʾ</div><hr width='100%' size='1'>
<display:table name="sessionScope.requestSublineList"   id="currentRowObject"  pagesize="18">
	 <display:column property="nonpatrolnum" title="δѲ���" href="${ctx}/SublineRealTimeAction.do?method=showRealTimeUnpatrol" paramId="sublineid" paramProperty="sublineid"/>
     <display:column property="planpointnum" title="ӦѲ����"/>
	 <display:column property="actualpointnum" title="��Ѳ����"/>
     <display:column property="todaypointnum" title="����Ѳ���" href="${ctx}/SublineRealTimeAction.do?method=showRealTimeToday" paramId="sublineid" paramProperty="sublineid"/>
	 <display:column media="html" title="�ƻ��Ƿ���">
	  <%
	  		BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	  		String deadlinedays = object.get("deadlinedays").toString();
	  		if (" ".equals(deadlinedays)){
	          %>δ֪
	        <%}else{	
	          %>��<%=deadlinedays %>��
	        <%
	        }	   	
      %>
     </display:column>
</display:table>

<p>
<center>
<input type="button" class="button" onclick="toGetBack()" value="����" >
</center>
<p>