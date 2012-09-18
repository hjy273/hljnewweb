<%@include file="/common/header.jsp"%>
<SCRIPT language=javascript src="${ctx}/js/prototype.js" type=""></SCRIPT>
<script type="text/javascript"
			src="${ctx}/js/validate.js"></script>
<script language="javascript">
function downloadFile() {
  location.href = "${ctx}/CableAction.do?method=downloadTemplet";
}

function showupload() {
    var modelid = document.getElementById("modelid");
    if(modelid.value=""){
    $("modelDiv").style.display="none";
    }
}

function noupload()
{
    var objpart = document.getElementById("groupDivId");
    var objup = document.getElementById("upDivId");

    objpart.style.display="block";
    objup.style.display="none";
}

function isValidForm() {
	var modelid = document.getElementById("modelid");
    var myform = document.forms[0];
    var length = myform.remark.value.getGBLength();
    if(myform.name.value==""){
    alert("�������Ʋ���Ϊ��! ");
	        myform.name.focus();
			return false;
    }
    if(myform.modelid.value==""){
    alert("��������û����Ӧ�Ĺ��! ");
	        myform.modelid.focus();
			return false;
    }
	if(trim(myform.name.value)==""){
			alert("�������Ʋ���Ϊ�ո�! ");
	        myform.name.focus();
			return false;
		}
		if(myform.factory.value==""){
    alert("�������Ҳ���Ϊ��! ");
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
   var mid = $("typeid").value;
    var urls = '${ctx}/MTInfoAction.do?method=queryTypeList&id='+mid+'&rnd='+Math.random();
    var pars = 'id='+mid+'&rnd='+Math.random();
    var modelid = document.getElementById("modelid");
    if(modelid.value=""){
    $("modelDiv").style.display="none";
    }
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
		<template:titile value="���Ӳ�����Ϣ" />
		<html:form onsubmit="return isValidForm()" method="Post"
			action="/materialInfoAction.do?method=addPartBase">
			<template:formTable contentwidth="260" namewidth="260">
				<template:formTr name="��������">
					<html:text property="name" styleClass="inputtext" style="width:205"
						maxlength="50" />
				</template:formTr>
				<template:formTr name="��������">
					<select name="typeid" class="inputtext" style="width:205px"
						id="typeid" onchange="changeModel()">
						<logic:present name="typelist">
							<logic:iterate id="r" name="typelist">
								<option value="<bean:write name="r" property="id" />">
									<bean:write name="r" property="typename" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formTr name="���Ϲ��" id="11">
					<div id="modelDiv" name="modelDiv">
						<select name="modelid" class="inputtext" style="width:205px"
							id="modelid">
							<logic:present name="modellist">
								<logic:iterate id="r" name="modellist">
									<option value="<bean:write name="r" property="id" />">
										<bean:write name="r" property="modelname" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
					</div>
				</template:formTr>
				
				<template:formTr name="����">
					<html:text property="price" styleClass="inputtext"
						style="width:205" maxlength="200" />
				</template:formTr>
				
				<template:formTr name="��������">
					<html:text property="factory" styleClass="inputtext"
						style="width:205" maxlength="200" />
				</template:formTr>
				<template:formTr name="��ע">
					 <html:textarea property="remark" styleClass="inputtext" style="width:270;height:100;" cols="10" rows="10"/>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">����</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">����</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		</body>