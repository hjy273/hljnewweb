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
  <template:formTable >
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
    <template:formTr name="投产日期">
    <html:text property="finishtime" styleClass="inputtext" style="width:160px" maxlength="12"/><font color="red"> *</font>
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
        <html:submit styleClass="button">提交</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
       <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</logic:equal>
<logic:notEqual value="e" name="TYPE">
<br />
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
       <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
      </td>
    </template:formSubmit>
    </html:form>

</logic:notEqual>
