<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/lineAction.do?method=delLine&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        var url = "${ctx}/lineAction.do?method=loadLine&id=" + idValue;
        self.location.replace(url);

}

</script>
<br>
<template:titile value="��ѯѲ����·��Ϣ���"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/lineAction.do?method=queryLine" id="currentRowObject"  pagesize="18">

  <display:column property="linename" title="��·����" sortable="true"/>
  <display:column property="linetype" title="��·����" sortable="true"/>
  <display:column property="ruledeptid" title="��������" sortable="true"/>
  <display:column property="regionid" title="��������" sortable="true"/>

<apptag:checkpower thirdmould="70904" ishead="0">

  <display:column media="html" title="�޸�"><%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String lineid = "";
    if(object != null)
     lineid = (String) object.get("lineid");
  %>
    <a href="javascript:toEdit('<%=lineid%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  <logic:equal value="12" name="LOGIN_USER" property="type">

  <display:column media="html" title="ɾ��">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String lineid = (String) object.get("lineid");
    //<display:column property="lineid" title="��·���"  sortable="true"/>  �������column������������ɾ��
  %>
    <a href="javascript:toDelete('<%=lineid%>')">ɾ��</a>
  </display:column>
  </logic:equal>

</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/lineAction.do?method=exportLineResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>