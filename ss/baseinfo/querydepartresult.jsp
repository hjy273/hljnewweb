<%@include file="/common/header.jsp"%>
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
<display:table name="sessionScope.queryresult" requestURI="${ctx}/departAction.do?method=queryDepart" id="currentRowObject" pagesize="18">


  <display:column property="deptname" style="width:30%" title="��������"/>
  <display:column property="linkmaninfo" style="width:20%" title="������ϵ��"/>
  <display:column property="parentid" style="width:30%" title="�ϼ�����"/>

  <apptag:checkpower thirdmould="70204" ishead="0">

  <display:column media="html" style="width:10%" title="�޸Ĳ���">
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

  <display:column media="html" style="width:10%" title="ɾ������">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String deptid = (String) object.get("deptid");
  %>
    <a href="javascript:toDelete('<%=deptid%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>

</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/departAction.do?method=exportDepartResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
