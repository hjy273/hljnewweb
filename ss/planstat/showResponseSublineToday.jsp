<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<%
   String sublineName = (String)request.getSession().getAttribute("sublineNameRTRequest");
   String requestTime = (String)request.getSession().getAttribute("requestTimeRT");
 %>
<script language="javascript" type="">
function toGetBack(){
        window.history.back();
}
</script><br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=sublineName %>���ս�ֹ<%=requestTime %>Ѳ����б�</div><hr width='100%' size='1'>
<display:table name="sessionScope.sublineToday"   id="currentRowObject"  pagesize="16">
	 <display:column property="patroltime" title="Ѳ��ʱ��" sortable="true"/>
	 <display:column property="pointname" title="Ѳ�������" sortable="true"/>
	 <display:column property="addressinfo" title="Ѳ���λ��" sortable="true"/>
	 <display:column property="isfocus" title="�Ƿ�ؼ���" sortable="true"/>
</display:table>

<p>
<center>
<input type="button" class="button" onclick="toGetBack()" value="����" >
</center>
<p>




