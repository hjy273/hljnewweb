<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="JavaScript" type="">

    function onSubmit(){
        if(window.confirm("��ȷ���Ƿ���?")){
            addForm.submit();
        }else{
            return;
        }
    }

    function onCheckboxSelect(value){

        TroubleCodeBean.setnumber.value =	value;
        if(value == 'all'){
            TroubleCodeBean.simnumber.multiple = true;
            TroubleCodeBean.simnumber.size = 5;
            obj = document.getElementById("seleID");
            for(i=0;i<obj.options.length;i++){
                obj.options[i].selected = true;
            }
        }
        if(value == 'some'){
            TroubleCodeBean.simnumber.multiple = true;
            TroubleCodeBean.simnumber.size = 5;
        }
        if(value == 'one'){
            TroubleCodeBean.simnumber.multiple = false;
            TroubleCodeBean.simnumber.size = "";
        }
    }
    function onGradClick(){
      obj = document.getElementById("gradID");
          if(obj.style.display == 'none'){
            obj.style.display = '';
            return;
        }
        if(obj.style.display == ''){
            obj.style.display = 'none';
            TroubleCodeBean.simnumber.multiple = false;
            TroubleCodeBean.simnumber.size = "";
            return;
        }
    }
function addGoBack(){
    var url="${ctx}/TroubleCodeAction.do?method=load_TroubleCode";
    window.location.replace(url);
}
</script>
<body>

<template:titile value="�´��¹���"/>
<html:form action="/TroubleCodeAction.do?method=send_TroubleCode" styleId="addForm">
  <input type="hidden" name="setnumber" id="setnumberID" value="one"/>
  <template:formTable namewidth="150" contentwidth="300">
    <template:formTr name="SIM����">
      <select id="seleID" name="simnumber" style='width:200'>
        <logic:present name="lSimNumber">
          <logic:iterate id="simId" name="lSimNumber">
            <option value="<bean:write name="simId" property="simnumber" />">
              <bean:write name="simId" property="simnumbername"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
      <input type="button" value="�߼�" class="button" onclick="onGradClick()"/>
    </template:formTr>
    <template:formTr name="�߼�ѡ��" tagID="gradID" style="display:none">
      <input type="radio" name="number" value="one" onclick="onCheckboxSelect(value)"/>
      �򵥸��豸�´��¹��� <br/>
      <input type="radio" name="number" value="some" onclick="onCheckboxSelect(value)"/>
      �򲿷��豸�´��¹���  <br/>
      <input type="radio" name="number" value="all" onclick="onCheckboxSelect(value)"/>
      �������豸�´��¹���


    </template:formTr>
    <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="onSubmit()">����</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>
