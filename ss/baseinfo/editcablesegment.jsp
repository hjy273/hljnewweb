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
function addGoBack(){
    location.href = "${ctx}/CableSegmentAction.do?method=queryCableSegment";
    return true;
}

</script>

<body>
<logic:equal value="e" name="TYPE">
<template:titile value="�༭������Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/CableSegmentAction.do?method=updateCableSegment">
  <html:hidden property="kid"/>
  <template:formTable >
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
    <template:formTr name="Ͷ������">
    <html:text property="finishtime" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
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
        <html:submit styleClass="button">�ύ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
      <td>
       <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</logic:equal>
<logic:notEqual value="e" name="TYPE">
<br />
<template:titile value="�鿴������ϸ��Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/CableSegmentAction.do?method=updateCableSegment">
<template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="���¶κ�">
    	<bean:write name="cablesegmentBean" property="segmentid"/>
    </template:formTr>
    <template:formTr name="������">
    	<bean:write name="cablesegmentBean" property="segmentname"/>
    </template:formTr>
    <template:formTr name="���¶�">
    	<bean:write name="cablesegmentBean" property="segmentdesc"/>
    </template:formTr>
    <template:formTr name="A��">
    	<bean:write name="cablesegmentBean" property="pointa"/>
    </template:formTr>
    <template:formTr name="Z��">
    	<bean:write name="cablesegmentBean" property="pointz"/>
    </template:formTr>
    <template:formTr name="·��">
    	<bean:write name="cablesegmentBean" property="route"/>
    </template:formTr>
    <template:formTr name="���跽ʽ">
    	<bean:write name="cablesegmentBean" property="laytype"/>
    </template:formTr>
    <template:formTr name="Ƥ��">
    	<bean:write name="cablesegmentBean" property="grosslength"/>
    </template:formTr>
    <template:formTr name="��о����">
    	<bean:write name="cablesegmentBean" property="corenumber"/>
    </template:formTr>
    <template:formTr name="��Ȩ����">
    	<bean:write name="cablesegmentBean" property="owner"/>
    </template:formTr>
    <template:formTr name="��������">
    	<bean:write name="cablesegmentBean" property="producer"/>
    </template:formTr>
    <template:formTr name="ʩ����λ">
    	<bean:write name="cablesegmentBean" property="builder"/>
    </template:formTr>
    <template:formTr name="���·�ʽ">
    	<bean:write name="cablesegmentBean" property="cabletype"/>
    </template:formTr>
    <template:formTr name="Ͷ������">
    	<bean:write name="cablesegmentBean" property="finishtime"/>
    </template:formTr>
    <template:formTr name="��о�ͺ�">
    	<bean:write name="cablesegmentBean" property="fibertype"/>
    </template:formTr>
    <template:formTr name="Ԥ������">
    	<bean:write name="cablesegmentBean" property="reservedlength"/>
    </template:formTr>
    <template:formTr name="��ע">
    	<bean:write name="cablesegmentBean" property="remark"/>
    </template:formTr>
    </template:formTable>
    <template:formSubmit>
      <td>
       <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
      </td>
    </template:formSubmit>
    </html:form>

</logic:notEqual>
