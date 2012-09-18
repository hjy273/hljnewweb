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
<template:titile value="待审批计划列表"/>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/YearMonthPlanAction.do?method=getUnapprovedYMPlan" id="currentRowObject"  pagesize="18">
	  <display:column property="planname" title="计划名称" />
     <display:column property="plantype" title="计划类型"  />
      <display:column property="stat" title="审批状态"  />
	 <display:column property="status" title="计划状态" />

  <display:column media="html" title="审批">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:toGetForm('<%=id%>')">审批</a>
  </display:column>

</display:table>