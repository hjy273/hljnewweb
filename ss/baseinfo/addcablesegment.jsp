<%@include file="/common/header.jsp"%>

<script language="javascript" type="">
function isValidForm(){
	if(cablesegmentBean.segmentid.value==""){
		alert("光缆段号不能为空！");
		return false;
	}
	if(cablesegmentBean.segmentname.value==""){
		alert("光缆名不能为空！");
		return false;
	}
	if(cablesegmentBean.segmentdesc.value==""){
		alert("光缆段不能为空！");
		return false;
	}
	if(cablesegmentBean.pointa.value==""){
		alert("A点不能为空！");
		return false;
	}
	if(cablesegmentBean.pointz.value==""){
		alert("Z点不能为空！");
		return false;
	}
	if(cablesegmentBean.route.value==""){
		alert("路由不能为空！");
		return false;
	}
	if(cablesegmentBean.laytype.value==""){
		alert("敷设方式不能为空！");
		return false;
	}
	if(cablesegmentBean.grosslength.value==""){
		alert("皮长不能为空！");
		return false;
	}
	if(cablesegmentBean.corenumber.value==""){
		alert("纤芯数量不能为空！");
		return false;
	}
	if(cablesegmentBean.owner.value==""){
		alert("产权属性不能为空！");
		return false;
	}
	if(cablesegmentBean.producer.value==""){
		alert("生产厂商不能为空！");
		return false;
	}
	if(cablesegmentBean.builder.value==""){
		alert("施工单位不能为空！");
		return false;
	}
	if(cablesegmentBean.cabletype.value==""){
		alert("成缆方式不能为空！");
		return false;
	}
	if(cablesegmentBean.finishtime.value==""){
		alert("投产日期不能为空！");
		return false;
	}
	if(cablesegmentBean.fibertype.value==""){
		alert("纤芯型号不能为空！");
		return false;
	}
	if(cablesegmentBean.reservedlength.value==""){
		alert("预留长度不能为空！");
		return false;
	}
	return true;
}
</script>

<body>
<template:titile value="增加光缆信息"/>

<html:form onsubmit="return isValidForm()" styleId="cablesegmentBean" method="Post" action="/CableSegmentAction.do?method=addCableSegment">
  <template:formTable>
    <template:formTr name="光缆段号">
      <html:text property="segmentid" styleClass="inputtext" style="width:160px" maxlength="10"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="光缆名">
      <html:text property="segmentname" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="光缆段">
    <html:text property="segmentdesc" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="A点">
    <html:text property="pointa" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="Z点">
    <html:text property="pointz" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="路由">
    <html:text property="route" styleClass="inputtext" style="width:160px" maxlength="240"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="敷设方式">
    <html:text property="laytype" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="皮长">
    <html:text property="grosslength" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="纤芯数量">
    <html:text property="corenumber" styleClass="inputtext" style="width:160px" maxlength="4"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="产权属性">
    <html:text property="owner" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="生产厂家">
    <html:text property="producer" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="施工单位">
    <html:text property="builder" styleClass="inputtext" style="width:160px" maxlength="120"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="成缆方式">
    <html:text property="cabletype" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
    </template:formTr>
    <!-- 下面这行由原来的输入框改成选择框，add by steven gong Mar 13,2007  -->
    <template:formTr name="投产日期" tagID="finishT" isOdd="false" style="display:"  >
              <html:text property="finishtime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:160px" readonly="true"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="纤芯型号">
    <html:text property="fibertype" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="预留长度">
    <html:text property="reservedlength" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="备注">
    <html:text property="remark" styleClass="inputtext" style="width:160px" maxlength="120"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
