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
<template:titile value="���ӹ�����Ϣ"/>

<html:form onsubmit="return isValidForm()" method="Post" action="/CableSegmentAction.do?method=addCableSegment">
  <template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="���¶κ�">
      <html:text property="segmentid" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
    <template:formTr name="������">
      <html:text property="segmentname" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="���¶�">
    <html:text property="segmentdesc" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="A��">
    <html:text property="pointa" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="Z��">
    <html:text property="pointz" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="·��">
    <html:text property="route" styleClass="inputtext" style="width:160" maxlength="240"/>
    </template:formTr>
    <template:formTr name="���跽ʽ">
    <html:text property="laytype" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <template:formTr name="Ƥ��">
    <html:text property="grosslength" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <template:formTr name="��о����">
    <html:text property="corenumber" styleClass="inputtext" style="width:160" maxlength="4"/>
    </template:formTr>
    <template:formTr name="��Ȩ����">
    <html:text property="owner" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="��������">
    <html:text property="producer" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="ʩ����λ">
    <html:text property="builder" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="���·�ʽ">
    <html:text property="cabletype" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <!-- ����������ԭ���������ĳ�ѡ���add by steven gong Mar 13,2007  -->
    <template:formTr name="Ͷ������" tagID="finishT"  style="display:"  >
              <html:text property="finishtime" styleClass="inputtext" style="width:160" readonly="true"/>
              <apptag:date property="finishtime"/>
    </template:formTr>
    <template:formTr name="��о�ͺ�">
    <html:text property="fibertype" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <template:formTr name="Ԥ������">
    <html:text property="reservedlength" styleClass="inputtext" style="width:160" maxlength="12"/>
    </template:formTr>
    <template:formTr name="��ע">
    <html:text property="remark" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="addGoBack()" >����</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
