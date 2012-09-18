<%@include file="/common/header.jsp"%>

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">

function toEdit(idValue){
    var url = "${ctx}/<%=request.getAttribute("editurl")%>&id=" + idValue+"&stat=edit";
    self.location.replace(url);
}

function toDelete(idValue){
    var url = "${ctx}/<%=request.getAttribute("delurl")%>&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="��ѯ�����Ϣ"/>
<body>
<%String queryurl=request.getAttribute("queryurl").toString(); %>

<display:table name="sessionScope.resultlist" requestURI="<%=queryurl%>" pagesize="18" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String id =null;
  String adjunct = null;

  if (object != null) {
     id = (String) object.get("id");
     adjunct = (String)object.get("adjunct");
  }
%>
  <display:column property="filename" style="align:center" title="�ĵ�����" maxLength="10"/>
  <display:column property="typename" title="�ĵ�����" style="align:center" maxLength="10"/>
  <display:column property="description" title="�ĵ�����" style="align:center" maxLength="10"/>

  <display:column media="html" title="�ĵ�" style="align:center">
  	<%
  		
  		if(adjunct == null || adjunct.equals("")){
  			out.print("");
  		}else{
  	%>
        <apptag:listAttachmentLink fileIdList="<%=adjunct%>" />
    <%} %>
 
  </display:column>
  <%if(request.getAttribute("delurl").toString().indexOf("System") != -1) {%>
  <apptag:checkpower thirdmould="130103" ishead="0">
  <display:column media="html" title="����" style="align:center">
  		   <a href="javascript:toEdit('<%=id%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  
  <apptag:checkpower thirdmould="130104" ishead="0">
  <display:column media="html" title="����" style="align:center">
  		   <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
  </display:column>
   </apptag:checkpower>
   <%} 
   if(request.getAttribute("delurl").toString().indexOf("Criterion") != -1){
   %>
    <apptag:checkpower thirdmould="130203" ishead="0">
  <display:column media="html" title="����" style="align:center">
  		   <a href="javascript:toEdit('<%=id%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="130204" ishead="0">
  <display:column media="html" title="����" style="align:center">
  		   <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>
  <%}
  if(request.getAttribute("delurl").toString().indexOf("Experience") != -1){
   %>
    <apptag:checkpower thirdmould="130303" ishead="0">
  <display:column media="html" title="����" style="align:center">
  		   <a href="javascript:toEdit('<%=id%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="130304" ishead="0">
  <display:column media="html" title="����" style="align:center">
  		   <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>
  <%} %>
</display:table>

</body>
