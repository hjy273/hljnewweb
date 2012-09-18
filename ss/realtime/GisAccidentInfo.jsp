<%@page import="com.cabletech.lineinfo.beans.*"%>
<%@include file="/common/header.jsp"%>
<template:titile value="事故点信息"/>
<html:form method="Post" action="/GisAccidentAction.do?method=updateSubline">
  <template:formTable>

	<template:formTr name="故障地点" isOdd="false">
      <html:hidden property="keyid"/>
      <html:text readonly="true" property="point" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    <template:formTr name="所在线段">
      <html:text  readonly="true" property="subline" styleClass="inputtext" style="width:100"/>
    </template:formTr>

    <logic:equal value="group" name="PMType">
    <template:formTr name="上报巡检维护组" isOdd="false">
      <html:text  readonly="true" property="patrol" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
    <template:formTr name="上报巡检人" isOdd="false">
      <html:text  readonly="true" property="patrol" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    </logic:notEqual>

    <template:formTr name="上报时间">
      <html:text  readonly="true" property="sendtime" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    <template:formTr name="故障" isOdd="false">
	  <html:text  readonly="true" property="optype" styleClass="inputtext" style="width:100"/>
    </template:formTr>

	<template:formTr name="该点累计报警次数" style="color:red">
	  <html:text  readonly="true" property="alerttime" styleClass="inputtext" style="width:100;color:red"/>
    </template:formTr>


  </template:formTable>
</html:form>
