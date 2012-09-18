<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
<script language="javascript">

 	 function valiD(form){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById("sq");
        var form = document.getElementById("addSurveyForm");
        if(!mysplit.test(obj.value)){
        	alert("你填写的不是数字,请重新输入");
            obj.focus();
            obj.value = "";
            return false;
        }
        var square  = form.square.value;
        var Payment = form.payment.value;
        var thisPayment = form.squared.value;
        if(square-Payment < thisPayment ){
          if (confirm("付款金额已超出结算款金额，请确认您是否付款？")){
            return true;
          }else{
            return false;
          }
        }

    }

    function addGoBack()
    {
      var url = "${ctx}/pageonholeaction.do?method=showSquare";
      self.location.replace(url);
    }
</script>
<body>

<template:titile value="填写结算付款"/>

<html:form action="/pageonholeaction?method=saveSquare" styleId="addSurveyForm"   onsubmit="return valiD(this)" >
  <template:formTable namewidth="150" contentwidth="350">
    <input type="hidden" name="id" value="<bean:write name="bean" property="id"/>">
    <template:formTr name="工程名称">
      <bean:write name="bean" property="changename"/>
    </template:formTr>
    <template:formTr name="工程地点">
      <bean:write name="bean" property="changeaddr"/>
    </template:formTr>
    <template:formTr name="工程性质">
      <bean:write name="bean" property="changepro"/>
    </template:formTr>
    <template:formTr name="网络性质">
      <bean:write name="bean" property="lineclass"/>
    </template:formTr>
    <template:formTr name="影响系统">
      <bean:write name="bean" property="involvedSystem"/>
    </template:formTr>
    <template:formTr name="迁改长度">
      <bean:write name="bean" property="changelength"/>
    </template:formTr>
    <template:formTr name="开始时间">
      <bean:write name="bean" property="starttime"/>
    </template:formTr>
    <template:formTr name="计划工期">
      <bean:write name="bean" property="plantime"/> 天
    </template:formTr>
    <template:formTr name="工程预算">
      <bean:write name="bean" property="budget"/> 万元
    </template:formTr>
    <template:formTr name="工程造价">
      <bean:write name="bean" property="cost"/> 万元
    </template:formTr>
    <template:formTr name="结算款额">
      <bean:write name="bean" property="square"/> 万元
      <html:hidden name="bean" property="square"/>
    </template:formTr>
    <template:formTr name="已付款额">
      <bean:write name="bean" property="squared"/> 万元
      <input  type="hidden" name="payment" value="<bean:write name="bean" property="squared"/> "/>
    </template:formTr>
    <template:formTr name="本次付款">
      <html:text property="squared" styleId="sq" styleClass="inputtext" style="width:100;" maxlength="8"/> 万元
    </template:formTr>

       <template:formSubmit>
       <td>
        <html:submit styleClass="button">付款</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">重置</html:reset>
      </td>
      <td>
       <input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>
</html>
