<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript">
<!--

function toGetForm(idValue){
        var url = "${ctx}/YearMonthPlanAction.do?method=loadYMPlan4Approve&id=" + idValue;
        self.location.replace(url);

}

//-->
</script>
<template:titile value="�������ƻ��б�"/>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/YearMonthPlanAction.do?method=getUnapprovedYMPlan" id="currentRowObject"  pagesize="18">
	  <display:column property="planname" title="�ƻ�����" />
     <display:column property="plantype" title="�ƻ�����"  />
      <display:column property="stat" title="����״̬"  />
	 <display:column property="status" title="�ƻ�״̬" />

  <display:column media="html" title="����">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:toGetForm('<%=id%>')">����</a>
  </display:column>

</display:table>