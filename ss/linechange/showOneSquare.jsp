<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
<script language="javascript">

 	 function valiD(form){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById("sq");
        var form = document.getElementById("addSurveyForm");
        if(!mysplit.test(obj.value)){
        	alert("����д�Ĳ�������,����������");
            obj.focus();
            obj.value = "";
            return false;
        }
        var square  = form.square.value;
        var Payment = form.payment.value;
        var thisPayment = form.squared.value;
        if(square-Payment < thisPayment ){
          if (confirm("�������ѳ�����������ȷ�����Ƿ񸶿")){
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

<template:titile value="��д���㸶��"/>

<html:form action="/pageonholeaction?method=saveSquare" styleId="addSurveyForm"   onsubmit="return valiD(this)" >
  <template:formTable namewidth="150" contentwidth="350">
    <input type="hidden" name="id" value="<bean:write name="bean" property="id"/>">
    <template:formTr name="��������">
      <bean:write name="bean" property="changename"/>
    </template:formTr>
    <template:formTr name="���̵ص�">
      <bean:write name="bean" property="changeaddr"/>
    </template:formTr>
    <template:formTr name="��������">
      <bean:write name="bean" property="changepro"/>
    </template:formTr>
    <template:formTr name="��������">
      <bean:write name="bean" property="lineclass"/>
    </template:formTr>
    <template:formTr name="Ӱ��ϵͳ">
      <bean:write name="bean" property="involvedSystem"/>
    </template:formTr>
    <template:formTr name="Ǩ�ĳ���">
      <bean:write name="bean" property="changelength"/>
    </template:formTr>
    <template:formTr name="��ʼʱ��">
      <bean:write name="bean" property="starttime"/>
    </template:formTr>
    <template:formTr name="�ƻ�����">
      <bean:write name="bean" property="plantime"/> ��
    </template:formTr>
    <template:formTr name="����Ԥ��">
      <bean:write name="bean" property="budget"/> ��Ԫ
    </template:formTr>
    <template:formTr name="�������">
      <bean:write name="bean" property="cost"/> ��Ԫ
    </template:formTr>
    <template:formTr name="������">
      <bean:write name="bean" property="square"/> ��Ԫ
      <html:hidden name="bean" property="square"/>
    </template:formTr>
    <template:formTr name="�Ѹ����">
      <bean:write name="bean" property="squared"/> ��Ԫ
      <input  type="hidden" name="payment" value="<bean:write name="bean" property="squared"/> "/>
    </template:formTr>
    <template:formTr name="���θ���">
      <html:text property="squared" styleId="sq" styleClass="inputtext" style="width:100;" maxlength="8"/> ��Ԫ
    </template:formTr>

       <template:formSubmit>
       <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">����</html:reset>
      </td>
      <td>
       <input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>
</html>
