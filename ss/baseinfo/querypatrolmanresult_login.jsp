<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td valign="middle" align="center" class="title2">巡检人员动态</td></tr></table>

<display:table name="sessionScope.queryresult_TWO"  requestURI="${ctx}/patrolAction.do?method=queryPatrol" id="currentRowObject"  pagesize="5">
  <display:column property="patrolname" title="巡检维护组" sortable="true"/>
  <display:column property="sim" title="设备手机号码" sortable="true"/>
  <display:column property="parentid" title="代维单位" sortable="true"/>
  <display:column property="jobinfo" title="工作信息" sortable="true"/>
</display:table>
