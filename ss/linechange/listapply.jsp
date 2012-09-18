<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<style type="text/css">
.subject{text-align:center;}
</style>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/changeapplyaction.do?method=delApplyInfo&changeid=" + idValue;
        self.location.replace(url);
    }
}
function toEdit(idValue){
    var url = "${ctx}/changeapplyaction.do?method=getApplyInfo&type=edit&changeid=" + idValue;
    self.location.replace(url);
}
function toLook(idValue){
    var url = "${ctx}/changeapplyaction.do?method=getApplyInfo&type=look&changeid=" + idValue;
    self.location.replace(url);
}
function warn(){
  alert("����Ϣ��ͨ����,�����޸Ļ�ɾ����");
}

</script>
<template:titile value="��ѯ����������Ϣ"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/changeapplyaction.do?method=getApplyInfoList" pagesize="18" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String step = null;
  String id =null,changeName="",changeSimpleName="";

  if (object != null) {
     step = object.get("step").toString();
     id = (String) object.get("id");
     changeName=(String)object.get("changename");
     changeSimpleName=changeName;
     if(changeName!=null&&changeName.length()>10){
     	changeSimpleName=changeName.substring(0,10)+"...";
     }
  }
%>
  <display:column media="html" sortable="true" style="align:center" title="��������" style="width:160px">
  	<a href="javascript:toLook('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  </display:column>
  <display:column property="changepro" title="��������" style="align:center" maxLength="4"/>
  <display:column property="changeaddr" title="���̵�ַ" style="align:center"" maxLength="4"/>
  <display:column property="lineclass" title="��������" style="align:center" maxLength="4"/>
  <display:column property="applytime"  title="��������"style="align:center" maxLength="10"/>
  <display:column media="html" title="�� ��" style="align:center" headerClass="subject">
  <apptag:checkpower thirdmould="50104" ishead="0">
  <%if (step.equals("A")){   %>
    <a href="javascript:toEdit('<%=id%>')">�޸�</a>
  <%}%>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="50105" ishead="0">
  <%if (step.equals("A")){%>|
  <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
  <%}%>
  </apptag:checkpower>
  </display:column>

</display:table>

<html:link action="/changeapplyaction.do?method=exportApplyResult">����ΪExcel�ļ�</html:link>
</body>
