<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="JavaScript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {
    //����У��
	if(document.all.opcode.value.length == 0){
		alert("�¹��벻��Ϊ��!! ");
		document.all.opcode.focus();
		return false;
	}
	if(document.all.operationdes.value.length != 0&&document.all.operationdes.value.trim().length==0){
		alert("�¹����Ʋ���Ϊ�ջ��߿ո�!! ");
        document.all.operationdes.value="";
		document.all.operationdes.focus();
		return false;
	}
    document.all.operationdes.value=document.all.operationdes.value.trim();
	document.all.operationcode.value = "A" + document.all.optype.value + document.all.opcode.value;
	//alert(PatrolOpBean.operationcode.value);

	return true;
}
 //�����Ƿ�����
    function valiD(id){
    	var mysplit = /\d\d/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("����д�Ĳ�������,����������");
             obj.focus();
             obj.value = "";
        }
    }
</script>

<template:titile value="��ѯѲ���¹�����Ϣ"/>

<html:form onsubmit="return isValidForm()" method="Post" action="/PatrolOpAction.do?method=queryPatrolOp">
  <template:formTable contentwidth="300" namewidth="200">
	<template:formTr name="�¹�����" >
      <select name="optype" class="inputtext" style="width:200">
		<option value="">����</option>
        <option value="1">��ʯ</option>
        <option value="2">�˾�</option>
		<option value="3">���</option>
		<option value="4">�����¹�</option>
      <select>
    </template:formTr>

    <template:formTr name="�¹���">
      <input type="text" name="opcode" id="id" onchange="valiD(id)" class="inputtext" style="width:200" maxlength="2"/>
		&nbsp;( ���������Ϣ )
    </template:formTr>

    <template:formTr name="�¹�����" >
		<html:hidden property="operationcode"/>
		<html:text property="operationdes" styleClass="inputtext" style="width:200" maxlength="45"/>
    </template:formTr>

		<template:formTr name="�¹ʼ���">
      <html:select property="emergencylevel" styleClass="inputtext" style="width:200">
	    <html:option value="">����</html:option>
        <html:option value="1">��΢</html:option>
        <html:option value="2">�ж�</html:option>
        <html:option value="3">����</html:option>
      </html:select>
	</template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
