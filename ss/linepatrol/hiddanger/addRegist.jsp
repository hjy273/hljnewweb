<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
	String.prototype.position=function(){
		return (/^\d+.\d+,\d+.\d+$/.test(this));
	}
		function getTroubleCode(typeId){
			$("code").length = 0;
			var url = "${ctx}/hiddangerAction.do?method=getTroubleCodeSelection&typeId="+typeId;
			var myAjax = new Ajax.Request( url , {
        	    method: 'get',
        	    asynchronous: 'true',
        	    onComplete: setTroubleCode }
        	);
		}
		function setTroubleCode(response){
			var str = response.responseText;
			if(str == "")
				return true;
			var optionlist = str.split("&");
			for(var i=0 ; i<optionlist.length ; i++){
				var t = optionlist[i].split(",")[0];
				var v = optionlist[i].split(",")[1];
				$("code").options[i] = new Option(v,t);
			}
		}
		function chooseMap(){
			var url = '/WEBGIS/gisextend/igis.jsp?actiontype=202&eid=position&userid=${LOGIN_USER.userID}';
			window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
		}
		function check(){
			if($('findTime').value == ''){
				alert('��������ʱ�䲻��Ϊ��');
				$('findTime').focus();
				return false;
			}
			//if(!$('position').value.position()){
			//	alert("���������������������!");
			//	$('position').focus();
			//	return false;
			//}
			var position = $('position').value;
			$('x').value = position.split(',')[0];
			$('y').value = position.split(',')[1];
//			processBar(form);
			return true;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="�����Ǽ�" />
	<html:form action="/hiddangerAction.do?method=addRegist" styleId="form" onsubmit="return check()">
		<template:formTable namewidth="150" contentwidth="300">
			<template:formTr name="�������">
				<html:text property="hiddangerNumber" styleClass="view" style="width:140px" readonly="true" />
			</template:formTr>
			<template:formTr name="�Ǽǵ�λ">
				${registBean.createrDept}
			</template:formTr>
			<template:formTr name="�Ǽ���">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${registBean.creater}" />
			</template:formTr>
			<template:formTr name="�Ǽ�����">
				<html:text property="name" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="��������λ��">
				<html:hidden property="x" styleId="x" />
				<html:hidden property="y" styleId="y" />
				<input type="text" name="position" id="position" style="width:140px" class="inputtext required validate-pattern-/^\d+.\d+,\d+.\d+$/" /><input type="button" value="ѡ��" onclick="chooseMap()"/>
			</template:formTr>
			<template:formTr name="��������ʱ��">
				<html:text property="findTime" styleId="findTime" styleClass="Wdate" style="width:140px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1',maxDate:'%y-%M-%d'})" />
			</template:formTr>
			<template:formTr name="��������">
				<apptag:setSelectOptions columnName1="typename" columnName2="id" tableName="troubletype" valueName="troubletype"/>
				<html:select property="type" styleId="type" styleClass="inputtext required" style="width:140px" onchange="getTroubleCode(this.options[this.selectedIndex].value)">
					<html:option value=" ">��ѡ��  </html:option>
			      	<html:options collection="troubletype" property="value" name="value" labelProperty="label"/>
			    </html:select>
			</template:formTr>
			<template:formTr name="��������">
				<html:select property="code" styleId="code" styleClass="inputtext required" style="width:140px"></html:select>
			</template:formTr>
			<template:formTr name="����������Դ">
				<html:text property="reporter" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="��������λ">
				<html:select property="treatDepartment" styleClass="inputtext required" style="width:140px">
			      	<html:options collection="dept" property="value" labelProperty="label"/>
			    </html:select>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">�ύ</html:submit>
				</td>
				<td>
					<html:reset property="action" styleClass="button">����</html:reset>
				</td>
			</template:formSubmit>
		</template:formTable>
		<jsp:include page="note.jsp" />
	</html:form>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</body>
