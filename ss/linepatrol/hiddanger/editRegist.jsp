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
		function init(){
			$("code").length = 0;
			var typeId = $('type').options[$('type').selectedIndex].value;
			if(typeId != ' '){
				var url = "${ctx}/hiddangerAction.do?method=getTroubleCodeSelection&typeId="+typeId;
				var myAjax = new Ajax.Request( url , {
	        	    method: 'get',
	        	    asynchronous: 'true',
	        	    onComplete: setTroubleCode }
	        	);
			}
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
			setSelected();
		}
		function setSelected(){
			var cs = $("code").options;
			for(var i = 0 ; i < cs.length ; i ++){
				if(cs[i].value == '${registBean.code}'){
					cs[i].selected = "selected";
				}
			}
		}
		function chooseMap(){
			var url = '/WEBGIS/gisextend/igis.jsp?actiontype=202&eid=position&userid=${LOGIN_USER.userID}';
			window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
		}
		function check(){
			if($('findTime').value == ''){
				alert('隐患发现时间不能为空');
				$('findTime').focus();
				return false;
			}
			var position = $('position').value;
			$('x').value = position.split(',')[0];
			$('y').value = position.split(',')[1];
			processBar(form);
			return true;
		}
	</script>
</head>
<body onload="changeStyle();init()">
	<template:titile value="隐患登记" />
	<html:form action="/hiddangerAction.do?method=editRegist" styleId="form" onsubmit="return check()">
		<html:hidden property="id" />
		<template:formTable namewidth="150" contentwidth="300">
			<template:formTr name="隐患编号">
				<html:text property="hiddangerNumber" styleClass="view" style="width:140px" readonly="true" />
			</template:formTr>
			<template:formTr name="登记单位">
				${registBean.createrDept}
			</template:formTr>
			<template:formTr name="登记人">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${registBean.creater}" />
			</template:formTr>
			<template:formTr name="登记名称">
				<html:text property="name" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="隐患位置">
				<html:hidden property="x" styleId="x" />
				<html:hidden property="y" styleId="y" />
				<input type="text" name="position" id="position" style="width:140px" class="inputtext required validate-pattern-/^\d+.\d+,\d+.\d+$/" value="${registBean.x},${registBean.y}" /><input type="button" value="选择" onclick="chooseMap()"/>
			</template:formTr>
			<template:formTr name="隐患发现时间">
				<html:text property="findTime" styleId="findTime" styleClass="Wdate" style="width:140px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1',maxDate:'%y-%M-%d'})" />
			</template:formTr>
			<template:formTr name="隐患分类">
				<apptag:setSelectOptions columnName1="typename" columnName2="id" tableName="troubletype" valueName="troubletype"/>
				<html:select property="type" styleId="type" styleClass="inputtext required" style="width:140px" onchange="init()">
					<html:option value=" ">请选择  </html:option>
			      	<html:options collection="troubletype" property="value" labelProperty="label"/>
			    </html:select>
			</template:formTr>
			<template:formTr name="隐患名称">
				<html:select property="code" styleId="code" styleClass="inputtext required" style="width:140px"></html:select>
			</template:formTr>
			<template:formTr name="发现隐患来源">
				<html:text property="reporter" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="隐患处理单位">
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${registBean.treatDepartment}" />
			</template:formTr>
			<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">提交</html:submit>
					</td>
					<td>
						<html:button property="action" styleClass="button" onclick="history.back()">返回</html:button>
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
