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
    alert("材料名称不能为空! ");
	        myform.name.focus();
			return false;
    }
    if(myform.modelid.value==""){
    alert("该类型下没有相应的规格! ");
	        myform.modelid.focus();
			return false;
    }
	if(trim(myform.name.value)==""){
			alert("材料名称不能为空格! ");
	        myform.name.focus();
			return false;
		}
		if(myform.factory.value==""){
    alert("生产厂家不能为空! ");
	        myform.factory.focus();
			return false;
    }
	if(trim(myform.factory.value)==""){
			alert("生产厂家不能为空格!! ");
	        myform.factory.focus();
			return false;
		}
	if(length>512){
		alert("备注最多输入256个汉字或者512个字符！");
		myform.remark.focus();
		return false;
}
	return true;
}

/*根据材料ID，获取相应的类型信息*/
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
		<template:titile value="增加材料信息" />
		<html:form onsubmit="return isValidForm()" method="Post"
			action="/materialInfoAction.do?method=addPartBase">
			<template:formTable contentwidth="260" namewidth="260">
				<template:formTr name="材料名称">
					<html:text property="name" styleClass="inputtext" style="width:205"
						maxlength="50" />
				</template:formTr>
				<template:formTr name="材料类型">
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
				<template:formTr name="材料规格" id="11">
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
				
				<template:formTr name="单价">
					<html:text property="price" styleClass="inputtext"
						style="width:205" maxlength="200" />
				</template:formTr>
				
				<template:formTr name="生产厂家">
					<html:text property="factory" styleClass="inputtext"
						style="width:205" maxlength="200" />
				</template:formTr>
				<template:formTr name="备注">
					 <html:textarea property="remark" styleClass="inputtext" style="width:270;height:100;" cols="10" rows="10"/>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">增加</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">重置</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		</body>