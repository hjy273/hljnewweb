<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
    function toGetForm(id){
    	var url = "${ctx}/getaction.do?method=showOne&source=showSquare&id=" + id;
        self.location.replace(url);
    }
function toSq(idValue){
    var url = "${ctx}/pageonholeaction.do?method=showOneSquare&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="���������ɹ���"/>
<body>

<display:table name="sessionScope.slist" requestURI="${ctx}/pageonholeaction.do?method=showSquare" pagesize="18" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String step = null;
  String id =null,changeName="",changeSimpleName="";

  if (object != null) {
     id = (String) object.get("id");
     changeName=(String)object.get("changename");
     changeSimpleName=changeName;
     if(changeName!=null&&changeName.length()>10){
     	changeSimpleName=changeName.substring(0,10)+"...";
     }
  }
%>
  <display:column media="html" sortable="true" style="align:center" title="��������" style="width:160px">
  	<a href="javascript:toGetForm('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  </display:column>
  <display:column property="changepro" title="��������" style="align:center" maxLength="4"/>
  <display:column property="changeaddr" title="���̵�ַ" style="align:center" maxLength="4"/>
  <display:column property="lineclass" title="��������" style="align:center" maxLength="4"/>
  <display:column property="square" title="�ܽ�����Ԫ��" style="align:center"/>
  <display:column property="squared" title="�ѽ�����Ԫ��" style="align:center"/>
  <display:column property="sqdate" title="��������" style="align:center" maxLength="10"/>

  <display:column media="html" title="����" style="align:center">
    <a href="javascript:toSq('<%=id%>')">����</a>
  </display:column>
</display:table></body>
