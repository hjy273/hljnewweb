<%@page import="com.cabletech.lineinfo.beans.*"%>
<%@include file="/common/header.jsp"%>
<template:titile value="Ѳ��켣��Ϣ"/>
<html:form method="Post" action="/PlanExeAction.do?method=updateSubline">
  <template:formTable>
	<template:formTr name="Ѳ�쵥λ" isOdd="false">
		<html:hidden property="patrolname"/>
      <html:text readonly="true" property="patrolname" styleClass="inputtext" style="width:100"/>
    </template:formTr>
	<template:formTr name="Ѳ�쿨��" isOdd="false">
      <html:hidden property="simid"/>
      <html:text readonly="true" property="simid" styleClass="inputtext" style="width:100"/>
    </template:formTr>
	<template:formTr name="Ѳ�������" isOdd="false">
      <html:text  readonly="true" property="pointname" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    <template:formTr name="�����߶�">
      <html:text  readonly="true" property="sublinename" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    <template:formTr name="Ѳ��ʱ��">
      <html:text  readonly="true" property="executetime" styleClass="inputtext" style="width:100"/>
    </template:formTr>
</template:formTable>
</html:form>
