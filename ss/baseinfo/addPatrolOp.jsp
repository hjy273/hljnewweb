<%@include file="/common/header.jsp"%>

<script language="JavaScript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {
    //����У��
	if(document.PatrolOpBean.opcode.value.length == 0){
		alert("�¹��벻��Ϊ��!! ");
		PatrolOpBean.opcode.focus();
		return false;
	}
	if(document.PatrolOpBean.operationdes.value.length == 0||document.PatrolOpBean.operationdes.value.trim().length==0){
		alert("�¹����Ʋ���Ϊ�ջ��߿ո�!! ");
        PatrolOpBean.operationdes.value="";
		PatrolOpBean.operationdes.focus();
		return false;
	}
    document.PatrolOpBean.operationdes.value=document.PatrolOpBean.operationdes.value.trim();
	PatrolOpBean.operationcode.value = "A" + PatrolOpBean.optype.value + PatrolOpBean.opcode.value;
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
<br>
<template:titile value="����Ѳ���¹ʴ���"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/PatrolOpAction.do?method=addPatrolOp">
  <template:formTable >

    <template:formTr name="�¹�����" isOdd="false">
      <select name="optype" class="inputtext" style="width:200px">
        <option value="1">��ʯ</option>
        <option value="2">�˾�</option>
		<option value="3">���</option>
		<option value="4">�����¹�</option>
      <select>
    </template:formTr>

    <template:formTr name="�¹���">
      <input type="text" name="opcode" id="Id" class="inputtext"  onchange="valiD(id)" style="width:200px" maxlength="2"/>
		&nbsp;( ���������Ϣ )
    </template:formTr>
    <template:formTr name="�¹�����" isOdd="false">
		<html:hidden property="operationcode"/>
		<html:text property="operationdes" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
	<template:formTr name="��Ӧ����">
		<html:text property="noticecycle" styleClass="inputtext" value="24" style="width:200px" maxlength="45"/>
        &nbsp;(��λ��Сʱ)
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
		<html:text property="handlecycle" styleClass="inputtext" value="24" style="width:200px" maxlength="45"/>
        &nbsp;(��λ��Сʱ)
    </template:formTr>
    <template:formTr name="�¹ʼ���">
      <html:select property="emergencylevel" styleClass="inputtext" style="width:200px">
        <html:option value="1">��΢</html:option>
        <html:option value="2">�ж�</html:option>
        <html:option value="3">����</html:option>
      </html:select>
	</template:formTr>

    <template:formTr name="��ע" isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:200px" maxlength="145"/>
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
