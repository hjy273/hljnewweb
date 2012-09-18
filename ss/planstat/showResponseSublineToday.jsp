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
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=sublineName %>今日截止<%=requestTime %>巡检点列表</div><hr width='100%' size='1'>
<display:table name="sessionScope.sublineToday"   id="currentRowObject"  pagesize="16">
	 <display:column property="patroltime" title="巡检时间" sortable="true"/>
	 <display:column property="pointname" title="巡检点名称" sortable="true"/>
	 <display:column property="addressinfo" title="巡检点位置" sortable="true"/>
	 <display:column property="isfocus" title="是否关键点" sortable="true"/>
</display:table>

<p>
<center>
<input type="button" class="button" onclick="toGetBack()" value="返回" >
</center>
<p>




