<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
       var url = "${ctx}/materialInfoAction.do?method=deletePartBase&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("����ɾ���ü�¼!");

}


function toEdit(idValue){
        var url = "${ctx}/materialInfoAction.do?method=loadPartBase&id=" + idValue;
        self.location.replace(url);

}
</script>
<template:titile value="��ѯ������Ϣ���"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/materialInfoAction.do?method=queryPartBase" id="currentRowObject" pagesize="18">
  <display:column property="name" style="width:20%" title="��������"/>
  <display:column property="modelname" style="width:15%" title="���Ϲ��"/>
  <display:column property="price" style="width:15%" title="���Ϲ��"/>
   <display:column property="factory" style="width:20%" title="��������"/>
    <display:column property="remark" style="width:20%" title="��ע"/>
   <apptag:checkpower thirdmould="70204" ishead="0">
<display:column media="html" style="width:20%" title="����">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    Object id =null;
    if(object != null)
     id = object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>')">�޸�</a>
     <apptag:checkpower thirdmould="70205" ishead="0">
    <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
    </apptag:checkpower>
  </display:column>
   </apptag:checkpower>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/materialInfoAction.do?method=exportMaterialInfoResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
