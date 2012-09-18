<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function isValidForm(){
	return true;
}
function addGoBack(){
    location.href = "${ctx}/CableSegmentAction.do?method=queryCableSegment";
    return true;
}

</script>

<body>
<logic:equal value="e" name="TYPE">
<template:titile value="编辑光缆信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/CableSegmentAction.do?method=updateCableSegment">
  <html:hidden property="kid"/>
  <template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="光缆段号">
      <html:text property="segmentid" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
    <template:formTr name="光缆名">
      <html:text property="segmentname" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="光缆段">
    <html:text property="segmentdesc" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="A点">
    <html:text property="pointa" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="Z点">
    <html:text property="pointz" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="路由">
    <html:text property="route" styleClass="inputtext" style="width:160" maxlength="240"/>
    </template:formTr>
    <template:formTr name="敷设方式">
    <html:text property="laytype" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <template:formTr name="皮长">
    <html:text property="grosslength" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <template:formTr name="纤芯数量">
    <html:text property="corenumber" styleClass="inputtext" style="width:160" maxlength="4"/>
    </template:formTr>
    <template:formTr name="产权属性">
    <html:text property="owner" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="生产厂家">
    <html:text property="producer" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="施工单位">
    <html:text property="builder" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="成缆方式">
    <html:text property="cabletype" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <template:formTr name="投产日期">
    <html:text property="finishtime" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <template:formTr name="纤芯型号">
    <html:text property="fibertype" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <template:formTr name="预留长度">
    <html:text property="reservedlength" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <template:formTr name="备注">
    <html:text property="remark" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">提交</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="addGoBack()" >返回</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</logic:equal>
<logic:notEqual value="e" name="TYPE">

<template:titile value="查看光缆详细信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/CableSegmentAction.do?method=updateCableSegment">
<template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="光缆段号">
    	<bean:write name="cablesegmentBean" property="segmentid"/>
    </template:formTr>
    <template:formTr name="光缆名">
    	<bean:write name="cablesegmentBean" property="segmentname"/>
    </template:formTr>
    <template:formTr name="光缆段">
    	<bean:write name="cablesegmentBean" property="segmentdesc"/>
    </template:formTr>
    <template:formTr name="A点">
    	<bean:write name="cablesegmentBean" property="pointa"/>
    </template:formTr>
    <template:formTr name="Z点">
    	<bean:write name="cablesegmentBean" property="pointz"/>
    </template:formTr>
    <template:formTr name="路由">
    	<bean:write name="cablesegmentBean" property="route"/>
    </template:formTr>
    <template:formTr name="敷设方式">
    	<bean:write name="cablesegmentBean" property="laytype"/>
    </template:formTr>
    <template:formTr name="皮长">
    	<bean:write name="cablesegmentBean" property="grosslength"/>
    </template:formTr>
    <template:formTr name="纤芯数量">
    	<bean:write name="cablesegmentBean" property="corenumber"/>
    </template:formTr>
    <template:formTr name="产权属性">
    	<bean:write name="cablesegmentBean" property="owner"/>
    </template:formTr>
    <template:formTr name="生产厂家">
    	<bean:write name="cablesegmentBean" property="producer"/>
    </template:formTr>
    <template:formTr name="施工单位">
    	<bean:write name="cablesegmentBean" property="builder"/>
    </template:formTr>
    <template:formTr name="成缆方式">
    	<bean:write name="cablesegmentBean" property="cabletype"/>
    </template:formTr>
    <template:formTr name="投产日期">
    	<bean:write name="cablesegmentBean" property="finishtime"/>
    </template:formTr>
    <template:formTr name="纤芯型号">
    	<bean:write name="cablesegmentBean" property="fibertype"/>
    </template:formTr>
    <template:formTr name="预留长度">
    	<bean:write name="cablesegmentBean" property="reservedlength"/>
    </template:formTr>
    <template:formTr name="备注">
    	<bean:write name="cablesegmentBean" property="remark"/>
    </template:formTr>
    </template:formTable>
    <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" onclick="addGoBack()" >返回</html:button>
      </td>
    </template:formSubmit>
    </html:form>

</logic:notEqual>
