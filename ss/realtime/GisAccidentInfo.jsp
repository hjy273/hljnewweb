<%@page import="com.cabletech.lineinfo.beans.*"%>
<%@include file="/common/header.jsp"%>
<template:titile value="�¹ʵ���Ϣ"/>
<html:form method="Post" action="/GisAccidentAction.do?method=updateSubline">
  <template:formTable>

	<template:formTr name="���ϵص�" isOdd="false">
      <html:hidden property="keyid"/>
      <html:text readonly="true" property="point" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    <template:formTr name="�����߶�">
      <html:text  readonly="true" property="subline" styleClass="inputtext" style="width:100"/>
    </template:formTr>

    <logic:equal value="group" name="PMType">
    <template:formTr name="�ϱ�Ѳ��ά����" isOdd="false">
      <html:text  readonly="true" property="patrol" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
    <template:formTr name="�ϱ�Ѳ����" isOdd="false">
      <html:text  readonly="true" property="patrol" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    </logic:notEqual>

    <template:formTr name="�ϱ�ʱ��">
      <html:text  readonly="true" property="sendtime" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    <template:formTr name="����" isOdd="false">
	  <html:text  readonly="true" property="optype" styleClass="inputtext" style="width:100"/>
    </template:formTr>

	<template:formTr name="�õ��ۼƱ�������" style="color:red">
	  <html:text  readonly="true" property="alerttime" styleClass="inputtext" style="width:100;color:red"/>
    </template:formTr>


  </template:formTable>
</html:form>
