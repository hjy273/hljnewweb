<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script  type="text/javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/AlertreceiverListAction.do?method=deleteAlertreceiverList&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        var url = "${ctx}/AlertreceiverListAction.do?method=loadAlertreceiverList&id=" + idValue;
        self.location.replace(url);

}


</script>
<body>
<template:titile value="��ѯ����������"/>
<display:table name="sessionScope.queryresult" id="currentRowObject" pagesize="18">
  <display:column property="simcode" title="���ձ�������" sortable="true"/>
  <display:column property="emergencylevel" title="�����¹ʼ���"  sortable="true"/>
  <display:column property="userid" title="������"  sortable="true"/>
  <display:column property="contractorid" title="�¹�������ά��λ"  sortable="true"/>

  <apptag:checkpower thirdmould="71504" ishead="0">
	  <display:column media="html" title="�޸�">
       <%
	    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String id = (String) object.get("id");
	  %>
	    <a href="javascript:toEdit('<%=id%>')">�޸�</a>
	  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="71505" ishead="0">
	  <display:column media="html" title="ɾ��">
       <%
	    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String id = (String) object.get("id");
	  %>
	     <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
	  </display:column>
  </apptag:checkpower>

</display:table>
<html:link action="/AlertreceiverListAction.do?method=exportAlertreceiverListResult">����ΪExcel�ļ�</html:link>
</body>
