<%@include file="/common/header.jsp"%>
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
      //  self.location.replace(url);
      window.location.href=url;

}


</script>
<br>
<template:titile value="��ѯ���������Ϣ���"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/TaskOperationAction.do?method=queryTaskOperation" pagesize="18" id="currentRowObject">

  <display:column property="operationname" title="�����������"/>
  <display:column property="operationdes" title="��  ע" maxLength="50" style="width:200px"/>


  <display:column media="html" title="�޸�">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>')">�޸�</a>
  </display:column>
  <display:column media="html" title="ɾ��">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
  </display:column>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/TaskOperationAction.do?method=exportTaskOperationResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
