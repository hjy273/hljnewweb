<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script type="text/javascript">
<!--
function toDelete(idValue){

    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/NoticeAction.do?method=delNotice&id=" + idValue;
        self.location.replace(url);
   }
}

function toEdit(idValue){
        var url = "${ctx}/NoticeAction.do?method=editForm&id=" + idValue;
        self.location.replace(url);

}
//-->
</script>
<template:titile value="������Ϣ"/>

<display:table name="sessionScope.noticelist"  id="currentRowObject"pagesize="15">
	<display:column property="title" title="����"  maxLength="10"/>
	<display:column property="content"  title="����ժҪ"  maxLength="15"/>
	<display:column property="issueperson" title="������"  maxLength="10" />
	<display:column property="isissue" title="�Ƿ񷢲�"  />
	<display:column property="issuedate" title="��������"  maxLength="10"></display:column>
	<display:column media="html" title="����">
	<%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
    String isissue = (String) object.get("isissue");
    if(isissue.indexOf("δ")!= -1){
  	%>
		<a href="javascript:toEdit('<%=id%>')">�޸�</a>
	<%}else{ %>
	 ---
	 <%} %>
    </display:column>
	<display:column media="html" title="����">
	<%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  	%>
		<a href="javascript:toDelete('<%=id%>')">ɾ��</a>

    </display:column>
</display:table>

