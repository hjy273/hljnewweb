<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="��ѯ���������ֳ�Ѳ����Ϣ���"/>

<display:table name="sessionScope.queryResult"  id="resultList" pagesize="18">
	 <logic:equal value="group" name="PMType">
	  	<display:column property="executorname" title="Ѳ��ά����" />
	  </logic:equal>
	  <logic:notEqual value="group" name="PMType">
	  	<display:column property="executorname" title="ִ��Ѳ��ά����" />
	  </logic:notEqual>

	<display:column property="worktime" title="ִ��ʱ��" />
	<display:column property="watchname" title="��������" />
	<display:column property="sublinename" title="�����ص�" />
</display:table>
<html:link action="/WatchExeStaAction.do?method=exportWatchDetail">����ΪExcel�ļ�</html:link>

</body>
</html>

