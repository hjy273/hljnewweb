<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<html>
<template:titile value="��������Ϣ"/>
<script language="javascript">

function loadForm(idValue){
	var url = "${ctx}/watchAction.do?method=loadWatchAsObj&id=" + idValue;
	self.location.replace(url);
}

function rowQuery(idValue){
	var url = "${ctx}/watchexeAction.do?method=queryExecuteInfo&id=" + idValue;
	self.location.replace(url);
}
function rowDelete(idValue){
	if(confirm("ȷ�����Ըõ㣿")){
        var url = "${ctx}/watchAction.do?method=deleteTempWatch&id=" + idValue;
        self.location.replace(url);
	}
}

function toEdit(idValue){
	var url = "${ctx}/watchAction.do?method=loadWatch&id=" + idValue;
	self.location.replace(url);
}

function toAdd(idValue){
	var url = "${ctx}/watchAction.do?method=getTempWatch&id=" + idValue;
	self.location.replace(url);
}

function toUploadPic(idValue){
	var url = "${ctx}/WatchPicAction.do?method=queryUploadPic&watchid=" + idValue;
	self.location.replace(url);
}



</script>
<style type="text/css">
.subject{text-align:center;}
</style>
<body>
<display:table name="sessionScope.querytempResult" requestURI="${ctx}/WatchExecuteQueryAction.do?method=queryAllTempWatchPoint" id="querytempResult" pagesize="18">

  <display:column property="x" title="����" sortable="true"/>
  <display:column property="y" title="γ��" sortable="true"/>
  <display:column property="regionname" title="��������" sortable="true"/>
  <display:column property="simid" title="SIM����" sortable="true"/>
  <display:column property="patrolname" title="Ѳ��ά����" sortable="true"/>
  <display:column property="receivetime" title="�ɼ�ʱ��" sortable="true"/>
 

 
  <display:column media="html" title="����" headerClass="subject" class="subject">  	
  	<%
    	BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("querytempResult");
    	String bedited = "";
    	String rowid = (String) object.get("gpscoordinate");
    	bedited = (String) object.get("bedited");
    	if(bedited.equals("���ƶ�")){
    %>
    -- 
	<%}else{%>
		<apptag:checkpower thirdmould="40103" ishead="0">
    		<a href="javascript:toAdd('<%=rowid%>')">�ƶ�</a> |
    	</apptag:checkpower>
    	<apptag:checkpower thirdmould="40104" ishead="0">
    	 	<a href="javascript:rowDelete('<%=rowid%>')">����</a>
    	</apptag:checkpower>
    <%}%> 
  </display:column>


</display:table>
<html:link action="/watchAction.do?method=exportTempWatchResult">����ΪExcel�ļ�</html:link>
</body></html>
