<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
       var url = "${ctx}/CableAction.do?method=deleteCable&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("����ɾ���ü�¼!");

}


function toEdit(idValue,type){
        var url = "${ctx}/CableAction.do?method=loadCable&id=" + idValue+"&t="+type;
        self.location.replace(url);

}

</script>
<template:titile value="��ѯ���¶ν��"/>
<display:table name="sessionScope.cableList" requestURI="${ctx}/CableAction.do?method=queryCable" id="currentRowObject" pagesize="18">
  <display:column property="cableno" style="width:10%" title="���¶α��"/>
  <display:column property="contractorname" style="width:10%" title="��ά��λ"/>
  <display:column property="cablename" style="width:15%" title="��������"/>
<display:column property="cablelinename" style="width:15%" title="���¶�����"/>
<display:column property="fibertype" style="width:15%" title="��о�ͺ�"/>
<display:column property="fibernumber" style="width:15%" title="��о����"/>
 
<display:column media="html" style="width:5%" title="�鿴">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>','r')">�鿴</a>
  </display:column>
  <apptag:checkpower thirdmould="70204" ishead="0">
  <display:column media="html" style="width:5%" title="�޸�">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>','e')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70205" ishead="0">

  <display:column media="html" style="width:5%" title="ɾ��">
    <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>
</display:table>
<logic:empty name="cableList">
</logic:empty>
<logic:notEmpty name="cableList">
<html:link action="/CableAction.do?method=exportCableResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>