<%@include file="/common/header.jsp"%>



<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
String strRegion = userinfo.getRegionID();
%>
<script language="javascript" type="">

function isValidForm() {
	var myform = document.forms[0];
    if(myform.cableno.value==''){
       alert('光缆段编号不能为空！');
        return false;
    }
    if(myform.cablename.value==''){
       alert('光缆名称不能为空！');
        return false;
    }
    if(myform.cablelinename.value==''){
       alert('光缆段名称不能为空！');
        return false;
    }
	if(isNaN(myform.fibernumber.value)){
       alert('纤芯数量必须是数字！');
        return false;
    }
    if(isNaN(myform.cablelength.value)){
       alert('皮长必须是数字！');
        return false;
    }
     if(isNaN(myform.unusecable.value)){
       alert('预留必须是数字！');
        return false;
    }
	if(isNaN(myform.filberlength.value)){
       alert('纤芯长度必须是数字！');
        return false;
    }

	return true;
}


</script>

<body>
<logic:equal value="e" name="TYPE">
<template:titile value="修改光缆段信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/CableAction.do?method=updateCable">
  <template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="光缆段编号">
    <html:hidden property="id" />
      <html:text property="cableno"  styleClass="inputtext" style="width:160" maxlength="40"/>
    </template:formTr>
           
   <template:formTr name="代维单位" isOdd="false">
      <html:select property="contractorid" styleClass="inputtext" style="width:160">
        <html:options collection="contractorlist" property="contractorid" labelProperty="contractorname"/>
      </html:select>
    </template:formTr>
    <template:formTr name="片区">
      <html:text property="area" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
    <template:formTr name="地区">
      <html:text property="county" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="系统名称">
      <html:text property="systemname" styleClass="inputtext" style="width:160" maxlength="400"/>
    </template:formTr>
    <template:formTr name="光缆名称">
      <html:text property="cablename" styleClass="inputtext" style="width:160" maxlength="200"/>
    </template:formTr>
    <template:formTr name="光缆段名称">
      <html:text property="cablelinename" styleClass="inputtext" style="width:160" maxlength="200"/>
    </template:formTr>
  <template:formTr name="敷设方式">
      <html:text property="laytype" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="生产厂家">
      <html:text property="company" styleClass="inputtext" style="width:160" maxlength="200"/>
    </template:formTr>
     <template:formTr name="施工单位">
      <html:text property="construct" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="产权属性">
      <html:text property="property" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="成缆方式">
      <html:text property="cabletype" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
     <template:formTr name="投产日期">
      <html:text  property="createtime" styleClass="inputtext" style="width:160" maxlength="20"/>
    </template:formTr>
    <template:formTr name="纤芯型号">
      <html:text property="fibertype" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="纤芯数量">
      <html:text property="fibernumber" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
     <template:formTr name="皮长">
      <html:text property="cablelength" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
     <template:formTr name="预留">
      <html:text property="unusecable" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
    <template:formTr name="备注">
      <html:text property="remark" styleClass="inputtext" style="width:160;height:100;" maxlength="120"/>
    </template:formTr>
     <template:formTr name="是否验收">
      <html:select property="isaccept" styleClass="inputtext" style="width:160">
         <html:option value="0">否</html:option>
         <html:option value="1">是</html:option>
       </html:select>
    </template:formTr>
     <template:formTr name="图纸编号">
      <html:text property="blueprintno" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
     <template:formTr name="纤芯长度">
      <html:text property="fiberlength" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
    
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">修改</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
      </td>

    </template:formSubmit>
  </template:formTable>
 <iframe name="hiddenInfoFrame" style="display:none"></iframe>
</html:form>
</logic:equal>
<logic:notEqual value="e" name="TYPE">
<template:titile value="查看光缆段详细信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/CableAction.do?method=updateCable">
<template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="光缆段编号">
    	<bean:write name="cableBean" property="id"/>
    </template:formTr>
    <template:formTr name="代维单位">
    	<bean:write name="cableBean" property="contractorid"/>
    </template:formTr>
    <template:formTr name="片区">
    	<bean:write name="cableBean" property="area"/>
    </template:formTr>
    <template:formTr name="地区">
    	<bean:write name="cableBean" property="county"/>
    </template:formTr>
    <template:formTr name="系统名称">
    	<bean:write name="cableBean" property="systemname"/>
    </template:formTr>
    <template:formTr name="光缆名称">
    	<bean:write name="cableBean" property="cablename"/>
    </template:formTr>
    <template:formTr name="光缆段名称">
    	<bean:write name="cableBean" property="cablelinename"/>
    </template:formTr>
    <template:formTr name="A基站">
    	<bean:write name="cableBean" property="apoint"/>
    </template:formTr>
    <template:formTr name="Z基站">
    	<bean:write name="cableBean" property="zpoint"/>
    </template:formTr>
    <template:formTr name="敷设方式">
    	<bean:write name="cableBean" property="laytype"/>
    </template:formTr>
    <template:formTr name="生产厂家">
    	<bean:write name="cableBean" property="company"/>
    </template:formTr>
    <template:formTr name="施工单位">
    	<bean:write name="cableBean" property="construct"/>
    </template:formTr>
    <template:formTr name="成缆方式">
    	<bean:write name="cableBean" property="cabletype"/>
    </template:formTr>
    <template:formTr name="投产日期">
    	<bean:write name="cableBean" property="createtime"/>
    </template:formTr>
    <template:formTr name="纤芯型号">
    	<bean:write name="cableBean" property="fibertype"/>
    </template:formTr>
    <template:formTr name="纤芯数量">
    	<bean:write name="cableBean" property="fibernumber"/>
    </template:formTr>
    <template:formTr name="皮长">
    	<bean:write name="cableBean" property="cablelength"/>
    </template:formTr>
    <template:formTr name="预留">
    	<bean:write name="cableBean" property="unusecable"/>
    </template:formTr>
    <template:formTr name="图纸编号">
    	<bean:write name="cableBean" property="blueprintno"/>
    </template:formTr>
    <template:formTr name="纤芯长度">
    	<bean:write name="cableBean" property="fiberlength"/>
    </template:formTr>
    <template:formTr name="是否验收">
    	<bean:write name="cableBean" property="isaccept"/>
    </template:formTr>
    <template:formTr name="备注">
    	<bean:write name="cableBean" property="remark"/>
    </template:formTr>
    </template:formTable>
    <template:formSubmit>
      <td>
       <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
      </td>
    </template:formSubmit>
    </html:form>
    </logic:notEqual>
</body>
