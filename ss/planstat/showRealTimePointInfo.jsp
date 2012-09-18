<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<%
    String sublineName = (String)request.getAttribute("RealiTimeSublineName");
%>
<script language="javascript" type="">
function toGetBack(){
  
   var url = "${ctx}/planstat/showSublineForPointConfirm.jsp";
   self.location.replace(url);
}

function toGetForm(idValue){
   var url = "${ctx}/PointConfirmAction.do?method=checkPointPatroled&pointid=" + idValue;
   self.location.replace(url);
}

</script><br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=sublineName%>所有应巡检点列表</div><hr width='100%' size='1'>
<display:table name="sessionScope.requestSublinePoint"   id="currentRowObject"  pagesize="16">
	 <display:column property="pointname" title="巡检点名称" sortable="true"/>
	 <display:column property="addressinfo" title="巡检点位置" sortable="true"/>
	 <display:column property="isfocus" title="是否关键点" sortable="true"/>
	 <display:column media="html" title="该点是否被巡">
	  <%
	  		BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	  		String pointid = object.get("pointid").toString();
	  %>
      <a href="javascript:toGetForm('<%=pointid%>')">查看</a>
     </display:column>
</display:table>

<p>
<center>
<input type="button" class="button" onclick="toGetBack()" value="返回" >
</center>
<p>




