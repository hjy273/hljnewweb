<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<html>
<template:titile value="��ѯ���������ֳ���Ϣ���"/>
<script language="javascript" type="">

function loadForm(idValue){
	var url = "${ctx}/watchAction.do?method=loadWatchAsObj&id=" + idValue;
	self.location.replace(url);
}

function loadSta(idValue){
	var url = "${ctx}/WatchExeStaAction.do?method=getWatchPointSta&id=" + idValue;
	self.location.replace(url);
}


function rowQuery(idValue){
	var url = "${ctx}/watchexeAction.do?method=queryExecuteInfo&id=" + idValue;
	self.location.replace(url);
}
function rowDelete(idValue){
	if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/watchAction.do?method=deleteWatch&id=" + idValue;
        self.location.replace(url);
	}
}

function toEdit(idValue){
	var url = "${ctx}/watchAction.do?method=loadWatch&id=" + idValue;
	self.location.replace(url);
}

function toUploadPic(idValue){
	var url = "${ctx}/WatchPicAction.do?method=queryLinkPic&watchid=" + idValue;
	self.location.replace(url);
}

function toViewUploadPic(idValue){
	var url = "${ctx}/WatchPicAction.do?method=viewLinkPicEx&watchid=" + idValue;
	self.location.replace(url);
}

function show1(){
  alert("��ʩ����Ϣ��û��ͳ�Ʊ���")
}
function show2(){
  alert("����ɶ����������޸ģ�")
}
function show3(){
  alert("����ɶ���������ɾ����")
}
function show4(){
  alert("δ��ɺ˲飬û��ͳ�Ʊ���")
}
</script>
<style type="text/css">
.subject{text-align:center;}
</style>
<body>
<display:table name="sessionScope.queryResult" requestURI="${ctx}/watchAction.do?method=queryWatch" id="resultList" pagesize="18">
  <display:column media="html" title="��������">
    <%
    BasicDynaBean objecta = (BasicDynaBean) pageContext.findAttribute("resultList");
    String rowida = "";
    String placename = "";
    String placenameTitle = "";
   	if(objecta != null){
   		rowida = (String) objecta.get("placeid");
    	placenameTitle = (String)objecta.get("placename");
    	 if(placenameTitle != null && placenameTitle.length() > 10) {
    		placename = placenameTitle.substring(0,10) + "...";
    	 } else {
    		placename = placenameTitle;
    	 }
   	}
    
    %>
    <a href="javascript:loadForm('<%=rowida%>')" title="<%=placenameTitle %>"><%=placename %></a>
    </display:column>
  <display:column property="contractorname" title="��ά��λ" sortable="true"/>
  <display:column property="innerregion" title="��������" sortable="true"/>
  <logic:equal value="group" name="PMType">
  	<display:column property="patrolname" title="Ѳ��ά����" sortable="true"/>
  </logic:equal>
  <logic:notEqual value="group" name="PMType">
  	<display:column property="patrolname" title="������" sortable="true"/>
  </logic:notEqual>
  <display:column property="placetype" title="��������" sortable="true"/>
  <display:column property="watchplace" title="����λ��" sortable="true"/>  
  <display:column property="begindate" title="��ʼ����" sortable="true"/>
  <display:column property="enddate" title="��ֹ����" sortable="true"/>
  <display:column media="html" title="����" headerClass="subject" >
  	 <%
    	BasicDynaBean object3 = (BasicDynaBean) pageContext.findAttribute("resultList");
    	String rowid3 = (String) object3.get("placeid");
    
      if(object3.get("dealstatus").equals("2")){

      %>
      <a href="javascript:loadSta('<%=rowid3%>')">ͳ�Ʊ���</a>

      <%}
      if(object3.get("dealstatus").equals("0")){%>
      <a href="javascript:show1()">ͳ�Ʊ���</a>
      <%}
      if(object3.get("dealstatus").equals("1"))%>
      <a href="javascript:show4()">ͳ�Ʊ���</a>
  	<apptag:checkpower thirdmould="40205" ishead="0">
    	<%if(!object3.get("dealstatus").equals("2")){%>
            | <a href="javascript:toEdit('<%=rowid3%>')">�޸�</a>
        <% }%>
   </apptag:checkpower>
   
   <logic:notEqual value="11" name="LOGIN_USER" property="type">
  	 | <a href="javascript:toViewUploadPic('<%=rowid3%>')">����</a>
   </logic:notEqual>  
   
   <apptag:checkpower thirdmould="40206" ishead="0">    
   	<%if(!object3.get("dealstatus").equals("2")){ %>
     | <a href="javascript:rowDelete('<%=rowid3%>')">ɾ��</a>
    <%}%>
    </apptag:checkpower>
  </display:column>
  <input type="hidden" name="webgis" value="web">
</display:table>
<html:link action="/watchAction.do?method=exportWatchResult">����ΪExcel�ļ�</html:link>
</body></html>
