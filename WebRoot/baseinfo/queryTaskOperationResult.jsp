<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/TaskOperationAction.do?method=deleteTaskOperation&id=" + idValue;
        self.location.replace(url);
    }
}


function toEdit(idValue){
        var url = "${ctx}/TaskOperationAction.do?method=loadTaskOperation&id=" + idValue;
        self.location.replace(url);

}


</script>

<template:titile value="��ѯ���������Ϣ���"/>
<display:table name="sessionScope.queryresult" pagesize="18" id="currentRowObject">

  <display:column property="operationname" title="�����������"/>
  <display:column property="operationdes" title="��  ע" maxLength="50"/>

  <apptag:checkpower thirdmould="71404" ishead="0">

  <display:column media="html" title="�޸�">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="71405" ishead="0">

  <display:column media="html" title="ɾ��">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>

</display:table>
<html:link action="/TaskOperationAction.do?method=exportTaskOperationResult">����ΪExcel�ļ�</html:link>
