<%@include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/prototype.js"></script>
<script type="text/javascript"
			src="${ctx}/js/validate.js"></script>
<script language="javascript">
function isValidForm() {
    var myform = document.forms[0];
    var length = myform.remark.value.getGBLength();
    var modelid = document.getElementById("modelid");
    if(myform.name.value==''){
       alert('�������Ʋ���Ϊ�գ�');
     myform.name.focus();
        return false;
    }
     if(myform.modelid.value==""){
    alert("��������û����Ӧ�Ĺ��! ");
	        myform.modelid.focus();
			return false;
    }
if(trim(myform.name.value)==""){
		alert("�������Ʋ���Ϊ�ո�!! ");
        myform.name.focus();
		return false;
	}
	 if(myform.factory.value==''){
       alert('�������Ҳ���Ϊ�գ�');
     myform.factory.focus();
        return false;
    }
if(trim(myform.factory.value)==""){
		alert("�������Ҳ���Ϊ�ո�!! ");
        myform.factory.focus();
		return false;
	}
if(length>512){
		alert("��ע�������256�����ֻ���512���ַ���");
		myform.remark.focus();
		return false;
}
	return true;
}

/*���ݲ���ID����ȡ��Ӧ��������Ϣ*/
function changeModel(){
var myform = document.forms[0];
   var mid = myform.typeid.value;
    var urls = '${ctx}/MTInfoAction.do?method=queryTypeList&id='+mid+'&rnd='+Math.random();
    var pars = 'id='+mid+'&rnd='+Math.random();
    $("modelDiv").innerHTML = "<img src='${ctx}/images/indicator.gif'>";
     var myAjax = new Ajax.Updater('modelDiv',urls,
     {
      method: 'post',
      parapmeters: pars,
      onSuccess:showRs,
      onComplete: showRs
     });
}

function showRs(rs){
}
</script>

<body>
 <div id="groupDivId">
<template:titile value="�޸Ĳ�����Ϣ"/>

<html:form onsubmit="return isValidForm()" method="Post" action="/materialInfoAction.do?method=updatePartBase">
  <template:formTable contentwidth="260" namewidth="260">
  <html:hidden property="id"/>
    <template:formTr name="��������">
      <html:text property="name" styleClass="inputtext" style="width:205" maxlength="50"/>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <html:select property="typeid" styleClass="inputtext" style="width:205" onchange="changeModel()">
        <html:options collection="typelist" property="id" labelProperty="typename"/>
      </html:select>
    </template:formTr>
   <template:formTr name="���Ϲ��" isOdd="false">
   <div id="modelDiv" name="modelDiv">
      <html:select property="modelid" styleClass="inputtext" style="width:205">
        <html:options collection="modellist" property="id" labelProperty="modelname"/>
      </html:select>
      </div>
    </template:formTr>
     <template:formTr name="����">
      <html:text property="price" styleClass="inputtext" style="width:205" maxlength="200"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="factory" styleClass="inputtext" style="width:205" maxlength="200"/>
    </template:formTr>
    <template:formTr name="��ע">
      <html:textarea property="remark" styleClass="inputtext" style="width:260;height:100;" cols="10" rows="10"/>
  </template:formTr>
       <template:formSubmit>
      <td>
        <html:submit styleClass="button" >����</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>

