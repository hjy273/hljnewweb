<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
       var url = "${ctx}/departAction.do?method=deleteDepart&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("����ɾ���ü�¼!");

}


function toEdit(idValue){
        var url = "${ctx}/departAction.do?method=loadDepart&id=" + idValue;
        self.location.replace(url);

}

</script>
<template:titile value="��ѯ������Ϣ���"/>
<display:table name="sessionScope.RESULTLIST" id="currentRowObject" pagesize="18">


  <display:column property="deptname"  title="��������"/>
  <display:column property="linkmaninfo"title="������ϵ��"/>
  <display:column property="parentid"  title="�ϼ�����"/>

  <apptag:checkpower thirdmould="70204" ishead="0">

  <display:column media="html" title="�޸Ĳ���">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String deptid = "";
    String id = "";
    if(object != null)
     deptid = (String) object.get("deptid");
  %>
    <a href="javascript:toEdit('<%=deptid%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70205" ishead="0">

  <display:column media="html"  title="ɾ������">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String deptid = (String) object.get("deptid");
  %>
    <a href="javascript:toDelete('<%=deptid%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>

</display:table>
<html:link action="/departAction.do?method=exportDepartResult">����ΪExcel�ļ�</html:link>
