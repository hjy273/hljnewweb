<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function doUpAccident(idValue){
    var url = "${ctx}/accidentAction.do?method=loadAccident&statusFlag=0&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="δ�����ϰ��б�"/>
<display:table name="sessionScope.queryresult" pagesize="20" id="currentRowObject">
<display:column property="contractor" title="��ά��λ" sortable="true" />
  <display:column property="sendtime" title="�ϱ�ʱ��" sortable="true"/>
  <display:column property="reason" title="�ϰ�ԭ��" sortable="true" />
  <display:column property="emlevel" title="���س̶�" sortable="true" />
  <display:column property="subline" title="Ѳ���" sortable="true" />
  <display:column property="point" title="Ѳ���" sortable="true" />


  <display:column media="html" title="����">

  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:doUpAccident('<%=id%>')">������ϰ�</a>
  </display:column>

</display:table>
 <html:link action="/accidentAction.do?method=exportUndoneAccident">����ΪExcel�ļ�</html:link>
