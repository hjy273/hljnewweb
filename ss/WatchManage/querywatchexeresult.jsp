<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="查询外力盯防现场巡检信息结果"/>

<display:table name="sessionScope.queryResult"  id="resultList" pagesize="18">
	 <logic:equal value="group" name="PMType">
	  	<display:column property="executorname" title="巡检维护组" />
	  </logic:equal>
	  <logic:notEqual value="group" name="PMType">
	  	<display:column property="executorname" title="执行巡检维护人" />
	  </logic:notEqual>

	<display:column property="worktime" title="执行时间" />
	<display:column property="watchname" title="外力盯防" />
	<display:column property="sublinename" title="盯防地点" />
</display:table>
<html:link action="/WatchExeStaAction.do?method=exportWatchDetail">导出为Excel文件</html:link>

</body>
</html>

