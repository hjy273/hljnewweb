<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/patrolSonAction.do?method=deletePatrolSon&id=" + idValue;
        self.location.replace(url);
    }

}

function toEdit(idValue){
        var url = "${ctx}/patrolSonAction.do?method=loadPatrolSon&id=" + idValue;
        self.location.replace(url);

}


</script>
<template:titile value="��ѯѲ��Ա��Ϣ���"/>
<display:table name="sessionScope.queryresult" id="currentRowObject"  pagesize="18">

  <display:column property="patrolname" title="��  ��"/>
  <display:column property="sex" title="��  ��"/>
  <display:column property="phone" title="�̶��绰"/>
  <display:column property="mobile" title="�ƶ��绰"/>
  <display:column property="parentid" title="��ά��λ"/>
  <logic:equal value="group" name="PMType">
  	<display:column property="groupid" title="Ѳ��ά����"/>
  </logic:equal>

  <display:column property="jobstate" title="����״̬"/>

  <apptag:checkpower thirdmould="70704" ishead="0">

  <display:column media="html" title="�޸�">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String patrolid = "";
    if(object != null)
     patrolid = (String) object.get("patrolid");
  %>
    <a href="javascript:toEdit('<%=patrolid%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
<apptag:checkpower thirdmould="70705" ishead="0">

  <display:column media="html" title="ɾ��">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String patrolid = (String) object.get("patrolid");
  %>
    <a href="javascript:toDelete('<%=patrolid%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>


</display:table>
<html:link action="/patrolSonAction.do?method=exportPatrolSonResult">����ΪExcel�ļ�</html:link>
