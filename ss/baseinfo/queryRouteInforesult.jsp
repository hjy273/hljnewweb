<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
       var url = "${ctx}/RouteInfoAction.do?method=deleteRouteInfo&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("����ɾ���ü�¼!");

}


function toEdit(idValue){
        var url = "${ctx}/RouteInfoAction.do?method=loadRouteInfo&id=" + idValue;
        self.location.replace(url);

}
</script>
<template:titile value="��ѯ·����Ϣ���"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/RouteInfoAction.do?method=queryRouteInfo" id="currentRowObject" pagesize="18">
  <display:column property="routename" style="width:30%" title="·������"/>
  <display:column property="regionid" style="width:30%" title="��������"/>
   <apptag:checkpower thirdmould="70204" ishead="0">
<display:column media="html" style="width:10%" title="����">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    if(object != null)
     id = (String) object.get("id");
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
<html:link action="/RouteInfoAction.do?method=exportRouteInfoResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
