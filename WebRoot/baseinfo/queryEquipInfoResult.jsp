<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/EquipInfoAction.do?method=deleteEquipInfo&id=" + idValue;
        self.location.replace(url);
    }
}
</script>
<template:titile value="��ѯװ��������Ϣ���"/>
<display:table name="sessionScope.queryresult" id="currentRowObject" pagesize="18">
  <display:column property="id" title="�豸���" href="${ctx}/EquipInfoAction.do?method=loadEquipInfo" paramId="id"/>
  <display:column property="name" title="�豸����"/>
  <display:column property="type" title="���"/>
  <display:column property="model" title="�豸�ͺ�"/>

  <display:column media="html" title="ɾ��">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
   if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
  </display:column>

</display:table>
