<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/patrolAction.do?method=deletePatrol&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        var url = "${ctx}/patrolAction.do?method=loadPatrol&id=" + idValue;
        self.location.replace(url);

}

</script>

<template:titile value="Ѳ��ά������Ϣһ����"/>
<display:table name="sessionScope.queryresult"  id="currentRowObject"  pagesize="18">

  <display:column property="patrolname" title="Ѳ��ά����"/>
  <display:column property="parentid" title="��ά��λ"/>
  <display:column property="jobinfo" title="������Ϣ"/>



  <apptag:checkpower thirdmould="70604" ishead="0">
  <display:column media="html" title="�޸�">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String patrolid =  "";
    if(object != null)
     patrolid = (String) object.get("patrolid");
    
  %>
    <a href="javascript:toEdit('<%=patrolid%>')">�޸�</a>

  </display:column>
  </apptag:checkpower>
   <apptag:checkpower thirdmould="70605" ishead="0">
 <display:column media="html" title="ɾ��">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String patrolid =  "";
    if(object != null)
     patrolid = (String) object.get("patrolid");
    
  %>
    <a href="javascript:toDelete('<%=patrolid%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/patrolAction.do?method=exportPatrolMan">����ΪExcel�ļ�</html:link>
</logic:notEmpty>


