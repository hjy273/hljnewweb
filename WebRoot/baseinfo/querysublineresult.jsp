<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/sublineAction.do?method=deleteSubline&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        
        var url = "${ctx}/sublineAction.do?method=loadSubline&id=" + idValue;
        self.location.replace(url);

}
function toGetBack(){
        var url = "${ctx}/sublineAction.do?method=loadQuerySublineForm";
        self.location.replace(url);
}
</script>

<template:titile value="��ѯѲ���߶���Ϣ���"/>
<display:table name="sessionScope.queryresult"  id="currentRowObject" pagesize="18">

  <display:column property="sublinename" title="Ѳ���߶�����" sortable="true"/>
  <display:column property="lineid" title="������·" sortable="true"/>
  <display:column property="ruledeptid" title="��������" sortable="true"/>
  <display:column property="linetype" title="��·����" sortable="true"/>
  <logic:equal value="group" name="PMType">
  	    <display:column property="patrolname" title="Ѳ��ά����" sortable="true"/>
  </logic:equal>
  <logic:notEqual value="group" name="PMType">
  	    <display:column property="patrolname" title="Ѳ��ά����" sortable="true"/>
  </logic:notEqual>

  <display:column property="regionid" title="��������" sortable="true"/>

<apptag:checkpower thirdmould="71004" ishead="0">

  <display:column media="html" title="�޸�">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String sublineid = "";
    if(object != null)
     sublineid = (String) object.get("sublineid");
     //<display:column property="sublineid" title="Ѳ��α��" sortable="true"/> �������column������������ɾ��
  %>
    <a href="javascript:toEdit('<%=sublineid%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
    <apptag:checkpower thirdmould="71005" ishead="0">

  <display:column media="html" title="ɾ��">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String sublineid = (String) object.get("sublineid");
  %>
    <a href="javascript:toDelete('<%=sublineid%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>
</display:table>
<html:link action="/sublineAction.do?method=exportSubline">����ΪExcel�ļ�</html:link>

