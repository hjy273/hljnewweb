<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/pointAction.do?method=deletePoint&id=" + idValue;
        self.location.replace(url);
    }
}
function toEdit(idValue){
        var url = "${ctx}/pointAction.do?method=loadPoint&id=" + idValue;
        self.location.replace(url);

}
</script>

<template:titile value="��ѯѲ�����Ϣ���"/>
<display:table name="sessionScope.queryresult" id="currentRowObject"  pagesize="18">

  <display:column property="pointname" title="Ѳ�������"/>
  <display:column property="addressinfo" title="Ѳ���λ��"/>
  <display:column property="pointtype" title="������"/>
  <display:column property="sublineid" title="����Ѳ���"/>
  <display:column property="isfocus" title="�Ƿ�ؼ���"/>
  <display:column property="regionid" title="��������"/>


  <apptag:checkpower thirdmould="71104" ishead="0">

  <display:column media="html" title="�޸�">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String pointid = "";
    if(object != null)
     pointid = (String) object.get("pointid");
  %>
    <a href="javascript:toEdit('<%=pointid%>')">�޸�</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="71105" ishead="0">

  </apptag:checkpower>

</display:table>
<html:link action="/pointAction.do?method=exportPointResult">����ΪExcel�ļ�</html:link>
