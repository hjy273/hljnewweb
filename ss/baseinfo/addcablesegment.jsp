<%@include file="/common/header.jsp"%>

<script language="javascript" type="">
function isValidForm(){
	if(cablesegmentBean.segmentid.value==""){
		alert("���¶κŲ���Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.segmentname.value==""){
		alert("����������Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.segmentdesc.value==""){
		alert("���¶β���Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.pointa.value==""){
		alert("A�㲻��Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.pointz.value==""){
		alert("Z�㲻��Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.route.value==""){
		alert("·�ɲ���Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.laytype.value==""){
		alert("���跽ʽ����Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.grosslength.value==""){
		alert("Ƥ������Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.corenumber.value==""){
		alert("��о��������Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.owner.value==""){
		alert("��Ȩ���Բ���Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.producer.value==""){
		alert("�������̲���Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.builder.value==""){
		alert("ʩ����λ����Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.cabletype.value==""){
		alert("���·�ʽ����Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.finishtime.value==""){
		alert("Ͷ�����ڲ���Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.fibertype.value==""){
		alert("��о�ͺŲ���Ϊ�գ�");
		return false;
	}
	if(cablesegmentBean.reservedlength.value==""){
		alert("Ԥ�����Ȳ���Ϊ�գ�");
		return false;
	}
	return true;
}
</script>

<body>
<template:titile value="���ӹ�����Ϣ"/>

<html:form onsubmit="return isValidForm()" styleId="cablesegmentBean" method="Post" action="/CableSegmentAction.do?method=addCableSegment">
  <template:formTable>
    <template:formTr name="���¶κ�">
      <html:text property="segmentid" styleClass="inputtext" style="width:160px" maxlength="10"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="������">
      <html:text property="segmentname" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="���¶�">
    <html:text property="segmentdesc" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="A��">
    <html:text property="pointa" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="Z��">
    <html:text property="pointz" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="·��">
    <html:text property="route" styleClass="inputtext" style="width:160px" maxlength="240"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="���跽ʽ">
    <html:text property="laytype" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="Ƥ��">
    <html:text property="grosslength" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��о����">
    <html:text property="corenumber" styleClass="inputtext" style="width:160px" maxlength="4"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��Ȩ����">
    <html:text property="owner" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��������">
    <html:text property="producer" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="ʩ����λ">
    <html:text property="builder" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="���·�ʽ">
    <html:text property="cabletype" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
    </template:formTr>
    <!-- ����������ԭ���������ĳ�ѡ���add by steven gong Mar 13,2007  -->
    <template:formTr name="Ͷ������" tagID="finishT" isOdd="false" style="display:"  >
              <html:text property="finishtime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:160px" readonly="true"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��о�ͺ�">
    <html:text property="fibertype" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="Ԥ������">
    <html:text property="reservedlength" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��ע">
    <html:text property="remark" styleClass="inputtext" style="width:160px" maxlength="120"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
