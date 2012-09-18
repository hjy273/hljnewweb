<%@page import="com.cabletech.watchinfo.beans.*"%>
<%@ include file="/common/header.jsp"%>

<script language="javascript" type="">
<!--


//-->
</script>

<template:titile value="填写核查信息中具体光缆信息部分"/>

<html:form  method="Post" action="/subWatchAction.do?method=updateSubWatch" >
    <template:formTable>

        <template:formTr name="受影响光缆名称"   isOdd="false" >
            <html:text property="segmentname" styleClass="inputtext" style="width:160" readonly="true"/>
			<html:hidden property="kid"/>
			<html:hidden property="watchid"/>
			<html:hidden property="segment_kid"/>
			<html:hidden property="fiber_kid"/>
			
        </template:formTr>

		<template:formTr name="芯数" >
            <html:text property="corenum" styleClass="inputtext" style="width:160" readonly="true"/>
        </template:formTr>

		<template:formTr name="业务类型"   isOdd="false" >
            <html:text property="netlayer" styleClass="inputtext" style="width:160" />
        </template:formTr>

		<template:formTr name="敷设方式" >
            <html:text property="laytype" styleClass="inputtext" style="width:160" readonly="true"/>
        </template:formTr>

		<template:formTr name="光缆业务影响面"   isOdd="false" >
            <html:text property="fibername" styleClass="inputtext" style="width:160" maxlength="200"/>
        </template:formTr>

		<template:formTr name="监控方式" >
            <html:text property="monitortype" styleClass="inputtext" style="width:160" maxlength="45"/>
        </template:formTr>

		<template:formTr name="光缆是否需要割接"   isOdd="false" >
		  <html:select property="ifneedcut" styleClass="inputtext" style="width:160">
			<html:option value="是">是</html:option>
			<html:option value="否">否</html:option>
		  </html:select>
        </template:formTr>

        <template:formSubmit>
            <td >
                <html:submit styleClass="button" >更新</html:submit>
            </td>
            <td >
                <html:button property="action"styleClass="button"  onclick="self.close()">取消</html:button>
            </td>
        </template:formSubmit>

   </template:formTable>
</html:form>
<iframe id="hiddenFrame" style="display:none">
</iframe>
<script language="javascript">
<!--
preSetEndDate();
//-->
</script>