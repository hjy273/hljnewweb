<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<script language="javascript" type="">
function isValidForm(){
	return true;
}
function addGoBack(){
    location.href = "${ctx}/CableSegmentAction.do?method=queryCableSegment";
    return true;
}
function setCyc(){
    finishTime.style.display = "";
}

</script>

<body>
<template:titile value="增加光缆信息"/>

<html:form onsubmit="return isValidForm()" method="Post" action="/CableSegmentAction.do?method=addCableSegment">
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
    <!-- 下面这行由原来的输入框改成选择框，add by steven gong Mar 13,2007  -->
    <template:formTr name="投产日期" tagID="finishT"  style="display:"  >
              <html:text property="finishtime" styleClass="inputtext" style="width:160" readonly="true"/>
              <apptag:date property="finishtime"/>
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
        <html:submit styleClass="button">增加</html:submit>
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
