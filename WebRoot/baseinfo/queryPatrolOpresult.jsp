<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/PatrolOpAction.do?method=deletePatrolOp&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        var url = "${ctx}/PatrolOpAction.do?method=loadPatrolOp&id=" + idValue;
        self.location.replace(url);

}
</script>

<template:titile value="��ѯѲ���¹�����Ϣ���"/>
<display:table name="sessionScope.queryresult" id="currentRowObject" pagesize="18">


  <display:column property="operationcode" title="�¹���" sortable="true"/>
  <display:column property="optype" title="�¹�����" sortable="true"/>
  <display:column property="emlevel" title="�¹ʼ���" sortable="true"/>
  <display:column property="operationdes" title="�¹���˵��" />

    <%-- n.b. that this could be done via the autolink attribute, but that rather defeats the purpose    --%>
   <apptag:checkpower thirdmould="71304" ishead="0">

    <display:column media="html" title="�޸�">
    <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String operationcode = "";
    if(object != null)
     operationcode = (String) object.get("operationcode");
  %>
    <a href="javascript:toEdit('<%=operationcode%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="71305" ishead="0">

    <display:column media="html" title="ɾ��">
     <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String operationcode = (String) object.get("operationcode");
  %>
    <a href="javascript:toDelete('<%=operationcode%>')">ɾ��</a>
  </display:column>
  </apptag:checkpower>
</display:table>
<html:link action="/PatrolOpAction.do?method=exportPatrolOpResult">����ΪExcel�ļ�</html:link>
