<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function doUpTrouble(idValue){
    var url = "${ctx}/accidentAction.do?method=loadTrouble&statusFlag=0&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="δ���������б�"/>
<display:table name="sessionScope.queryresult" pagesize="20" id="currentRowObject">
  <display:column property="sendtime" title="�ϱ�ʱ��" sortable="true"/>
  <display:column property="reason" title="��������" sortable="true" />
  <display:column property="emlevel" title="���س̶�" sortable="true" />
  <display:column property="subline" title="Ѳ���" sortable="true" />
  <display:column property="point" title="Ѳ���" sortable="true" />
  <display:column property="contractor" title="��ά��λ" sortable="true" />
  <display:column media="html" title="����">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:doUpTrouble('<%=id%>')">���������</a>
  </display:column>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
 <html:link action="/accidentAction.do?method=exportUndoneTrouble">����ΪExcel�ļ�</html:link>
 </logic:notEmpty>
