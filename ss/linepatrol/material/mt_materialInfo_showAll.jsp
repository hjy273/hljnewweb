<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
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
   <display:column property="typename" style="width:15%" title="��������" />
  <display:column property="modelname" style="width:15%" title="���Ϲ��"/>
   <display:column property="factory" style="width:20%" title="��������"/>
    <display:column property="remark" style="width:20%" title="��ע"/>
<display:column media="html" style="width:20%" title="����">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    Object id =null;
    if(object != null)
     id = object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>')">�޸�</a>
    <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
  </display:column>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/materialInfoAction.do?method=exportMaterialInfoResult">����ΪExcel�ļ�</html:link>
<br/>
<div align="center">
<input type="button" class="button" onclick="history.back()" value="����"/>
</div>
</logic:notEmpty>
