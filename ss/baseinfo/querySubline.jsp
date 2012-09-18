<%@include file="/common/header.jsp"%>
<html>
<body>
<template:titile value="查询巡检线段信息"/>
<html:form method="Post" action="/sublineAction.do?method=querySubline">
  <template:formTable>
    <template:formTr name="巡检线段名称">
      <html:text property="subLineName" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="所属线路" isOdd="false">
      <apptag:setSelectOptions valueName="lineCollection" tableName="lineinfo" columnName1="linename" region="true" columnName2="lineid"/>
      <html:select property="lineID" styleClass="inputtext" style="width:200px">
        <html:option value="">不限</html:option>
        <html:options collection="lineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="所属部门">
      <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/>
      <html:select property="ruleDeptID" styleClass="inputtext" style="width:200px">
        <html:option value="">不限</html:option>
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="线路类型" isOdd="false">
     <apptag:quickLoadList cssClass="inputtext" name="lineType" listName="layingmethod"  style="width:200px" type="select"/>
      <!--html:select property="lineType" styleClass="inputtext" style="width:200px"->
        <-html:option value="">不限</html:option->
        <-html:option value="00">直埋</html:option->
        <-html:option value="01">架空</html:option->
        <-html:option value="02">管道</html:option->
		<-html:option value="04">挂墙</html:option->
		<-html:option value="03">混合</html:option->
	  <-/html:select-->
    </template:formTr>

    <logic:equal value="group" name="PMType">
    	<template:formTr name="巡检维护组">
	      <html:select property="patrolid" styleClass="inputtext" style="width:200px">
	        <html:option value="">无</html:option>
	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select>
	    </template:formTr>
    </logic:equal>
     <logic:notEqual value="group" name="PMType">
    	<template:formTr name="巡检维护人">
	      <html:select property="patrolid" styleClass="inputtext" style="width:200px">
	        <html:option value="">无</html:option>
	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select>
	    </template:formTr>
    </logic:notEqual>


    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>
</html>